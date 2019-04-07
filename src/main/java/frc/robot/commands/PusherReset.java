/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class PusherReset extends Command {
  Timer timer;
  boolean wasJustPressed;
  double extendTime;
  public PusherReset() {
    timer = new Timer();
    wasJustPressed = false;
    extendTime = 1;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (!wasJustPressed && Robot.oi.doubleButtons.get(Robot.oi.commandIndex.get("climbRelease")).getButtonState()) {
      wasJustPressed = true;
      timer.start();
    } else if (timer.get() > extendTime && wasJustPressed) {
      Robot.oi.doubleButtons.get(Robot.oi.commandIndex.get("climbRelease")).setButtonState(false);
      timer.stop();
      timer.reset();
      wasJustPressed = false;
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
