package quadx.comm.MAVLink.enums;

/**
 * Commands to be executed by the MAV. They can be executed on user request, or
 * as part of a mission script. If the action is used in a mission, the
 * parameter mapping to the waypoint/mission message is as follows: Param 1,
 * Param 2, Param 3, Param 4, X: Param 5, Y:Param 6, Z:Param 7. This command list
 * is similar what ARINC 424 is for commercial aircraft: A data format how to
 * interpret waypoint/mission data.
 */

public class MAV_CMD {

	/* |Empty| Empty| Empty| Empty| Empty| Empty| Altitude| */
	public static final byte MAV_CMD_NAV_INC_ALTITUDE = 1;

	/*
	 * Loiter around this MISSION an unlimited amount of time |Empty| Empty|
	 * Radius around MISSION, in meters. If positive loiter clockwise, else
	 * counter-clockwise| Desired yaw angle.| Latitude| Longitude| Altitude|
	 */
	public static final byte MAV_CMD_NAV_LOITER_UNLIM = 17;

	/*
	 * Loiter around this MISSION for X turns |Turns| Empty| Radius around
	 * MISSION, in meters. If positive loiter clockwise, else counter-clockwise|
	 * Desired yaw angle.| Latitude| Longitude| Altitude|
	 */
	public static final byte MAV_CMD_NAV_LOITER_TURNS = 18;

	/*
	 * Loiter around this MISSION for X seconds |Seconds (decimal)| Empty|
	 * Radius around MISSION, in meters. If positive loiter clockwise, else
	 * counter-clockwise| Desired yaw angle.| Latitude| Longitude| Altitude|
	 */
	public static final byte MAV_CMD_NAV_LOITER_TIME = 19;

	/*
	 * Return to launch location |Empty| Empty| Empty| Empty| Empty| Empty|
	 * Empty|
	 */
	public static final byte MAV_CMD_NAV_RETURN_TO_LAUNCH = 20;

	/*
	 * Land at location |Empty| Empty| Empty| Desired yaw angle.| Latitude|
	 * Longitude| Altitude|
	 */
	public static final byte MAV_CMD_NAV_LAND = 21;

	/*
	 * Takeoff from ground / hand |Minimum pitch (if airspeed sensor present),
	 * desired pitch without sensor| Minimum roll (if airspeed sensor present),
	 * desired roll without sensor| Empty| Yaw angle (if magnetometer present),
	 * ignored without magnetometer| Latitude| Longitude| Altitude|
	 */
	public static final byte MAV_CMD_NAV_TAKEOFF = 22;

	/* Land at location |Empty| Empty| Empty| Empty| Empty| Empty| Empty| */
	public static final byte MAV_CMD_NAV_EMERGENCY_LAND = 23;

	public static final byte MAV_CMD_ENUM_END = 24;
}
