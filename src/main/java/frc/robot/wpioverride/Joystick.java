package frc.robot.wpioverride;

public class Joystick extends edu.wpi.first.wpilibj.Joystick {
  public static final double AXIS_THRESHOLD = 0.1;

  public Joystick(final int port) {
    super(port);
  }

  @Override
  public double getRawAxis(int axis) {
    double rawAxis = super.getRawAxis(axis);
    if (Math.abs(rawAxis) <= AXIS_THRESHOLD) {
      rawAxis = 0;
    }
    return rawAxis;
  }
}
