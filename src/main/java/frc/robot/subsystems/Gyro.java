/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class Gyro extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private AHRS navX;

  /**
   * Sets up the NavX
   */
  public Gyro() {
    try {
      navX = new AHRS(SPI.Port.kMXP);
    }
    catch(RuntimeException ex) {
      DriverStation.reportError("NavX Error: " + ex.getMessage(), true);
    }
  }

  /**
   * @return The {@link com.kauailabs.navx.frc.AHRS NavX}
   */
  public AHRS getNavX() {
    return navX;
  }

  /**
  * Resets the {@link com.kauailabs.navx.frc.AHRS NavX}
  */
  public void reset() {
    navX.reset();
  }

  /**
   * @return The rotation reported by the {@link com.kauailabs.navx.frc.AHRS NavX}
   */
  public double getAngle() {
    return navX.getAngle();
  }

  /**
   * @return The altitude reported by the {@link com.kauailabs.navx.frc.AHRS NavX}
   */
  public double getAlti() {
    return navX.getAltitude();
  }

  /**
   * @return Whether or not the {@link com.kauailabs.navx.frc.AHRS NavX} reports rotation
   */
  public boolean isRotating() {
    return navX.isRotating();
  }

  /**
   * @return The yaw reported by the {@link com.kauailabs.navx.frc.AHRS NavX}
   */
  public double getYaw() {
    return navX.getYaw();
  }

  /**
   * @return The rotation reported by the {@link com.kauailabs.navx.frc.AHRS NavX}
   */
  public double getPitch() {
    return navX.getPitch();
  }

  /**
   * @return The rotation reported by the {@link com.kauailabs.navx.frc.AHRS NavX}
   */
  public double getRoll() {
    return navX.getRoll();
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
