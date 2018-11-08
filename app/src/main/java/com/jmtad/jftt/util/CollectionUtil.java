package com.jmtad.jftt.util;

import java.util.Collection;

/**
 * CollectionUtil
 */
public final class CollectionUtil {
    public static boolean isEmpty(Collection collection) {
        return null == collection || collection.isEmpty();
    }

    /**
     * check whether the data in the array are all zeros
     */
    public static boolean isArrayAllZeros(byte[] arr, int size) {
        boolean result = true;
        int len = arr.length;
        if (len > size) {
            len = size;
        }
        for (int i = 0; i < len; i++) {
            if (arr[i] != 0) {
                result = false;
                break;
            }
        }
        return result;
    }
}