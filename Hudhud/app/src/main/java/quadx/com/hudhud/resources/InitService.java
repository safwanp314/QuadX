package quadx.com.hudhud.resources;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbManager;

import mavlink.AndroidMavLinkRadioStream;
import quadx.drone.controller.Drone;

public class InitService extends IntentService {

    public InitService() {
        super("InitService");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        Resources.drone = new Drone(0, 0, new AndroidMavLinkRadioStream(usbManager, 57600));
    }

    @Override
    protected void onHandleIntent(Intent intent) {
    }
}
