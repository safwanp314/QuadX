package quadx.com.droidx;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbManager;

import mavlink.AndroidMavLinkRadioStream;
import quadx.com.droidx.resources.Resources;
import quadx.com.droidx.ui.MainActivity;
import quadx.drone.controller.Drone;
import quadx.drone.controller.utils.exceptions.DroneConnectionException;

public class UsbPortConnectService extends IntentService {

    public static final String ACTION_USB_PORT_CONNECT_SERVICE = "quadx.com.droidx.action.USB_PORT_CONNECT_SERVICE";

    public UsbPortConnectService() {
        super("UsbPortConnectService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_USB_PORT_CONNECT_SERVICE.equals(action)) {
                handleActionUsbPortConnectService();
            }
        }
    }

    private void handleActionUsbPortConnectService() {
        Resources.usbPortConnect = true;
        UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        Drone drone = new Drone(0, 0, new AndroidMavLinkRadioStream(usbManager, 57600));
        try {
            drone.connect();
            Resources.setDrone(drone);
        } catch (DroneConnectionException e) {
            e.printStackTrace();
        }
    }
}
