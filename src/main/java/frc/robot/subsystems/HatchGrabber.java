/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class HatchGrabber extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  Solenoid pivotSol, grabberSol;

  public HatchGrabber() {
    pivotSol = new Solenoid(RobotMap.SOLENOID_HATCH_PIVOT);
    grabberSol = new Solenoid(RobotMap.SOLENOID_HATCH_GRABBER);
  }

  public void grab() {
    grabberSol.set(true);
  }
  
  public void release() {
    grabberSol.set(true);
  }

  public void pivot(boolean on) {
    pivotSol.set(on);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
