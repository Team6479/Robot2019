/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

/**
 * Starts the Vision code on the Jetson
 * @author Leo Wilson
 */
public class StartJetsonCode extends InstantCommand {
  public StartJetsonCode() {
    super();
    requires(Robot.jetsonSSH);
  }

  // Called once when the command executes
  @Override
  protected void initialize() {
    Robot.jetsonSSH.run("vision-2019 tape");
  }

}
