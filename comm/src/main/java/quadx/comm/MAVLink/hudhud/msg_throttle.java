package quadx.comm.MAVLink.hudhud;

// MESSAGE THROTTLE PACKING
import quadx.comm.MAVLink.MAVLinkPacket;
import quadx.comm.MAVLink.Messages.MAVLinkMessage;
import quadx.comm.MAVLink.Messages.MAVLinkPayload;
//import android.util.Log;
        
        /**
        * Send a throttle with up to four parameters to the MAV
        */
        public class msg_throttle extends MAVLinkMessage{
        
        public static final int MAVLINK_MSG_ID_THROTTLE = 13;
        public static final int MAVLINK_MSG_LENGTH = 10;
        private static final long serialVersionUID = MAVLINK_MSG_ID_THROTTLE;
        
        
         	/**
        * Engine Factor Slider
        */
        public short engine_factor;
         	/**
        * Left-Right throttle
        */
        public short left_right;
         	/**
        * Front-Back throttle
        */
        public short front_back;
         	/**
        * Up-Down throttle
        */
        public short up_down;
         	/**
        * Rotation throttle
        */
        public short rotation;
        
        
        /**
        * Generates the payload for a mavlink message for a message of this type
        * @return
        */
        public MAVLinkPacket pack(){
		MAVLinkPacket packet = new MAVLinkPacket();
		packet.len = MAVLINK_MSG_LENGTH;
		packet.sysid = 255;
		packet.compid = 190;
		packet.msgid = MAVLINK_MSG_ID_THROTTLE;
        		packet.payload.putShort(engine_factor);
        		packet.payload.putShort(left_right);
        		packet.payload.putShort(front_back);
        		packet.payload.putShort(up_down);
        		packet.payload.putShort(rotation);
        
		return packet;
        }
        
        /**
        * Decode a throttle message into this class fields
        *
        * @param payload The message to decode
        */
        public void unpack(MAVLinkPayload payload) {
        payload.resetIndex();
        	    this.engine_factor = payload.getShort();
        	    this.left_right = payload.getShort();
        	    this.front_back = payload.getShort();
        	    this.up_down = payload.getShort();
        	    this.rotation = payload.getShort();
        
        }
        
        /**
        * Constructor for a new message, just initializes the msgid
        */
        public msg_throttle(){
    	msgid = MAVLINK_MSG_ID_THROTTLE;
        }
        
        /**
        * Constructor for a new message, initializes the message with the payload
        * from a mavlink packet
        *
        */
        public msg_throttle(MAVLinkPacket mavLinkPacket){
        this.sysid = mavLinkPacket.sysid;
        this.compid = mavLinkPacket.compid;
        this.msgid = MAVLINK_MSG_ID_THROTTLE;
        unpack(mavLinkPacket.payload);
        //Log.d("MAVLink", "THROTTLE");
        //Log.d("MAVLINK_MSG_ID_THROTTLE", toString());
        }
        
                  
        /**
        * Returns a string with the MSG name and data
        */
        public String toString(){
    	return "MAVLINK_MSG_ID_THROTTLE -"+" engine_factor:"+engine_factor+" left_right:"+left_right+" front_back:"+front_back+" up_down:"+up_down+" rotation:"+rotation+"";
        }
        }
        