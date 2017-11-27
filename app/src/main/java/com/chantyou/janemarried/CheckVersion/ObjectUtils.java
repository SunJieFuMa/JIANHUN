package com.chantyou.janemarried.CheckVersion;

/**
 * Created by Administrator on 2016/7/24.
 */

public class ObjectUtils {
    public ObjectUtils() {
    }

    public static boolean isEquals(Object actual, Object expected) {
        if(actual != expected) {
            if(actual == null) {
                if(expected != null) {
                    return false;
                }
            } else if(!actual.equals(expected)) {
                return false;
            }
        }

        return true;
    }

    public static Long[] transformLongArray(long[] source) {
        Long[] destin = new Long[source.length];

        for(int i = 0; i < source.length; ++i) {
            destin[i] = Long.valueOf(source[i]);
        }

        return destin;
    }

    public static long[] transformLongArray(Long[] source) {
        long[] destin = new long[source.length];

        for(int i = 0; i < source.length; ++i) {
            destin[i] = source[i].longValue();
        }

        return destin;
    }

    public static Integer[] transformIntArray(int[] source) {
        Integer[] destin = new Integer[source.length];

        for(int i = 0; i < source.length; ++i) {
            destin[i] = Integer.valueOf(source[i]);
        }

        return destin;
    }

    public static int[] transformIntArray(Integer[] source) {
        int[] destin = new int[source.length];

        for(int i = 0; i < source.length; ++i) {
            destin[i] = source[i].intValue();
        }

        return destin;
    }

    public static <V> int compare(V v1, V v2) {
        return v1 == null?(v2 == null?0:-1):(v2 == null?1:((Comparable)v1).compareTo(v2));
    }
}
