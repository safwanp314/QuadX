package quadx.comm.MAVLink.hudhud;

// MESSAGE COMMAND PACKING
import quadx.comm.MAVLink.MAVLinkPacket;
import quadx.comm.MAVLink.Messages.MAVLinkMessage;
import quadx.comm.MAVLink.Messages.MAVLinkPayload;

//import android.util.Log;

/**
 * Send a command with up to seven parameters to the MAV
 */
public class msg_command extends MAVLinkMessage {

	public static final int MAVLINK_MSG_ID_COMMAND = 10;
	public static final int MAVLINK_MSG_LENGTH = 31;
	private static final long serialVersionUID = MAVLINK_MSG_ID_COMMAND;

	/**
	 * Parameter 1, as defined by MAV_CMD enum.
	 */
	public float param1;
	/**
	 * Parameter 2, as defined by MAV_CMD enum.
	 */
	public float param2;
	/**
	 * Parameter 3, as defined by MAV_CMD enum.
	 */
	public float param3;
	/**
	 * Parameter 4, as defined by MAV_CMD enum.
	 */
	public float param4;
	/**
	 * Parameter 5, as defined by MAV_CMD enum.
	 */
	public float param5;
	/**
	 * Parameter 6, as defined by MAV_CMD enum.
	 */
	public float param6;
	/**
	 * Parameter 7, as defined by MAV_CMD enum.
	 */
	public float param7;
	/**
	 * The system executing the action
	 */
	public byte target;
	/**
	 * The component executing the action
	 */
	public byte target_component;
	/**
	 * The command id
	 */
	public byte command;

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
		packet.msgid = MAVLINK_MSG_ID_COMMAND;
		packet.payload.putFloat(param1);
		packet.payload.putFloat(param2);
		packet.payload.putFloat(param3);
		packet.payload.putFloat(param4);
		packet.payload.putFloat(param5);
		packet.payload.putFloat(param6);
		packet.payload.putFloat(param7);
		packet.payload.putByte(target);
		packet.payload.putByte(target_component);
		packet.payload.putByte(command);

		return packet;
	}

	/**
	 * Decode a command message into this class fields
	 *
	 * @param payload
	 *            The message to decode
	 */
	public void unpack(MAVLinkPayload payload) {
		payload.resetIndex();
		this.param1 = payload.getFloat();
		this.param2 = payload.getFloat();
		this.param3 = payload.getFloat();
		this.param4 = payload.getFloat();
		this.param5 = payload.getFloat();
		this.param6 = payload.getFloat();
		this.param7 = payload.getFloat();
		this.target = payload.getByte();
		this.target_component = payload.getByte();
		this.command = payload.getByte();

	}

	/**
	 * Constructor for a new message, just initializes the msgid
	 */
	public msg_command() {
		msgid = MAVLINK_MSG_ID_COMMAND;
	}

	/**
	 * Constructor for a new message, initializes the message with the payload
	 * from a mavlink packet
	 *
	 */
	public msg_command(MAVLinkPacket mavLinkPacket) {
		this.sysid = mavLinkPacket.sysid;
		this.compid = mavLinkPacket.compid;
		this.msgid = MAVLINK_MSG_ID_COMMAND;
		unpack(mavLinkPacket.payload);
		// Log.d("MAVLink", "COMMAND");
		// Log.d("MAVLINK_MSG_ID_COMMAND", toString());
	}

	/**
	 * Returns a string with the MSG name and data
	 */
	public String toString() {
		return "MAVLINK_MSG_ID_COMMAND -" + " param1:" + param1 + " param2:"
				+ param2 + " param3:" + param3 + " param4:" + param4
				+ " param5:" + param5 + " param6:" + param6 + " param7:"
				+ param7 + " target:" + target + " target_component:"
				+ target_component + " command:" + command + "";
	}
}
