package quadx.com.droidx.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import quadx.com.droidx.resources.Resources;
import quadx.drone.controller.Drone;
import quadx.drone.controller.utils.events.DroneEvent;
import quadx.drone.controller.utils.events.DroneEventListener;

/**
 * Created by SAFWAN on 9/19/15.
 */
public class InfoMap {

    public static Map<String, String> ITEM_MAP = new LinkedHashMap<>();

    static {
        ITEM_MAP.put("Pitch", String.format("%.5f", 0.0f));
        ITEM_MAP.put("Roll", String.format("%.5f", 0.0f));
        ITEM_MAP.put("Yaw", String.format("%.5f", 0.0f));
        ITEM_MAP.put("Altitude", String.format("%.5f", 0.0f));
        ITEM_MAP.put("Latitude", String.format("%.5f", 0.0f));
        ITEM_MAP.put("Longitude", String.format("%.5f", 0.0f));

        ITEM_MAP.put("Battery", String.format("%.0f%%", 0.0f));
        ITEM_MAP.put("Signal", String.format("%.0f", 0.0f));

        ITEM_MAP.put("Device-X", String.format("%.5f", 0.0f));
        ITEM_MAP.put("Device-Y", String.format("%.5f", 0.0f));
        ITEM_MAP.put("Device-Z", String.format("%.5f", 0.0f));
    }
}
