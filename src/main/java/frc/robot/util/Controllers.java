/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.util;

import frc.robot.Robot;

/**
 * Class to handle all controller values and buttons
 * 
 * @author Aiden Onstott
 */
public class Controllers {
  public enum ControllerType {
    XBOX, JOYSTICK;
  }

  private static ControllerType getControllerType() {
    return ControllerType.JOYSTICK;
  }

  public static double getYAxis() {
    if (getControllerType() == ControllerType.XBOX) {
      // Get xbox controller
      return 0;
    } else if (getControllerType() == ControllerType.JOYSTICK) {
      return -Robot.oi.stick.getRawAxis(JoystickMap.joystickYAxis);
    } else {
      return 0;
    }
  }

  public static double getXAxis() {
    if (getControllerType() == ControllerType.XBOX) {
      // Get xbox controller
      return 0;
    } else if (getControllerType() == ControllerType.JOYSTICK) {
      return Robot.oi.stick.getRawAxis(JoystickMap.joystickZAxis);
    } else {
      return 0;
    }
  }

  public static double getThrottle() {
    if (getControllerType() == ControllerType.XBOX) {
      return 1;
    } else if (getControllerType() == ControllerType.JOYSTICK) {
      return Robot.oi.stick.getThrottle();
    } else {
      return 1;
    }
  }
}
