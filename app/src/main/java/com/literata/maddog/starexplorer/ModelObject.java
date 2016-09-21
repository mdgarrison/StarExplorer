package com.literata.maddog.starexplorer;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public abstract class ModelObject {
    protected SceneManager mManager;
    protected Context mContext;
    public float[] mVertexData;
    public FloatBuffer mDataBuffer;
    public int mVboIndex;
    public boolean mIsVisible;
    
    protected int mNumVertices;
    protected int mNumVerticesToDraw;
    protected int mDrawMode;
    protected int mStride;
    
    protected final float[] mLocation     = new float[3];
    protected final float[] mModelMatrix  = new float[16];
    protected final float[] mMvpMatrix    = new float[16];
    protected final float[] mMvMatrix     = new float[16];
    protected final float[] mNormalMatrix = new float[9];
    
    public static int POSITION_COMPONENT_COUNT = 4;
    public static int COLOR_COMPONENT_COUNT    = 4;
    public static int NORMAL_COMPONENT_COUNT   = 3;
    public static int TEXTURE_COMPONENT_COUNT  = 2;
    public static int BYTES_PER_FLOAT          = 4;
    
    protected ShaderProgram mPrimaryShader   = null;
    protected ShaderProgram mSecondaryShader = null;
    
    public ModelObject(Context context, SceneManager mgr) {
        mContext  = context;
        mManager = mgr;
        Matrix.setIdentityM(mModelMatrix, 0);
        mIsVisible = true;
        setLocation(0f, 0f, 0f);
    }
    
    public float[] getModelMatrix() {
        return mModelMatrix;
    }
    
    public float[] getLocation() {
        return mLocation;
    }
    
    public void setLocation(float x, float y, float z) {
        mLocation[0] = x;
        mLocation[1] = y;
        mLocation[2] = z;
    }
    
    public void setPrimaryShader(ShaderProgram shader) {
        mPrimaryShader = shader;
    }
    
    public ShaderProgram getPrimaryShader() {
        return mPrimaryShader;
    }
    
    public void setSecondaryShader(ShaderProgram shader) {
        mSecondaryShader = shader;
    }
    
    public ShaderProgram getSecondaryShader() {
        return mSecondaryShader;
    }
    
    public void onSurfaceCreated(int vboIndex) {
        mVboIndex = vboIndex;
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, mDataBuffer.capacity() * BYTES_PER_FLOAT, mDataBuffer, GLES20.GL_STATIC_DRAW);
    }

    public int getNumVerticesToDraw() {
        return mNumVerticesToDraw;    	
    }
    
    public void setNumVerticesToDraw(int numVerts) {
        mNumVerticesToDraw = numVerts;
    }
    
    public void setVisibleFlag(boolean isVisible) {
        mIsVisible = isVisible;
    }
    
    public boolean getVisibleFlag() {
        return mIsVisible;
    }
    
    protected void constructMvpMatrix(float[] view, float[] proj) {
        Matrix.multiplyMM(mMvMatrix, 0, view, 0, mModelMatrix, 0);
        Matrix.multiplyMM(mMvpMatrix, 0, proj, 0, mMvMatrix, 0);
    }
    
    protected void makeFloatBuffer()
    {
        ByteBuffer bb = ByteBuffer.allocateDirect(mVertexData.length * BYTES_PER_FLOAT);
        bb.order(ByteOrder.nativeOrder());
        mDataBuffer = bb.asFloatBuffer();
        mDataBuffer.put(mVertexData);
        mDataBuffer.position(0);
    }

    public int getVboIndex() {
        return mVboIndex;
    }


    protected abstract void initializeModel();

    public abstract void onDrawFrame(float[] viewMat, float[] projMat);
}
