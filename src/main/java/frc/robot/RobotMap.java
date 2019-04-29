/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 *
 * Format for variable names should be: <SUBSYSTEM>_<NAME>
 */
public class RobotMap {

  // PWM
  public static final int CLIMBER_SPARK = 0;

  // Drivetrain Motors
  public static final int DRIVETRAIN_LEFT_FRONT = 0;
  public static final int DRIVETRAIN_LEFT_BACK = 2;
  public static final int DRIVETRAIN_RIGHT_FRONT = 1;
  public static final int DRIVETRAIN_RIGHT_BACK = 3;

  // Pneumatics
  public static final int COMPRESSOR = 0;
  public static final int SOLENOID_HATCH_PIVOT_0 = 3;
  public static final int SOLENOID_HATCH_PIVOT_1 = 2;
  public static final int SOLENOID_HATCH_GRABBER_0 = 1;
  public static final int SOLENOID_HATCH_GRABBER_1 = 0;
  public static final int SOLENOID_CLIMBER_0 = 6; // These will be changed later
  public static final int SOLENOID_CLIMBER_1 = 7; // These will be changed later
  public static final int SOLENOID_PUSHER_0 = 4;
  public static final int SOLENOID_PUSHER_1 = 5;
}
