package com.sleep.aopa

import android.Manifest
import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sleep.opengl_analysis.AirHockeyActivity
import com.sleep.opengl_analysis.FirstOpenGlActivity
import com.sleep.retrofit_analysis.RetrofitAnalysisActivity
import com.sleep.view_analysis.ScrollerActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.system.exitProcess

/**
 * author：xingkong on 2019-06-02
 * e-mail：xingkong@changjinglu.net
 *
 */
class MainActivity : AppCompatActivity() {
    private val permissions = arrayOf(
            Manifest.permission.CAMERA, Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.INTERNET
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, 999)
        }
        rcv_entrance.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rcv_entrance.adapter = MainAdapter(fetchAllActivities())
        StrictMode.setVmPolicy(StrictMode.VmPolicy
                .Builder()
                .detectLeakedClosableObjects()
                .detectActivityLeaks()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .build())
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
                .detectCustomSlowCalls()
                .build())
    }

    private fun fetchAllActivities(): MutableList<Class<out Activity>> {
        val activities = mutableListOf<Class<out Activity>>()

        activities.add(RetrofitAnalysisActivity::class.java)
        activities.add(ScrollerActivity::class.java)
        activities.add(FirstOpenGlActivity::class.java)
        activities.add(AirHockeyActivity::class.java)

        return activities
    }

    override fun onBackPressed() {
        super.onBackPressed()
        exitProcess(0)
    }

    override fun onResume() {
        super.onResume()
        Log.i("MainActivity","onResume invoke")
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        Log.i("MainActivity","onWindowFocusChanged invoke")
        reportFullyDrawn()
    }
}