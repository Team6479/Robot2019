/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ClimberDeploy;

/**
 * Add your docs here.
 */
public class DeployClimber extends CommandBase {
  private final ClimberDeploy climberDeploy;

  /**
   * Add your docs here.
   */
  public DeployClimber(ClimberDeploy climberDeploy) {
    this.climberDeploy = climberDeploy;
    addRequirements(this.climberDeploy);
    withTimeout(1);
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
    climberDeploy.deploy();
  }

  // Called once after timeout
  @Override
  public void end(boolean interrupted) {
    climberDeploy.retract();
  }
}
