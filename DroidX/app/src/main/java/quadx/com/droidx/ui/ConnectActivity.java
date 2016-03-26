package quadx.com.droidx.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.custom.menu.ActionItem;
import android.custom.menu.ViewChangeHandler;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import mavlink.AndroidMavLinkRadioStream;
import quadx.com.droidx.R;
import quadx.com.droidx.resources.Resources;
import quadx.com.droidx.ui.camera.CameraActivity;
import quadx.com.droidx.ui.info.InfoActivity;
import quadx.com.droidx.ui.map.MapActivity;
import quadx.drone.controller.Drone;
import quadx.drone.controller.utils.exceptions.DroneConnectionException;

public class ConnectActivity extends Activity {

    private Drone drone;

    private ActionItem initActionItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //-------------------------------------------------------
        setContentView(R.layout.activity_connect);
        final Button connectButton = (Button) findViewById(R.id.connectButton);
        connectButton.setOnClickListener(new ConnectButtonClickEventListener());
        //-------------------------------------------------------
        ImageButton viewChangeButton = (ImageButton) findViewById(R.id.viewChangeButton);
        ViewChangeHandler viewChangeHandler = new ViewChangeHandler(this);
        viewChangeHandler.setAnchor(viewChangeButton);

        Intent mapIntent = new Intent(getApplicationContext(), MapActivity.class);
        mapIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_NO_HISTORY);
        final ActionItem normalMapActionItem = new ActionItem("Map", getResources().getDrawable(R.drawable.map_icon));
        viewChangeHandler.addActionActivity(normalMapActionItem, mapIntent);
        normalMapActionItem.addListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MapActivity.setMapType("Normal");
            }
        });
        final ActionItem satelliteMapActionItem = new ActionItem("Satellite", getResources().getDrawable(R.drawable.satellite_map_icon));
        viewChangeHandler.addActionActivity(satelliteMapActionItem, mapIntent);
        satelliteMapActionItem.addListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MapActivity.setMapType("Satellite");
            }
        });

        Intent infoIntent = new Intent(getApplicationContext(), InfoActivity.class);
        infoIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_NO_HISTORY);
        final ActionItem infoActionItem = new ActionItem("Info", getResources().getDrawable(R.drawable.info_icon));
        viewChangeHandler.addActionActivity(infoActionItem, infoIntent);

        Intent cameraIntent = new Intent(getApplicationContext(), CameraActivity.class);
        cameraIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_NO_HISTORY);
        final ActionItem cameraActionItem = new ActionItem("Camera", getResources().getDrawable(R.drawable.camera_icon));
        viewChangeHandler.addActionActivity(cameraActionItem, cameraIntent);
        //-------------------------------------------------------
        initActionItem = normalMapActionItem;
        MainActivity.viewChangeHandler = viewChangeHandler;
        //-------------------------------------------------------
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
            drone = new Drone(0, 0, new AndroidMavLinkRadioStream(usbManager, 57600));
            drone.connect();
            Resources.setDrone(drone);
        } catch (DroneConnectionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        MainActivity.viewChangeHandler.show(initActionItem);
    }

    private class ConnectButtonClickEventListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            try {
                UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
                drone = new Drone(0, 0, new AndroidMavLinkRadioStream(usbManager, 57600));
                drone.connect();
                Resources.setDrone(drone);
                MainActivity.viewChangeHandler.show(initActionItem);
            } catch (DroneConnectionException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}