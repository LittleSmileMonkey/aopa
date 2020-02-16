package com.sleep.opengl_analysis.airhockey_v3;

import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import com.sleep.common.CommonApplication;
import com.sleep.common.TagUtil;
import com.sleep.opengl_analysis.R;
import com.sleep.opengl_analysis.objects.Mallet;
import com.sleep.opengl_analysis.objects.Table;
import com.sleep.opengl_analysis.programs.ColorShaderProgram;
import com.sleep.opengl_analysis.programs.TextureShaderProgram;
import com.sleep.opengl_analysis.util.MatrixHelper;
import com.sleep.opengl_analysis.util.TextureHelper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.*;

/**
 * author：xingkong on 2020-02-10
 * e-mail：xingkong@changjinglu.net
 * <p>
 * 为table接入纹理
 */
public class AirHockeyRender_v3 implements GLSurfaceView.Renderer {

    private final float[] projectionMatrix = new float[16];
    private final float[] modelMatrix = new float[16];

    private Table table;
    private Mallet mallet;

    private TextureShaderProgram textureProgram;
    private ColorShaderProgram colorProgram;

    private int texture;

    public AirHockeyRender_v3() {
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClearColor(0.0f, 0f, 0f, 0f);
        table = new Table();
        mallet = new Mallet();

        textureProgram = new TextureShaderProgram();
        colorProgram = new ColorShaderProgram();

        texture = TextureHelper.loadTexture(R.mipmap.air_hockey_surface);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        if (CommonApplication.LOG_ON) {
            Log.i(TagUtil.getTag(this), String.format("width = %s, height = %s", width, height));
        }
        glViewport(0, 0, width, height);
        MatrixHelper.perspectiveM(projectionMatrix, 45, (float) width / (float) height, 1f, 0f);
        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.translateM(modelMatrix, 0, 0f, 0f, -3f);
        Matrix.rotateM(modelMatrix, 0, -60f, 1f, 0f, 0f);
        final float[] temp = new float[16];
        Matrix.multiplyMM(temp, 0, projectionMatrix, 0, modelMatrix, 0);
        System.arraycopy(temp, 0, projectionMatrix, 0, temp.length);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        glClear(GL_COLOR_BUFFER_BIT);

        textureProgram.useProgram();
        textureProgram.setUniforms(projectionMatrix, texture);
        table.bindData(textureProgram);
        table.draw();

        colorProgram.useProgram();
        colorProgram.setUniforms(projectionMatrix);
        mallet.bindData(colorProgram);
        mallet.draw();
    }
}
