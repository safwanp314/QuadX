package quadx.com.droidx.ui.info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quadx.com.droidx.resources.InfoMap;
import quadx.com.droidx.resources.Resources;
import quadx.drone.controller.Drone;
import quadx.drone.controller.utils.events.DroneEvent;
import quadx.drone.controller.utils.events.DroneEventListener;

/**
 * Created by SAFWAN on 9/22/15.
 */
public class DroneInfoEventListener implements DroneEventListener {

    private static final Logger DRONE_LOGGER = LoggerFactory.getLogger("quadx.com.droidx.drone.log");

    public InfoFragmentUpdater infoFragmentUpdater;

    public void actionPerformed(DroneEvent event) {

        Drone drone = event.getDrone();
        InfoMap.ITEM_MAP.put("Pitch", String.format("%.5f", drone.getNav().pitch));
        InfoMap.ITEM_MAP.put("Roll", String.format("%.5f", drone.getNav().roll));
        InfoMap.ITEM_MAP.put("Yaw", String.format("%.5f", drone.getNav().yaw));
        InfoMap.ITEM_MAP.put("Altitude", String.format("%.5f", drone.getNav().altitude));
        InfoMap.ITEM_MAP.put("Latitude", String.format("%.5f", drone.getNav().latitude));
        InfoMap.ITEM_MAP.put("Longitude", String.format("%.5f", drone.getNav().longitude));

        if(infoFragmentUpdater!=null) {
            infoFragmentUpdater.updateInfo();
        }
        DRONE_LOGGER.info(InfoMap.ITEM_MAP.toString());
    }
}
