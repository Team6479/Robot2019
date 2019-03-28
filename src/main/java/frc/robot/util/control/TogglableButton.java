/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.util.control;

import edu.wpi.first.wpilibj.GenericHID;
import robot.controllers.ButtonTracker;

/**
 * Uses ButtonTracker to toggle buttons on and off
 */
public class TogglableButton {
    /**
     * Makes a button that can be toggled on and off
     * @param controller The controller
     * @param port The port for the button
     */
    public TogglableButton(GenericHID controller, int port) {
        this.button = new ButtonTracker(controller, port);
        buttonState = false;
        buttonWasJustPressed = false;
    }

    /**
     * @return the button's state
     */
    public Boolean getButtonState() {
        return buttonState;
    }

    /**
     * Checks for button presses and toggles if necessary
     */
    public void updateButton() {
        if (button.isPressed() && !buttonWasJustPressed) {
            buttonState = !buttonState;
            buttonWasJustPressed = true;
        } else if (!button.isPressed() && buttonWasJustPressed) {
            buttonWasJustPressed = false;
        }
    }

    public void setButtonState(Boolean newState) {
        buttonState = newState;
    }

    private ButtonTracker button;
    private Boolean buttonState;
    private Boolean buttonWasJustPressed;
}
