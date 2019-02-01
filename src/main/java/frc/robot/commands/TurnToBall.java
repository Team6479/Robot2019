/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.TCP;

public class TurnToBall extends Command {
  private static final double ERR = 1;
  double rotaion;
  double current;

  public TurnToBall() {
    requires(Robot.tcp);
    requires(Robot.drivetrain);
    requires(Robot.gyro);
    // eg. requires(chassis);
  }

  /**
   * A custom logistic curve function designed for the robot
   * May need to be tuned to particular robots
   * @param x The value to sigmoid
   * @return The y-value of the curve at x
   */
  public double siggy(double x) {
    // decreasing this increases the steepness of the curve
    final double STEEPNESS_TUNER = 20;
    // affects the y-intercept
    final double Y_TUNER = -1;
    // affects the x-intercept
    final double X_TUNER = 1;
    // affects the height of the curve
    final double HEIGHT_TUNER = -1;
    // offsets the y-value of the curve
    // will be multiplied by -1 for negative x-values
    final double Y_OFFSET = 0;
    if(x > -ERR && x < ERR) { // A primitive 1D deadzone
      return 0;
    }
    else if(x > 0) {
      return HEIGHT_TUNER * (Math.pow(Math.E, x / STEEPNESS_TUNER) / (Math.pow(Math.E, x / STEEPNESS_TUNER) + X_TUNER) + Y_TUNER) + Y_OFFSET;
    }
    else {
      return HEIGHT_TUNER * (Math.pow(Math.E, x / STEEPNESS_TUNER) / (Math.pow(Math.E, x / STEEPNESS_TUNER) + X_TUNER) + Y_TUNER) - Y_OFFSET;
    }
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    String[] output = Robot.tcp.get(TCP.Keys.POSITION).split(",");
    double[] vals = new double[output.length];
    for(int i = 0; i < output.length; i++) {
      vals[i] = Double.parseDouble(output[i]);
    }
    rotaion = vals[1];
    Robot.gyro.reset();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    current = Robot.gyro.getAngle();
    Robot.drivetrain.mecanumDrive(0, siggy(rotaion - current), 0);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Math.abs(Robot.gyro.getAngle() - rotaion) <= ERR;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
