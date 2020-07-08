package com.sleep.opengl_analysis

import android.os.Bundle
import android.os.FileObserver
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_first_open_gl.*
import java.io.File
import kotlin.concurrent.thread

class FirstOpenGlActivity : AppCompatActivity() {

    private var isSupportGl20 = false
    private val mRender = FirstOpenGlRender()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_open_gl)
//        val supportGl20 = OpenGlUtil.judgeOpenGlVersion(0x20000)
//        if (supportGl20){
//            isSupportGl20 = true
//            gsv.setEGLContextClientVersion(2)
//            gsv.setRenderer(mRender)
//        }else{
//            Toast.makeText(this,"not support gl20",Toast.LENGTH_SHORT).show()
//            finish()
//        }
        bt_start.setOnClickListener {
            Thread.sleep(10 * 1000)
        }

        bt_stop.setOnClickListener {
            my_surface_view.stopDraw()
        }
        thread {
            val file = File("/data/anr/traces.txt")
            if (!file.exists()) {
                file.createNewFile()
            }
            val fo = object : FileObserver(file.absolutePath) {
                override fun onEvent(event: Int, path: String?) {
                    Log.i("FileObserver", "onEvent = $event, path = $path")
                }
            }
            fo.startWatching()
        }
    }

    override fun onResume() {
        super.onResume()
//        if (isSupportGl20) gsv.onResume()
    }

    override fun onPause() {
        super.onPause()
//        if (isSupportGl20) gsv.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        my_surface_view.destroy()
    }
}
