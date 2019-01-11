package frc.robot.drivers;


public class XboxController extends edu.wpi.first.wpilibj.XboxController {
  public static final double AXIS_THRESHOLD = 0.1;

  public XboxController(final int port){
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