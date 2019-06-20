package frc.robot.wpioverride;

import robot.controllers.XboxMap;

public class XboxController extends edu.wpi.first.wpilibj.XboxController {
	private double deadZone;
	private DriverStation driverStation = DriverStation.getInstance();

	/**
	 * Construct an instance of a joystick. The joystick index is the USB port on the drivers
	 * station.
	 *
	 * @param port The port on the Driver Station that the joystick is plugged into.
	 */
	public XboxController(int port) {
		super(port);
		deadZone = 0.2;
	}

	@Override
	public double getRawAxis(int axis) {
		if (axis == XboxMap.LeftTrigger || axis == XboxMap.RightTrigger) {
			return super.getRawAxis(axis);
		}

		double x;
		double y;

		if (axis == XboxMap.LeftJoystickX || axis == XboxMap.LeftJoystickY) {
			x = super.getRawAxis(XboxMap.LeftJoystickX);
			y = super.getRawAxis(XboxMap.LeftJoystickY);
		}
		else {
			x = super.getRawAxis(XboxMap.RightJoystickX);
			y = super.getRawAxis(XboxMap.RightJoyStickY);
		}

		//Magnitude
		double mag = Math.sqrt((x * x) + (y * y));

		if (mag > deadZone) {
			double range = 1.0 - deadZone;
			double normMag = Math.min(1.0, (mag - deadZone) / range);
			double scale = normMag / mag;
			x = x * scale;
			y = y * scale;
		}
		else {
			x = 0;
			y = 0;
		}

		if (axis == XboxMap.LeftJoystickX || axis == XboxMap.RightJoystickX) {
			return x;
		}
		else {
			return y;
		}
	}

  /**
   * Get the angle in degrees of a POV on the HID.
   *
   * <p>The POV angles start at 0 in the up direction, and increase clockwise (eg right is 90,
   * upper-left is 315).
   *
   * @param pov The index of the POV to read (starting at 0)
   * @return the angle of the POV in degrees, or -1 if the POV is not pressed.
   */
  @Override
  public int getPOV(int pov) {
    return driverStation.getStickPOV(super.getPort(), pov);
  }
}
