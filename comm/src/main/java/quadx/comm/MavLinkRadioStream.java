package quadx.comm;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import quadx.comm.MAVLink.MAVLinkPacket;
import quadx.comm.MAVLink.Parser;
import quadx.comm.MAVLink.hudhud.msg_attitude;
import quadx.comm.MAVLink.hudhud.msg_boot;
import quadx.comm.MAVLink.hudhud.msg_command;
import quadx.comm.MAVLink.hudhud.msg_command_ack;
import quadx.comm.MAVLink.hudhud.msg_guidence;
import quadx.comm.MAVLink.hudhud.msg_heartbeat;
import quadx.comm.MAVLink.hudhud.msg_navigation;
import quadx.comm.MAVLink.hudhud.msg_ping;
import quadx.comm.MAVLink.hudhud.msg_propeller;
import quadx.comm.MAVLink.hudhud.msg_throttle;
import quadx.comm.exceptions.RadioConnectionException;
import quadx.comm.listeners.MavLinkAttitudeEventListener;
import quadx.comm.listeners.MavLinkBootEventListener;
import quadx.comm.listeners.MavLinkCommandAckEventListener;
import quadx.comm.listeners.MavLinkCommandEventListener;
import quadx.comm.listeners.MavLinkGuidenceEventListener;
import quadx.comm.listeners.MavLinkHeartbeatEventListener;
import quadx.comm.listeners.MavLinkNavigationEventListener;
import quadx.comm.listeners.MavLinkPingEventListener;
import quadx.comm.listeners.MavLinkPropellerEventListener;
import quadx.comm.listeners.MavLinkThrottleEventListener;

public abstract class MavLinkRadioStream {

	// --------------------------------------------
	protected InputStream serialInStream = null;
	protected OutputStream serialOutStream = null;
	// --------------------------------------------
	protected boolean connected = false;
	public boolean isConnected() {
		return connected;
	}
	// --------------------------------------------
	private Parser mavlinkParser = new Parser();

	private MavLinkHeartbeatEventListener mavLinkHeartbeatEvent;
	public void setMavLinkHeartbeatEvent(
			MavLinkHeartbeatEventListener mavLinkHeartbeatEvent) {
		this.mavLinkHeartbeatEvent = mavLinkHeartbeatEvent;
	}

	private MavLinkBootEventListener mavLinkBootEvent;
	public void setMavLinkBootEvent(MavLinkBootEventListener mavLinkBootEvent) {
		this.mavLinkBootEvent = mavLinkBootEvent;
	}

	private MavLinkPingEventListener mavLinkPingEvent;
	public void setMavLinkPingEvent(MavLinkPingEventListener mavLinkPingEvent) {
		this.mavLinkPingEvent = mavLinkPingEvent;
	}

	private MavLinkCommandEventListener mavLinkCommandEvent;
	public void setMavLinkCommandEvent(
			MavLinkCommandEventListener mavLinkCommandEvent) {
		this.mavLinkCommandEvent = mavLinkCommandEvent;
	}

	private MavLinkCommandAckEventListener mavLinkCommandAckEvent;
	public void setMavLinkCommandAckEvent(
			MavLinkCommandAckEventListener mavLinkCommandAckEvent) {
		this.mavLinkCommandAckEvent = mavLinkCommandAckEvent;
	}

	private MavLinkAttitudeEventListener mavLinkAttitudeEvent;
	public void setMavLinkAttitudeEvent(
			MavLinkAttitudeEventListener mavLinkAttitudeEvent) {
		this.mavLinkAttitudeEvent = mavLinkAttitudeEvent;
	}

	private MavLinkThrottleEventListener mavLinkThrottleEvent;
	public void setMavLinkThrottleEvent(
			MavLinkThrottleEventListener mavLinkThrottleEvent) {
		this.mavLinkThrottleEvent = mavLinkThrottleEvent;
	}
	
	private MavLinkNavigationEventListener mavLinkNavigationEvent;	
	public void setMavLinkNavigationEvent(
			MavLinkNavigationEventListener mavLinkNavigationEvent) {
		this.mavLinkNavigationEvent = mavLinkNavigationEvent;
	}

