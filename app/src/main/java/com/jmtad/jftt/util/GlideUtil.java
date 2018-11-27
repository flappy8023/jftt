package com.jmtad.jftt.util;

import android.content.Context;
import android.widget.ImageView;

import com.jmtad.jftt.config.GlideApp;


/**
 * @description:
 * @author: luweiming
 * @create: 2018-09-30 17:25
 **/
public class GlideUtil {
    public static void loadImage(Context context, String url, ImageView imageView) {
        GlideApp.with(context).load(url).into(imageView);
    }

    public static void loadImage(Context context, String url, ImageView imageView, int placeholder) {
        GlideApp.with(imageView).load(url).placeholder(placeholder).error(placeholder).into(imageView);
    }

    public static void loadImage(Context context, String url, ImageView imageView, int placeholder, int error) {
        GlideApp.with(context).load(url).placeholder(placeholder).error(error).into(imageView);
    }

    public static void loadLocalImage(Context context, int res, ImageView imageView) {
        GlideApp.with(context).load(res).into(imageView);
    }

    public static void loadLocalImage(Context context, int res, ImageView imageView, int placeholder) {
        GlideApp.with(imageView).load(res).placeholder(placeholder).error(placeholder).into(imageView);
    }

    public static void loadLocalImage(Context context, int res, ImageView imageView, int placeholder, int error) {
        GlideApp.with(context).load(res).placeholder(placeholder).error(error).into(imageView);
    }
}
