package quadx.comm.MAVLink.enums;

public class MAV_MODE {

	/* Uninitialized system, state is unknown. */
	public static final byte MAV_MODE_AUTO = 0;
	
	/* System is booting up. */
	public static final byte MAV_MODE_SEMIAUTO = 1;
	
	/* System is calibrating and not flight-ready. */
	public static final byte MAV_MODE_GUIDED = 2;
	
	public static final byte MAV_MODE_ENUM_END = 3;
}
            