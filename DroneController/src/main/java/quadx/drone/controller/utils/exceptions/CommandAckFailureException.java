package quadx.drone.controller.utils.exceptions;

public class CommandAckFailureException extends Exception {

	private static final long serialVersionUID = 1243397085200332578L;
	
	public CommandAckFailureException(byte command) {
		super("Command:"+command+" acknowledgement failure");
	}
}
