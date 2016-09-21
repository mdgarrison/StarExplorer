package com.literata.maddog.starexplorer;

import android.content.Context;
import android.opengl.GLES20;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class StarField extends ModelObject {
    private Utils mUtilStar = new Utils();
    private int mTotalStars;
    public int mMagCount[] = new int[5];

    public class starData                                                                                                                    //1
    {
        float x;
        float y;
        float z;
        float mag;
        float r,g,b,a;
        float ra, dec;
    }

    starData[] m_Data;

    public StarField(Context context, SceneManager mgr) {
        super(context, mgr);
        initializeModel();
        mDrawMode = GLES20.GL_POINTS;
        mStride = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT;
    }

    public void toggleMagnitude(int magIndex) {
        mNumVerticesToDraw = 0;
        for (int i = 0; i <= magIndex; i++) {
            mNumVerticesToDraw += mMagCount[i];
        }
    }

    protected void initializeModel() {
        String xml;
        xml = mUtilStar.readPlistFromAssets(mContext, "USNOStarCatalog10.xml");

        final Document doc = mUtilStar.XMLfromString(xml);
        final NodeList nodes = doc.getElementsByTagName("TR");

        mTotalStars = nodes.getLength();

        m_Data = new starData[mTotalStars];

        for (int i = 0; i < mTotalStars; i++) {
            m_Data[i] = new starData();
            Node starDat = nodes.item(i);

            NodeList data = starDat.getChildNodes();

            String raStr  = data.item(0).getTextContent();
            String decStr = data.item(1).getTextContent();
            String magStr = data.item(3).getTextContent();

            try {
                m_Data[i].ra = Float.parseFloat(raStr);
                m_Data[i].dec = Float.parseFloat(decStr);
                m_Data[i].mag = Float.parseFloat(magStr);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (m_Data[i].mag > 4.0) {
                mMagCount[4]++;
            }
            else if (m_Data[i].mag > 3.0) {
                mMagCount[3]++;
            }
            else if (m_Data[i].mag > 2.0) {
                mMagCount[2]++;
            }
            else if (m_Data[i].mag > 1.0) {
                mMagCount[1]++;
            }
            else {
                mMagCount[0]++;
            }

            mUtilStar.sphereToRectTheta(
                    m_Data[i].ra / Utils.DEGREES_PER_RADIAN,
                    m_Data[i].dec / Utils.DEGREES_PER_RADIAN ,
                    Utils.STANDARD_RADIUS);

            m_Data[i].x = mUtilStar.getXPrime();
            m_Data[i].y = mUtilStar.getYPrime();
            m_Data[i].z = mUtilStar.getZPrime();

            m_Data[i].mag += 1.44f;

            float tmpMag = (6.43f - m_Data[i].mag) / 6.43f;

            if (tmpMag < 0.15f) {
                tmpMag = 0.15f;
            }
            m_Data[i].r = m_Data[i].g = m_Data[i].b = tmpMag;
            m_Data[i].a = 1.0f;

            if ((m_Data[i].ra == 0.0f) && (m_Data[i].dec == 0.0f)) {
                m_Data[i].r = 1.0f;
                m_Data[i].g = m_Data[i].b = 0.0f;
            }
        }

        mVertexData = new float[mTotalStars * (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT)];
        
        for (int i = 0; i < mTotalStars; i++) {
            mVertexData[i + (7 * i) + 0] = m_Data[i].x;
            mVertexData[i + (7 * i) + 1] = m_Data[i].y;
            mVertexData[i + (7 * i) + 2] = m_Data[i].z;
            mVertexData[i + (7 * i) + 3] = 1.0f;
            mVertexData[i + (7 * i) + 4] = m_Data[i].r;
            mVertexData[i + (7 * i) + 5] = m_Data[i].g;
            mVertexData[i + (7 * i) + 6] = m_Data[i].b;
            mVertexData[i + (7 * i) + 7] = m_Data[i].a;
        }
        makeFloatBuffer();
        mNumVertices = mVertexData.length / (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT);
        mNumVerticesToDraw = mNumVertices;
    }

    public void onDrawFrame(float[] viewMat, float[] projMat) {
        constructMvpMatrix(viewMat, projMat);
        drawVBO();
    }

    public void drawVBO() {
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
