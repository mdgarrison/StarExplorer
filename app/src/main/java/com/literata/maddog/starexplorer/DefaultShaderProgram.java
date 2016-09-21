package com.literata.maddog.starexplorer;

import com.literata.maddog.starexplorer.R;
import android.content.Context;
import android.opengl.GLES20;

public class DefaultShaderProgram  extends ShaderProgram {
    private static final String A_POSITION = "a_Position";
    private static final String A_COLOR    = "a_Color";
    private static final String U_MATRIX   = "u_Matrix";
    
    private static final int ATTRIB_POSITION = 1;
    private static final int ATTRIB_COLOR    = 2;
    
    public int aPositionLocation;
    public int aColorLocation;
    public int uMatrixLocation;
    
    public DefaultShaderProgram(Context context) {
        super(context);
    }
    
    public int assembleShaderProgram() {
        int progId = 0;

        String vertShaderSource = TextResourceReader.readTextFileFromResource(mContext, R.raw.simple_vertex_shader);
        String fragShaderSource = TextResourceReader.readTextFileFromResource(mContext, R.raw.simple_fragment_shader);

        int vertShader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
        GLES20.glShaderSource(vertShader,  vertShaderSource);
        GLES20.glCompileShader(vertShader);
        mVertOpStatus = getVertCompileStatus();
        
        int fragShader = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
        GLES20.glShaderSource(fragShader,  fragShaderSource);
        GLES20.glCompileShader(fragShader);

        mFragOpStatus = getFragCompileStatus();
        
        progId = GLES20.glCreateProgram();

        mCreateOpStatus = getCreateOpStatus(progId);
        
        GLES20.glAttachShader(progId, vertShader);
        GLES20.glAttachShader(progId, fragShader);
        
        GLES20.glBindAttribLocation(progId, ATTRIB_POSITION, "a_Position");
        GLES20.glBindAttribLocation(progId, ATTRIB_COLOR,    "a_Color");

        GLES20.glLinkProgram(progId);

        mLinkStatus = getLinkOpStatus(progId);
        
        return progId;
    }
    
    public void onSurfaceCreated() {
        mProgramOid = assembleShaderProgram();
        uMatrixLocation   = GLES20.glGetUniformLocation(mProgramOid, U_MATRIX);
        aPositionLocation = GLES20.glGetAttribLocation(mProgramOid, A_POSITION);
        aColorLocation    = GLES20.glGetAttribLocation(mProgramOid, A_COLOR);
    }
}
