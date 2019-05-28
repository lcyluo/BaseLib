package com.lcy.base.core.utils;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.FloatRange;
import android.view.WindowManager;
import com.lcy.base.core.common.BaseApplication;

/**
 * android设备工具类
 * <p/>
 * Created by lcy on 15/11/13.
 * email:lcyzxin@gmail.com
 * version 1.0
 */
public class DeviceUtil {


    public static String getMetaApiHost() {
        String apiHost = "";
        try {
            PackageManager packageManager = BaseApplication.instance.getPackageManager();
            ApplicationInfo appInfo = packageManager.getApplicationInfo(BaseApplication.instance.getPackageName(), PackageManager.GET_META_DATA);
            apiHost = appInfo.metaData.getString("API_HOST");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return apiHost;
    }

    public static String getMetaApiFile() {
        String apiHost = "";
        try {
            PackageManager packageManager = BaseApplication.instance.getPackageManager();
            ApplicationInfo appInfo = packageManager.getApplicationInfo(BaseApplication.instance.getPackageName(), PackageManager.GET_META_DATA);
            apiHost = appInfo.metaData.getString("API_FILE");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return apiHost;
    }

    /**
     * 获取版本号
     */
    public static String getVersionName() {
        String versionName = "";
        try {
            PackageManager packageManager = BaseApplication.instance.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(BaseApplication.instance.getPackageName(), 0);
            versionName = packInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 返回版本号
     * 对应build.gradle中的versionCode
     */
    public static String getVersionCode() {
        String versionCode = "";
        try {
            PackageManager packageManager = BaseApplication.instance.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(BaseApplication.instance.getPackageName(), 0);
            versionCode = String.valueOf(packInfo.versionCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    public static int getVersionCodeInt() {
        int versionCode = -1;
        try {
            PackageManager packageManager = BaseApplication.instance.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(BaseApplication.instance.getPackageName(), 0);
            versionCode = packInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取手机品牌
     */
    public static String getPhoneBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取手机型号
     */
    public static String getPhoneModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机Android API等级（22、23 ...）
     */
    public static int getBuildLevel() {
        return android.os.Build.VERSION.SDK_INT;
    }

    /**
     * 获取手机Android 版本（4.4、5.0、5.1 ...）
     */
    public static String getBuildVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 设置窗体透明度
     *
     * @param bgAlpha 范围0~1
     */
    public static void setBackgroundAlpha(Activity activity, @FloatRange(from = 0.0, to = 1.0) float bgAlpha) {
        if (activity == null) return;
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        //   设置透明度（这是窗体本身的透明度，非背景）
        lp.alpha = bgAlpha;
        //  设置灰度
        lp.dimAmount = bgAlpha;
        if (bgAlpha == 1) {
            //  不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        } else {
            //  此行代码主要是解决在华为 红米米手机上半透明效果无效的bug
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        activity.getWindow().setAttributes(lp);
    }

}
