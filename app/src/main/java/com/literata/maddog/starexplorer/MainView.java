package com.literata.maddog.starexplorer;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.PointF;

public class MainView extends GLSurfaceView {
    private MainRenderer mRenderer;
    float mDensity = 0;

    public MainView(Context context)
    {
        super(context);
        if (!isInEditMode()) {
            final DisplayMetrics displayMetrics = new DisplayMetrics();
            mDensity = displayMetrics.density;

            mRenderer = new MainRenderer(context);

            setEGLContextClientVersion(2);
            setEGLConfigChooser(8, 8, 8, 8, 16, 1);

            getHolder().setFormat(PixelFormat.RGBA_8888);

            setRenderer(mRenderer);
        }
    }

    public MainView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        if (!isInEditMode()) {
            final DisplayMetrics displayMetrics = new DisplayMetrics();
            mDensity = displayMetrics.density;

            mRenderer = new MainRenderer(context);

            setEGLContextClientVersion(2);
            setEGLConfigChooser(8, 8, 8, 8, 16, 1);

            getHolder().setFormat(PixelFormat.RGBA_8888);

            setRenderer(mRenderer);
        }
    }

    public boolean getToggleSphereVisible() {
        return mRenderer.getToggleSphereVisible();
    }
    
    public boolean toggleSphereVisible() {
        return mRenderer.toggleSphereVisible();
    }

    public int getToggleMagnitude() {
        return mRenderer.getToggleMagnitude();
    }

    public int toggleMagnitude() {
        return mRenderer.toggleMagnitude();
    }

    public boolean getToggleLockX() {
        return mRenderer.getToggleLockX();
    }

    public boolean toggleLockX() {
        return mRenderer.toggleLockX();
    }

    public boolean getToggleLockY() {
        return mRenderer.getToggleLockY();
    }

    public boolean toggleLockY() {
        return mRenderer.toggleLockY();
    }

    public boolean getToggleRefs() {
        return mRenderer.getToggleRefs();
    }

    public boolean toggleRefs() {
        return mRenderer.toggleRefs();
    }

    public void doReset() {
        mRenderer.doReset();
    }

    public String toggleActiveCamera() {
        return mRenderer.toggleActiveCamera();
    }

    public boolean getTogglePlanetRotation() {
        return mRenderer.getTogglePlanetRotation();
    }

    public boolean togglePlanetRotation() {
        return mRenderer.togglePlanetRotation();
    }
    
    public boolean getToggleLightRotation() {
        return mRenderer.getToggleLightRotation();
    }
    
    public boolean toggleLightRotation() {
        return mRenderer.toggleLightRotation();
    }
    
    public boolean getToggleCameraRotation() {
        return mRenderer.getToggleCameraRotation();
    }

    public boolean toggleCameraRotation() {
        return mRenderer.toggleCameraRotation();
    }
}


