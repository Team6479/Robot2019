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

  public double siggy(double x) {
    if(x == 0) {
      return 0;
    }
    else if(x > 0) {
      return -(Math.pow(Math.E, x / 45) / (Math.pow(Math.E, x / 45) + 1)) + 1;
    }
    else {
      return -(Math.pow(Math.E, x / 45) / (Math.pow(Math.E, x / 45) + 1)) - 1;
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
