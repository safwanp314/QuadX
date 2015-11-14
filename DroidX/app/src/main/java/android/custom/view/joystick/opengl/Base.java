package android.custom.view.joystick.opengl;

import android.graphics.Bitmap;
import android.opengl.GLUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Base {

    private FloatBuffer vertexBuffer;  // Buffer for vertex-array
    private float[] color = {0.3f, 0.3f, 0.3f, 1.0f};

    float textureCoordinates[] = {0.0f, 1.0f,
            1.0f, 1.0f,
            0.0f, 0.0f,
            1.0f, 0.0f};

    private float[][] getEdges(float x, float y, float z, float l_2, float b_2, float h, float t) {
        return new float[][]{
                {x - l_2, y - b_2, z + 0},    // 0. left-bottom-front
                {x + l_2, y - b_2, z + 0},    // 1. right-bottom-front
                {x - l_2, y - b_2, z + h},    // 2. left-top-front
                {x + l_2, y - b_2, z + h},    // 3. right-top-front
                {x - l_2, y + b_2, z + 0},    // 4. left-bottom-back
                {x + l_2, y + b_2, z + 0},    // 5. right-bottom-back
                {x - l_2, y + b_2, z + h},    // 6. left-top-back
                {x + l_2, y + b_2, z + h},    // 7. right-top-back
                {x - (l_2 - t), y - (b_2 - t), z + h},    // 8. left-top_in-front
                {x + (l_2 - t), y - (b_2 - t), z + h},    // 9. right-top_in-front
                {x - (l_2 - t), y + (b_2 - t), z + h},    // 10. left-top_in-back
                {x + (l_2 - t), y + (b_2 - t), z + h}     // 11. right-top_in-back
        };
    }

    private float[] getVertices(float x, float y, float z, float l, float b, float h, float t) {
        float[][] edges = getEdges(x, y, z, l / 2, b / 2, h, t);
        return new float[]{  // Vertices of the 6 faces
                // FRONT
                edges[0][0], edges[0][1], edges[0][2],  // 0. left-bottom-front
                edges[1][0], edges[1][1], edges[1][2],  // 1. right-bottom-front
                edges[2][0], edges[2][1], edges[2][2],  // 2. left-top-front
                edges[3][0], edges[3][1], edges[3][2],  // 3. right-top-front
                // BACK
                edges[4][0], edges[4][1], edges[4][2],  // 4. left-bottom-back
                edges[5][0], edges[5][1], edges[5][2],  // 5. right-bottom-back
                edges[6][0], edges[6][1], edges[6][2],  // 6. left-top-back
                edges[7][0], edges[7][1], edges[7][2],  // 7. right-top-back
                // LEFT
                edges[0][0], edges[0][1], edges[0][2],  // 0. left-bottom-front
                edges[4][0], edges[4][1], edges[4][2],  // 4. left-bottom-back
                edges[2][0], edges[2][1], edges[2][2],  // 2. left-top-front
                edges[6][0], edges[6][1], edges[6][2],  // 6. left-top-back
                // RIGHT
                edges[1][0], edges[1][1], edges[1][2],  // 1. right-bottom-front
                edges[5][0], edges[5][1], edges[5][2],  // 5. right-bottom-back
                edges[3][0], edges[3][1], edges[3][2],  // 3. right-top-front
                edges[7][0], edges[7][1], edges[7][2],  // 7. right-top-back
                // BOTTOM
                edges[0][0], edges[0][1], edges[0][2],  // 0. left-bottom-front
                edges[1][0], edges[1][1], edges[1][2],  // 1. right-bottom-front
                edges[4][0], edges[4][1], edges[4][2],  // 4. left-bottom-back
                edges[5][0], edges[5][1], edges[5][2],  // 5. right-bottom-back
                // FRONT-TOP
                edges[2][0], edges[2][1], edges[2][2],  // 2. left-top-front
                edges[3][0], edges[3][1], edges[3][2],  // 3. right-top-front
                edges[8][0], edges[8][1], edges[8][2],  // 8. left-top_in-front
                edges[9][0], edges[9][1], edges[9][2],  // 9. right-top_in-front
                // RIGHT-TOP
                edges[9][0], edges[9][1], edges[9][2],  // 9. right-top_in-front
                edges[3][0], edges[3][1], edges[3][2],  // 3. right-top-front
                edges[11][0], edges[11][1], edges[11][2],  // 11. right-top_in-back
                edges[7][0], edges[7][1], edges[7][2],  // 7. right-top-back
                // BACK-TOP
                edges[10][0], edges[10][1], edges[10][2],  // 10. left-top_in-back
                edges[11][0], edges[11][1], edges[11][2],  // 11. right-top_in-back
                edges[6][0], edges[6][1], edges[6][2],  // 6. left-top-back
                edges[7][0], edges[7][1], edges[7][2],  // 7. right-top-back
                // LEFT-TOP
                edges[10][0], edges[10][1], edges[10][2],  // 10. left-top_in-back
                edges[6][0], edges[6][1], edges[6][2],  // 6. left-top-back
                edges[8][0], edges[8][1], edges[8][2],  // 8. left-top_in-front
                edges[2][0], edges[2][1], edges[2][2]  // 2. left-top-front
        };
    }

    // Constructor - Set up the buffers
    public Base(float l, float b, float h, float t) {
        init(getVertices(0, 0, 0, l, b, h, t));
    }

    private void init(float[] vertices) {
        // Setup vertex-array buffer. Vertices in float. An float has 4 bytes
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder()); // Use native byte order
        vertexBuffer = vbb.asFloatBuffer(); // Convert from byte to float
        vertexBuffer.put(vertices);         // Copy data into buffer
        vertexBuffer.position(0);           // Rewind
    }

    // Draw the shape
    public void draw(GL10 gl) {

        gl.glFrontFace(GL10.GL_CCW);    // Front face in counter-clockwise orientation
        gl.glEnable(GL10.GL_CULL_FACE); // Enable cull face
        gl.glCullFace(GL10.GL_BACK);    // Cull the back face (don't display)

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        vertexBuffer.position(0);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);

        gl.glColor4f(color[0], color[1], color[2], color[3]);

        //FRONT
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
        //BACK
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 4, 4);
        //LEFT
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 8, 4);
        //RIGHT
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 12, 4);
        //BOTTOM
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 16, 4);
        //TOP-IN
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 20, 4);
        //TOP-IN
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 24, 4);
        //TOP-IN
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 28, 4);
        //TOP-IN
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 32, 4);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisable(GL10.GL_CULL_FACE);
    }
}