package com.literata.maddog.starexplorer;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

public class ReferenceLines extends ModelObject {

    public ReferenceLines(Context context, SceneManager mgr) {
        super(context, mgr);
        initializeModel();
        mDrawMode = GLES20.GL_LINE_LOOP;
        mStride = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT;
    }

    protected void initializeModel() {
        float radius = 1000.0f;
        int numPoints = 360;
        float[] colorData = { 192.0f / 256.0f * 0.5f, 128.0f / 256.0f * 0.5f, 128.0f / 256.0f * 0.5f, 0.0f};
        mVertexData = new float[(numPoints + 1) * (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT)];

        for (int i = 0; i <= 360; i++) {
            mVertexData[i + (7 * i) + 0] = ((float) Math.cos((double) i * Math.PI / 180.0)) * radius;
            mVertexData[i + (7 * i) + 1] = 0.0f;
            mVertexData[i + (7 * i) + 2] = ((float) Math.sin((double) i * Math.PI / 180.0)) * radius;
            mVertexData[i + (7 * i) + 3] = 1.0f;
            mVertexData[i + (7 * i) + 4] = colorData[0];
            mVertexData[i + (7 * i) + 5] = colorData[1];
            mVertexData[i + (7 * i) + 6] = colorData[2];
            mVertexData[i + (7 * i) + 7] = colorData[3];
        }

        makeFloatBuffer();
        mNumVertices = mVertexData.length / (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT);
        mNumVerticesToDraw = mNumVertices;
    }

    @Override
    public void onDrawFrame(float[] viewMat, float[] projMat) {
        Matrix.setIdentityM(mModelMatrix, 0);
        constructMvpMatrix(viewMat, projMat);
        drawVBO();		

        Matrix.rotateM(mModelMatrix, 0, 90, 0.0f, 0.0f, 1.0f);
        constructMvpMatrix(viewMat, projMat);
        drawVBO();

        Matrix.rotateM(mModelMatrix, 0, 90, 1.0f, 0.0f, 0.0f);
        constructMvpMatrix(viewMat, projMat);
        drawVBO();
    }

    private void drawVBO() {
        DefaultShaderProgram shader = (DefaultShaderProgram) mPrimaryShader;

        //GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glUseProgram(shader.getProgramOid());
        GLES20.glUniformMatrix4fv(shader.uMatrixLocation, 1, false, mMvpMatrix, 0);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVboIndex);
        GLES20.glEnableVertexAttribArray(shader.aPositionLocation);
        GLES20.glVertexAttribPointer(shader.aPositionLocation, POSITION_COMPONENT_COUNT, GLES20.GL_FLOAT, false, mStride, 0);
        GLES20.glEnableVertexAttribArray(shader.aColorLocation);
        GLES20.glVertexAttribPointer(shader.aColorLocation, COLOR_COMPONENT_COUNT, GLES20.GL_FLOAT, false, mStride, POSITION_COMPONENT_COUNT * BYTES_PER_FLOAT);
        GLES20.glDrawArrays(mDrawMode, 0, mNumVerticesToDraw);
        //GLES20.glDisable(GLES20.GL_DEPTH_TEST);
    }
}
