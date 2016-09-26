package com.literata.maddog.starexplorer;

import android.opengl.Matrix;

public class CameraObject {
    private float mCameraOrbitalRadius;
    private final float[] mViewMatrix       = new float[16];
    private final float[] mProjectionMatrix = new float[16];

    private float[] mEyePosition            = new float[3];
    private float[] mOrigEyePosition        = new float[3];
    private float[] mTargetPosition         = new float[3];
    private float[] mUpVector               = new float[3];
    private float   mFieldOfView;
    private float   mWidth;
    private float   mHeight;
    private float   mZNear;
    private float   mZFar;
    private String  mName;

    public CameraObject(String name, float[] eyePos, float[] tgtPos, float[] upVec) {
        mName = name;
        mEyePosition[0] = eyePos[0];
        mEyePosition[1] = eyePos[1];
        mEyePosition[2] = eyePos[2];

        mOrigEyePosition[0] = eyePos[0];
        mOrigEyePosition[1] = eyePos[1];
        mOrigEyePosition[2] = eyePos[2];

        mTargetPosition[0] = tgtPos[0];
        mTargetPosition[1] = tgtPos[1];
        mTargetPosition[2] = tgtPos[2];

        mUpVector[0] = upVec[0];
        mUpVector[1] = upVec[1];
        mUpVector[2] = upVec[2];

        mCameraOrbitalRadius = calculateCameraOrbitalRadius();

        setViewMatrix();
    }

    public String getCameraName() {
        return mName;
    }

    public void resetCamera() {
        Matrix.setLookAtM(
                mViewMatrix, 0,
                mEyePosition[0], mEyePosition[1], mEyePosition[2],
                mTargetPosition[0], mTargetPosition[1], mTargetPosition[2],
                mUpVector[0], mUpVector[1], mUpVector[2]);
    }

    public void rotateX(float deg) {
        mEyePosition[2] = -mCameraOrbitalRadius * (float) Math.cos(deg * Math.PI / 180.0f);
        mEyePosition[0] = mCameraOrbitalRadius * (float) Math.sin(deg * Math.PI / 180.0f);
        setViewMatrix();
    }

    public void setProjectionMatrix(float fov, int width, int height, float near, float far) {
        mFieldOfView = fov;
        mWidth       = (float) width;
        mHeight      = (float) height;
        mZNear       = near;
        mZFar        = far;

        Matrix.perspectiveM(mProjectionMatrix, 0, mFieldOfView, mWidth / mHeight, mZNear, mZFar);
    }

    public float calculateCameraOrbitalRadius() {
        return (float) Math.sqrt((mEyePosition[0] * mEyePosition[0]) + (mEyePosition[2] * mEyePosition[2]));
    }

    public void setViewMatrix() {
        Matrix.setLookAtM(
                mViewMatrix, 0,
                mEyePosition[0], mEyePosition[1], mEyePosition[2],
                mTargetPosition[0], mTargetPosition[1], mTargetPosition[2],
                mUpVector[0], mUpVector[1], mUpVector[2]);
    }

    public float[] getViewMatrix() {
        return mViewMatrix;
    }
    
    public float[] getProjectionMatrix() {
        return mProjectionMatrix;
    }
    
    public float[] getEyePosition() {
        float[] tViewMat = new float[16];
        Matrix.invertM(tViewMat, 0, mViewMatrix, 0);

        return new float[] {tViewMat[12], tViewMat[13], tViewMat[14]};
    }

    public void setFieldOfView(float fov) {
        mFieldOfView = fov;
    }
    
    public float getFieldOfView() {
        return mFieldOfView;
    }

    public void adjustXVal(float x) {
        mEyePosition[0] += x;
        setViewMatrix();
    }

    public void adjustYVal(float y) {
        mEyePosition[1] += y;
        setViewMatrix();
    }

    public void adjustZVal(float z) {
        mEyePosition[2] += z;
        setViewMatrix();
    }

    public void reset() {
        mEyePosition[0] = mOrigEyePosition[0];
        mEyePosition[1] = mOrigEyePosition[1];
        mEyePosition[2] = mOrigEyePosition[2];
        setViewMatrix();
    }
}
