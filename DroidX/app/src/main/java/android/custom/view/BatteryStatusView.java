package android.custom.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.SensorManager;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import quadx.com.droidx.R;

/**
 * TODO: document your custom view class.
 */
public class BatteryStatusView extends FrameLayout {

    private ImageView mBatteryIcon;
    private TextView mBatteryText;
    private int status = 10;

    public BatteryStatusView(Context context) {
        super(context);
        init(null, 0);
    }

    public BatteryStatusView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public BatteryStatusView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_battery_status, this);

        mBatteryIcon = (ImageView) findViewById(R.id.batteryIcon);
        mBatteryText = (TextView) findViewById(R.id.batteryText);
        setBatteryStatus(status);
    }

    public void setBatteryStatus(int status) {
        this.status = status;
        refreshBatteryStatusIcons(status);
    }

    public void refreshBatteryStatusIcons(int status) {
        this.status = status;

        int imageNum = (int) Math.ceil(status/20);
        int heightImage = 16 + (imageNum)*(216+32);

        Bitmap mBatteryIcons = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.battery_icons);
        Bitmap statusBitmap = Bitmap.createBitmap(mBatteryIcons, 40, heightImage, 512, 216);
        mBatteryIcon.setImageBitmap(statusBitmap);
        mBatteryIcon.setScaleType(ImageView.ScaleType.FIT_CENTER);

        mBatteryText.setText(" " + status + "%");
    }
}
