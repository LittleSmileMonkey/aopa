package com.sleep.common;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

/**
 * author：xingkong on 2019-05-21
 * e-mail：xingkong@changjinglu.net
 */
public class CommonApplication extends Application {
    @Override
    public void onCreate() {
        Utils.init(this);
        super.onCreate();
    }
}
