package quadx.utils.mavlink;

import quadx.comm.MavLinkRadioStream;
import quadx.comm.MAVLink.Messages.MAVLinkMessage;

public abstract class RepeativeMessageSendActivity implements Runnable {

	protected MavLinkRadioStream radioStream;
	protected MAVLinkMessage message;
	private long repaetInterval;
	private int repeatCount;

	public RepeativeMessageSendActivity(MavLinkRadioStream radioStream, long repaetInterval) {
		this.radioStream = radioStream;
		this.repaetInterval = repaetInterval;
		this.repeatCount = -1;
	}

	public RepeativeMessageSendActivity(MavLinkRadioStream radioStream, long repaetInterval, int repeatCount) {
		this.radioStream = radioStream;
		this.repaetInterval = repaetInterval;
		this.repeatCount = repeatCount;
	}

	public abstract void createMessage();

	public void run() {
		for (int i = repeatCount; i != 0; i--) {
			if (radioStream.isConnected()) {
				createMessage();
				radioStream.sendMessage(message.pack());
			}
			try {
				Thread.sleep(repaetInterval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void start() {
		new Thread(this).start();
	}
}
