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
  double rotaion;

  public TurnToBall() {
    requires(Robot.tcp);
    requires(Robot.drivetrain);
    // eg. requires(chassis);
  }

  public static double degToPercent(double deg) {
    if(deg > 180) {
      deg = -(deg % 180);
    }
    return deg / 180;
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
  }

  /*public double avgRotation() {
    return (Robot.drivetrain.rightMaster.get)
  }*/
  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    //
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
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
