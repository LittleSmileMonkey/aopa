package com.sleep.opengl_analysis.util;

import android.util.Log;

import com.sleep.common.CommonApplication;

import static android.opengl.GLES20.*;

/**
 * author：xingkong on 2020-02-10
 * e-mail：xingkong@changjinglu.net
 */
public class ShaderHelper {

    private static final String TAG = ShaderHelper.class.getSimpleName();

    public static int compileVertexShader(String shaderCode) {
        return compileShader(GL_VERTEX_SHADER, shaderCode);
    }

    public static int complieFragmentShader(String shaderCode) {
        return compileShader(GL_FRAGMENT_SHADER, shaderCode);
    }

    private static int compileShader(int type, String shaderCode) {
        final int shaderObjectId = glCreateShader(type);
        if (shaderObjectId == 0) {
            if (CommonApplication.LOG_ON) {
                Log.w(TAG, "Could not create new shader.");
            }
            return 0;
        }
        //将shader代码传给OpenGL
        glShaderSource(shaderObjectId, shaderCode);
        //让OpenGL编译上传的shaderCode
        glCompileShader(shaderObjectId);
        //获取编译状态，并赋值给compileStatus第0个元素
        final int[] compileStatus = new int[1];
        glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, compileStatus, 0);
        if (CommonApplication.LOG_ON) {
            //获取编译的详细信息
            Log.v(TAG, "Result of compiling source: " + "\n" + shaderCode + "\n:" + glGetShaderInfoLog(shaderObjectId));
        }
        if (compileStatus[0] == 0) {
            //编译失败
            glDeleteShader(shaderObjectId);
            if (CommonApplication.LOG_ON) {
                Log.w(TAG, "Compilation of shader failed.");
            }
            return 0;
        }
        return shaderObjectId;
    }

    public static int linkProgram(int vertexShaderId, int fragmentShaderId) {
        final int programObjectId = glCreateProgram();
        if (programObjectId == 0) {
            if (CommonApplication.LOG_ON) {
                Log.w(TAG, "Could not create new program.");
            }
            return 0;
        }
        glAttachShader(programObjectId, vertexShaderId);
        glAttachShader(programObjectId, fragmentShaderId);
        glLinkProgram(programObjectId);
        //获取link状态
        final int[] programLinkStatus = new int[1];
        glGetProgramiv(programObjectId, GL_LINK_STATUS, programLinkStatus, 0);
        if (programLinkStatus[0] == 0) {
            //link失败
            glDeleteProgram(programObjectId);
            if (CommonApplication.LOG_ON) {
                Log.w(TAG, "Result of linking program :\n " + glGetProgramInfoLog(programObjectId));
            }
            return 0;
        }
        return programObjectId;
    }

    public static boolean validateProgram(int programObjectId) {
        glValidateProgram(programObjectId);
        final int[] validateStatus = new int[1];
        glGetProgramiv(programObjectId, GL_VALIDATE_STATUS, validateStatus, 0);
        Log.v(TAG, "Result of validating program: " + validateStatus[0] + "\nLog:" + glGetProgramInfoLog(programObjectId));
        return validateStatus[0] != 0;
    }
}
