package com.jmtad.jftt.util;

import android.text.TextUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: AESUtils.java
 */

public class AESUtils {

    private static final String TAG = "AESUtils";

    private static final String IV_STRING = "16-Bytes--String";

    /**
     * AES128 + BASE64
     *
     * @param content
     * @param key
     * @return
     */
    public static String encryptAES(String content, String key) {
        if (TextUtils.isEmpty(content)) {
            return content;
        }
        try {
            byte[] byteContent = content.getBytes("UTF-8");
            // 注意，为了能与 iOS 统一
            // 这里的 key 不可以使用 KeyGenerator、SecureRandom、SecretKey 生成
            byte[] enCodeFormat = key.getBytes();
            SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
            byte[] initParam = IV_STRING.getBytes();
            IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);
            // 指定加密的算法、工作模式和填充方式
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] encryptedBytes = cipher.doFinal(byteContent);
            // 同样对加密后数据进行 base64 编码
            String enCode = BASE64Util.encode(encryptedBytes);

            return enCode;
        } catch (Exception e) {

        }
        return content;
    }


    /**
     * AES128 + BASE64
     *
     * @param content
     * @param key
     * @return
     */
    public static String decryptAES(String content, String key) {

        if (TextUtils.isEmpty(content)) {
            return content;
        }

        try {
            // base64 解码

            byte[] encryptedBytes = BASE64Util.decode(content);
            byte[] enCodeFormat = key.getBytes();
            SecretKeySpec secretKey = new SecretKeySpec(enCodeFormat, "AES");
            byte[] initParam = IV_STRING.getBytes();
            IvParameterSpec ivParameterSpec = new IvParameterSpec(initParam);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
            byte[] result = cipher.doFinal(encryptedBytes);
            return new String(result, "UTF-8");
        } catch (Exception e) {

        }

        return content;
    }
}
