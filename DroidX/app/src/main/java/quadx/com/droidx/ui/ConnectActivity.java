package quadx.com.droidx.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import mavlink.AndroidMavLinkRadioStream;
import quadx.com.droidx.R;
import quadx.com.droidx.resources.Resources;
import quadx.drone.controller.Drone;
import quadx.drone.controller.utils.exceptions.DroneConnectionException;

public class ConnectActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
            Drone drone = new Drone(0, 0, new AndroidMavLinkRadioStream(usbManager, 57600));
            try {
                drone.connect();
                Resources.setDrone(drone);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            } catch (DroneConnectionException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            setContentView(R.layout.activity_connect);

            final Button connectButton = (Button) findViewById(R.id.connectButton);
            connectButton.setOnClickListener(new ConnectButtonClickEventListener());
        }
    }

    private class ConnectButtonClickEventListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            try {
                UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
                Drone drone = new Drone(0, 0, new AndroidMavLinkRadioStream(usbManager, 57600));
                try {
                    drone.connect();
                    Resources.setDrone(drone);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } catch (DroneConnectionException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}