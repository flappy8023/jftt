package com.jmtad.jftt.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.jmtad.jftt.App;

/**
 * @description:
 * @author: luweiming
 * @create: 2018-10-16 13:47
 **/
public class SharedPreferenceUtil {
    /*秘钥*/
    private static final String SEED = "abcdef";

    public enum Key {
        USER_ID,
        HEAD_IMG_URL,
        OPEN_ID,
        UNION_ID,
        NICK_NAME,
        PHONE,
        APK_URL,
        //声音开关
        SOUND_SWITCH
    }

    /**
     * SharedPreference存储文件名称
     */
    private static final String PREFERENCE_FILE_NAME = "sp_jftt";
    private static SharedPreferenceUtil instance;
    //存储用户声音
    private final SharedPreferences sharedPreferences = App.getContext().getSharedPreferences(PREFERENCE_FILE_NAME, Context
            .MODE_PRIVATE);

    private SharedPreferenceUtil() {
    }

    public static SharedPreferenceUtil getInstance() {
        if (null == instance) {
            instance = new SharedPreferenceUtil();
        }
        return instance;
    }

    public void saveApkUrl(String url) {
        setStringData(Key.APK_URL + "", url);
    }

    public String getApkUrl() {
        return getStringData(Key.APK_URL + "", "");
    }

    public void saveUserId(String userId) {
        setStringData(Key.USER_ID + "", userId);
    }

    public String getUserId() {
        return getStringData(Key.USER_ID + "", "");
    }

    public void saveUionId(String unionId) {
        setStringData(Key.UNION_ID + "", unionId);
    }

    public String getUnionId() {
        return getStringData(Key.UNION_ID + "", "");
    }

    public void savePhone(String phone) {
        setStringData(Key.PHONE + "", phone);
    }

    public String getPhone() {
        return getStringData(Key.PHONE + "", "");
    }

    public void saveSoundSwitch(boolean soundSwitch) {
        setBoolData(Key.SOUND_SWITCH + "", soundSwitch);
    }

    public boolean getSoundSwitch() {
        return getBoolData(Key.SOUND_SWITCH + "", true);
    }

    public String getStringData(String key, String defaultValue) {
        String content = getInstance().sharedPreferences.getString(key, null);
        return TextUtils.isEmpty(content) ? defaultValue : AESUtils.decryptAES(content, SEED);
    }

    public int getIntData(String key, int defaultValue) {
        return getInstance().sharedPreferences.getInt(key, defaultValue);
    }

    public boolean getBoolData(String key, boolean defaultValue) {
        return getInstance().sharedPreferences.getBoolean(key, defaultValue);
    }

    public void setStringData(String key, String value) {
        SharedPreferences.Editor editor = getInstance().sharedPreferences.edit();
        editor.putString(key, AESUtils.encryptAES(value, SEED));
        editor.commit();
    }

    public void setIntData(String key, int value) {
        SharedPreferences.Editor editor = getInstance().sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void setBoolData(String key, boolean value) {
        SharedPreferences.Editor editor = getInstance().sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void remove(String key) {
        SharedPreferences.Editor editor = getInstance().sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }

    public void putObject(String key, Object value) {
        Gson gson = new Gson();
        String objJson = gson.toJson(value);
        sharedPreferences.edit().putString(key, AESUtils.encryptAES(objJson, SEED)).apply();
    }

    public void putString(String key, String value) {
        sharedPreferences.edit().putString(key, AESUtils.encryptAES(value, SEED)).apply();
    }

    public void putBoolean(String key, boolean value) {
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public <T> T getObject(String key, Class<T> tClass) {
        Gson gson = new Gson();
        String json = sharedPreferences.getString(key, "");
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        return gson.fromJson(AESUtils.decryptAES(json, SEED), tClass);
    }
}
