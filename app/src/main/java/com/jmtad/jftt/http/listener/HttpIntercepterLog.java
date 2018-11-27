package com.jmtad.jftt.http.listener;

import com.jmtad.jftt.BuildConfig;
import com.jmtad.jftt.util.LogUtil;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: HttpIntercepterLog.java
 */

public class HttpIntercepterLog implements Interceptor {

    private static final String TAG = "HttpIntercepterLog";

    private static final Charset UTF8 = Charset.forName("UTF-8");

    private static boolean isPrintUserInfo = true;

    /**
     * 重定向
     */
    private static final int ERROR_301 = 0x012D;

    private static final int ERROR_302 = 0x012E;

    private static final int ERROR_201 = 0x00C9;

    public static final String HTTPTIME = "HttpTimeStatistics";

    public enum Level {
        /**
         * No logs.
         */
        NONE,
        /**
         * Logs request and response lines.
         * <p>
         * <p>Example:
         * <pre>{@code
         * --> POST /greeting http/1.1 (3-byte body)
         *
         * <-- 200 OK (22ms, 6-byte body)
         * }</pre>
         */
        BASIC,
        /**
         * Logs request and response lines and their respective headers.
         * <p>
         * <p>Example:
         * <pre>{@code
         * --> POST /greeting http/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         * --> END POST
         *
         * <-- 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         * <-- END HTTP
         * }</pre>
         */
        HEADERS,
        /**
         * Logs request and response lines and their respective headers and bodies (if present).
         * <p>
         * <p>Example:
         * <pre>{@code
         * --> POST /greeting http/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         *
         * Hi?
         * --> END POST
         *
         * <-- 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         *
         * Hello!
         * <-- END HTTP
         * }</pre>
         */
        BODY
    }

    public interface Logger {
        void log(String message);

        void jsonLog(String message);

        void xmlLog(String message);

        /**
         * A {@link Logger} defaults output appropriate for the current platform.
         */
        Logger DEFAULT = new Logger() {
            @Override
            public void log(String message) {
                if (!isPrintUserInfo) return;
                LogUtil.debug(message);
            }

            @Override
            public void jsonLog(String message) {
                if (!isPrintUserInfo) return;
            }

            @Override
            public void xmlLog(String message) {
                if (!isPrintUserInfo) return;
            }
        };
    }

    public HttpIntercepterLog() {
        this(Logger.DEFAULT);
        if (BuildConfig.DEBUG) {
            level = Level.BODY;
            isPrintUserInfo = true;
        } else {
            level = Level.NONE;
            isPrintUserInfo = false;
        }
    }

    public HttpIntercepterLog(Logger logger) {
        this.logger = logger;
    }

    private final Logger logger;

    private volatile Level level = Level.BODY;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        String requestUrl = "";

        HttpUrl url = request.url();


        if (url != null) {
            requestUrl = url.toString();
        }

        //获取接口名称
        String interfaceName = requestUrl;
        int index = requestUrl.indexOf("V3");
        if (index > 0 && index < (requestUrl.length() - 3)) {
            interfaceName = requestUrl.substring(index + 3);
        }

        if (level == Level.NONE) {
            long startNs = System.nanoTime();
            Response response = null;
            try {
                response = chain.proceed(request);
                int httpCode = 0;
                if (null != response) {
                    httpCode = response.code();
                }
                long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
                StringBuffer sb = new StringBuffer(HttpIntercepterLog.HTTPTIME)
                        .append("|")
                        .append(tookMs)
                        .append("ms|")
                        .append(interfaceName)
                        .append("|")
                        .append(httpCode);
                LogUtil.debug(sb.toString());
                //SuperLog.info2SD(TAG, HttpIntercepterLog.HTTPTIME + " [release][V6]|time:" + tookMs + "ms" + "|" + "interfaceName:" + interfaceName + "|" + httpCode + "|");
            } catch (Exception e) {
                LogUtil.error(interfaceName + " exception : " + e);
            }
            return response;
        }

        boolean logBody = level == Level.BODY;
        boolean logHeaders = logBody || level == Level.HEADERS;

        RequestBody requestBody = request.body();
        boolean hasRequestBody = requestBody != null;

        Connection connection = chain.connection();
        Protocol protocol = connection != null ? connection.protocol() : Protocol.HTTP_1_1;
        String requestStartMessage = "Request Body:--> " + request.method() + ' ' + request.url() + ' ' + protocol;
        if (!logHeaders && hasRequestBody) {
            requestStartMessage += " (" + requestBody.contentLength() + "-byte body)";
        }
        logger.log(requestStartMessage);

        if (logHeaders) {
            if (hasRequestBody) {
                // Request body headers are only present when installed as a network interceptor. Force
                // them to be included (when available) so there values are known.
                if (requestBody.contentType() != null) {
                    logger.log("Content-Type: " + requestBody.contentType());
                }
                if (requestBody.contentLength() != -1) {
                    logger.log("Content-Length: " + requestBody.contentLength());
                }
            }

            Headers headers = request.headers();
            for (int i = 0, count = headers.size(); i < count; i++) {
                String name = headers.name(i);
                // Skip headers from the request body as they are explicitly logged above.
                if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
                    logger.log(name + ": " + headers.value(i));
                }
            }

            if (!logBody || !hasRequestBody) {
                logger.log("--> END " + request.method());
            } else if (bodyEncoded(request.headers())) {
                logger.log("--> END " + request.method() + " (encoded body omitted)");
            } else {
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);

