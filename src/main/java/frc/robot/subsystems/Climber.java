/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Climber extends Subsystem {
  public DoubleSolenoid climberSolRelease;
  public DoubleSolenoid climberSolGrab;
  public Spark climber;
  public Relay spike;

  public Climber() {
    climberSolRelease = new DoubleSolenoid(RobotMap.SOLENOID_PUSHER_0, RobotMap.SOLENOID_PUSHER_1);
    climberSolGrab = new DoubleSolenoid(RobotMap.SOLENOID_CLIMBER_0, RobotMap.SOLENOID_CLIMBER_1);
    climber = new Spark(RobotMap.CLIMBER_SPARK);
    spike = new Relay(RobotMap.CLIMBER_SPIKE);
  }

  public void setRelease(boolean state) { // true = extended, false = retracted
    if(state) {
      climberSolRelease.set(DoubleSolenoid.Value.kForward);
    }
    else {
      climberSolRelease.set(DoubleSolenoid.Value.kReverse);
    }
  }

  public void setGrab(boolean state) { // true = extended, false = retracted
    if (state) {
      climberSolGrab.set(DoubleSolenoid.Value.kForward);
    } else {
      climberSolGrab.set(DoubleSolenoid.Value.kReverse);
    }
  }

  public void setClimberSpark(boolean state1, boolean state2) { // true = on, false = off
    if (state1 ^ state2) {
      if (state1) {
        climber.set(-1);
      } else if (state2) {
        climber.set(0.5);
      }
    } else {
      climber.set(0);
    } 
  }

  public void setSpike(boolean state) {
    if (state) {
      spike.set(Relay.Value.kOn);
    } else {
      spike.set(Relay.Value.kOff);
    }
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new frc.robot.commands.Climber());
  }
}
