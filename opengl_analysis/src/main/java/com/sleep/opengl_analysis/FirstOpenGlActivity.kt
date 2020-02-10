package com.sleep.opengl_analysis

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sleep.opengl_analysis.util.OpenGlUtil
import kotlinx.android.synthetic.main.activity_first_open_gl.*

class FirstOpenGlActivity : AppCompatActivity() {

    var isSupportGl20 = false
    val mRender = FirstOpenGlRender()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_open_gl)
        val supportGl20 = OpenGlUtil.judgeOpenGlVersion(0x20000)
        if (supportGl20){
            isSupportGl20 = true
            gsv.setEGLContextClientVersion(2)
            gsv.setRenderer(mRender)
        }else{
            Toast.makeText(this,"not support gl20",Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        if (isSupportGl20) gsv.onResume()
    }

    override fun onPause() {
        super.onPause()
        if (isSupportGl20) gsv.onPause()
    }
}
