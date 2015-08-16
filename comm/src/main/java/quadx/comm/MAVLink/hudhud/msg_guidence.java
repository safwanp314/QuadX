package quadx.comm.MAVLink.hudhud;

// MESSAGE GUIDENCE PACKING
import quadx.comm.MAVLink.MAVLinkPacket;
import quadx.comm.MAVLink.Messages.MAVLinkMessage;
import quadx.comm.MAVLink.Messages.MAVLinkPayload;

//import android.util.Log;

/**
 * Target Guidence System Data.
 */
public class msg_guidence extends MAVLinkMessage {

	public static final int MAVLINK_MSG_ID_GUIDENCE = 33;
	public static final int MAVLINK_MSG_LENGTH = 24;
	private static final long serialVersionUID = MAVLINK_MSG_ID_GUIDENCE;

	/**
	 * Target Pitch
	 */
	public float target_pitch;
	/**
	 * Target Roll
	 */
	public float target_roll;
	/**
	 * Target Yaw
	 */
	public float target_yaw;
	/**
	 * Target Pitch Rate
	 */
	public float target_pitch_rate;
	/**
	 * Target Roll Rate
	 */
	public float target_roll_rate;
	/**
	 * Target Yaw Rate
	 */
	public float target_yaw_rate;

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
		packet.msgid = MAVLINK_MSG_ID_GUIDENCE;
		packet.payload.putFloat(target_pitch);
		packet.payload.putFloat(target_roll);
		packet.payload.putFloat(target_yaw);
		packet.payload.putFloat(target_pitch_rate);
		packet.payload.putFloat(target_roll_rate);
		packet.payload.putFloat(target_yaw_rate);

		return packet;
	}

	/**
	 * Decode a guidence message into this class fields
	 *
	 * @param payload
	 *            The message to decode
	 */
	public void unpack(MAVLinkPayload payload) {
		payload.resetIndex();
		this.target_pitch = payload.getFloat();
		this.target_roll = payload.getFloat();
		this.target_yaw = payload.getFloat();
		this.target_pitch_rate = payload.getFloat();
		this.target_roll_rate = payload.getFloat();
		this.target_yaw_rate = payload.getFloat();

	}

	/**
	 * Constructor for a new message, just initializes the msgid
	 */
	public msg_guidence() {
		msgid = MAVLINK_MSG_ID_GUIDENCE;
	}

	/**
	 * Constructor for a new message, initializes the message with the payload
	 * from a mavlink packet
	 *
	 */
	public msg_guidence(MAVLinkPacket mavLinkPacket) {
		this.sysid = mavLinkPacket.sysid;
		this.compid = mavLinkPacket.compid;
		this.msgid = MAVLINK_MSG_ID_GUIDENCE;
		unpack(mavLinkPacket.payload);
		// Log.d("MAVLink", "GUIDENCE");
		// Log.d("MAVLINK_MSG_ID_GUIDENCE", toString());
	}

	/**
	 * Returns a string with the MSG name and data
	 */
	public String toString() {
		return "MAVLINK_MSG_ID_GUIDENCE -" + " target_pitch:" + target_pitch
				+ " target_roll:" + target_roll + " target_yaw:" + target_yaw
				+ " target_pitch_rate:" + target_pitch_rate
				+ " target_roll_rate:" + target_roll_rate + " target_yaw_rate:"
				+ target_yaw_rate + "";
	}
}
