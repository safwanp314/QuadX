package android.custom.menu;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * Created by SAFWAN on 11/9/15.
 */
public class PopupWindows {

    protected final View anchor;
    protected final PopupWindow window;
    private View root;
    protected final WindowManager windowManager;

    private Drawable background = null;

    public PopupWindows(View anchor) {

        this.anchor = anchor;
        this.window = new PopupWindow(anchor.getContext());

        // when a touch even happens outside of the window
        // make the window go away
        this.window.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    PopupWindows.this.window.dismiss();
                    return true;
                }
                return false;
            }
        });

        this.windowManager = (WindowManager) this.anchor.getContext().getSystemService(Context.WINDOW_SERVICE);
        onCreate();
    }

    protected void onCreate() {
    }

    public void preShow(int width, int height) {

        if (this.root == null) {
            throw new IllegalStateException("setContentView was not called with a view to display.");
        }
        onShow();

        if (this.background == null) {
            this.window.setBackgroundDrawable(new BitmapDrawable());
        } else {
            this.window.setBackgroundDrawable(this.background);
        }

        this.window.setWidth(width);
        this.window.setHeight(height);
        this.window.setTouchable(true);
        this.window.setFocusable(true);
        this.window.setOutsideTouchable(true);

        this.window.setContentView(this.root);
    }

    protected void onShow() {
    }

    public void setContentView(View root) {
        this.root = root;
    }

    public void dismiss() {
        this.window.dismiss();
    }
}
