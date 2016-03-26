package quadx.com.droidx.ui;

import android.accounts.Account;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.custom.menu.ViewChangeHandler;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.ToggleButton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quadx.com.droidx.R;
import quadx.com.droidx.resources.Resources;
import quadx.com.droidx.resources.Settings;
import quadx.com.utils.GoogleAccountManager;
import quadx.drone.controller.Drone;
import quadx.drone.controller.utils.exceptions.CommandAckFailureException;
import quadx.drone.controller.utils.exceptions.DroneConnectionException;

public abstract class MainActivity extends Activity {

    private static final Logger DEBUG_LOGGER = LoggerFactory.getLogger("quadx.com.droidx.debug");

    protected ImageButton viewChangeButton;
    public static ViewChangeHandler viewChangeHandler;

    protected ToggleButton startButton;
    protected ImageButton settingButton;
    protected Button emergencyButton;
    protected SeekBar engineFactorSeekBar;

    protected View onScreenJoySticks;
    protected static boolean onScreenJoySticksStatus = true;
    protected Button onScreenJoyStickViewHideButton;

    private static final int RESULT_SETTINGS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutViewId());
        setViewResources();
    }

    protected void setViewResources() {
        //-------------------------------------------------------
        startButton = (ToggleButton) findViewById(R.id.startButton);
        emergencyButton = (Button) findViewById(R.id.emergencyButton);
        engineFactorSeekBar = (SeekBar) findViewById(R.id.engineFactorSeekBar);
        //-------------------------------------------------------
        settingButton = (ImageButton) findViewById(R.id.settingButton);
        settingButton.setOnClickListener(new SettingButtonClickEventListener());

        viewChangeButton = (ImageButton) findViewById(R.id.viewChangeButton);
        viewChangeHandler.setAnchor(viewChangeButton);

        onScreenJoySticks = (View) findViewById(R.id.onScreenJoySticks);

        onScreenJoyStickViewHideButton = (Button) findViewById(R.id.onScreenJoyStickViewHideButton);
        onScreenJoyStickViewHideButton.setOnClickListener(new OnScreenJoyStickViewHideButtonClickEventListener());
        //-------------------------------------------------------
        onSettingChanged();
        if (Resources.usbPortConnect) {
            onUsbPortConnect();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(onScreenJoySticksStatus) {
            onScreenJoySticksStatus = true;
            onScreenJoyStickViewHideButton.setText("Hide");
            onScreenJoySticks.setVisibility(View.VISIBLE);
        } else {
            onScreenJoySticksStatus = false;
            onScreenJoyStickViewHideButton.setText("Show");
            onScreenJoySticks.setVisibility(View.INVISIBLE);
        }
        //-------------------------------------------------------
    }

    protected abstract int getLayoutViewId();

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void onUsbPortConnect() {
        startButton.setOnCheckedChangeListener(new StartButtonChangeEventListener());
        emergencyButton.setOnClickListener(new EmergencyButtonClickEventListener());
        engineFactorSeekBar.setOnSeekBarChangeListener(new EngineFactorSeekBarEventListener());
        //-------------------------------------------------------
        //Resources.setInfoFragmentUpdater(infoFragment);
        //OrientationJoyStickView joyButtonView = (OrientationJoyStickView) findViewById(R.id.joyButton);
        //Resources.setJoyButtonView(joyButtonView);
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
            Intent settingIntent = new Intent(getApplicationContext(), SettingsActivity.class);
            settingIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivityForResult(settingIntent, RESULT_SETTINGS);
        }
    }

    private class OnScreenJoyStickViewHideButtonClickEventListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if(!onScreenJoySticksStatus) {
                onScreenJoySticksStatus = true;
                onScreenJoyStickViewHideButton.setText("Hide");
                onScreenJoySticks.setVisibility(View.VISIBLE);
            } else {
                onScreenJoySticksStatus = false;
                onScreenJoyStickViewHideButton.setText("Show");
                onScreenJoySticks.setVisibility(View.INVISIBLE);
            }
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

        try {
            View onScreenJoyStickLayout = findViewById(R.id.onScreenJoyStickLayout);
            if (onScreenJoyStickLayout != null)
                onScreenJoyStickLayout.setVisibility(View.GONE);
            View orientationJoyStickLayout = findViewById(R.id.orientationJoyStickLayout);
            if (orientationJoyStickLayout != null)
                orientationJoyStickLayout.setVisibility(View.GONE);
            View onScreenJoyStick3DLayout = findViewById(R.id.onScreenJoyStick3DLayout);
            if (onScreenJoyStick3DLayout != null)
                onScreenJoyStick3DLayout.setVisibility(View.GONE);

            if (joyStickType != null) {
                switch (joyStickType) {
                    case "OnScreenJoyStick":
                        if (onScreenJoyStickLayout != null)
                            onScreenJoyStickLayout.setVisibility(View.VISIBLE);
                        break;
                    case "OrientationJoyStick":
                        if (orientationJoyStickLayout != null)
                            orientationJoyStickLayout.setVisibility(View.VISIBLE);
                        break;
                    case "OnScreenJoyStick3D":
                        if (onScreenJoyStick3DLayout != null)
                            onScreenJoyStick3DLayout.setVisibility(View.VISIBLE);
                        break;
                    default:
                        if (onScreenJoyStickLayout != null)
                            onScreenJoyStickLayout.setVisibility(View.VISIBLE);
                        break;
                }
            } else {
                if (onScreenJoyStickLayout != null)
                    onScreenJoyStickLayout.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {

        }
    }
}