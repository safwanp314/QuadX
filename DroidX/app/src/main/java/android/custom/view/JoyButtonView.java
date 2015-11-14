package android.custom.view;

import android.content.Context;
import android.custom.view.events.JoyButtonEvent;
import android.custom.view.events.JoyButtonEventListener;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quadx.com.droidx.R;

/**
 * TODO: document your custom view class.
 */
public class JoyButtonView extends FrameLayout {

    private static final Logger DEBUG_LOGGER = LoggerFactory.getLogger("quadx.com.droidx.debug");

    private ImageButton imageButton;
    private int imageButtonSize;
    private SensorManager sensorManager;

    private final float[] mValuesMagnet = new float[3];
    private final float[] mValuesAccel = new float[3];
    private final float[] mRotationMatrix = new float[9];

    public final float[] mValuesOrientation = new float[3];

    private JoyButtonEventListener joyButtonEventListener;
    public void setJoyButtonEventListener(JoyButtonEventListener joyButtonEventListener) {
        this.joyButtonEventListener = joyButtonEventListener;
    }

    public JoyButtonView(Context context) {
        super(context);
        init(null, 0);
    }

    public JoyButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public JoyButtonView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        imageButtonSize = (w + h) / 2;

        FrameLayout.LayoutParams imageButtonParams = (FrameLayout.LayoutParams) imageButton.getLayoutParams();
        int margin = (int) (0.06f * imageButtonSize);
        imageButtonParams.setMargins(margin, margin, margin, margin);
        imageButton.setLayoutParams(imageButtonParams);
    }

    private void init(AttributeSet attrs, int defStyle) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_joy_button, this);

        imageButton = (ImageButton) findViewById(R.id.joyButtonViewImage);
        sensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);

        setListners(sensorManager, mEventListener);
        imageButton.setOnTouchListener(mTouchListener);
    }

    private final OnTouchListener mTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                FrameLayout.LayoutParams imageButtonParams = (FrameLayout.LayoutParams) imageButton.getLayoutParams();
                int margin = (int) (0.10f * imageButtonSize);
                imageButtonParams.setMargins(margin, margin, margin, margin);
                imageButton.setLayoutParams(imageButtonParams);
            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                FrameLayout.LayoutParams imageButtonParams = (FrameLayout.LayoutParams) imageButton.getLayoutParams();
                int margin = (int) (0.06f * imageButtonSize);
                imageButtonParams.setMargins(margin, margin, margin, margin);
                imageButton.setLayoutParams(imageButtonParams);
            }
            return false;
        }
    };

    private final SensorEventListener mEventListener = new SensorEventListener() {

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        public void onSensorChanged(SensorEvent event) {
            if (imageButton.isPressed()) {
                boolean sensorChanged = false;
                switch (event.sensor.getType()) {
                    case Sensor.TYPE_ACCELEROMETER:
                        System.arraycopy(event.values, 0, mValuesAccel, 0, 3);
                        sensorChanged = true;
                        break;
                    case Sensor.TYPE_MAGNETIC_FIELD:
                        System.arraycopy(event.values, 0, mValuesMagnet, 0, 3);
                        sensorChanged = true;
                        break;
                }
                if (sensorChanged) {
                    SensorManager.getRotationMatrix(mRotationMatrix, null, mValuesAccel, mValuesMagnet);
                    SensorManager.getOrientation(mRotationMatrix, mValuesOrientation);
                    DEBUG_LOGGER.debug("x:" + mValuesOrientation[0] + " y:" + mValuesOrientation[1] + " z:" + mValuesOrientation[2]);
                    if(joyButtonEventListener != null) {
                        JoyButtonEvent joyButtonEvent = new JoyButtonEvent();
                        joyButtonEvent.xOrientation = mValuesOrientation[0];
                        joyButtonEvent.yOrientation = mValuesOrientation[1];
                        joyButtonEvent.zOrientation = mValuesOrientation[2];
                        joyButtonEventListener.onSensorChanged(joyButtonEvent);
                    }
                }
            }
        }
    };

    // Register the event listener and sensor type.
    private void setListners(SensorManager sensorManager, SensorEventListener mEventListener) {

        sensorManager.registerListener(mEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(mEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_NORMAL);
    }
}