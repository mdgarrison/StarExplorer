package com.literata.maddog.starexplorer;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

public class PointLight extends ModelObject {

    public PointLight(Context context, SceneManager mgr) {
        super(context, mgr);
        initializeModel();
        mDrawMode = GLES20.GL_POINTS;
        mStride = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT;
    }

    protected void initializeModel() {
        mVertexData = new float[] {
            0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f
        };
        
        makeFloatBuffer();
        mNumVertices = mVertexData.length / (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT);
        mNumVerticesToDraw = mNumVertices;
        
    }

    @Override
    public void onDrawFrame(float[] viewMat, float[] projMat) {
        int i;
        Matrix.setIdentityM(mModelMatrix, 0);
        Matrix.translateM(mModelMatrix, 0, 2.0f, 0.0f, 0.0f);
        constructMvpMatrix(viewMat, projMat);
        drawVBO();		
    }

    private void drawVBO() {
        DefaultShaderProgram mDefShader = (DefaultShaderProgram) mPrimaryShader;

        GLES20.glUseProgram(mDefShader.getProgramOid());
        GLES20.glUniformMatrix4fv(mDefShader.uMatrixLocation, 1, false, mMvpMatrix, 0);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVboIndex);
        GLES20.glEnableVertexAttribArray(mDefShader.aPositionLocation);
        GLES20.glVertexAttribPointer(mDefShader.aPositionLocation, POSITION_COMPONENT_COUNT, GLES20.GL_FLOAT, false, mStride, 0);
        GLES20.glEnableVertexAttribArray(mDefShader.aColorLocation);
        GLES20.glVertexAttribPointer(mDefShader.aColorLocation, COLOR_COMPONENT_COUNT, GLES20.GL_FLOAT, false, mStride, POSITION_COMPONENT_COUNT * BYTES_PER_FLOAT);
        GLES20.glDrawArrays(mDrawMode, 0, mNumVerticesToDraw);
    }
}
