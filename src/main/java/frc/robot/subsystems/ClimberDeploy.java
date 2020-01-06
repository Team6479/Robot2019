/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

/** Add your docs here. */
public class ClimberDeploy extends SubsystemBase {
  private DoubleSolenoid deployDubSol;

  public ClimberDeploy() {
    deployDubSol = new DoubleSolenoid(RobotMap.SOLENOID_PUSHER_0, RobotMap.SOLENOID_PUSHER_1);
  }

  public void deploy() {
    deployDubSol.set(DoubleSolenoid.Value.kForward);
  }

  public void retract() {
    deployDubSol.set(DoubleSolenoid.Value.kReverse);
  }
}
