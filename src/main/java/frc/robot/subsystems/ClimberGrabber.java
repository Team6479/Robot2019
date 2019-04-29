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
public class ClimberGrabber extends Subsystem {
  private DoubleSolenoid climberDubSol;

  public ClimberGrabber() {
    climberDubSol = new DoubleSolenoid(RobotMap.SOLENOID_CLIMBER_0, RobotMap.SOLENOID_CLIMBER_1);
  }

  @Override
  public void initDefaultCommand() {
  }

  public void grab() {
    climberDubSol.set(DoubleSolenoid.Value.kForward);
  }

  public void release() {
    climberDubSol.set(DoubleSolenoid.Value.kReverse);
  }

  public void toggle() {
    DoubleSolenoid.Value state = climberDubSol.get();
    if (state == DoubleSolenoid.Value.kForward) {
      this.release();
    }
    // This should trigger for both kOff and kReverse states
    else {
      this.grab();
    }
  }
}
