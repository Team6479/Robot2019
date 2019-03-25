/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.OI.ButtonType;
import frc.robot.util.control.Controllers;

public class HatchGrabber extends Command {

  public HatchGrabber() {
    requires(Robot.hatchGrabber);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    if(Robot.hatchGrabber.grabberSol.get() == DoubleSolenoid.Value.kReverse) {
      Robot.oi.togglableButtons.get(Robot.oi.commandIndex.get("hatchGrabber")).setButtonState(true);
    }
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (Controllers.checkButtonStatus(Robot.oi.commandIndex.get("hatchGrabber"), ButtonType.TOGGLABLE)) {
      Robot.hatchGrabber.grab();
    } else {
      Robot.hatchGrabber.release();
    }
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
