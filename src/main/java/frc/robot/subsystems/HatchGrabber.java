/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class HatchGrabber extends SubsystemBase {
  private DoubleSolenoid grabberSol;

  public HatchGrabber() {
    grabberSol = new DoubleSolenoid(RobotMap.SOLENOID_HATCH_GRABBER_0, RobotMap.SOLENOID_HATCH_GRABBER_1);

    // Set default state to grab
    grab();
  }

  public void grab() {
    grabberSol.set(DoubleSolenoid.Value.kForward);
  }

  public void release() {
    grabberSol.set(DoubleSolenoid.Value.kReverse);
  }

  public void toggle() {
    DoubleSolenoid.Value state = grabberSol.get();
    if (state == DoubleSolenoid.Value.kForward) {
      this.release();
    }
    // This should trigger for both kOff and kReverse states
    else {
      this.grab();
    }
  }

}
