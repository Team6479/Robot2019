/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Pneumatics extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public Compressor compressor;

  /**
   * An enum of Solenoids
   * Used to store the {@link edu.wpi.first.wpilibj.Solenoid Solenoid} objects
   */
  public enum Solenoids {
    PLACEHOLDER(RobotMap.SOLENOID_PLACEHOLDER);
    public Solenoid solenoid;
    private Solenoids(int port) {
      this.solenoid = new Solenoid(port);
    }
  }

  public Pneumatics() {
    compressor = new Compressor(RobotMap.COMPRESSOR);
  }

  /**
   * Sets a {@link Solenoids solenoid} on/off
   * @param sol The solenoid to set
   * @param state The new state of the solenoid
   */
  public static void setSolenoidState(Solenoids sol, boolean state) {
    sol.solenoid.set(state);
  }
  /**
   * Sets a {@link edu.wpi.first.wpilibj.Solenoid Solenoid} on/off
   * @param sol The {@link edu.wpi.first.wpilibj.Solenoid Solenoid} to set
   * @param state The new state of the solenoid
   */
  public static void setSolenoidState(Solenoid sol, boolean state) {
    sol.set(state);
  }
  /**
   * Sets a {@link edu.wpi.first.wpilibj.Solenoid Solenoid} on/off
   * @param port The port of the {@link edu.wpi.first.wpilibj.Solenoid Solenoid} to set
   * @param state The new state of the solenoid
   * @return The newly created {@link edu.wpi.first.wpilibj.Solenoid Solenoid}
   * To avoid creating unnecessary objects, please use this method minimally
   */
  public static Solenoid setSolenoidState(int port, boolean state) {
    Solenoid sol = new Solenoid(port);
    sol.set(state);
    return sol;
  }

  /**
   * Sets a {@link Solenoids solenoid} on/off
   * @param sol The solenoid to set
   * @return The current state of the {@link edu.wpi.first.wpilibj.Solenoid Solenoid}
   */
  public static boolean getSolenoidState(Solenoids sol) {
    return sol.solenoid.get();
  }
  /**
   * Sets a {@link edu.wpi.first.wpilibj.Solenoid Solenoid} on/off
   * @param sol The {@link edu.wpi.first.wpilibj.Solenoid Solenoid} to set
   * @return The current state of the {@link edu.wpi.first.wpilibj.Solenoid Solenoid}
   */
  public static boolean getSolenoidState(Solenoid sol) {
    return sol.get();
  }
  /**
   * Sets a {@link edu.wpi.first.wpilibj.Solenoid Solenoid} on/off
   * @param port The port of the {@link edu.wpi.first.wpilibj.Solenoid Solenoid} to set
   * @return The current state of the {@link edu.wpi.first.wpilibj.Solenoid Solenoid}
   * To avoid creating unnecessary objects, please use this method minimally
   */
  public static boolean getSolenoidState(int port) {
    Solenoid sol = new Solenoid(port);
    return sol.get();
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
