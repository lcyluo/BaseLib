package com.lcy.base.core.utils;

import android.util.Log;
import com.lcy.base.core.BuildConfig;


/**
 * Log工具类
 * <p/>
 * 封装系统Log工具类，便于统一Log的输出和关闭
 *
 * @author lcy
 */

public class LogUtil {

    private static final boolean DEBUG = BuildConfig.DEBUG;

    private static final String APP_NAME = "QZ_BUILDING";

    public static int v(String msg) {
        return v("", msg);
    }

    public static int d(String msg) {
        return d("", msg);
    }

    public static int i(String msg) {
        return i("", msg);
    }

    public static int w(String msg) {
        return w("", msg);
    }

    public static int e(String msg) {
        return e("", msg);
    }

    public static int v(String tag, String msg) {
        return DEBUG ? Log.v(APP_NAME, getTracePrefix("v") + msg) : 0;
    }

    public static int d(String tag, String msg) {
        return DEBUG ? Log.d(APP_NAME, getTracePrefix("d") + tag + " " + msg)
                : 0;
    }

    public static int i(String tag, String msg) {
        return DEBUG ? Log.i(APP_NAME, getTracePrefix("i") + tag + " " + msg)
                : 0;
    }

    public static int w(String tag, String msg) {
        return DEBUG ? Log.w(APP_NAME, getTracePrefix("w") + tag + " " + msg)
                : 0;
    }

    public static int e(String tag, String msg) {
        return DEBUG ? Log.e(APP_NAME, getTracePrefix("e") + tag + " " + msg)
                : 0;
    }

    public static int v(String tag, String msg, Throwable tr) {
        return DEBUG ? Log.v(APP_NAME, tag + ": " + msg, tr) : 0;
    }

    public static int d(String tag, String msg, Throwable tr) {
        return DEBUG ? Log.d(APP_NAME, tag + ": " + msg, tr) : 0;
    }

    public static int i(String tag, String msg, Throwable tr) {
        return DEBUG ? Log.i(APP_NAME, tag + ": " + msg, tr) : 0;
    }

    public static int w(String tag, String msg, Throwable tr) {
        return DEBUG ? Log.w(APP_NAME, tag + ": " + msg, tr) : 0;
    }

    public static int e(String tag, String msg, Throwable tr) {
        return DEBUG ? Log.e(APP_NAME, tag + ": " + msg, tr) : 0;
    }

    private static String getTracePrefix(String logLevel) {
        StackTraceElement[] sts = new Throwable().getStackTrace();
        StackTraceElement st = null;
        for (int i = 0; i < sts.length; i++) {
            if (sts[i].getMethodName().equalsIgnoreCase(logLevel)
                    && i + 2 < sts.length) {

                if (sts[i + 1].getMethodName().equalsIgnoreCase(logLevel)) {
                    st = sts[i + 2];
                    break;
                } else {
                    st = sts[i + 1];
                    break;
                }
            }
        }
        if (st == null) {
            return "";
        }

        String clsName = st.getClassName();
        if (clsName.contains("$")) {
            clsName = clsName.substring(clsName.lastIndexOf(".") + 1,
                    clsName.indexOf("$"));
        } else {
            clsName = clsName.substring(clsName.lastIndexOf(".") + 1);
        }
        return clsName + "-> " + st.getMethodName() + "():";
    }

    public static void show(String str) {
        str = str.trim();
        int index = 0;
        int maxLength = 4000;
        String sub = "";
        while (index < str.length()) {
            // java的字符不允许指定超过总的长度end
            if (str.length() <= index + maxLength) {
                sub = str.substring(index);
            } else {
                sub = str.substring(index, index + maxLength);
            }
            index += maxLength;
        }
        LogUtil.d(sub.trim());
    }
}
