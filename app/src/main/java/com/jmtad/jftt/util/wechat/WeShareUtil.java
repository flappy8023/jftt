package com.jmtad.jftt.util.wechat;

import android.graphics.Bitmap;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;

import java.io.ByteArrayOutputStream;

/**
 * @description:
 * @author: luweiming
 * @create: 2018-11-02 09:39
 **/
public class WeShareUtil {
    private static final int THUMB_SIZE = 150;

    public static void shareUrl(String title, String des, String url, Bitmap bmp, int scene) {
        WXWebpageObject webpageObject = new WXWebpageObject();
        webpageObject.webpageUrl = url;
        WXMediaMessage mediaMessage = new WXMediaMessage(webpageObject);
        mediaMessage.title = title;
        mediaMessage.description = des;
        if (null != bmp) {
            Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
            bmp.recycle();
            mediaMessage.thumbData = bmpToByteArray(thumbBmp, true);
        }
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = mediaMessage;
        req.scene = scene;
        WechatUtil.getInstance().getApi().sendReq(req);
    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private static String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}
