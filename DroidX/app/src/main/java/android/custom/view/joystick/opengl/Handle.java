package android.custom.view.joystick.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Handle {

    private FloatBuffer vertexBuffer;  // Buffer for vertex-array
    private int numFaces = 6;
    private float[] color = {0.75f, 0.75f, 0.75f, 1.0f};

    private float[][] getEdges(float x, float y, float z, float l_2, float b_2, float h_2) {
        return new float[][]{
                {x - l_2, y - b_2, z + h_2},    // 0. left-bottom-front
                {x + l_2, y - b_2, z + h_2},    // 1. right-bottom-front
                {x - l_2, y + b_2, z + h_2},    // 2. left-top-front
                {x + l_2, y + b_2, z + h_2},    // 3. right-top-front
                {x - l_2, y - b_2, z - h_2},    // 4. left-bottom-back
                {x - l_2, y + b_2, z - h_2},    // 5. left-top-back
                {x + l_2, y - b_2, z - h_2},    // 6. right-bottom-back
                {x + l_2, y + b_2, z - h_2}};   // 7. right-top-back
    }

    private float[] getVertices(float x, float y, float z, float l, float b, float h) {
        float[][] edges = getEdges(x, y, z, l / 2, b / 2, h / 2);
        return new float[]{  // Vertices of the 6 faces
                // FRONT
                edges[0][0], edges[0][1], edges[0][2],  // 0. left-bottom-front
                edges[1][0], edges[1][1], edges[1][2],  // 1. right-bottom-front
                edges[2][0], edges[2][1], edges[2][2],  // 2. left-top-front
                edges[3][0], edges[3][1], edges[3][2],  // 3. right-top-front
                // BACK
                edges[6][0], edges[6][1], edges[6][2],  // 6. right-bottom-back
                edges[4][0], edges[4][1], edges[4][2],  // 4. left-bottom-back
                edges[7][0], edges[7][1], edges[7][2],  // 7. right-top-back
                edges[5][0], edges[5][1], edges[5][2],  // 5. left-top-back
                // LEFT
                edges[4][0], edges[4][1], edges[4][2],  // 4. left-bottom-back
                edges[0][0], edges[0][1], edges[0][2],  // 0. left-bottom-front
                edges[5][0], edges[5][1], edges[5][2],  // 5. left-top-back
                edges[2][0], edges[2][1], edges[2][2],  // 2. left-top-front
                // RIGHT
                edges[1][0], edges[1][1], edges[1][2],  // 1. right-bottom-front
                edges[6][0], edges[6][1], edges[6][2],  // 6. right-bottom-back
                edges[3][0], edges[3][1], edges[3][2],  // 3. right-top-front
                edges[7][0], edges[7][1], edges[7][2],  // 7. right-top-back
                // TOP
                edges[2][0], edges[2][1], edges[2][2],  // 2. left-top-front
                edges[3][0], edges[3][1], edges[3][2],  // 3. right-top-front
                edges[5][0], edges[5][1], edges[5][2],  // 5. left-top-back
                edges[7][0], edges[7][1], edges[7][2],  // 7. right-top-back
                // BOTTOM
                edges[4][0], edges[4][1], edges[4][2],  // 4. left-bottom-back
                edges[6][0], edges[6][1], edges[6][2],  // 6. right-bottom-back
                edges[0][0], edges[0][1], edges[0][2],  // 0. left-bottom-front
                edges[1][0], edges[1][1], edges[1][2]  // 1. right-bottom-front
        };
    }

    // Constructor - Set up the buffers
    public Handle(float l, float b, float h) {
        init(getVertices(0, 0, 0, l, b, h));
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
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);

        // Render all the faces
        for (int face = 0; face < numFaces; face++) {
            // Set the color for each of the faces
            gl.glColor4f(color[0], color[1], color[2], color[3]);
            // Draw the primitive from the vertex-array directly
            gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, face * 4, 4);
        }
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisable(GL10.GL_CULL_FACE);
    }
}