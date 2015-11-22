package quadx.com.droidx.resources;

import android.custom.view.OrientationJoyStickView;

import quadx.com.droidx.ui.info.ControlInfoEventListener;
import quadx.com.droidx.ui.info.DroneInfoEventListener;
import quadx.com.droidx.ui.info.InfoFragmentUpdater;
import quadx.drone.controller.Drone;

/**
 * Created by SAFWAN on 8/20/15.
 */
public class Resources {

    public static Drone drone;
    private static InfoFragmentUpdater infoFragmentUpdater;

    public static void setDrone(Drone drone) {
        Resources.drone = drone;
    }

    public static void setInfoFragmentUpdater(InfoFragmentUpdater fragmentUpdater) {
        infoFragmentUpdater = fragmentUpdater;
        DroneInfoEventListener droneInfoEventListener = new DroneInfoEventListener();
        drone.getEventHandler().addDroneEventListener(droneInfoEventListener);
        droneInfoEventListener.infoFragmentUpdater = infoFragmentUpdater;
    }
/*
    public static void setJoyButtonView(OrientationJoyStickView joyButtonView) {
        ControlInfoEventListener controlInfoEventListener = new ControlInfoEventListener();
        controlInfoEventListener.infoFragmentUpdater = infoFragmentUpdater;
        joyButtonView.setJoyButtonEventListener(controlInfoEventListener);
    }*/
}
