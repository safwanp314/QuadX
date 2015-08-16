package quadx.comm.MAVLink.hudhud;

// MESSAGE NAVIGATION PACKING
import quadx.comm.MAVLink.MAVLinkPacket;
import quadx.comm.MAVLink.Messages.MAVLinkMessage;
import quadx.comm.MAVLink.Messages.MAVLinkPayload;

//import android.util.Log;

/**
 * The navigation of the aeronautical frame.
 */
public class msg_navigation extends MAVLinkMessage {

	public static final int MAVLINK_MSG_ID_NAVIGATION = 31;
	public static final int MAVLINK_MSG_LENGTH = 20;
	private static final long serialVersionUID = MAVLINK_MSG_ID_NAVIGATION;

	/**
	 * Latitude
	 */
	public float latitude;
	/**
	 * Longitude
	 */
	public float longitude;
	/**
	 * Height from sea level
	 */
	public float altitude;
	/**
	 * Speed
	 */
	public float speed;
	/**
	 * Climb Speed
	 */
	public float climb_rate;

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
		packet.msgid = MAVLINK_MSG_ID_NAVIGATION;
		packet.payload.putFloat(latitude);
		packet.payload.putFloat(longitude);
		packet.payload.putFloat(altitude);
		packet.payload.putFloat(speed);
		packet.payload.putFloat(climb_rate);

		return packet;
	}

	/**
	 * Decode a navigation message into this class fields
	 *
	 * @param payload
	 *            The message to decode
	 */
	public void unpack(MAVLinkPayload payload) {
		payload.resetIndex();
		this.latitude = payload.getFloat();
		this.longitude = payload.getFloat();
		this.altitude = payload.getFloat();
		this.speed = payload.getFloat();
		this.climb_rate = payload.getFloat();

	}

	/**
	 * Constructor for a new message, just initializes the msgid
	 */
	public msg_navigation() {
		msgid = MAVLINK_MSG_ID_NAVIGATION;
	}

	/**
	 * Constructor for a new message, initializes the message with the payload
	 * from a mavlink packet
	 *
	 */
	public msg_navigation(MAVLinkPacket mavLinkPacket) {
		this.sysid = mavLinkPacket.sysid;
		this.compid = mavLinkPacket.compid;
		this.msgid = MAVLINK_MSG_ID_NAVIGATION;
		unpack(mavLinkPacket.payload);
		// Log.d("MAVLink", "NAVIGATION");
		// Log.d("MAVLINK_MSG_ID_NAVIGATION", toString());
	}

	/**
	 * Returns a string with the MSG name and data
	 */
	public String toString() {
		return "MAVLINK_MSG_ID_NAVIGATION -" + " latitude:" + latitude
				+ " longitude:" + longitude + " altitude:" + altitude
				+ " speed:" + speed + " climb_rate:" + climb_rate + "";
	}
}
