package android.custom.view;

import android.content.Context;
import android.custom.view.events.JoyButtonEventListener;
import android.custom.view.joystick.opengl.XaxisCylinder;
import android.custom.view.joystick.opengl.YaxisCylinder;
import android.custom.view.joystick.opengl.Handle;
import android.custom.view.joystick.opengl.HandleRod;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.util.AttributeSet;
import android.view.MotionEvent;

import android.custom.view.joystick.opengl.Base;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by SAFWAN on 8/26/15.
 */
public class OnScreenJoyStick3DView extends GLSurfaceView implements JoyStickView {

    private float centreX;
    private float centreY;
    private float width;
    private float height;
    private JoystickGLRenderer renderer;

    private JoyButtonEventListener joyButtonEventListener;
    public void setJoyButtonEventListener(JoyButtonEventListener joyButtonEventListener) {
        this.joyButtonEventListener = joyButtonEventListener;
    }

    public OnScreenJoyStick3DView(Context context) {
        super(context);
        init(null, 0);
    }

    public OnScreenJoyStick3DView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Create an OpenGL ES 2.0 context.
        //setEGLContextClientVersion(2);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        centreX = 0.0f;
        centreY = 0.0f;
        width = w / 13;
        height = h / 13;
        renderer = new JoystickGLRenderer();

        renderer.setXdeg(0.0f);
        renderer.setYdeg(0.0f);
        setRenderer(renderer);

        super.setMeasuredDimension(w, h);
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        invalidate();
        float x = event.getX();
        float y = event.getY();
        float xWidth = getWidth();

