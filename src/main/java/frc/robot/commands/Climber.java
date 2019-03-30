/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI.ButtonType;
import frc.robot.Robot;
import frc.robot.util.control.Controllers;

public class Climber extends Command {
  public Climber() {
    requires(Robot.climber);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.climber.setRelease(Controllers.checkButtonStatus(Robot.oi.commandIndex.get("climbRelease"), ButtonType.DOUBLE));
    Robot.climber.setGrab(Controllers.checkButtonStatus(Robot.oi.commandIndex.get("grabHab"), ButtonType.TOGGLABLE));
    Robot.climber.setClimberSpark(Controllers.checkButtonStatus(Robot.oi.commandIndex.get("climbUp"), ButtonType.POV), 
        Controllers.checkButtonStatus(Robot.oi.commandIndex.get("climbDown"), ButtonType.POV));
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