	private MavLinkPropellerEventListener mavLinkPropellerEvent;
	public void setMavLinkPropellerEvent(
			MavLinkPropellerEventListener mavLinkPropellerEvent) {
		this.mavLinkPropellerEvent = mavLinkPropellerEvent;
	}
	
	private MavLinkGuidenceEventListener mavLinkGuidenceEvent;
	public void setMavLinkGuidenceEvent(
			MavLinkGuidenceEventListener mavLinkGuidenceEvent) {
		this.mavLinkGuidenceEvent = mavLinkGuidenceEvent;
	}

	// --------------------------------------------
	public abstract void connect() throws RadioConnectionException;

	public abstract void close();
	// --------------------------------------------
	public void sendMessage(MAVLinkPacket packet) {
		byte[] packetBytes = packet.encodePacket();
		try {
			synchronized (serialOutStream) {
				serialOutStream.write(packetBytes, 0, packetBytes.length);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void dataReceiveEvent() throws IOException {
		int available = serialInStream.available();
		for (int i = 0; i < available; i++) {
			int character = serialInStream.read();
			MAVLinkPacket mavLinkPacket = mavlinkParser.mavlink_parse_char(character);
			if (mavLinkPacket != null) {
				switch (mavLinkPacket.msgid) {
				case msg_heartbeat.MAVLINK_MSG_ID_HEARTBEAT:
					msg_heartbeat msgHeartbeat = (msg_heartbeat) mavLinkPacket.unpack();
					if (mavLinkHeartbeatEvent != null)
						mavLinkHeartbeatEvent.handleHeartbeatEvent(msgHeartbeat);
					break;
				case msg_boot.MAVLINK_MSG_ID_BOOT:
					msg_boot msgBoot = (msg_boot) mavLinkPacket.unpack();
					if (mavLinkBootEvent != null)
						mavLinkBootEvent.handleBootEvent(msgBoot);
					break;
				case msg_ping.MAVLINK_MSG_ID_PING:
					msg_ping msgPing = (msg_ping) mavLinkPacket.unpack();
					if (mavLinkPingEvent != null)
						mavLinkPingEvent.handlePingEvent(msgPing);
					break;
				case msg_command.MAVLINK_MSG_ID_COMMAND:
					msg_command msgCommand = (msg_command) mavLinkPacket
							.unpack();
					if (mavLinkCommandEvent != null)
						mavLinkCommandEvent.handleCommandEvent(msgCommand);
					break;
				case msg_command_ack.MAVLINK_MSG_ID_COMMAND_ACK:
					msg_command_ack msgCommandAck = (msg_command_ack) mavLinkPacket.unpack();
					if (mavLinkCommandAckEvent != null)
						mavLinkCommandAckEvent.handleCommandAckEvent(msgCommandAck);
					break;
				case msg_throttle.MAVLINK_MSG_ID_THROTTLE:
					msg_throttle msg_throttle = (msg_throttle) mavLinkPacket.unpack();
					if (mavLinkThrottleEvent != null)
						mavLinkThrottleEvent.handleThrottleEvent(msg_throttle);
					break;
				case msg_attitude.MAVLINK_MSG_ID_ATTITUDE:
					msg_attitude msgAttitude = (msg_attitude) mavLinkPacket.unpack();
					if (mavLinkAttitudeEvent != null)
						mavLinkAttitudeEvent.handleAttitudeEvent(msgAttitude);
					break;
				case msg_navigation.MAVLINK_MSG_ID_NAVIGATION:
					msg_navigation msg_navigation = (msg_navigation) mavLinkPacket.unpack();
					if (mavLinkNavigationEvent != null)
						mavLinkNavigationEvent.handleNavigationEvent(msg_navigation);
					break;
				case msg_propeller.MAVLINK_MSG_ID_PROPELLER:
					msg_propeller msg_propeller = (msg_propeller) mavLinkPacket.unpack();
					if(mavLinkPropellerEvent != null)
						mavLinkPropellerEvent.handlePropellerEvent(msg_propeller);
				break;
				case msg_guidence.MAVLINK_MSG_ID_GUIDENCE:
					msg_guidence msg_guidence = (msg_guidence) mavLinkPacket.unpack();
					if(mavLinkGuidenceEvent != null)
						mavLinkGuidenceEvent.handleGuidenceEvent(msg_guidence);
				break;
				default:
					break;
				}
			}
		}
	}
}
