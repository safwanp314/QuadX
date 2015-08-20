package quadx.pidrone;

import java.util.Date;

import quadx.comm.MavLinkRadioStream;
import quadx.comm.exceptions.RadioConnectionException;
import quadx.pidrone.action.PiDroneActionHandler;
import quadx.pidrone.command.listeners.PiDroneCommandEventListener;
import quadx.pidrone.control.PiDroneController;
import quadx.pidrone.enums.DroneStat;
import quadx.pidrone.exceptions.PiDroneConnectionException;
import quadx.pidrone.guidence.PiDroneGuidence;
import quadx.pidrone.guidence.activity.GuidenceSendActivity;
import quadx.pidrone.guidence.listeners.PiDroneGuidenceEventListener;
import quadx.pidrone.navigation.AhrsNav;
import quadx.pidrone.navigation.activity.AttitudeSendActivity;
import quadx.pidrone.navigation.activity.NavigationSendActivity;
import quadx.pidrone.propeller.DronePropellers;
import quadx.pidrone.propeller.activity.PropellerSendActivity;
import quadx.pidrone.resources.imu.ArduImu;
import quadx.pidrone.resources.propeller.PropellersDriver;
import quadx.pidrone.status.DroneStatus;
import quadx.pidrone.status.activity.HeartbeatSendActivity;
import quadx.pidrone.throttle.DroneThrottle;
import quadx.pidrone.throttle.listeners.PiDroneThrottleEventListener;

public class PiDrone {
	
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
	public boolean active;
	// --------------------------------------------
	//action
	private final PiDroneActionHandler actionHandler = new PiDroneActionHandler(this);
	public PiDroneActionHandler getActionHandler() {
		return actionHandler;
	}
	// --------------------------------------------
	//status
	private final DroneStatus status = new DroneStatus();
	public DroneStatus getStatus() {
		return status;
	}
	// --------------------------------------------
	//navigation
	private final ArduImu imu = new ArduImu();
	private final AhrsNav nav = new AhrsNav(imu);
	public AhrsNav getNav() {
		return nav;
	}
	// --------------------------------------------
	//throttle
	private final DroneThrottle throttle = new DroneThrottle();
	public DroneThrottle getThrottle() {
		return throttle;
	}
	// --------------------------------------------
	//propeller
	private final PropellersDriver propDriver = new PropellersDriver();
	private final DronePropellers propellers = new DronePropellers(propDriver);
	public DronePropellers getPropellers() {
		return propellers;
	}
	// --------------------------------------------
	//guidence
	private final PiDroneGuidence guidence = new PiDroneGuidence();
	public PiDroneGuidence getGuidence() {
		return guidence;
	}
	//--------------------------------------------
	private Date startTime = new Date();
	public Date getStartTime() {
		return startTime;
	}
	//--------------------------------------------
	private final MavLinkRadioStream radioStream;
	public MavLinkRadioStream getRadioStream() {
		return radioStream;
	}
	//--------------------------------------------
	private PiDroneController controller = new PiDroneController(this);
	//--------------------------------------------
	public PiDrone(int networkId, int droneId, MavLinkRadioStream radioStream) {
		super();
		this.networkId = networkId;
		this.droneId = droneId;
		this.radioStream = radioStream;
	}
	
	public void connect() throws RadioConnectionException {
		System.out.println("connecting ...");
		
		try {
			radioStream.connect();
			connected = radioStream.isConnected();
			if(connected) {
				status.stat = DroneStat.BOOTING;
				initilize();
			}
			System.out.println("connected");
		} catch (RadioConnectionException e) {
			System.out.println("connection failed.");
			e.printStackTrace();
			throw e;
		}
	}
		
	public void initilize() {
		radioStream.setMavLinkCommandEvent(new PiDroneCommandEventListener(this));
		radioStream.setMavLinkThrottleEvent(new PiDroneThrottleEventListener(this));
		radioStream.setMavLinkGuidenceEvent(new PiDroneGuidenceEventListener(this));
		radioStream.setMavLinkGuidenceEvent(new PiDroneGuidenceEventListener(this));
		
		status.setHeartbeatSendActivity(new HeartbeatSendActivity(this));
		propellers.setPropellerSendActivity(new PropellerSendActivity(this));
		nav.setAttitudeSendActivity(new AttitudeSendActivity(this));
		nav.setNavigationSendActivity(new NavigationSendActivity(this));
		guidence.setGuidenceSendActivity(new GuidenceSendActivity(this));
		
		status.getHeartbeatSendActivity().start();
	}

	public void start() throws PiDroneConnectionException {
		if(connected) {
			System.out.println("starting ...");
			//---------------- Drone Stat -------------
			status.stat = DroneStat.STANDBY;
			//------------ Controller Thread ----------
			controller.start();
			//-------------- Mavlink Thread -----------
			propellers.getPropellerSendActivity().start();
			nav.getAttitudeSendActivity().start();
			nav.getNavigationSendActivity().start();
			guidence.getGuidenceSendActivity().start();
			
			System.out.println("started");
		}
	}
}
