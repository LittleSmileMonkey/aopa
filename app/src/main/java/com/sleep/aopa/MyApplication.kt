package com.sleep.aopa

import android.app.Application
import com.squareup.leakcanary.LeakCanary

/**
 * author：xingkong on 2020-05-28
 * e-mail：xingkong@changjinglu.net
 *
 */
class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        if(!LeakCanary.isInAnalyzerProcess(this)){
            LeakCanary.install(this)
        }
    }
}