/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class ClimberWinch extends Subsystem {
  private Spark climberMotor;

  public ClimberWinch() {
    climberMotor = new Spark(RobotMap.CLIMBER_SPARK);
  }

  @Override
  public void initDefaultCommand() {
    // setDefaultCommand(new ClimberClimb());
  }

  public void pull() {
    climberMotor.set(-1);
  }

  public void release() {
    climberMotor.set(0.5);
  }

  public void stop() {
    climberMotor.set(0);
  }
}
