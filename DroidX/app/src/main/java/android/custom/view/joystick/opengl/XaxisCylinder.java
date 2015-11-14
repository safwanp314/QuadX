package android.custom.view.joystick.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by SAFWAN on 9/1/15.
 */
public class XaxisCylinder {

    private float cutBAngle, cutW;
    private float r, h;
    private float step = 0.5f;
    private float[] color = {0.4f, 0.4f, 0.4f, 1.0f};

    // Constructor - Set up the buffers
    public XaxisCylinder(float r, float h, float cutW, float cutB) {
        this.r = r;
        this.h = h;
        this.cutBAngle = (float) (Math.asin(cutB / (2 * r)) * 180.0f) / (float) Math.PI;
        this.cutW = cutW;
    }

    public void draw(GL10 gl) {

        float[][] vertices = new float[32][3];
        ByteBuffer vbb;
        FloatBuffer vertexBuffer;
        vbb = ByteBuffer.allocateDirect(vertices.length * vertices[0].length * 4);
        vbb.order(ByteOrder.nativeOrder());
        vertexBuffer = vbb.asFloatBuffer();

        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        int n = 0;
        for (float angleA = cutBAngle; angleA <= 360 - cutBAngle; angleA += step) {

            float cos = (float) Math.cos(angleA * Math.PI / 180.0);
            float sin = -(float) Math.sin(angleA * Math.PI / 180.0);

            vertices[n][0] = r * cos;
            vertices[n][1] = -h / 2;
            vertices[n][2] = r * sin;
            vertices[n + 1][0] = r * cos;
            vertices[n + 1][1] = h / 2;
            vertices[n + 1][2] = r * sin;

            vertexBuffer.put(vertices[n]);
            vertexBuffer.put(vertices[n + 1]);
            n += 2;
            if (n > 31) {
                vertexBuffer.position(0);
                gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
                gl.glColor4f(color[0], color[1], color[2], color[3]);
                gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, n);
                n = 0;
                angleA -= step;
            }
        }
        vertexBuffer.position(0);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
        gl.glColor4f(color[0], color[1], color[2], color[3]);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, n);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
        /*
        //------------------------------------------------------------------------------
        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        n = 0;
        for (float angleA = 0; angleA <= 360; angleA += step) {

            float cos = (float) Math.cos(angleA * Math.PI / 180.0);
            float sin = -(float) Math.sin(angleA * Math.PI / 180.0);

            vertices[n][0] = (r-0.5f) * cos;
            vertices[n][1] = -h / 2;
            vertices[n][2] = (r-0.5f) * sin;
            vertices[n + 1][0] = (r-0.5f) * cos;
            vertices[n + 1][1] = h / 2;
            vertices[n + 1][2] = (r-0.5f) * sin;

            vertexBuffer.put(vertices[n]);
            vertexBuffer.put(vertices[n + 1]);
            n += 2;
            if (n > 31) {
                vertexBuffer.position(0);
                gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
                gl.glColor4f(color2[0], color2[1], color2[2], color2[3]);
                gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, n);
                n = 0;
                angleA -= step;
            }
        }
        vertexBuffer.position(0);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
        gl.glColor4f(color2[0], color2[1], color2[2], color2[3]);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, n);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
        //------------------------------------------------------------------------------
        */
        //------------------------------------------------------------------------------
        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        n = 0;
        for (float angleA = -cutBAngle; angleA <= cutBAngle; angleA += step) {
            float cos = (float) Math.cos(angleA * Math.PI / 180.0);
            float sin = -(float) Math.sin(angleA * Math.PI / 180.0);

            vertices[n][0] = r * cos;
            vertices[n][1] = -cutW / 2;
            vertices[n][2] = r * sin;
            vertices[n + 1][0] = r * cos;
            vertices[n + 1][1] = -h / 2;
            vertices[n + 1][2] = r * sin;
            vertexBuffer.put(vertices[n]);
            vertexBuffer.put(vertices[n + 1]);
            n += 2;
            if (n > 31) {
                vertexBuffer.position(0);
                gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
                gl.glColor4f(color[0], color[1], color[2], color[3]);
                gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, n);
                n = 0;
                angleA -= step;
            }
        }
        vertexBuffer.position(0);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
        gl.glColor4f(color[0], color[1], color[2], color[3]);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, n);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
        //------------------------------------------------------------------------------
        //------------------------------------------------------------------------------
        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        n = 0;
        for (float angleA = -cutBAngle; angleA <= cutBAngle; angleA += step) {
            float cos = (float) Math.cos(angleA * Math.PI / 180.0);
            float sin = -(float) Math.sin(angleA * Math.PI / 180.0);

            vertices[n][0] = r * cos;
            vertices[n][1] = cutW / 2;
            vertices[n][2] = r * sin;
            vertices[n + 1][0] = r * cos;
            vertices[n + 1][1] = h / 2;
            vertices[n + 1][2] = r * sin;
            vertexBuffer.put(vertices[n]);
            vertexBuffer.put(vertices[n + 1]);
            n += 2;
            if (n > 31) {
                vertexBuffer.position(0);
                gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
                gl.glColor4f(color[0], color[1], color[2], color[3]);
                gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, n);
                n = 0;
                angleA -= step;
            }
        }
        vertexBuffer.position(0);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
        gl.glColor4f(color[0], color[1], color[2], color[3]);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, n);
        //-------------------------------------------------------------------
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
    }
}
