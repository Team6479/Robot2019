/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.Button;
import frc.robot.subsystems.ClimberWinch;

public class ClimberClimb extends CommandBase {
  private final ClimberWinch climberWinch;
  private final Button upButton;
  private final Button downButton;

  public ClimberClimb(ClimberWinch climberWinch, Button upButton, Button downButton) {
    this.climberWinch = climberWinch;
    this.upButton = upButton;
    this.downButton = downButton;
    addRequirements(this.climberWinch);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    //Robot.oi.xbox.getPOVButton(0, true)
    if (upButton.get()) {
      climberWinch.pull();
    } else if (downButton.get()) { //Robot.oi.xbox.getPOVButton(180, true)
      climberWinch.release();
    } else {
      climberWinch.stop();
    }
  }

  // Called once after isFinished returns true
  @Override
  public void end(boolean interrupted) {
    climberWinch.stop();
  }
}
