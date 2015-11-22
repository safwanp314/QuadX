package android.custom.view;

import android.content.Context;
import android.custom.view.events.JoyButtonEventListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quadx.com.droidx.R;

/**
 * Created by SAFWAN on 11/16/15.
 */
public class OnScreenJoyStickView extends FrameLayout implements JoyStickView {

    private static final Logger DEBUG_LOGGER = LoggerFactory.getLogger("quadx.com.droidx.debug");

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
    }
}
