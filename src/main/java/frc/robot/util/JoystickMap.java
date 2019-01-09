/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.util;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class JoystickMap {
  // For example to map the left and right motors, you could define the
  // following variables to use with your drivetrain subsystem.
  // public static int leftMotor = 1;
  // public static int rightMotor = 2;

  // If you are using multiple modules, make sure to define both the port
  // number and the module. For example you with a rangefinder:
  // public static int rangefinderPort = 1;
  // public static int rangefinderModule = 1;

  // Controllers
  public static final int joystick = 0;

  // Joystick Buttons
  public static final int joystickButton1 = 1;
  public static final int joystickButton2 = 2;
  public static final int joystickButton3 = 3;
  public static final int joystickButton4 = 4;
  public static final int joystickButton5 = 5;
  public static final int joystickButton6 = 6;
  public static final int joystickButton7 = 7;
  public static final int joystickButton8 = 8;
  public static final int joystickButton9 = 9;
  public static final int joystickButton10 = 10;
  public static final int joystickButton11 = 11;
  public static final int joystickButton12 = 12;
  public static final int joystickButton13 = 13;

  public static final int joystickXAxis = 0;
  public static final int joystickYAxis = 1;
  public static final int joystickZAxis = 2;
}
