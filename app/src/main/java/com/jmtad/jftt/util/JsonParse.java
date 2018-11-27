package com.jmtad.jftt.util;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: JsonParse.java
 * @author: yh
 * @date: 2016-10-31 10:10
 */

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: JsonParse.java
 */

public class JsonParse {

    private static final String TAG = "JsonParse";

    private static Gson gson;


    static {
        gson = new GsonBuilder().create();
    }

    /**
     * @param json
     * @return
     */
    public static String object2String(final Object json) {
        try {
            String string = gson.toJson(json);
            return string;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param json
     * @param classOfT
     * @param <T>
     * @return
     */
    public static <T> T json2Object(String json, Class<T> classOfT) {
        try {
            return gson.fromJson(json, classOfT);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T json2Object(String json, Type type) {
        try {
            return gson.fromJson(json, type);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * object2String
     *
     * @param json
     * @return
     */
    public static String object2String(final Object json, Type type) {
        try {
            String string = gson.toJson(json, type);
            return string;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * List<Object>换json
     *
     * @param list
     * @param elementClasses
     * @return
     */
    public static String classListToJson(List list, Class<?> elementClasses) {
        try {
            return gson.toJson(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * json转 List<Object>
     *
     * @param json
     * @param cls
     * @return
     */
    public static <T> List<T> jsonToClassList(String json, Class<T> cls) {
        if (!TextUtils.isEmpty(json)) {
            try {
                //SuperLog.debug(TAG,"JSON:\n"+json);
                JsonArray array = new JsonParser().parse(json).getAsJsonArray();
                if (null != array && array.size() > 0) {
                    List<T> list = new ArrayList<>();
                    for (final JsonElement elem : array) {
                        list.add(gson.fromJson(elem, cls));
                    }
                    return list;
                }
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * json转List<String>
     *
     * @param json
     * @return
     */
    public static List<String> jsonToStringList(String json) {
        Gson gson = new Gson();
        List<String> strList = gson.fromJson(json, new TypeToken<List<String>>() {
        }.getType());
        return strList;
    }

    /**
     * List换json
     *
     * @param list
     * @return
     */
    public static String listToJsonString(List list) {
        return gson.toJson(list);
    }

    /**
     * objectList转json StringList
     *
     * @param objectList
     * @param <T>
     * @return
     */
    public static <T> ArrayList<String> jsonListToStringList(ArrayList<T> objectList) {
        ArrayList<String> jsonList = new ArrayList<String>();
        for (int i = 0; i < objectList.size(); i++) {
            jsonList.add(gson.toJson(objectList.get(i)));
        }
        return jsonList;
    }

    /**
     * json StringList转objectList
     *
     * @param jsonList
     * @param <T>
     * @return
     */
    public static <T> ArrayList<T> jsonListToObjectList(ArrayList<String> jsonList, Class<T> cls) {
        ArrayList<T> objectList = new ArrayList<T>();
        for (int i = 0; i < jsonList.size(); i++) {
            objectList.add(gson.fromJson(jsonList.get(i), cls));
        }
        return objectList;
    }

    public static <T> Map<String, T> jsonToMap(String jsonStr) {
        Gson gson = new Gson();
        Map<String, T> map = gson.fromJson(jsonStr, new TypeToken<Map<String, T>>() {
        }.getType());

        return map;
    }

}