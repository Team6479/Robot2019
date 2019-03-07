/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.ArrayList;

import frc.robot.drivers.Joystick;
import frc.robot.drivers.XboxController;
import frc.robot.util.control.ControllerMap;
import frc.robot.util.control.JoystickMap;
import frc.robot.util.control.TogglableButton;
import robot.controllers.ButtonTracker;
import robot.controllers.XboxMap;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  //// CREATING BUTTONS
  // One type of button is a joystick button which is any button on a
  //// joystick.
  // You create one by telling it which joystick it's on and which button
  // number it is.
  // Joystick stick = new Joystick(port);
  // Button button = new JoystickButton(stick, buttonNumber);

  // There are a few additional built in buttons you can use. Additionally,
  // by subclassing Button you can create custom triggers and bind those to
  // commands the same as any other Button.

  //// TRIGGERING COMMANDS WITH BUTTONS
  // Once you have a button, it's trivial to bind it to a button in one of
  // three ways:

  // Start the command when the button is pressed and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenPressed(new ExampleCommand());

  // Run the command while the button is being held down and interrupt it once
  // the button is released.
  // button.whileHeld(new ExampleCommand());

  // Start the command when the button is released and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenReleased(new ExampleCommand());
  public Joystick stick;

  public ButtonTracker axisLock;

  /* ------------------------------- */

  public XboxController controller;

  public ArrayList<TogglableButton> togglableButtons = new ArrayList<TogglableButton>();
  public ArrayList<ButtonTracker> buttons = new ArrayList<ButtonTracker>();

  public final int hatchPivot = 0;
  public final int hatchGrabber = 1;
  public final int climbDown = 2;
  public final int grabHab = 3;

  public final int climbUp = 0;

  public static enum ButtonType {
    TOGGLABLE, HOLD;
  }

  public void initalizeJoystick() {
    //Joyctick object
    stick = new Joystick(ControllerMap.joystick);
    axisLock = new ButtonTracker(stick, JoystickMap.joystickButton12);
  }

  public void initalizeXbox() {
    //Xbox controller object
    controller = new XboxController(ControllerMap.controller);
    togglableButtons.add(new TogglableButton(controller, XboxMap.AButton));
    togglableButtons.add(new TogglableButton(controller, XboxMap.YButton));
    togglableButtons.add(new TogglableButton(controller, XboxMap.LeftBumper));
    togglableButtons.add(new TogglableButton(controller, XboxMap.BackButton));
    buttons.add(new ButtonTracker(controller, XboxMap.RightBumper));
  }
}
