package android.custom.menu;

import android.graphics.drawable.Drawable;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SAFWAN on 11/9/15.
 */
public class ActionItem {

    private String title;
    private Drawable icon;
    private List<View.OnClickListener> listeners = new ArrayList<>();
    private boolean selected;

    public ActionItem(String title, Drawable icon) {
        super();
        this.title = title;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public View.OnClickListener getListener() {
        return new ActionItemListener();
    }

    public void addListener(View.OnClickListener listener) {
        if (listener!=null)
            listeners.add(listener);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    private class ActionItemListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            for (View.OnClickListener listener:listeners) {
                listener.onClick(view);
            }
        }
    }
}
