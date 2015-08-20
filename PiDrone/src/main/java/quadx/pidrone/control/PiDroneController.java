package quadx.pidrone.control;

import quadx.pidrone.PiDrone;
import quadx.pidrone.navigation.AhrsNav;
import quadx.pidrone.propeller.DronePropellers;
import quadx.utils.MathUtils;
import quadx.utils.RepetiveJob;

public class PiDroneController extends RepetiveJob {

	private PiDrone drone;

	private final double m = 1.0;
	private final double g = 9.81;
	private final double w = m * g;

	private final double Ixx = 4.856E-3;
	private final double Iyy = 4.856E-3;
	private final double Izz = 8.801E-3;

	private final double k = 2.980E-6;
	private final double b = 1.140E-7;
	private final double l = 0.25;

	private final double kpPitch = 5.00, kdPitch = 0.00;
	private final double kpRoll = 5.00, kdRoll = 0.00;
	private final double kpYaw = 0.00, kdYaw = 0.00;
	private double thrust;

	private final float[] frameAngle = { 0.0f, 0.0f, 45.0f };
	private double yawError, pitchError, rollError;

	private final double SPEED_SCALE = 0.0625;
	private final double ERROR_SCALE = 1.0;

	private final double MAX_PITCH_ERROR = 250.0;
	private final double MAX_ROLL_ERROR = 250.0;

	private final int MIN_ROTOR_SPEED = (int) (18.0 / SPEED_SCALE);
	private final int MAX_ROTOR_SPEED = (int) (255.0 / SPEED_SCALE);

	private final int MIN_ROTOR_SPEED_2 = MIN_ROTOR_SPEED * MIN_ROTOR_SPEED;
	private final int MAX_ROTOR_SPEED_2 = MAX_ROTOR_SPEED * MAX_ROTOR_SPEED;

	private double pitch;
	private double roll;
	private double yaw;

	private double targetPitch;
	private double targetRoll;
	private double targetYaw;

	private double pitchDiff;
	private double rollDiff;
	private double yawDiff;

	private double prevPitchDiff;
	private double prevRollDiff;
	private double prevYawDiff;

	public PiDroneController(PiDrone drone) {
		this.drone = drone;
		this.repeatInterval = 2;
	}

	private void calPDError() {
		// ----------------------------------------------
		pitchDiff = targetPitch - pitch;
		rollDiff = targetRoll - roll;
		yawDiff = targetYaw - yaw;
		// ----------------------------------------------
		pitchError = kdPitch * (pitchDiff - prevPitchDiff) + kpPitch
				* pitchDiff;
		rollError = kdRoll * (rollDiff - prevRollDiff) + kpRoll * rollDiff;
		yawError = kdYaw * (yawDiff - prevYawDiff) + kpYaw * yawDiff;
		// ----------------------------------------------
		prevPitchDiff = pitchDiff;
		prevRollDiff = rollDiff;
		prevYawDiff = yawDiff;
		// ----------------------------------------------
		pitchError *= ERROR_SCALE;
		rollError *= ERROR_SCALE;
		yawError *= ERROR_SCALE;
		// ----------------------------------------------
		if (Math.abs(pitchError) > MAX_PITCH_ERROR) {
			if (pitchError < 0) {
				pitchError = -MAX_PITCH_ERROR;
			} else {
				pitchError = MAX_PITCH_ERROR;
			}
		}
		if (Math.abs(rollError) > MAX_ROLL_ERROR) {
			if (rollError < 0) {
				rollError = -MAX_ROLL_ERROR;
			} else {
				rollError = MAX_ROLL_ERROR;
			}
		}
	}

	public void execute() {
		// ----------------------------------------------
		DronePropellers propellers = drone.getPropellers();
		// ----------------------------------------------
		readNav();
		// ----------------------------------------------
		if (drone.active) {
			thrust = drone.getGuidence().appliedThrust
					+ (w / (Math.cos(Math.toRadians(pitch)) * Math.cos(Math
							.toRadians(roll))));
			calPDError();

			double rotor1SpeedSquare = (thrust - (2 * b * pitchError * Ixx + yawError
					* Izz * k * l)
					/ (b * l))
					/ (4 * k);
			rotor1SpeedSquare = Math.max(rotor1SpeedSquare, MIN_ROTOR_SPEED_2);
			rotor1SpeedSquare = Math.min(rotor1SpeedSquare, MAX_ROTOR_SPEED_2);
			rotor1SpeedSquare *= drone.getGuidence().engineFactor;

			double rotor2SpeedSquare = (thrust - (-2 * b * rollError * Iyy - yawError
					* Izz * k * l)
					/ (b * l))
					/ (4 * k);
			rotor2SpeedSquare = Math.max(rotor2SpeedSquare, MIN_ROTOR_SPEED_2);
			rotor2SpeedSquare = Math.min(rotor2SpeedSquare, MAX_ROTOR_SPEED_2);
			rotor2SpeedSquare *= drone.getGuidence().engineFactor;

			double rotor3SpeedSquare = (thrust - (-2 * b * pitchError * Ixx + yawError
					* Izz * k * l)
					/ (b * l))
					/ (4 * k);
			rotor3SpeedSquare = Math.max(rotor3SpeedSquare, MIN_ROTOR_SPEED_2);
			rotor3SpeedSquare = Math.min(rotor3SpeedSquare, MAX_ROTOR_SPEED_2);
			rotor3SpeedSquare *= drone.getGuidence().engineFactor;

			double rotor4SpeedSquare = (thrust - (2 * b * rollError * Iyy - yawError
					* Izz * k * l)
					/ (b * l))
					/ (4 * k);
			rotor4SpeedSquare = Math.max(rotor4SpeedSquare, MIN_ROTOR_SPEED_2);
			rotor4SpeedSquare = Math.min(rotor4SpeedSquare, MAX_ROTOR_SPEED_2);
			rotor4SpeedSquare *= drone.getGuidence().engineFactor;

			// Front-Left Prop
			propellers.motor1 = (int) (Math.sqrt(rotor1SpeedSquare) * SPEED_SCALE);
			// Front-Right Prop
			propellers.motor2 = (int) (Math.sqrt(rotor2SpeedSquare) * SPEED_SCALE);
			// Rear-Right Prop
			propellers.motor3 = (int) (Math.sqrt(rotor3SpeedSquare) * SPEED_SCALE);
			// Rear-Left Prop
			propellers.motor4 = (int) (Math.sqrt(rotor4SpeedSquare) * SPEED_SCALE);

		} else {
			propellers.motor1 = 0;
			propellers.motor2 = 0;
			propellers.motor3 = 0;
			propellers.motor4 = 0;
		}
		// ----------------------------------------------
		propellers.update();
	}

	private void readNav() {
		AhrsNav nav = drone.getNav();
		nav.readNav();

		float[] droneAxis = { nav.pitch, nav.roll, nav.yaw };
		float[] frameAxis = MathUtils.axisConversion(droneAxis, frameAngle);
		float[] targetDroneAxis = { drone.getGuidence().targetPitch,
				drone.getGuidence().targetRoll, drone.getGuidence().targetYaw };
		float[] targetFrameAxis = MathUtils.axisConversion(targetDroneAxis,
				frameAngle);

		pitch = frameAxis[0];
		roll = frameAxis[1];
		yaw = frameAxis[2];

		targetPitch = targetFrameAxis[0];
		targetRoll = targetFrameAxis[1];
		targetYaw = targetFrameAxis[2];
	}
}
