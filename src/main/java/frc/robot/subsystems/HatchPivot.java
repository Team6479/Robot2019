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
public class HatchPivot extends Subsystem {
  private DoubleSolenoid pivotSol;

  public HatchPivot() {
    pivotSol = new DoubleSolenoid(RobotMap.SOLENOID_HATCH_PIVOT_0, RobotMap.SOLENOID_HATCH_PIVOT_1);

    // Set default state to inside robot frame
    pivotBack();
  }

  @Override
  public void initDefaultCommand() {
  }

  public void pivotForward() {
    pivotSol.set(DoubleSolenoid.Value.kForward);
  }

  public void pivotBack() {
    pivotSol.set(DoubleSolenoid.Value.kReverse);
  }

  public void toggle() {
    DoubleSolenoid.Value state = pivotSol.get();
    if (state == DoubleSolenoid.Value.kForward) {
      this.pivotBack();
    }
    // This should trigger for both kOff and kReverse states
    else {
      this.pivotForward();
    }
  }

}