                Charset charset = UTF8;
                MediaType contentType = requestBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }

                logger.log("");
                if (isPlaintext(buffer)) {
                    logger.log(buffer.readString(charset));
                    logger.log("--> END " + request.method()
                            + " (" + requestBody.contentLength() + "-byte body)");
                } else {
                    logger.log("--> END " + request.method() + " (binary "
                            + requestBody.contentLength() + "-byte body omitted)");
                }
            }
        }

        long startNs = System.nanoTime();
        Response response = null;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            LogUtil.error(interfaceName + " exception: " + e);
        }

        if (null == response) {
            LogUtil.error(interfaceName + " response is null.");
            return null;
        }

        logger.log("Response Code = " + response.code());

        //************打印返回 header************

        Headers headers = response.headers();
        if (headers != null) {
            for (int i = 0, count = headers.size(); i < count; i++) {
                String name = headers.name(i);
                // Skip headers from the request body as they are explicitly logged above.
                if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
                    logger.log("Response hearders --> " + name + ": " + headers.value(i));
                }
            }
        }

        ///**
        // * 获取VSP请求中的Cookie值
        // */
        //if (!TextUtils.isEmpty(response.header("Set-Cookie"))
        //    && url != null
        //    && url.toString() != null
        //    && url.toString().contains("VSP")) {
        //
        //    String cookie = response.header("Set-Cookie");
        //
        //    logger.log("[Set Cookie] = " + cookie);
        //
        //    if(cookie.contains("JSESSIONID=")){
        //
        //        String cookies[];
        //
        //        /**
        //         *"JSESSIONID=0493B4B2FB1204C53F92785895C1EDA7; Path=/VSP/; HttpOnly"
        //         */
        //        if (cookie.contains(";")) {
        //            cookies = cookie.split("[;]");
        //            cookie = cookies[0];
        //            logger.log("[Split] JSESSIONID = " + cookie);
        //        }
        //
        //        //SessionService.getInstance().getSession().setSessionId(cookie);
        //        //SessionService.getInstance().commitSession();
        //    }
        //
        //    if(cookie.contains("CSRFSESSION=")){
        //
        //        String cookies[];
        //
        //        /**
        //         *"CSRFSESSION=1684c8d516bfe83f467398f818c71ea56edace08fe2550ac664ef6b960de27bd"
        //         */
        //        if (cookie.contains(";")) {
        //            cookies = cookie.split("[;]");
        //            cookie = cookies[0];
        //            logger.log("[Split] CSRFSESSION = " + cookie);
        //        }
        //
        //        //SessionService.getInstance().getSession().setCookie(cookie);
        //        //SessionService.getInstance().commitSession();
        //    }
        //}


        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();
        String bodySize = contentLength != -1 ? contentLength + "-byte" : "unknown-length";
        logger.log("Response Body:<-- " + response.code() + ' ' + response.message() + ' '
                + response.request().url() + " (" + tookMs + "ms" + (!logHeaders ? ", "
                + bodySize + " body" : "") + ')');
        LogUtil.debug(HttpIntercepterLog.HTTPTIME + " [debug]|time:" + tookMs + "ms" + "|" + "interfaceName:" + interfaceName + "|" + response.code());

        if (logHeaders) {
            if (!logBody || !HttpHeaders.hasBody(response)) {
                logger.log("<-- END HTTP");
            } else if (bodyEncoded(response.headers())) {
                logger.log("<-- END HTTP (encoded body omitted)");
            } else {
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                Buffer buffer = source.buffer();

                /**
                 * 防止没有数据时，json解析失败，抛出异常java.io.EOFException
                 */
                if (response.code() == ERROR_201 && buffer.size() == 0) {
                    buffer.writeUtf8("{ \"message\":\"code = 201\"}");
                }

                Charset charset = UTF8;
                MediaType contentType = responseBody.contentType();
                if (contentType != null) {
                    try {
                        charset = contentType.charset(UTF8);
                    } catch (UnsupportedCharsetException e) {
                        logger.log("");
                        logger.log("Couldn't decode the response body; charset is likely malformed.");
                        logger.log("<-- END HTTP");

                        return response;
                    }
                }

                if (!isPlaintext(buffer)) {
                    logger.log("");
                    logger.log("<-- END HTTP (binary " + buffer.size() + "-byte body omitted)");
                    return response;
                }

                if (contentLength != 0) {
                    logger.log("");
                    String body = buffer.clone().readString(charset);

                    logger.log("Before Format:" + body);

                    if (body != null) {
                        if (body.contains("<?xml") || body.contains("<html>") && body.length() < 4000) {
                            logger.log("<---------------------------------XML Format------------------------------>");
                            logger.xmlLog(body);
                        } else {
                            logger.log("<---------------------------------JSON Format------------------------------>");
                            logger.jsonLog(body);
                        }
                    }

                    /*
                    try {

                        StringReader reader = new StringReader(body);
                        Properties properties = new Properties();
                        properties.load(reader);

                        logger.log("After Format:"+properties.toString());

                    } catch (IOException e) {
                        e.printStackTrace();
                        SuperLog.error(TAG,"HttpIntercepterLog intercept error");
                    }*/


                }

                logger.log("<-- END HTTP (" + buffer.size() + "-byte body)");
            }
        }

        return response;
    }

    /**
     * Returns true if the body in question probably contains human readable text. Uses a small sample
     * of code points to detect unicode control characters commonly used in binary file signatures.
     */
    static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }


}