package com.literata.maddog.starexplorer;

import android.opengl.GLES20;

import java.util.ArrayList;
import java.util.List;

public class SceneManager {
    private int mNumModels;
    private int[] mVboBuffers;

    private float[] mViewMatrix;
    private float[] mProjectionMatrix;
    private CameraObject mActiveCamera = null;
    private int mActiveCameraIndex = 0;

    List<ModelObject>   mObjectList = new ArrayList<ModelObject>();
    List<CameraObject>  mCameraList = new ArrayList<CameraObject>();
    List<ShaderProgram> mShaderList = new ArrayList<ShaderProgram>();

    public SceneManager() {
        mNumModels = 0;
    }

    public void setPrimaryShader(ModelObject modelObj, ShaderProgram shaderPgm) {
        if (mObjectList.contains(modelObj)) {
            modelObj.setPrimaryShader(shaderPgm);
        }
    }

    public float[] getEyePosition() {
        return mActiveCamera.getEyePosition();
    }

    public void setSecondaryShader(ModelObject modelObj, ShaderProgram shaderPgm) {
        if (mObjectList.contains(modelObj)) {
            modelObj.setSecondaryShader(shaderPgm);
        }
    }

    public void addModelObject(ModelObject modelObj) {
        mObjectList.add(modelObj);
        mNumModels++;
    }

    public void addCameraObject(CameraObject camObj) {
        mCameraList.add(camObj);
    }

    public void setActiveCamera(CameraObject camObj) {
        mActiveCamera = null;

        if (mCameraList.contains(camObj)) {
            mActiveCameraIndex = mCameraList.indexOf(camObj);
            mActiveCamera = mCameraList.get(mActiveCameraIndex);
        }
    }

    public void toggleActiveCamera() {
        mActiveCameraIndex++;
        if (mActiveCameraIndex > (mCameraList.size() - 1)) {
            mActiveCameraIndex = 0;
        }
        mActiveCamera = mCameraList.get(mActiveCameraIndex);
    }

    public CameraObject getActiveCamera() {
        return mCameraList.get(mActiveCameraIndex);
    }

    public void addShaderProgram(ShaderProgram shader) {
        mShaderList.add(shader);
    }

    public void onSurfaceCreated() {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES20.glClear( GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);

        mVboBuffers = new int[mNumModels];
        GLES20.glGenBuffers(mNumModels, mVboBuffers, 0);
        for (int i = 0; i < mObjectList.size(); i++) {
            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVboBuffers[i]);
            mObjectList.get(i).onSurfaceCreated(mVboBuffers[i]);
        }
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
    }

    public void onDrawFrame() {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glClear( GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);

        if (mActiveCamera != null) {
            for (ModelObject model : mObjectList) {
                if (model.getVisibleFlag()) {
                    mViewMatrix = mActiveCamera.getViewMatrix();
                    mProjectionMatrix = mActiveCamera.getProjectionMatrix();
                    GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, model.getVboIndex());
                    model.onDrawFrame(mViewMatrix.clone(), mProjectionMatrix.clone());
                }
            }
            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        }
    }
}
