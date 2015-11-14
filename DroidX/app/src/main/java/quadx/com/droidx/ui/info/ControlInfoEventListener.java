package quadx.com.droidx.ui.info;

import android.custom.view.events.JoyButtonEvent;
import android.custom.view.events.JoyButtonEventListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quadx.com.droidx.resources.InfoMap;
import quadx.com.droidx.resources.Resources;

/**
 * Created by SAFWAN on 11/3/15.
 */
public class ControlInfoEventListener implements JoyButtonEventListener {

    private static final Logger DRONE_LOGGER = LoggerFactory.getLogger("quadx.com.droidx.drone.log");

    public InfoFragmentUpdater infoFragmentUpdater;

    @Override
    public void onSensorChanged(JoyButtonEvent event) {

        InfoMap.ITEM_MAP.put("Device-X", String.format("%.5f", event.xOrientation));
        InfoMap.ITEM_MAP.put("Device-Y", String.format("%.5f", event.yOrientation));
        InfoMap.ITEM_MAP.put("Device-Z", String.format("%.5f", event.zOrientation));

        if(infoFragmentUpdater!=null) {
            infoFragmentUpdater.updateInfo();
        }
        DRONE_LOGGER.info(InfoMap.ITEM_MAP.toString());
    }
}
