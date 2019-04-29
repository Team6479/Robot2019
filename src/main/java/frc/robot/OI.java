/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.commands.DeployClimber;
import frc.robot.util.buttons.MultiButton;
import frc.robot.util.controllers.CBXboxController;
import frc.robot.util.controllers.CBXboxController.Buttons;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  public CBXboxController xbox = new CBXboxController(0);

  public OI() {
    xbox.getButton(Buttons.kA).whenPressed(new InstantCommand(Robot.hatchGrabber, Robot.hatchGrabber::toggle));
    xbox.getButton(Buttons.kY).whenPressed(new InstantCommand(Robot.hatchPivot, Robot.hatchPivot::toggle));
    xbox.getButton(Buttons.kBumperLeft).whenPressed(new InstantCommand(Robot.climberGrabber, Robot.hatchGrabber::toggle));
    xbox.getPOVButton(0, true).whenPressed(new InstantCommand(Robot.climberWinch, Robot.climberWinch::pull));
    xbox.getPOVButton(0, true).whenReleased(new InstantCommand(Robot.climberWinch, Robot.climberWinch::stop));
    xbox.getPOVButton(180, true).whenPressed(new InstantCommand(Robot.climberWinch, Robot.climberWinch::release));
    xbox.getPOVButton(180, true).whenReleased(new InstantCommand(Robot.climberWinch, Robot.climberWinch::stop));

    new MultiButton(xbox, Buttons.kStart.value, Buttons.kBack.value).whenPressed(new DeployClimber());
  }
}
