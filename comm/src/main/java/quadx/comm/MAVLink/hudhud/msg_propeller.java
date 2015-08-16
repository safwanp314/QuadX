package quadx.comm.MAVLink.hudhud;

// MESSAGE PROPELLER PACKING
import quadx.comm.MAVLink.MAVLinkPacket;
import quadx.comm.MAVLink.Messages.MAVLinkMessage;
import quadx.comm.MAVLink.Messages.MAVLinkPayload;

//import android.util.Log;

/**
 * Propelller Data.
 */
public class msg_propeller extends MAVLinkMessage {

	public static final int MAVLINK_MSG_ID_PROPELLER = 32;
	public static final int MAVLINK_MSG_LENGTH = 16;
	private static final long serialVersionUID = MAVLINK_MSG_ID_PROPELLER;

	/**
	 * Propeller1 Speed
	 */
	public float propeller1;
	/**
	 * Propeller2 Speed
	 */
	public float propeller2;
	/**
	 * Propeller3 Speed
	 */
	public float propeller3;
	/**
	 * Propeller4 Speed
	 */
	public float propeller4;

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
		packet.msgid = MAVLINK_MSG_ID_PROPELLER;
		packet.payload.putFloat(propeller1);
		packet.payload.putFloat(propeller2);
		packet.payload.putFloat(propeller3);
		packet.payload.putFloat(propeller4);

		return packet;
	}

	/**
	 * Decode a propeller message into this class fields
	 *
	 * @param payload
	 *            The message to decode
	 */
	public void unpack(MAVLinkPayload payload) {
		payload.resetIndex();
		this.propeller1 = payload.getFloat();
		this.propeller2 = payload.getFloat();
		this.propeller3 = payload.getFloat();
		this.propeller4 = payload.getFloat();

	}

	/**
	 * Constructor for a new message, just initializes the msgid
	 */
	public msg_propeller() {
		msgid = MAVLINK_MSG_ID_PROPELLER;
	}

	/**
	 * Constructor for a new message, initializes the message with the payload
	 * from a mavlink packet
	 *
	 */
	public msg_propeller(MAVLinkPacket mavLinkPacket) {
		this.sysid = mavLinkPacket.sysid;
		this.compid = mavLinkPacket.compid;
		this.msgid = MAVLINK_MSG_ID_PROPELLER;
		unpack(mavLinkPacket.payload);
		// Log.d("MAVLink", "PROPELLER");
		// Log.d("MAVLINK_MSG_ID_PROPELLER", toString());
	}

	/**
	 * Returns a string with the MSG name and data
	 */
	public String toString() {
		return "MAVLINK_MSG_ID_PROPELLER -" + " propeller1:" + propeller1
				+ " propeller2:" + propeller2 + " propeller3:" + propeller3
				+ " propeller4:" + propeller4 + "";
	}
}
