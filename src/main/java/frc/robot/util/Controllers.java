/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.util;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.Robot;

/**
 * Class to handle all controller values and buttons
 * 
 * @author Aiden Onstott
 */
public class Controllers {
  /**
   * Enum to handle controller types such as Xbox, Joystick, and others if needed.
   */
  public enum ControllerType {
    XBOX, JOYSTICK;
  }

  /**
   * Gets/sets controller type (xbox/joystick)
   * @return Returns controller type (xbox/joystick)
   */
  private static ControllerType getControllerType() {
    return ControllerType.XBOX;
  }

  /**
   * Gets/returns y axis double.
   * @return y axis value based on the ControllerType.
   */
  public static double getYAxis() {
    if (getControllerType() == ControllerType.XBOX) {
      //Return the combined values of both xbox triggers
      return Robot.oi.controller.getTriggerAxis(Hand.kRight) - Robot.oi.controller.getTriggerAxis(Hand.kLeft);
    } else if (getControllerType() == ControllerType.JOYSTICK) {
      //Return the y axis of the joyctick
      return -Robot.oi.stick.getRawAxis(JoystickMap.joystickYAxis);
    } else {
      //Default return
      return 0;
    }
  }

  /**
   * Gets/returns x axis double.
   * @return x axis value based on the ControllerType.
   */
  public static double getXAxis() {
    if (getControllerType() == ControllerType.XBOX) {
      //Return the x axis of the left analouge sitck
      return Robot.oi.controller.getX(Hand.kLeft);
    } else if (getControllerType() == ControllerType.JOYSTICK) {
      //Return the z axis (rotation) of the joystick
      return Robot.oi.stick.getRawAxis(JoystickMap.joystickZAxis);
    } else {
      //Default return
      return 0;
    }
  }

  /**
   * Gets/returns throttle value.
   * @return Throttle value if ControllerType is joystick. If ControllerType is xbox, the method returns 1.
   */
  public static double getThrottle() {
    if (getControllerType() == ControllerType.XBOX) {
      //Sets throttle/sensitivity to full
      return 1;
    } else if (getControllerType() == ControllerType.JOYSTICK) {
      //Returns the postition of the bottom throttle on joystick
      return Robot.oi.stick.getThrottle();
    } else {
      //Defualt return
      return 1;
    }
  }
}
