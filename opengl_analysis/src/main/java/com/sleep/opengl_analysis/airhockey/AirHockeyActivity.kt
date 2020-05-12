package com.sleep.opengl_analysis

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sleep.opengl_analysis.airhockey.AirHockeyRender_3d

/**
 * 空气曲棍球项目入口
 */
class AirHockeyActivity : AppCompatActivity() {

    private var isSupportGl20 = false
    private val mRender = AirHockeyRender_3d()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_air_hockey)
//        val supportGl20 = OpenGlUtil.judgeOpenGlVersion(0x20000)
//        if (supportGl20){
//            isSupportGl20 = true
//            gsv.setEGLContextClientVersion(2)
//            gsv.setRenderer(mRender)
//        }else{
//            Toast.makeText(this,"not support gl20",Toast.LENGTH_SHORT).show()
//            finish()
//        }
    }
//
//    override fun onResume() {
//        super.onResume()
//        if (isSupportGl20) gsv.onResume()
//    }
//
//    override fun onPause() {
//        super.onPause()
//        if (isSupportGl20) gsv.onPause()
//    }
}
