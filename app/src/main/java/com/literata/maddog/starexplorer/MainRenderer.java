package com.literata.maddog.starexplorer;

import android.opengl.GLES20;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.PointF;
import android.opengl.GLSurfaceView.Renderer;

public class MainRenderer implements Renderer {
    private float mCameraDistance = 5.0f;

    private SceneManager    mManager;
    //private Earth           mEarth;
    private PointLight      mPointLight;
    private CelestialSphere mCelestialSphere;
    private ReferenceLines  mRefLines;
    private StarField       mStarField;
    //private Moon            mMoon;

    private DefaultShaderProgram mDefaultShader;
    //private EarthDaysideShaderProgram mEarthDaysideShader;
    //private EarthNightsideShaderProgram mEarthNightsideShader;

    private CameraObject   mCamera1;
    private CameraObject   mCamera2;
    private CameraObject   mCamera3;
    private CameraObject   mCamera4;
    private CameraObject   mCamera5;
    
    private final Context mContext;
    
    private PointF mUserAngle = new PointF(0f, 0f);
    
    private int mWidth;
    private int mHeight;

    private PointF mStandardCameraOrbitAngle = new PointF(0.25f, 0.00f);
    private PointF mCameraOrbitAngle         = new PointF(mStandardCameraOrbitAngle.x, 0.00f); 
    private PointF mUserCameraOrbitAngle     = new PointF(0.00f, 0.00f);
    
    // Toggle flags to interact with UI controls
    private boolean mSphereRefsVisible = true;
    private boolean mGridRefsVisible   = false;
    private boolean mSphereVisible     = true;
    private int     mMagnitudeToggle   = 4;
    private boolean mToggleDebug       = false;
    private int     mToggleStep        = 0;
    private boolean mLockX             = false;
    private boolean mLockY             = true;
    private boolean mSwapFlag          = true;
    private boolean mTogglePov         = false;
    private boolean mToggleProjection  = true;
    private boolean mToggleCameraRotation = false;
    
    public MainRenderer(Context context) {
        this.mContext = context;

        mManager  = new SceneManager();

        //mEarth = new Earth(mContext, mManager);
        //mEarth.setVisibleFlag(mSwapFlag);
        //mManager.addModelObject(mEarth);
        
        mPointLight = new PointLight(mContext, mManager);
        mManager.addModelObject(mPointLight);
        
        mCelestialSphere = new CelestialSphere(mContext, mManager);
        mManager.addModelObject(mCelestialSphere);
        
        mRefLines = new ReferenceLines(mContext, mManager);
        mManager.addModelObject(mRefLines);
        
        mStarField = new StarField(mContext, mManager);
        mManager.addModelObject(mStarField);
        
        //mMoon = new Moon(mContext, mManager);
        //mManager.addModelObject(mMoon);
    }
    
    public void initCameras() {
        initCamera4();
        mManager.setActiveCamera(mCamera4);
    }
    
    public void initCamera4() {
        float[] eyePos = {0f, 250f, 3000f};
        float[] tgtPos = {0f, 0f, 0f};
        float[] upVec  = {0f, 1f, 0f};
        mCamera4 = new CameraObject("Cam4", eyePos, tgtPos, upVec);

        mCamera4.setProjectionMatrix(60f, mWidth, mHeight, 1f, 11000f);
        mManager.addCameraObject(mCamera4);
    }

    public void initShaders() {
        //mEarthDaysideShader = new EarthDaysideShaderProgram(mContext);
        //mEarthNightsideShader = new EarthNightsideShaderProgram(mContext);

        //mManager.setPrimaryShader(mEarth, mEarthDaysideShader);
        //mManager.setSecondaryShader(mEarth, mEarthNightsideShader);
        
        mDefaultShader = new DefaultShaderProgram(mContext);
        mManager.setPrimaryShader(mPointLight,  mDefaultShader);
        
        mManager.setPrimaryShader(mCelestialSphere,  mDefaultShader);
        
        mManager.setPrimaryShader(mRefLines,  mDefaultShader);

        mManager.setPrimaryShader(mStarField,  mDefaultShader);
        
        //mManager.setPrimaryShader(mMoon, mEarthDaysideShader);
        //mManager.setSecondaryShader(mMoon, mEarthNightsideShader);
    }
    
    public void setFieldOfView(float fov) {
        mManager.getActiveCamera().setFieldOfView(fov);
    }
    
    public float getFieldOfView() {
        return mManager.getActiveCamera().getFieldOfView();
    }

    public void setUserAngles(PointF angle) {
        mUserCameraOrbitAngle.x = (mLockX ? 0.0f : angle.x / 10.0f);
        mUserCameraOrbitAngle.y = (mLockY ? 0.0f : angle.y / 10.0f);
    }
    
    public void clearUserAngles() {
        mUserAngle.x = 0f;
        mUserAngle.y = 0f;
    }
    
    public float getCameraDistance() {
        return 0f; //mEyePosition[2];
    }

    public void toggleDebugger() {
        mToggleDebug = mToggleDebug != true;
    }
    
    public int getToggleMagnitude() {
        return mMagnitudeToggle;
    }
    
