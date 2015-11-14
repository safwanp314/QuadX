package quadx.com.droidx.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.custom.menu.ActionItem;
import android.custom.menu.QuickActionBar;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by SAFWAN on 11/14/15.
 */
public class ViewChangeHandler implements View.OnClickListener {

    private static final Logger DEBUG_LOGGER = LoggerFactory.getLogger("quadx.com.droidx.debug");

    private QuickActionBar activeQab;
    private FragmentManager fragmentManager;
    private int bgFragmentContainer;
    private Map<ActionItem, Fragment> fragmentMap = new LinkedHashMap<>();
    private BgFragmentManager bgFragmentManager;
    private QuickActionBar qab;

    public ViewChangeHandler(FragmentActivity activity, int viewId, int bgFragmentContainer) {
        View view = activity.findViewById(viewId);
        qab = new QuickActionBar(view);
        qab.dismiss();

        fragmentManager = activity.getFragmentManager();
        bgFragmentManager = new BgFragmentManager(fragmentManager);
        this.bgFragmentContainer = bgFragmentContainer;
    }

    public void addActionFragment(ActionItem actionItem, Fragment fragment) {
        actionItem.addListener(new ActionItemListener(actionItem));
        if(!fragmentMap.containsValue(fragment)) {
            fragmentManager.beginTransaction().add(bgFragmentContainer, fragment).hide(fragment).commit();
        }
        fragmentMap.put(actionItem, fragment);
        qab.addItem(actionItem);
    }

    public void show(ActionItem actionItem) {
        Fragment fragment = fragmentMap.get(actionItem);
        bgFragmentManager.show(fragment);
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

            Fragment fragment = fragmentMap.get(id);
            bgFragmentManager.show(fragment);
            activeQab.dismiss();
        }
    }

    private class BgFragmentManager {

        private Fragment currentFragment;
        private FragmentManager fragmentManager;

        public BgFragmentManager(FragmentManager fragmentManager) {
            this.fragmentManager = fragmentManager;
        }

        public void show(Fragment fragment) {
            if (fragment != null) {
                if (this.currentFragment != null) {
                    fragmentManager.beginTransaction()
                            .hide(this.currentFragment)
                            .show(fragment)
                            .commit();

                } else {
                    fragmentManager.beginTransaction()
                            .show(fragment)
                            .commit();
                }
            }
            this.currentFragment = fragment;
        }
    }
}