package com.sleep.opengl_analysis.util;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.os.Build;

import com.blankj.utilcode.util.Utils;

/**
 * author：xingkong on 2020-02-09
 * e-mail：xingkong@changjinglu.net
 */
public class OpenGlUtil {
    public static boolean judgeOpenGlVersion(int reqVersion) {
        final Application app = Utils.getApp();
        final ConfigurationInfo deviceConfigurationInfo = ((ActivityManager) app.getSystemService(Context.ACTIVITY_SERVICE)).getDeviceConfigurationInfo();
        if (deviceConfigurationInfo != null) {
            return deviceConfigurationInfo.reqGlEsVersion >= reqVersion ||
                    (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1
                            && (Build.FINGERPRINT.startsWith("generic")
                            || Build.FINGERPRINT.startsWith("unknown")
                            || Build.MODEL.contains("google_sdk")
                            || Build.MODEL.contains("Emulator")
                            || Build.MODEL.contains("Android SDK build for x86")));
        }
        return false;
    }
}
