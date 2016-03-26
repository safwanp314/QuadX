package android.custom.menu;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quadx.com.droidx.R;

/**
 * Created by SAFWAN on 11/9/15.
 */
public class QuickActionBar extends PopupWindows {

    private static final Logger DEBUG_LOGGER = LoggerFactory.getLogger("quadx.com.droidx.debug");

    private final View root;
    private final LayoutInflater inflater;

    private ViewGroup itemArray;

    int xPos, yPos;
    private int pos = 1;

    public QuickActionBar(Context context) {

        super(context);

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        root = inflater.inflate(R.layout.menu_popup_windows, null);

        setContentView(root);

        itemArray = (ViewGroup) root.findViewById(R.id.itemList);

        //init();
    }

    public void addItem(ActionItem action) {

        String title = action.getTitle();
        Drawable icon = action.getIcon();
        View.OnClickListener listener = action.getListener();

        ViewGroup actionHolder = (ViewGroup) inflater.inflate(R.layout.menu_quick_action, null);
        ImageView actionImage = (ImageView) actionHolder.findViewById(R.id.image);
        TextView actionTxt = (TextView) actionHolder.findViewById(R.id.caption);

        if (icon != null) {
            actionImage.setImageDrawable(icon);
        } else {
            actionImage.setVisibility(View.GONE);
        }

        if (title != null) {
            actionTxt.setText(title);
        } else {
            actionTxt.setVisibility(View.GONE);
        }

        if (listener != null) {
            actionHolder.setOnClickListener(listener);
        }
        actionHolder.setFocusable(true);
        actionHolder.setClickable(true);
        itemArray.addView(actionHolder, pos);
        pos++;
    }

    private void displayDirectionChoice(int directionChoice, int requestedX) {

        final View arrowDown = root.findViewById(R.id.arrow_down);
        final View arrowUp = root.findViewById(R.id.arrow_up);

        final View showArrow = (directionChoice == R.id.arrow_up) ? arrowUp : arrowDown;
        final View hideArrow = (directionChoice == R.id.arrow_up) ? arrowDown : arrowUp;
        final int arrowWidth = arrowUp.getMeasuredWidth();
        showArrow.setVisibility(View.VISIBLE);
        ViewGroup.MarginLayoutParams param = (ViewGroup.MarginLayoutParams) showArrow.getLayoutParams();
        param.leftMargin = requestedX - arrowWidth / 2;
        hideArrow.setVisibility(View.INVISIBLE);
    }

    public void show() {

        if (anchor != null) {
            int screenWidth = windowManager.getDefaultDisplay().getWidth();
            int screenHeight = windowManager.getDefaultDisplay().getHeight();

            root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            root.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int rootWidth = Math.min(root.getMeasuredWidth(), (int) (0.75 * screenWidth));
            int rootHeight = Math.min(root.getMeasuredHeight(), (int) (0.75 * screenHeight));

            preShow(rootWidth, rootHeight);

            int[] location = new int[2];
            anchor.getLocationOnScreen(location);
            Rect anchorRect = new Rect(location[0], location[1], location[0] + anchor.getWidth(), location[1] + anchor.getHeight());

            xPos = location[0];
            yPos = anchorRect.top - rootHeight;
            boolean popupOnTop = true;

            int pointerMargin = anchorRect.centerX() - xPos;
            if (xPos + rootWidth > screenWidth) {
                pointerMargin = anchorRect.centerX() - (screenWidth - rootWidth);
            }
            if (screenHeight - anchorRect.bottom > rootHeight) {
                yPos = anchorRect.bottom;
                popupOnTop = false;
            }

            displayDirectionChoice(((popupOnTop) ? R.id.arrow_down : R.id.arrow_up), pointerMargin);
            window.showAtLocation(this.anchor, Gravity.LEFT | Gravity.TOP, xPos, yPos);
        }
    }
}
