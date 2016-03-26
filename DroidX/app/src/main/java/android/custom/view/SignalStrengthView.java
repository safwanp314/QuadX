package android.custom.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.ImageView;

import quadx.com.droidx.R;

/**
 * Created by SAFWAN on 11/7/15.
 */
public class SignalStrengthView extends ImageView {

    private int strength = 100;

    public SignalStrengthView(Context context) {
        super(context);
        init(null, 0);
    }

    public SignalStrengthView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public SignalStrengthView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyle) {

        setSignalStregth(strength);
    }

    public void setSignalStregth(int strength) {
        this.strength = strength;
        refreshSignalStregthIcons(strength);
    }

    public void refreshSignalStregthIcons(int strength) {

        int imageNum = (int) Math.ceil(strength/20);
        int imageWidth = 80 + (imageNum)*(232+41);

        Bitmap mSignalIcons = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.signal_icons);
        Bitmap statusBitmap = Bitmap.createBitmap(mSignalIcons, imageWidth, 16, 273, 170);
        setImageBitmap(statusBitmap);
        setScaleType(ImageView.ScaleType.FIT_CENTER);
        setBackgroundColor(Color.TRANSPARENT);
    }
}
