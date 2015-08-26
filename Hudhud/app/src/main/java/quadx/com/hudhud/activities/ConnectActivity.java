package quadx.com.hudhud.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import mavlink.AndroidMavLinkRadioStream;
import quadx.com.hudhud.R;
import quadx.com.hudhud.resources.Resources;
import quadx.drone.controller.Drone;
import quadx.drone.controller.utils.exceptions.DroneConnectionException;

public class ConnectActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        final Button connectButton = (Button) findViewById(R.id.connectButton);
        connectButton.setOnClickListener(new ConnectButtonClickEventListener());
    }

    private class ConnectButtonClickEventListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
            Drone drone = new Drone(0, 0, new AndroidMavLinkRadioStream(usbManager, 57600));
            try {
                drone.connect();
                Resources.drone = drone;
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            } catch (DroneConnectionException e) {
                e.printStackTrace();
            }
        }
    }
}