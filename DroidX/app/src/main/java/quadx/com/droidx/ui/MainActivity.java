package quadx.com.droidx.ui;

import android.accounts.Account;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.custom.menu.ActionItem;
import android.custom.view.OnScreenJoyStickView;
import android.custom.view.OrientationJoyStickView;
import android.hardware.Camera;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.ToggleButton;

import com.google.android.gms.maps.GoogleMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quadx.com.droidx.R;
import quadx.com.droidx.resources.Resources;
import quadx.com.droidx.resources.Settings;
import quadx.com.droidx.ui.camera.CameraFragment;
import quadx.com.droidx.ui.info.InfoFragment;
import quadx.com.droidx.ui.map.NormalMapFragment;
import quadx.com.utils.GoogleAccountManager;
import quadx.drone.controller.Drone;
import quadx.drone.controller.utils.exceptions.CommandAckFailureException;
import quadx.drone.controller.utils.exceptions.DroneConnectionException;

public class MainActivity extends FragmentActivity {

    private static final Logger DEBUG_LOGGER = LoggerFactory.getLogger("quadx.com.droidx.debug");

    private NormalMapFragment mapFragment = NormalMapFragment.newInstance(GoogleMap.MAP_TYPE_NORMAL);
    private CameraFragment camera0Fragment = CameraFragment.newInstance(Camera.CameraInfo.CAMERA_FACING_BACK);
    private InfoFragment infoFragment = InfoFragment.newInstance();

    private static final int RESULT_SETTINGS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //-------------------------------------------------------
        ToggleButton startButton = (ToggleButton) findViewById(R.id.startButton);
        startButton.setOnCheckedChangeListener(new StartButtonChangeEventListener());

        Button emergencyButton = (Button) findViewById(R.id.emergencyButton);
        emergencyButton.setOnClickListener(new EmergencyButtonClickEventListener());

        SeekBar engineFactorSeekBar = (SeekBar) findViewById(R.id.engineFactorSeekBar);
        engineFactorSeekBar.setOnSeekBarChangeListener(new EngineFactorSeekBarEventListener());

        //-------------------------------------------------------
        ImageButton viewChangeButton = (ImageButton) findViewById(R.id.viewChangeButton);

        ViewChangeHandler viewChangeHandler = new ViewChangeHandler(this, R.id.viewChangeButton, R.id.bgFragmentContainer);
        final ActionItem normalMap = new ActionItem("Map", getResources().getDrawable(R.drawable.map_icon));
        normalMap.addListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapFragment.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }
        });
        final ActionItem satelliteMap = new ActionItem("Satellite", getResources().getDrawable(R.drawable.satellite_map_icon));
        satelliteMap.addListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapFragment.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            }
        });
        final ActionItem info = new ActionItem("Info", getResources().getDrawable(R.drawable.info_icon));
        final ActionItem camera0 = new ActionItem("Camera0", getResources().getDrawable(R.drawable.camera_icon2));

        viewChangeHandler.addActionFragment(normalMap, mapFragment);
        viewChangeHandler.addActionFragment(satelliteMap, mapFragment);
        viewChangeHandler.addActionFragment(info, infoFragment);
        viewChangeHandler.addActionFragment(camera0, camera0Fragment);

        viewChangeHandler.show(normalMap);

        viewChangeButton.setOnClickListener(viewChangeHandler);
        //-------------------------------------------------------
        ImageButton settingButton = (ImageButton) findViewById(R.id.settingButton);
        settingButton.setOnClickListener(new SettingButtonClickEventListener());
        onSettingChanged();
        //-------------------------------------------------------
        Resources.setInfoFragmentUpdater(infoFragment);
        //OrientationJoyStickView joyButtonView = (OrientationJoyStickView) findViewById(R.id.joyButton);
        //Resources.setJoyButtonView(joyButtonView);
        //-------------------------------------------------------
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private class StartButtonChangeEventListener implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean status) {
            final Drone drone = Resources.drone;
            if (drone != null) try {
                if (status) {
                    drone.getActionHandler().start();
                } else {
                    drone.getActionHandler().stop();
                }
            } catch (DroneConnectionException | CommandAckFailureException e) {
                e.printStackTrace();
                ((ToggleButton) findViewById(R.id.startButton)).setChecked(false);
                ((SeekBar) findViewById(R.id.engineFactorSeekBar)).setProgress(0);
            }
        }
    }

    private class EmergencyButtonClickEventListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            final Drone drone = Resources.drone;
            if (drone != null) {
                try {
                    drone.getActionHandler().stop();
                    ((ToggleButton) findViewById(R.id.startButton)).setChecked(false);
                    ((SeekBar) findViewById(R.id.engineFactorSeekBar)).setProgress(0);
                } catch (DroneConnectionException | CommandAckFailureException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class EngineFactorSeekBarEventListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            final Drone drone = Resources.drone;
            if (drone != null) {
                double value = (double) i / 100;
                try {
                    drone.getActionHandler().engineFactorSlider(value, 0.0);
                } catch (DroneConnectionException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    }

    private class SettingButtonClickEventListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivityForResult(intent, RESULT_SETTINGS);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_SETTINGS:
                SharedPreferences sharedPrefs = PreferenceManager
                        .getDefaultSharedPreferences(this);

                String username = sharedPrefs.getString("prefUsername", "NULL");
                Account userAccount = new GoogleAccountManager(getApplicationContext()).getAccountByUserName(username);
                Settings.setUserAccount(userAccount);

                String joystick = sharedPrefs.getString("prefJoyStick", "NULL");
                Settings.setJoyStickView(joystick);

                Boolean sendCrashReport = sharedPrefs.getBoolean("prefSendReport", false);
                Settings.setSendCrashReport(sendCrashReport);

                int syncFrequency = Integer.parseInt(sharedPrefs.getString("prefSyncFrequency", "2"));
                Settings.setSyncFrequency(syncFrequency);

                onSettingChanged();
                break;
        }
    }

    private void onSettingChanged() {
        setJoyStick(Settings.joyStickType);
    }

    private void setJoyStick(String joyStickType) {

        View onScreenJoyStickLayout = findViewById(R.id.onScreenJoyStickLayout);
        onScreenJoyStickLayout.setVisibility(View.GONE);
        View orientationJoyStickLayout = findViewById(R.id.orientationJoyStickLayout);
        orientationJoyStickLayout.setVisibility(View.GONE);
        View onScreenJoyStick3DLayout = findViewById(R.id.onScreenJoyStick3DLayout);
        onScreenJoyStick3DLayout.setVisibility(View.GONE);

        if(joyStickType != null) {
            switch (joyStickType) {
                case "OnScreenJoyStick":
                    onScreenJoyStickLayout.setVisibility(View.VISIBLE);
                    break;
                case "OrientationJoyStick":
                    orientationJoyStickLayout.setVisibility(View.VISIBLE);
                    break;
                case "OnScreenJoyStick3D":
                    onScreenJoyStick3DLayout.setVisibility(View.VISIBLE);
                    break;
                default:
                    onScreenJoyStickLayout.setVisibility(View.VISIBLE);
                    break;
            }
        } else {
            onScreenJoyStickLayout.setVisibility(View.VISIBLE);
        }
    }
}