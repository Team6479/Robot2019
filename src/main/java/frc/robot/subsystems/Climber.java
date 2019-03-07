/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Climber extends Subsystem {
  public DoubleSolenoid grabberSol;

  public Climber() {
    
    grabberSol = new DoubleSolenoid(RobotMap.SOLENOID_CLIMBER_0, RobotMap.SOLENOID_CLIMBER_1);
  }

  public void grab() {
    grabberSol.set(DoubleSolenoid.Value.kReverse);
  }

  public void release() {
    grabberSol.set(DoubleSolenoid.Value.kForward);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new frc.robot.commands.HatchGrabber());
  }
}
