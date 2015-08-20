package quadx.com.hudhud.activities;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.io.IOException;

import comm.serial.Cp21xxSerialDriver;
import comm.serial.UsbSerialDriver;
import comm.serial.UsbSerialPort;
import quadx.com.hudhud.R;

public class MainActivity extends Activity {

    private UsbSerialPort serialPort;
    private Switch switch1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        switch1 = (Switch) findViewById(R.id.switch1);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }
}