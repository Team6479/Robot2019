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
   * Enum to handle controller types such as Xbox, Joystick, and others if needed. Also has code for SmartDashboard
   */
  public static enum ControllerType {
    xbox("XBOX"),
    joystick("JOYSTICK");

    private ControllerType(String key) {
      this.key = key;
    }

    private String key;

    public String getKey() {
      return key;
    }

    public static final String name = "Controller";
  }

  public static ControllerType controllerType;
  public static Boolean axisIsLocked = false;
  public static Boolean axisButtonWasJustPressed = false;

  /**
   * Sets controller type (xbox/joystick)
   * @param controllerInput Controller type to set to
   */
  public static void setControllerType(ControllerType controllerInput) {
    controllerType = controllerInput;
    if (controllerType == ControllerType.joystick) {
      Robot.oi.initalizeJoystick();
    } else if (controllerType == ControllerType.xbox) {
      Robot.oi.initalizeXbox();
    }
  }

  public static void checkAxisButton() {
    if (controllerType == ControllerType.joystick && Robot.oi.axisLock.isPressed() && !axisButtonWasJustPressed) {
      axisIsLocked = !axisIsLocked;
      axisButtonWasJustPressed = true;
    } else if (controllerType == ControllerType.joystick && !Robot.oi.axisLock.isPressed()) {
      axisButtonWasJustPressed = false;
    }
  }

  /**
   * Gets/returns y axis double.
   * @return y axis value based on the ControllerType.
   */
  public static double getYAxis() {
    if (controllerType == ControllerType.xbox) {
      //Return the x axis of the left analouge sitck
      return -Robot.oi.controller.getY(Hand.kLeft);
    } else if (controllerType == ControllerType.joystick) {
      if (axisIsLocked) {
        return -Math.round(Robot.oi.stick.getRawAxis(JoystickMap.joystickYAxis));
      } else {
        //Return the y axis of the joystick
        return -Robot.oi.stick.getRawAxis(JoystickMap.joystickYAxis);
      }
    } else {
      //Default return
      return 0;
    }
  }

  /**
   * Gets/returns x axis double.
   * @return x axis values based on the ControllerType
   */
  public static double getXAxis() {
    if (controllerType == ControllerType.xbox) {
      //Return the x axis of the left analogue sitck
      return Robot.oi.controller.getTriggerAxis(XboxMap.rightHand) - Robot.oi.controller.getTriggerAxis(XboxMap.leftHand);
    } else if (controllerType == ControllerType.joystick) {
      if (axisIsLocked) {
        return -Math.round(Robot.oi.stick.getRawAxis(JoystickMap.joystickXAxis));
      } else {
        //Return the y axis of the joystick
        return -Robot.oi.stick.getRawAxis(JoystickMap.joystickXAxis);
      }
    } else {
      //Defualt return
      return 0;
    }
  }

  /**
   * Gets/returns x axis double.
   * @return x axis value based on the ControllerType.
   */
  public static double getZAxis() {
    if (controllerType == ControllerType.xbox) {
      //Return the combined values of both xbox triggers
      return Robot.oi.controller.getX(XboxMap.rightHand);
    } else if (controllerType == ControllerType.joystick) {
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
    if (controllerType == ControllerType.xbox) {
      //Sets throttle/sensitivity to full
      return -1;
    } else if (controllerType == ControllerType.joystick) {
      //Returns the postition of the bottom throttle on joystick
      return Robot.oi.stick.getThrottle();
    } else {
      //Defualt return
      return 1;
    }
  }
}
