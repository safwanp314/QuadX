package quadx.comm.MAVLink.hudhud;

// MESSAGE BOOT PACKING
import quadx.comm.MAVLink.MAVLinkPacket;
import quadx.comm.MAVLink.Messages.MAVLinkMessage;
import quadx.comm.MAVLink.Messages.MAVLinkPayload;

//import android.util.Log;

/**
 * The boot message indicates that a system is starting. The onboard software
 * version allows to keep track of onboard soft/firmware revisions.
 */
public class msg_boot extends MAVLinkMessage {

	public static final int MAVLINK_MSG_ID_BOOT = 1;
	public static final int MAVLINK_MSG_LENGTH = 4;
	private static final long serialVersionUID = MAVLINK_MSG_ID_BOOT;

	/**
	 * The onboard software version
	 */
	public int version;

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
		packet.msgid = MAVLINK_MSG_ID_BOOT;
		packet.payload.putInt(version);

		return packet;
	}

	/**
	 * Decode a boot message into this class fields
	 *
	 * @param payload
	 *            The message to decode
	 */
	public void unpack(MAVLinkPayload payload) {
		payload.resetIndex();
		this.version = payload.getInt();

	}

	/**
	 * Constructor for a new message, just initializes the msgid
	 */
	public msg_boot() {
		msgid = MAVLINK_MSG_ID_BOOT;
	}

	/**
	 * Constructor for a new message, initializes the message with the payload
	 * from a mavlink packet
	 *
	 */
	public msg_boot(MAVLinkPacket mavLinkPacket) {
		this.sysid = mavLinkPacket.sysid;
		this.compid = mavLinkPacket.compid;
		this.msgid = MAVLINK_MSG_ID_BOOT;
		unpack(mavLinkPacket.payload);
		// Log.d("MAVLink", "BOOT");
		// Log.d("MAVLINK_MSG_ID_BOOT", toString());
	}

	/**
	 * Returns a string with the MSG name and data
	 */
	public String toString() {
		return "MAVLINK_MSG_ID_BOOT -" + " version:" + version + "";
	}
}
