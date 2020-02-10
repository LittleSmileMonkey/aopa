package com.sleep.opengl_analysis;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.sleep.common.TagUtil;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.*;

/**
 * author：xingkong on 2020-02-10
 * e-mail：xingkong@changjinglu.net
 */
public class FirstOpenGlRender implements GLSurfaceView.Renderer {
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClearColor(1.0f, 0f, 0f, 0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        Log.i(TagUtil.getTag(this), String.format("width = %s, height = %s", width, height));
        glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
    }
}
