package android.custom.view.joystick.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by SAFWAN on 9/1/15.
 */
public class Sphere {

    private float x, y, z;
    private float r;
    private float step = 2.0f;
    private float[] color = {0.5f, 0.5f, 0.5f, 1.0f};

    // Constructor - Set up the buffers
    public Sphere(float r) {
        this.r = r;
        this.step = step;
    }

    public Sphere(float r, float step) {
        this.r = r;
        this.step = step;
    }

    // Constructor - Set up the buffers
    public Sphere(float x, float y, float z, float r, float step) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.r = r;
        this.step = step;
    }

    public void draw(GL10 gl) {

        float[][] vertices = new float[32][3];
        ByteBuffer vbb;
        FloatBuffer vertexBuffer;
        vbb = ByteBuffer.allocateDirect(vertices.length * vertices[0].length * 4);
        vbb.order(ByteOrder.nativeOrder());
        vertexBuffer = vbb.asFloatBuffer();

        float angleA, angleB;
        float cos, sin;
        float r1, r2;
        float h1, h2;

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);

        for (angleA = -90.0f; angleA < 90.0f; angleA += step) {
            int n = 0;

            r1 = r * (float) Math.cos(angleA * Math.PI / 180.0);
            r2 = r * (float) Math.cos((angleA + step) * Math.PI / 180.0);
            h1 = r * (float) Math.sin(angleA * Math.PI / 180.0);
            h2 = r * (float) Math.sin((angleA + step) * Math.PI / 180.0);

            // Fixed latitude, 360 degrees rotation to traverse a weft
            for (angleB = 0.0f; angleB <= 360.0f; angleB += step) {

                cos = (float) Math.cos(angleB * Math.PI / 180.0);
                sin = -(float) Math.sin(angleB * Math.PI / 180.0);

                vertices[n][0] = (r2 * cos);
                vertices[n][1] = (h2);
                vertices[n][2] = (r2 * sin);
                vertices[n + 1][0] = (r1 * cos);
                vertices[n + 1][1] = (h1);
                vertices[n + 1][2] = (r1 * sin);
                vertexBuffer.put(vertices[n]);
                vertexBuffer.put(vertices[n + 1]);
                n += 2;
                if (n > 31) {
                    vertexBuffer.position(0);
                    gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
                    gl.glNormalPointer(GL10.GL_FLOAT, 0, vertexBuffer);
                    gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, n);
                    n = 0;
                    angleB -= step;
                }
            }
            vertexBuffer.position(0);
            gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
            gl.glNormalPointer(GL10.GL_FLOAT, 0, vertexBuffer);
            gl.glColor4f(color[0], color[1], color[2], color[3]);
            gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, n);
        }
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
    }
}