    public int toggleMagnitude() {
        mMagnitudeToggle++;
        
        if (mMagnitudeToggle > 4) {
            mMagnitudeToggle = 0;
        }
        return mMagnitudeToggle;
    }

    public int getToggleStep() {
        return mToggleStep;
    }
    
    public int toggleStep() {
        mToggleStep++;
        if (mToggleStep > 9) {
            mToggleStep = 0;
        }
        return mToggleStep;
    }

    public boolean toggleLockY() {
        mLockY = (!mLockY);
        return mLockY;
    }

    public boolean getToggleLockY() {
        return mLockY;
    }

    public boolean toggleLockX() {
        mLockX = (!mLockX);
        return mLockX;
    }

    public boolean getToggleLockX() {
        return mLockX;
    }

    public void doReset() {
        mManager.getActiveCamera().resetCamera();
    }
    
    public boolean getToggleSphereVisible() {
        return mSphereVisible;
    }

    public boolean toggleSphereVisible() {
        mSphereVisible = (!mSphereVisible);
        mCelestialSphere.setVisibleFlag(mSphereVisible);
        return mSphereVisible;
    }
    
    public boolean getToggleGridRefsVisible() {
        return mGridRefsVisible;
    }
    
    public boolean toggleGridRefsVisible() {
        mGridRefsVisible = (!mGridRefsVisible);
        return mGridRefsVisible;
    }
    
    public boolean getToggleRefs() {
        return mSphereRefsVisible;
    }

    public boolean toggleRefs() {
        mSphereRefsVisible = (!mSphereRefsVisible);
        mRefLines.setVisibleFlag(mGridRefsVisible);
        return mSphereRefsVisible;
    }

    public boolean getToggleSwap() {
        return mSwapFlag;
    }

    public boolean toggleSwap() {
        mSwapFlag = (!mSwapFlag);
        //mEarth.setVisibleFlag(mSwapFlag);
        return mSwapFlag;
    }
    
    public boolean getTogglePov() {
        return mTogglePov;
    }
    
    public boolean togglePov() {
        mTogglePov = (!mTogglePov);
        return mTogglePov;
    }
    
    public boolean getToggleProjection() {
        return mToggleProjection;
    }
    
    public boolean toggleProjection() {
        mToggleProjection = (!mToggleProjection);
        return mToggleProjection;
    }
    
    public float setCameraDistance(float newZ) {
        return newZ;
    }
    
    void setDeltaXY(float x, float y) {
    }
    
    void clearDeltaXY() {
    }
    
    public String toggleActiveCamera() {
        mManager.toggleActiveCamera();
        return mManager.getActiveCamera().getCameraName();
    }
    
    public String getToggleActiveCamera() {
        return mManager.getActiveCamera().getCameraName();
    }
    
    /*
     * ***********************************************************************
     * Planet Rotation Controls
     * ***********************************************************************
     */

    public boolean togglePlanetRotation() {
        return true; //mEarth.togglePlanetRotation();
    }
    
    public boolean getTogglePlanetRotation() {
        return true; //mEarth.getTogglePlanetRotation();
    }
    
    public void setTogglePlanetRotation(boolean val) {
        //mEarth.setTogglePlanetRotation(val);
    }

    /*
     * ***********************************************************************
     * Light Rotation Controls
     * ***********************************************************************
     */
    public boolean toggleLightRotation() {
        return true; //mEarth.toggleLightRotation();
    }
    
    public boolean getToggleLightRotation() {
        return true; //mEarth.getToggleLightRotation();
    }

    public void setToggleLightRotation(boolean val) {
        //mEarth.setToggleLightRotation(val);
    }
    
    /*
     * ***********************************************************************
     * Camera Rotation Controls
     * ***********************************************************************
     */
    public boolean toggleCameraRotation() {
        mToggleCameraRotation = (!mToggleCameraRotation);
        return mToggleCameraRotation;
    }
    
    public boolean getToggleCameraRotation() {
        return mToggleCameraRotation;
    }
    
    public void setToggleCameraRotation(boolean val) {
        mToggleCameraRotation = val;
    }
    
    
    
    @Override
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {

        initShaders();

        //mEarthDaysideShader.onSurfaceCreated();
        //mEarthNightsideShader.onSurfaceCreated();
        mDefaultShader.onSurfaceCreated();
        mManager.onSurfaceCreated();
    }

    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        mWidth = width;
        mHeight = height;
        GLES20.glViewport(0, 0, mWidth, mHeight);

        initCameras();
        mManager.setActiveCamera(mCamera1);
    }

    @Override
    public void onDrawFrame(GL10 glUnused) {
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        mManager.onDrawFrame();
        GLES20.glDisable(GLES20.GL_DEPTH_TEST);

        if (mToggleCameraRotation) {
            mManager.getActiveCamera().rotateX(mCameraOrbitAngle.x + mUserCameraOrbitAngle.x);
            mUserCameraOrbitAngle.x = 0.0f;
            mCameraOrbitAngle.x += 0.25f;
            if (mCameraOrbitAngle.x >= 360.0f) {
                mCameraOrbitAngle.x = 0.0f;
            }
        }
    }
}
