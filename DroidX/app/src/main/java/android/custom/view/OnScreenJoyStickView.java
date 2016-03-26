package android.custom.view;

import android.content.Context;
import android.custom.view.events.JoyButtonEventListener;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quadx.com.droidx.R;

/**
 * Created by SAFWAN on 11/16/15.
 */
public class OnScreenJoyStickView extends FrameLayout implements JoyStickView {

    private static final Logger DEBUG_LOGGER = LoggerFactory.getLogger("quadx.com.droidx.debug");

    private float centreX, centreY;
    private float radius;

    private ImageView joyStickLeft;
    private ImageView joyStickRight;
    private ImageView joyStickTop;
    private ImageView joyStickBottom;

    private JoyButtonEventListener joyButtonEventListener;

    public void setJoyButtonEventListener(JoyButtonEventListener joyButtonEventListener) {
        this.joyButtonEventListener = joyButtonEventListener;
    }

    public OnScreenJoyStickView(Context context) {
        super(context);
        init(null, 0);
    }

    public OnScreenJoyStickView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public OnScreenJoyStickView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_onscreen_joystick, this);

        joyStickLeft = (ImageView) findViewById(R.id.joyStickLeft);
        joyStickRight = (ImageView) findViewById(R.id.joyStickRight);
        joyStickTop = (ImageView) findViewById(R.id.joyStickTop);
        joyStickBottom = (ImageView) findViewById(R.id.joyStickBottom);

        setOnTouchListener(mTouchListener);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centreX = w / 2;
        centreY = h / 2;
        radius = ((w + h) / 2) / 2;

        DEBUG_LOGGER.debug("View Id:"+getId()+"->radius:" + radius);
    }

    private final OnTouchListener mTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

            DEBUG_LOGGER.trace("Start->OnScreenJoyStickView:OnTouchListener");
            int action = MotionEventCompat.getActionMasked(motionEvent);
            // Get the index of the pointer associated with the action.
            int index = MotionEventCompat.getActionIndex(motionEvent);

            float touchX = motionEvent.getX(index);
            float touchY = motionEvent.getY(index);
            float touchDist = (float) Math.sqrt((touchX - centreX) * (touchX - centreX) +
                    (touchY - centreY) * (touchY - centreY));
            float touchAngle = (float) Math.toDegrees(Math.atan2((touchY - centreY), (touchX - centreX)));
            DEBUG_LOGGER.debug("touch dist:" + touchDist + ", touch angle:" + touchAngle);
            if (touchDist < radius) {
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_POINTER_DOWN:
                        if (touchAngle > -135.0f && touchAngle <= -45.0f) {
                            joyStickTop.setVisibility(View.VISIBLE);
                        } else if (touchAngle > -45.0f && touchAngle <= 45.0f) {
                            joyStickRight.setVisibility(View.VISIBLE);
                        } else if (touchAngle > 45.0f && touchAngle <= 135.0f) {
                            joyStickBottom.setVisibility(View.VISIBLE);
                        } else if (touchAngle < -135.0f || touchAngle > 135.0f) {
                            joyStickLeft.setVisibility(View.VISIBLE);
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP:
                        joyStickLeft.setVisibility(View.GONE);
                        joyStickRight.setVisibility(View.GONE);
                        joyStickTop.setVisibility(View.GONE);
                        joyStickBottom.setVisibility(View.GONE);
                        break;
                }
                DEBUG_LOGGER.trace("End->OnScreenJoyStickView:OnTouchListener");
                return true;
            } else {
                DEBUG_LOGGER.trace("End->OnScreenJoyStickView:OnTouchListener");
                return false;
            }
        }
    };
}
