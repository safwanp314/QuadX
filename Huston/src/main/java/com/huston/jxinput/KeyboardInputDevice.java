package com.huston.jxinput;

import java.awt.event.KeyEvent;

import quadx.drone.controller.Drone;

import com.huston.jxinput.buttons.ToggleKeyButton;
import com.huston.jxinput.events.StartButtonListener;
import com.huston.jxinput.events.YawLockButtonListener;

import de.hardcode.jxinput.JXInputManager;
import de.hardcode.jxinput.event.JXInputEventManager;

public class KeyboardInputDevice extends InputDevice {

	public KeyboardInputDevice(Drone drone) {
		super(drone);
		deviceName = "Keyboard";
	}

	@Override
	protected void configureDevice() {
		mDevice = JXInputManager.createKeyboardDevice();

		mDevice.createButton(KeyEvent.VK_ESCAPE);
		
		mButtonStart = new ToggleKeyButton(mDevice.createButton(KeyEvent.VK_SPACE));
		JXInputEventManager.addListener(new StartButtonListener(drone, this), mButtonStart);
		
		mButtonLeft = mDevice.createButton(KeyEvent.VK_LEFT);
		mButtonRight = mDevice.createButton(KeyEvent.VK_RIGHT);
		mButtonFront = mDevice.createButton(KeyEvent.VK_UP);
		mButtonRear = mDevice.createButton(KeyEvent.VK_DOWN);
		
		mButtonClock = mDevice.createButton(KeyEvent.VK_D);
		mButtonAntiClock = mDevice.createButton(KeyEvent.VK_A);
		mButtonDown = mDevice.createButton(KeyEvent.VK_S);
		mButtonUp = mDevice.createButton(KeyEvent.VK_W);
		
		mButtonYawLock = mDevice.createButton(KeyEvent.VK_Y);
		JXInputEventManager.addListener(new YawLockButtonListener(drone), mButtonYawLock);
		
		mButtonIncEngineFactor = mDevice.createButton(KeyEvent.VK_X);
		mButtonDecEngineFactor = mDevice.createButton(KeyEvent.VK_Z);
	}
}
