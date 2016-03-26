package android.custom.menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by SAFWAN on 11/14/15.
 */
public class ViewChangeHandler implements View.OnClickListener {

    private static final Logger DEBUG_LOGGER = LoggerFactory.getLogger("quadx.com.droidx.debug");

    private Context context;
    private QuickActionBar activeQab;
    private Map<ActionItem, Intent> activityMap = new LinkedHashMap<>();
    private QuickActionBar qab;
    private Intent activeIntent;

    public ViewChangeHandler(Context context) {
        this.context = context;
        qab = new QuickActionBar(context);
        qab.dismiss();
    }

    public void addActionActivity(ActionItem actionItem, Intent intent) {
        actionItem.addListener(new ActionItemListener(actionItem));
        activityMap.put(actionItem, intent);
        qab.addItem(actionItem);
    }

    public void show(ActionItem actionItem) {
        activeQab = qab;
        activeIntent = activityMap.get(actionItem);
        context.startActivity(activeIntent);
    }

    public void setAnchor(View anchor) {
        qab.setAnchor(anchor);
        anchor.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        DEBUG_LOGGER.trace("view change click");
        DEBUG_LOGGER.trace("view is:" + view.getId());
        qab.show();
        activeQab = qab;
    }

    private class ActionItemListener implements View.OnClickListener {

        private ActionItem id;

        public ActionItemListener(ActionItem id) {
            this.id = id;
        }

        @Override
        public void onClick(View view) {

            Intent intent = activityMap.get(id);
            if (intent != activeIntent) {
                context.startActivity(intent);
                activeIntent = intent;
            }
            activeQab.dismiss();
        }
    }
}