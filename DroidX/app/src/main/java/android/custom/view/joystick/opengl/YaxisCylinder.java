package android.custom.view.joystick.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by SAFWAN on 9/1/15.
 */
public class YaxisCylinder {

    private float x, y, z;
    private float r, h;
    private float step = 2.0f;
    private float[] color = {0.45f, 0.45f, 0.45f, 1.0f};

    // Constructor - Set up the buffers
    public YaxisCylinder(float r, float h) {
        this.r = r;
        this.h = h;
    }

    public YaxisCylinder(float r, float h, float step) {
        this.r = r;
        this.h = h;
        this.step = step;
    }

    public void draw(GL10 gl) {

        float[][] vertices = new float[32][3];

        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * vertices[0].length * 4);
        vbb.order(ByteOrder.nativeOrder());
        FloatBuffer vertexBuffer = vbb.asFloatBuffer();

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);

        int n = 0;
        // Fixed latitude, 360 degrees rotation to traverse a weft
        for (float angleA = 0.0f; angleA <= 360.0f; angleA += step) {

            float cos = (float) Math.cos(angleA * Math.PI / 180.0);
            float sin = -(float) Math.sin(angleA * Math.PI / 180.0);

            vertices[n][0] = r * cos;
            vertices[n][1] = -h / 2;
            vertices[n][2] = r * sin;
            vertices[n + 1][0] = r * cos;
            vertices[n + 1][1] = +h / 2;
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
        // ----------------------------------------------------------------
        // ----------------------------------------------------------------
        // ----------------------------------------------------------------
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
        n = 0;
        for (float angleA = 0.0f; angleA <= 360.0f; angleA += step) {

            float cos = (float) Math.cos(angleA * Math.PI / 180.0);
            float sin = -(float) Math.sin(angleA * Math.PI / 180.0);

            vertices[n][0] = r * cos;
            vertices[n][1] = -h / 2;
            vertices[n][2] = r * sin;
            vertices[n + 1][0] = 0;
            vertices[n + 1][1] = -h / 2;
            vertices[n + 1][2] = 0;
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
        // ----------------------------------------------------------------
        // ----------------------------------------------------------------
        // ----------------------------------------------------------------
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
        n = 0;
        for (float angleA = 0.0f; angleA <= 360.0f; angleA += step) {

            float cos = (float) Math.cos(angleA * Math.PI / 180.0);
            float sin = -(float) Math.sin(angleA * Math.PI / 180.0);

            vertices[n][0] = r * cos;
            vertices[n][1] = h / 2;
            vertices[n][2] = r * sin;
            vertices[n + 1][0] = 0;
            vertices[n + 1][1] = h / 2;
            vertices[n + 1][2] = 0;
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
    }
}