        float xdeg = 0.0f, ydeg = 0.0f;
        if (event.getAction() != MotionEvent.ACTION_UP) {
            ydeg = 2.25f * (float) (Math.asin((x - xWidth / 2) / xWidth) * 180.0f / Math.PI);
            xdeg = 2.25f * (float) (Math.asin((y - xWidth / 2) / xWidth) * 180.0f / Math.PI);
        }
        renderer.setXdeg(xdeg);
        renderer.setYdeg(ydeg);
        requestRender();
        return true;
    }

    private class JoystickGLRenderer implements GLSurfaceView.Renderer {

        private Base base;
        private XaxisCylinder xaxisCylinder;
        private YaxisCylinder yaxisCylinder;
        private Handle handle;
        private HandleRod handleRod;

        private float xdeg, ydeg;

        public void setXdeg(float xdeg) {
            if (xdeg > 30.0f) {
                xdeg = 30.0f;
            } else if (xdeg < -30.0f) {
                xdeg = -30.0f;
            }
            this.xdeg = xdeg;
        }

        public void setYdeg(float ydeg) {
            if (ydeg > 32.0f) {
                ydeg = 32.0f;
            } else if (ydeg < -32.0f) {
                ydeg = -32.0f;
            }
            this.ydeg = ydeg;
        }

        private float baseW = width, baseB = height, baseT = 0.075f * width;
        private float xcylinderR = baseB / 2, xcylinderH = baseW - 2 * baseT;
        private float baseH = 2 * xcylinderR;
        private float baseOffset = xcylinderR;

        private float ycylinderH = 0.4f * xcylinderR, ycylinderR = 0.90f * xcylinderH / 2;
        private float xcutW = 0.80f * 2 * ycylinderR, xcutB = 1.15f * ycylinderH;
        private float ycylinderZOffset = 0.97f * (xcylinderR - ycylinderR);

        private float rodR = 0.4f * ycylinderH / 2, rodH = 0.23f * width;
        private float rodOffset = 0.95f * (ycylinderR + (rodH / 2) + ycylinderZOffset);

        private float handleW = 0.6f * ycylinderH, handleB = handleW, handleH = 0.95f * handleW;
        private float handleOffset = rodOffset + (rodH / 2);

        public JoystickGLRenderer() {
            base = new Base(baseW, baseB, baseH, baseT);//0, 0, 0, 3, 3, 1
            xaxisCylinder = new XaxisCylinder(xcylinderR, xcylinderH, xcutW, xcutB);
            yaxisCylinder = new YaxisCylinder(ycylinderR, ycylinderH);
            handleRod = new HandleRod(rodR, rodH);//0, 1.75f, 0, 0.6f, 2.0f, 0.6f
            handle = new Handle(handleW, handleB, handleH);//0, 2.8f, 0, 1.0f, 0.6f, 1.0f
        }

        /**
         * The Surface is created/init()
         */
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {

            gl.glShadeModel(GL10.GL_SMOOTH);            //Enable Smooth Shading
            gl.glClearColor(1.0f, 1.0f, 1.0f, 0.5f);    //Black Background
            gl.glClearDepthf(1.0f);                    //Depth Buffer Setup
            gl.glEnable(GL10.GL_DEPTH_TEST);            //Enables Depth Testing
            gl.glDepthFunc(GL10.GL_LEQUAL);            //The Type Of Depth Testing To Do

            //Really Nice Perspective Calculations
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
        }

        /**
         * Here we do our drawing
         */
        public void onDrawFrame(GL10 gl) {
            //Clear Screen And Depth Buffer
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
            gl.glLoadIdentity();                    //Reset The Current Modelview Matrix

            gl.glTranslatef(0.0f, 0.0f, -10000.0f);
            //gl.glRotatef(-90, 0, 1, 0);
            //gl.glRotatef(-90, 1, 0, 0);

            gl.glPushMatrix();
            gl.glTranslatef(0.0f, 0.0f, -baseOffset);
            base.draw(gl);
            gl.glPopMatrix();

            drawComponent(gl, xdeg, ydeg);
        }

        private void drawComponent(GL10 gl, float xdeg, float ydeg) {
            // ----------------------------------------------------------------
            // ----------------------------------------------------------------
            gl.glPushMatrix();
            gl.glRotatef(xdeg, 1, 0, 0);
            gl.glRotatef(-90, 0, 0, 1);
            gl.glRotatef(-90, 0, 1, 0);
            xaxisCylinder.draw(gl);
            gl.glRotatef(90, 0, 1, 0);
            gl.glRotatef(90, 0, 0, 1);
            gl.glPopMatrix();
            // ----------------------------------------------------------------
            // ----------------------------------------------------------------
            gl.glRotatef(ydeg, 0, 1, 0);
            //-------------------------------
            gl.glPushMatrix();
            float ycylinderXOffset = (float) (xcylinderR * Math.sin(xdeg * Math.PI / 180.0f));
            gl.glTranslatef(0.0f, -ycylinderXOffset, ycylinderZOffset);
            gl.glRotatef(-90, 0, 1, 0);
            yaxisCylinder.draw(gl);
            gl.glPopMatrix();
            //-------------------------------
            gl.glPushMatrix();
            float rodXAngle = (float) ((float) Math.atan(ycylinderXOffset / (ycylinderZOffset + ycylinderR)) * 180.0f / Math.PI);
            gl.glRotatef(rodXAngle, 1, 0, 0);
            //-------------------------------
            gl.glPushMatrix();
            gl.glRotatef(-90, 1, 0, 0);
            gl.glTranslatef(0.0f, -rodOffset, 0.0f);
            handleRod.draw(gl);
            gl.glPopMatrix();
            //-------------------------------
            gl.glPushMatrix();
            gl.glTranslatef(0.0f, 0.0f, handleOffset);
            handle.draw(gl);
            gl.glPopMatrix();
            //-------------------------------
            gl.glPopMatrix();
            // ----------------------------------------------------------------
            // ----------------------------------------------------------------

        }

        /**
         * If the surface changes, reset the view
         */
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            if (height == 0) {                        //Prevent A Divide By Zero By
                height = 1;                        //Making Height Equal One
            }
            gl.glViewport(0, 0, width, height);    //Reset The Current Viewport
            gl.glMatrixMode(GL10.GL_PROJECTION);    //Select The Projection Matrix
            gl.glLoadIdentity();                    //Reset The Projection Matrix

            //GLU.gluLookAt(gl, 0, 0, 25, 0, 0, 0, 0, 1, 0);
            //Calculate The Aspect Ratio Of The Window
            GLU.gluPerspective(gl, 0.15f, (float) width / (float) height, 9975.0f, 10025.0f);

            gl.glMatrixMode(GL10.GL_MODELVIEW);    //Select The Modelview Matrix
            gl.glLoadIdentity();                    //Reset The Modelview Matrix
        }
    }
}
