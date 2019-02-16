/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.util.control.Controllers;

/**
 * An example command. You can replace me with your own command.
 */
public class TeleopDrive extends Command {
  private double scale;

  public TeleopDrive() {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.drivetrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when \this Command is scheduled to run
  @Override
  protected void execute() {
    // Execute arcadeDrive with the x axis and y axis
    scale = (-Controllers.getThrottle() + 1) / 2;
    // scale = 1;    
    // Robot.drivetrain.arcadeDrive(Robot.oi.controller.getX(Hand.kLeft),
    // Robot.oi.controller.getY(Hand.kLeft));
    Controllers.updateButtons();
    Robot.drivetrain.mecanumDrive(Controllers.getXAxis() * scale, Controllers.getZAxis() * scale, Controllers.getYAxis() * scale);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
