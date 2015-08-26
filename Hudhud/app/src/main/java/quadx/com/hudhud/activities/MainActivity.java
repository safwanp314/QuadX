package quadx.com.hudhud.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import quadx.com.hudhud.R;
import quadx.com.hudhud.resources.Resources;
import quadx.drone.controller.Drone;
import quadx.drone.controller.utils.exceptions.CommandAckFailureException;
import quadx.drone.controller.utils.exceptions.DroneConnectionException;

public class MainActivity extends Activity {

    private ToggleButton startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (ToggleButton) findViewById(R.id.startButton);
        startButton.setOnCheckedChangeListener(new StartButtonChangeEventListener());

        final Button emergencyButton = (Button) findViewById(R.id.emergencyButton);
        emergencyButton.setOnClickListener(new EmergencyButtonClickEventListener());
    }

    private class StartButtonChangeEventListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean status) {
            final Drone drone = Resources.drone;
            if (drone != null) {
                try {
                    if (status) {
                        drone.getActionHandler().start();
                    } else {
                        drone.getActionHandler().stop();
                    }
                } catch (DroneConnectionException e) {
                    e.printStackTrace();
                    startButton.setChecked(false);
                } catch (CommandAckFailureException e) {
                    e.printStackTrace();
                    startButton.setChecked(false);
                }
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
                    startButton.setChecked(false);
                } catch (DroneConnectionException e) {
                    e.printStackTrace();
                } catch (CommandAckFailureException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
