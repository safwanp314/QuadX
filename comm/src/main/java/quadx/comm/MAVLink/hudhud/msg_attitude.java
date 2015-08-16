package quadx.comm.MAVLink.hudhud;

// MESSAGE ATTITUDE PACKING
import quadx.comm.MAVLink.MAVLinkPacket;
import quadx.comm.MAVLink.Messages.MAVLinkMessage;
import quadx.comm.MAVLink.Messages.MAVLinkPayload;

//import android.util.Log;

/**
 * The attitude in the aeronautical frame (right-handed, Z-down, X-front,
 * Y-right).
 */
public class msg_attitude extends MAVLinkMessage {

	public static final int MAVLINK_MSG_ID_ATTITUDE = 30;
	public static final int MAVLINK_MSG_LENGTH = 24;
	private static final long serialVersionUID = MAVLINK_MSG_ID_ATTITUDE;

	/**
	 * Roll angle (rad, -pi..+pi)
	 */
	public float roll;
	/**
	 * Pitch angle (rad, -pi..+pi)
	 */
	public float pitch;
	/**
	 * Yaw angle (rad, -pi..+pi)
	 */
	public float yaw;
	/**
	 * Roll angular speed (rad/s)
	 */
	public float roll_rate;
	/**
	 * Pitch angular speed (rad/s)
	 */
	public float pitch_rate;
	/**
	 * Yaw angular speed (rad/s)
	 */
	public float yaw_rate;

	/**
	 * Generates the payload for a mavlink message for a message of this type
	 * 
	 * @return
	 */
	public MAVLinkPacket pack() {
		MAVLinkPacket packet = new MAVLinkPacket();
		packet.len = MAVLINK_MSG_LENGTH;
		packet.sysid = 255;
		packet.compid = 190;
		packet.msgid = MAVLINK_MSG_ID_ATTITUDE;
		packet.payload.putFloat(roll);
		packet.payload.putFloat(pitch);
		packet.payload.putFloat(yaw);
		packet.payload.putFloat(roll_rate);
		packet.payload.putFloat(pitch_rate);
		packet.payload.putFloat(yaw_rate);

		return packet;
	}

	/**
	 * Decode a attitude message into this class fields
	 *
	 * @param payload
	 *            The message to decode
	 */
	public void unpack(MAVLinkPayload payload) {
		payload.resetIndex();
		this.roll = payload.getFloat();
		this.pitch = payload.getFloat();
		this.yaw = payload.getFloat();
		this.roll_rate = payload.getFloat();
		this.pitch_rate = payload.getFloat();
		this.yaw_rate = payload.getFloat();

	}

	/**
	 * Constructor for a new message, just initializes the msgid
	 */
	public msg_attitude() {
		msgid = MAVLINK_MSG_ID_ATTITUDE;
	}

	/**
	 * Constructor for a new message, initializes the message with the payload
	 * from a mavlink packet
	 *
	 */
	public msg_attitude(MAVLinkPacket mavLinkPacket) {
		this.sysid = mavLinkPacket.sysid;
		this.compid = mavLinkPacket.compid;
		this.msgid = MAVLINK_MSG_ID_ATTITUDE;
		unpack(mavLinkPacket.payload);
		// Log.d("MAVLink", "ATTITUDE");
		// Log.d("MAVLINK_MSG_ID_ATTITUDE", toString());
	}

	/**
	 * Returns a string with the MSG name and data
	 */
	public String toString() {
		return "MAVLINK_MSG_ID_ATTITUDE -" + " roll:" + roll + " pitch:"
				+ pitch + " yaw:" + yaw + " roll_rate:" + roll_rate
				+ " pitch_rate:" + pitch_rate + " yaw_rate:" + yaw_rate + "";
	}
}
