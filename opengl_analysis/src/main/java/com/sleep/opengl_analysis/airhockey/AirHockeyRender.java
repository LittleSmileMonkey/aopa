package com.sleep.opengl_analysis.airhockey;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.sleep.common.CommonApplication;
import com.sleep.common.TagUtil;
import com.sleep.opengl_analysis.R;
import com.sleep.opengl_analysis.util.ShaderHelper;
import com.sleep.opengl_analysis.util.TextResourceReader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.*;
/**
 * author：xingkong on 2020-02-10
 * e-mail：xingkong@changjinglu.net
 *
 * OpenGl 编写步骤
 * 1. 对要画的图形进行抽象，转换为OpenGL中的三种基本元素的坐标
 * 2. 通过java ByteBuffer API 申请本地内存，并将坐标复制到本地内存中，并将position指向0
 * 3. 编写glsl代码(本例在raw文件夹中)
 * 4. 加载、编译各种shader并验证
 * 5. 创建并连接program，验证program
 * 6. 通过gl api 拿到glsl中定义的变量的句柄(location)
 * 7. 关联顶点坐标与顶点byteBuffer数据
 * 8. 通过各种句柄及坐标画想要的图形。
 */
public class AirHockeyRender implements GLSurfaceView.Renderer {

    //java中每个浮点占4个字节
    private static final int BYTES_PERFLOAT = 4;
    //每个position用两个浮点进行标记
    private static final int POSITION_COMPONENT_COUNT = 2;

    private static final String A_POSITION = "a_Position";
    private static final String U_COLOR = "u_Color";
    private int uColorLocation;
    private int aPositionLocation;

    //step 1. 对要画的图形进行抽象，转换为OpenGL中的三种基本元素的坐标
    private float[] tableVerticesWidthTriangles = {
            //background triangle 1
            -0.6f, -0.55f,
            0.6f, 0.55f,
            -0.6f, 0.55f,

            //background triangle 2
            -0.6f, -0.55f,
            0.6f, -0.55f,
            0.6f, 0.55f,

            //triangle 1
            -0.5f, -0.5f,
            0.5f, 0.5f,
            -0.5f, 0.5f,

            //triangle 2
            -0.5f, -0.5f,
            0.5f, -0.5f,
            0.5f, 0.5f,

            //center line
            -0.5f, 0f,
            0.5f, 0f,

            //Mallets
            0f, -0.25f,
            0f, 0.25f,

            //center ball
            0f, 0f
    };

    private int program;

    private final FloatBuffer vertexData = ByteBuffer
            .allocateDirect(tableVerticesWidthTriangles.length * BYTES_PERFLOAT)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer();

    private String vertexShaderSource;
    private String fragmentShaderSource;

    public AirHockeyRender() {
        //step 2. 通过java ByteBuffer API 申请本地内存，并将坐标复制到本地内存中，并将position指向0
        vertexData.put(tableVerticesWidthTriangles);
        vertexShaderSource = TextResourceReader.readTextFileFromResouce(R.raw.simple_vertex_shader);
        fragmentShaderSource = TextResourceReader.readTextFileFromResouce(R.raw.simple_fragment_shader);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClearColor(0.0f, 0f, 0f, 0f);

        //step 4. 加载并编译各种shader并验证
        int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource);
        int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource);

        //step 5. 创建并连接program，验证program
        program = ShaderHelper.linkProgram(vertexShader, fragmentShader);
        if (CommonApplication.LOG_ON) {
            ShaderHelper.validateProgram(program);
        }
        glUseProgram(program);

        //step 6. 通过gl api 拿到glsl中定义的变量的句柄(location)
        //Uniform location
        uColorLocation = glGetUniformLocation(program, U_COLOR);
        //Attribute location
        aPositionLocation = glGetAttribLocation(program, A_POSITION);
        //确保内部指针开buffer开头
        vertexData.position(0);

        //step 7. 关联顶点坐标与顶点byteBuffer数据
        glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, 0, vertexData);
        glEnableVertexAttribArray(aPositionLocation);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        if (CommonApplication.LOG_ON) {
            Log.i(TagUtil.getTag(this), String.format("width = %s, height = %s", width, height));
        }
        glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        //step 8. 通过各种句柄及坐标画想要的图形。

        //背景桌面
        glUniform4f(uColorLocation, 0f, 1.0f, 0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 0, 6);

        //更新着色器中的u_Color值
        glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f);
        //画桌子
        glDrawArrays(GL_TRIANGLES, 6, 6);

        glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
        glDrawArrays(GL_LINES, 12, 2);

        //画第一个蓝色木槌
        glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
        glDrawArrays(GL_POINTS, 14, 1);
        //画第二个红色木槌
        glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
        glDrawArrays(GL_POINTS, 15, 1);

        //中心冰球
        glUniform4f(uColorLocation, 0.67f, 0.80f, 0.94f, 1.00f);
        glDrawArrays(GL_POINTS, 16, 1);
    }
}
