package quadx.comm.MAVLink.enums;

/** result from a mavlink command */

public class MAV_RESULT {
	
	/* Command ACCEPTED and EXECUTED */
	public static final byte MAV_RESULT_ACCEPTED = 0;
	
	/* Command TEMPORARY REJECTED/DENIED */
	public static final byte MAV_RESULT_TEMPORARILY_REJECTED = 1;
	
	/* Command PERMANENTLY DENIED */
	public static final byte MAV_RESULT_DENIED = 2;
	
	/* Command UNKNOWN/UNSUPPORTED */
	public static final byte MAV_RESULT_UNSUPPORTED = 3;
	
	/* Command executed, but failed */
	public static final byte MAV_RESULT_FAILED = 4;
	
	public static final byte MAV_RESULT_ENUM_END = 5;
}
            