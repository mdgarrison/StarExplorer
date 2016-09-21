package com.literata.maddog.starexplorer;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

public class Grid extends ModelObject {

    public Grid(Context context, SceneManager mgr) {
        super(context, mgr);
        initializeModel();
        mDrawMode = GLES20.GL_LINES;
        mStride = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT;
    }


    @Override
    protected void initializeModel() {
        float gridWidth = 500.0f;
        float spacing = 20.0f;
        float r = 64.0f/256.0f;
        float g = 64.0f/256.0f;
        float b = 64.0f/256.0f;
        float a = 0.0f;
        int vertexCount = ((int) gridWidth + 1) * 4;
        
        mVertexData = new float[vertexCount * 8];
        int vIndex = 0;

        for (float x = -(gridWidth / 2.0f); x <= (gridWidth / 2.0f); x += spacing) {
            mVertexData[vIndex +  0] = x;
            mVertexData[vIndex +  1] = (gridWidth / 2.0f);
            mVertexData[vIndex +  2] = 0f;
            mVertexData[vIndex +  3] = 1f;
            mVertexData[vIndex +  4] = r;
            mVertexData[vIndex +  5] = g;
            mVertexData[vIndex +  6] = b;
            mVertexData[vIndex +  7] = a;
            mVertexData[vIndex +  8] = x;
            mVertexData[vIndex +  9] = -(gridWidth / 2.0f);
            mVertexData[vIndex + 10] = 0f;
            mVertexData[vIndex + 11] = 1f;
            mVertexData[vIndex + 12] = r;
            mVertexData[vIndex + 13] = g;
            mVertexData[vIndex + 14] = b;
            mVertexData[vIndex + 15] = a;
            vIndex += 16;
        }

        for (float y = -(gridWidth / 2.0f); y <= (gridWidth / 2.0f); y += spacing) {
            mVertexData[vIndex +  0] = (gridWidth / 2.0f);
            mVertexData[vIndex +  1] = y;
            mVertexData[vIndex +  2] = 0f;
            mVertexData[vIndex +  3] = 1f;
            mVertexData[vIndex +  4] = r;
            mVertexData[vIndex +  5] = g;
            mVertexData[vIndex +  6] = b;
            mVertexData[vIndex +  7] = a;
            mVertexData[vIndex +  8] = -(gridWidth / 2.0f);
            mVertexData[vIndex +  9] = y;
            mVertexData[vIndex + 10] = 0f;
            mVertexData[vIndex + 11] = 1f;
            mVertexData[vIndex + 12] = r;
            mVertexData[vIndex + 13] = g;
            mVertexData[vIndex + 14] = b;
            mVertexData[vIndex + 15] = a;
            vIndex += 16;
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

        Matrix.rotateM(mModelMatrix, 0, 90, 1.0f, 0.0f, 0.0f);
        constructMvpMatrix(viewMat, projMat);
        drawVBO();

        Matrix.setIdentityM(mModelMatrix, 0);
        Matrix.rotateM(mModelMatrix, 0, 90, 0.0f, 1.0f, 0.0f);
        constructMvpMatrix(viewMat, projMat);
        drawVBO();
    }

    private void drawVBO() {
        DefaultShaderProgram shader = (DefaultShaderProgram) mPrimaryShader;

        GLES20.glUseProgram(shader.getProgramOid());
        GLES20.glUniformMatrix4fv(shader.uMatrixLocation, 1, false, mMvpMatrix, 0);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVboIndex);
        GLES20.glEnableVertexAttribArray(shader.aPositionLocation);
        GLES20.glVertexAttribPointer(shader.aPositionLocation, POSITION_COMPONENT_COUNT, GLES20.GL_FLOAT, false, mStride, 0);
        GLES20.glEnableVertexAttribArray(shader.aColorLocation);
        GLES20.glVertexAttribPointer(shader.aColorLocation, COLOR_COMPONENT_COUNT, GLES20.GL_FLOAT, false, mStride, POSITION_COMPONENT_COUNT * BYTES_PER_FLOAT);
        GLES20.glDrawArrays(mDrawMode, 0, mNumVerticesToDraw);
    }
}
