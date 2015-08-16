package com.huston.jxinput;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

import quadx.drone.controller.Drone;

import com.huston.jxinput.events.ClockAnticlockAxisListener;
import com.huston.jxinput.events.EngineFactorAxisListener;
import com.huston.jxinput.events.FrontRightAxisListener;
import com.huston.jxinput.events.LeftRightAxisListener;
import com.huston.jxinput.events.UpDownAxisListener;
import com.huston.jxinput.panel.DroneInputDevicePanel;

import de.hardcode.jxinput.Axis;
import de.hardcode.jxinput.Button;
import de.hardcode.jxinput.JXInputManager;
import de.hardcode.jxinput.event.JXInputEventManager;
import de.hardcode.jxinput.keyboard.JXKeyboardInputDevice;
import de.hardcode.jxinput.virtual.JXVirtualInputDevice;
import de.hardcode.jxinput.virtual.VirtualAxis;

public abstract class InputDevice implements ActionListener {

	protected JXVirtualInputDevice mVirtualDevice = null;
	protected JXKeyboardInputDevice mDevice = null;
	protected String deviceName;
	protected Drone drone;
	
	public Button mButtonStart;
	public Button mButtonYawLock;
	
	public Button mButtonIncEngineFactor;
	public Button mButtonDecEngineFactor;
	
	protected Button mButtonUp;
	protected Button mButtonDown;
	protected Button mButtonLeft;
	protected Button mButtonRight;
	protected Button mButtonFront;
	protected Button mButtonRear;
	protected Button mButtonClock;
	protected Button mButtonAntiClock;
	
	public VirtualAxis engineFactor;
	public VirtualAxis leftRight;
	public VirtualAxis frontRear;
	public VirtualAxis upDown;
	public VirtualAxis clkAclk;
	
	private DroneInputDevicePanel devicePanel;
	
	public InputDevice(Drone drone) {
		this.drone = drone;
		JXInputManager.reset();
		
		configureDevice();
		configureVirtualInputDevice();
		initDevicePanels();
		
		new Timer( 50, this ).start();
		//JXInputEventManager.setTriggerIntervall(50);
	}
	
	protected abstract void configureDevice();
	
	/**
	 * Configure a test JXVirtualInputdevice.
	 */
	void configureVirtualInputDevice() {
		mVirtualDevice = JXInputManager.createVirtualDevice();
		int i = 0;
		
		engineFactor = mVirtualDevice.createAxis(i++);
		engineFactor.setButtons(mButtonIncEngineFactor, mButtonDecEngineFactor);
		engineFactor.setName("Engine Factor");
		engineFactor.setType(Axis.SLIDER);
		engineFactor.setTimeFor0To1(5000);
		engineFactor.setTimeFor1To0(-1);
		JXInputEventManager.addListener(new EngineFactorAxisListener(drone), engineFactor);
		
		leftRight = mVirtualDevice.createAxis(i++);
		leftRight.setButtons(mButtonRight, mButtonLeft);
		leftRight.setName("Left-Right");
		leftRight.setType(Axis.TRANSLATION);
		leftRight.setTimeFor0To1(1000);
		leftRight.setTimeFor1To0(1000);
		JXInputEventManager.addListener(new LeftRightAxisListener(drone), leftRight);
		
		frontRear = mVirtualDevice.createAxis(i++);
		frontRear.setButtons(mButtonFront, mButtonRear);
		frontRear.setName("Front-Rear");
		frontRear.setType(Axis.TRANSLATION);
		frontRear.setTimeFor0To1(1000);
		frontRear.setTimeFor1To0(1000);
		JXInputEventManager.addListener(new FrontRightAxisListener(drone), frontRear);
		
		upDown = mVirtualDevice.createAxis(i++);
		upDown.setButtons(mButtonUp, mButtonDown);
		upDown.setName("Up-Down");
		upDown.setType(Axis.TRANSLATION);
		upDown.setTimeFor0To1(1000);
		upDown.setTimeFor1To0(1000);
		JXInputEventManager.addListener(new UpDownAxisListener(drone), upDown);
		
		clkAclk = mVirtualDevice.createAxis(i++);
		clkAclk.setButtons(mButtonClock, mButtonAntiClock);
		clkAclk.setName("Clk-Aclk");
		clkAclk.setType(Axis.ROTATION);
		clkAclk.setTimeFor0To1(1000);
		clkAclk.setTimeFor1To0(1000);
		JXInputEventManager.addListener(new ClockAnticlockAxisListener(drone), clkAclk);
	}

	public void attach(Component component) {
		mDevice.listenTo(component);
		component.requestFocus();
	}

	void initDevicePanels() {
		devicePanel = new DroneInputDevicePanel();
		devicePanel.add("Start", mButtonStart);
		devicePanel.add("Lock-Yaw", mButtonYawLock);
		devicePanel.add(engineFactor);
		devicePanel.add(leftRight);
		devicePanel.add(frontRear);
		devicePanel.add(upDown);
		devicePanel.add(clkAclk);
	}

	public DroneInputDevicePanel getDevicePanel() {
		return devicePanel;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void actionPerformed(ActionEvent e ) {
		JXInputManager.updateFeatures();
		updateDroneFeatures();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				devicePanel.update();
			}
		});
	}

	private void updateDroneFeatures() {
		if(engineFactor.getValue()==0.0) {
			engineFactor.setTimeFor1To0(-1);
		}	
	}

	public void resetEngineFactor() {
		engineFactor.setTimeFor1To0(1);
	}
}
