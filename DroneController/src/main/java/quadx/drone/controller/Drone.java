package quadx.drone.controller;

import quadx.comm.exceptions.RadioConnectionException;
import quadx.comm.win.WinMavLinkRadioStream;
import quadx.drone.controller.command.DroneCommand;
import quadx.drone.controller.command.activities.DroneCommandSendActivity;
import quadx.drone.controller.command.listeners.DroneCommandAckEventListener;
import quadx.drone.controller.guidence.DroneGuidence;
import quadx.drone.controller.guidence.activities.DroneGuidenceSendActivity;
import quadx.drone.controller.guidence.listeners.DroneGuidenceEventListener;
import quadx.drone.controller.navigation.AhrsNav;
import quadx.drone.controller.navigation.listeners.DroneAttitudeEventListener;
import quadx.drone.controller.navigation.listeners.DroneNavigationEventListener;
import quadx.drone.controller.propellers.DronePropellers;
import quadx.drone.controller.propellers.listeners.DronePropellerEventListener;
import quadx.drone.controller.status.DroneStatus;
import quadx.drone.controller.status.listeners.DroneHeartbeatEventListener;
import quadx.drone.controller.throttle.DroneThrottle;
import quadx.drone.controller.throttle.activities.DroneThrottleSendActivity;
import quadx.drone.controller.utils.events.DroneEventHandler;
import quadx.drone.controller.utils.exceptions.DroneConnectionException;

public class Drone {

	private int networkId = 0x00;
	public int getNetworkId() {
		return networkId;
	}
	private int droneId = 0x00;
	public int getDroneId() {
		return droneId;
	}
	//--------------------------------------------
	private boolean connected;
	public boolean isConnected() {
		return connected;
	}
	// --------------------------------------------
	private DroneStatus status = new DroneStatus();
	public DroneStatus getStatus() {
		return status;
	}
	// --------------------------------------------
	private DroneActionHandler actionHandler = new DroneActionHandler(this);
	public DroneActionHandler getActionHandler() {
		return actionHandler;
	}
	// --------------------------------------------
	//event
	private DroneEventHandler eventHandler = new DroneEventHandler(this);
	public DroneEventHandler getEventHandler() {
		return eventHandler;
	}
	// --------------------------------------------
	//throttle
	private DroneThrottle throttle = new DroneThrottle();
	public DroneThrottle getThrottle() {
		return throttle;
	}
	// --------------------------------------------
	//propeller
	private DroneCommand command = new DroneCommand();
	public DroneCommand getCommand() {
		return command;
	}
	// --------------------------------------------
	//navigation
	private AhrsNav nav = new AhrsNav();
	public AhrsNav getNav() {
		return nav;
	}
	// --------------------------------------------
	//guidence
	private DroneGuidence guidence = new DroneGuidence();
	public DroneGuidence getGuidence() {
		return guidence;
	}
	// --------------------------------------------
	//propeller
	private DronePropellers propellers = new DronePropellers();
	public DronePropellers getPropellers() {
		return propellers;
	}
	// --------------------------------------------
	private WinMavLinkRadioStream radioStream = new WinMavLinkRadioStream();
	public WinMavLinkRadioStream getRadioStream() {
		return radioStream;
	}
	private String port = "COM1";
	private int baudRate = 57600;
	// --------------------------------------------
	public Drone(int networkId, int droneId) {
		super();
		this.networkId = networkId;
		this.droneId = droneId;
		initilize();
	}
	
	void initilize() {
		//status
		radioStream.setMavLinkHeartbeatEvent(new DroneHeartbeatEventListener(this));
		//navigation
		radioStream.setMavLinkAttitudeEvent(new DroneAttitudeEventListener(this));
		radioStream.setMavLinkNavigationEvent(new DroneNavigationEventListener(this));
		//throttle
		throttle.setThrottleSendActivity(new DroneThrottleSendActivity(this));
		//command
		command.setCommandSendActivity(new DroneCommandSendActivity(this));
		radioStream.setMavLinkCommandAckEvent(new DroneCommandAckEventListener(this));
		//guidence
		guidence.setGuidenceSendActivity(new DroneGuidenceSendActivity(this));
		radioStream.setMavLinkGuidenceEvent(new DroneGuidenceEventListener(this));
		//propeller
		radioStream.setMavLinkPropellerEvent(new DronePropellerEventListener(this));
	}
	
	public void connect() throws DroneConnectionException {
		try {			
			radioStream.connect(port, baudRate);
			connected = radioStream.isConnected();
		} catch (RadioConnectionException e) {
			e.printStackTrace();
			throw new DroneConnectionException();
		}
	}

	public void connect(String port, int baudRate)
			throws DroneConnectionException {
		this.port = port;
		this.baudRate = baudRate;
		connect();
	}
	
	public void close() throws DroneConnectionException {
		radioStream.close();
	}
}
