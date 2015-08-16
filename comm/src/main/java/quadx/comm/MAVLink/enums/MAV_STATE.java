package quadx.comm.MAVLink.enums;

public class MAV_STATE {

	/* Uninitialized system, state is unknown. */
	public static final byte MAV_STATE_UNINIT = 0;

	/* System is booting up. */
	public static final byte MAV_STATE_BOOT = 1;

	/* System is calibrating and not flight-ready. */
	public static final byte MAV_STATE_CALIBRATING = 2;

	/* System is grounded and on standby. It can be launched any time. */
	public static final byte MAV_STATE_STANDBY = 3;

	/* System is active and might be already airborne. Motors are engaged. */
	public static final byte MAV_STATE_ACTIVE = 4;

	/* System is in a non-normal flight mode. It can however still navigate. */
	public static final byte MAV_STATE_CRITICAL = 5;

	/*
	 * System is in a non-normal flight mode. It lost control over parts or over
	 * the whole airframe. It is in mayday and going down.
	 */
	public static final byte MAV_STATE_EMERGENCY = 6;

	/* System just initialized its power-down sequence, will shut down now. */
	public static final byte MAV_STATE_POWEROFF = 7;

	public static final byte MAV_STATE_ENUM_END = 8;
}
