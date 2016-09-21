package com.literata.maddog.starexplorer;

import android.opengl.Matrix;

public class CameraObject {
    private float mCameraOrbitalRadius;
    private final float[] mViewMatrix       = new float[16];
    private final float[] mProjectionMatrix = new float[16];

    private float[] mEyePosition            = new float[3];
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
        //Matrix.setIdentityM(mViewMatrix, 0);
        Matrix.setLookAtM(
                mViewMatrix, 0,
                mEyePosition[0], mEyePosition[1], mEyePosition[2],
                mTargetPosition[0], mTargetPosition[1], mTargetPosition[2],
                mUpVector[0], mUpVector[1], mUpVector[2]);
    }

    public void rotateX(float deg) {
        //Matrix.rotateM(mViewMatrix, 0, deg, 0f, 1f, 0f);
        mEyePosition[2] = -mCameraOrbitalRadius * (float) Math.cos(deg * Math.PI / 180.0f);
        mEyePosition[0] = mCameraOrbitalRadius * (float) Math.sin(deg * Math.PI / 180.0f);
        setViewMatrix();
    }

    public void rotateY(float deg) {
        //Matrix.rotateM(mViewMatrix, 0, deg, 1f, 0f, 0f);
    }

    public void setProjectionMatrix(float fov, int width, int height, float near, float far) {
        mFieldOfView = fov;
        mWidth       = (float) width;
        mHeight      = (float) height;
        mZNear       = near;
        mZFar        = far;

        Matrix.perspectiveM(mProjectionMatrix, 0, mFieldOfView, mWidth / mHeight, mZNear, mZFar);
    }


    public void setFrustum(float left, float right, float bottom, float top, float near, float far) {
        Matrix.frustumM(mProjectionMatrix, 0, left, right, bottom, top, near, far);
    }

    public void setOrthoMatrix(float left, float right, float bottom, float top, float near, float far) {
        Matrix.orthoM(mProjectionMatrix, 0, left, right, bottom, top, near, far);
    }

    public float calculateCameraOrbitalRadius() {
        return (float) Math.sqrt((mEyePosition[0] * mEyePosition[0]) + (mEyePosition[2] * mEyePosition[2]));
    }

    private void setViewMatrix() {
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
    
    public void setEyePosition(float[] eye) {
        mEyePosition[0] = eye[0];
        mEyePosition[1] = eye[1];
        mEyePosition[2] = eye[2];
        setViewMatrix();
    }
    
    public float[] getEyePosition() {
        float[] tViewMat = new float[16];
        Matrix.invertM(tViewMat, 0, mViewMatrix, 0);

        return new float[] {tViewMat[12], tViewMat[13], tViewMat[14]};
    }

    public void setTargetPosition(float[] target) {
        mTargetPosition[0] = target[0];
        mTargetPosition[1] = target[1];
        mTargetPosition[2] = target[2];
        setViewMatrix();
    }
    
    public float[] getTargetPosition() {
        return mTargetPosition;
    }
    
    public void setUpVector(float[] upVec) {
        mUpVector[0] = upVec[0];
        mUpVector[1] = upVec[1];
        mUpVector[2] = upVec[2];
    }
    
    public float[] getUpVector() {
        return mUpVector;
    }
    
    
    public void setFieldOfView(float fov) {
        mFieldOfView = fov;
    }
    
    public float getFieldOfView() {
        return mFieldOfView;
    }
}
