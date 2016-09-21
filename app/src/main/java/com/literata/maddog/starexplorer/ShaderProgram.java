package com.literata.maddog.starexplorer;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

public abstract class ShaderProgram {
    private final static String TAG = "ShaderProgram";
    protected Context mContext;
    protected int mProgramOid;
    protected int mVertShaderOid;
    protected int mFragShaderOid;
    
    protected int mVertOpStatus   = 0;
    protected int mFragOpStatus   = 0;
    protected int mCreateOpStatus = 0;
    protected int mLinkStatus     = 0;

    public ShaderProgram(Context context) {
        mContext = context;
    }

    protected int getVertCompileStatus() {
        final int[] opStatus = new int[1];
        GLES20.glGetShaderiv(mVertShaderOid, GLES20.GL_COMPILE_STATUS, opStatus, 0);
        if (LoggerConfig.ON) {
            Log.v(TAG, "GLES20.glCompileShader(vertShader): " + Integer.toString(opStatus[0]) + " [" + (opStatus[0] == GLES20.GL_TRUE ? " TRUE]" : "FALSE]"));
            Log.v(TAG, GLES20.glGetShaderInfoLog(mVertShaderOid));
        }
        return opStatus[0];
    }

    protected int getFragCompileStatus() {
        final int[] opStatus = new int[1];
        GLES20.glGetShaderiv(mFragShaderOid, GLES20.GL_COMPILE_STATUS, opStatus, 0);
        if (LoggerConfig.ON) {
            Log.v(TAG, "GLES20.glCompileShader(fragShader): " + Integer.toString(opStatus[0]) + " [" + (opStatus[0] == GLES20.GL_TRUE ? " TRUE]" : "FALSE]"));
            Log.v(TAG, GLES20.glGetShaderInfoLog(mFragShaderOid));
        }
        return opStatus[0];
    }

    protected int getCreateOpStatus(int progId) {
        if (LoggerConfig.ON) {
            Log.v(TAG, "GLES20.glCreateProgram(): " + Integer.toString(progId));
        }
        return progId;
    }

    protected int getLinkOpStatus(int progId) {
        final int[] opStatus = new int[1];
        GLES20.glGetProgramiv(progId, GLES20.GL_LINK_STATUS, opStatus, 0);
        if (LoggerConfig.ON) {
            Log.v(TAG, "GLES20.glLinkProgram(" + Integer.toString(progId) + "): " + Integer.toString(opStatus[0]));
            Log.v(TAG, GLES20.glGetProgramInfoLog(progId));
        }
        return progId;
    }

    protected void checkGlError(String op) 
    {
        int error;
        
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) 
        {
            Log.e(TAG, op + ": glError " + error);
            throw new RuntimeException(op + ": glError " + error);
        }
    }
    
    public abstract int assembleShaderProgram();
    
    public abstract void onSurfaceCreated();

    public int getProgramOid() {
        return mProgramOid;
    }
}
