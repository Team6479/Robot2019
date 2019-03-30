/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.util.control.buttonTypes;

import edu.wpi.first.wpilibj.GenericHID;

/**
 * Uses ButtonTracker to toggle buttons on and off
 */
public class TogglableButton extends ButtonTracker {
    /**
     * Makes a button that can be toggled on and off
     * 
     * @param controller The controller
     * @param port       The port for the button
     */
    public TogglableButton(GenericHID controller, int port) {
        super(controller, port);
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
     * Evaluates the button as on or off
     */
    public void updateButton() {
        if (isPressed() && !buttonWasJustPressed) {
            buttonState = !buttonState;
            buttonWasJustPressed = true;
        } else if (!isPressed() && buttonWasJustPressed) {
            buttonWasJustPressed = false;
        }
    }

    /**
     * Sets the button's state according to the parameter
     * 
     * @param newState The state to set the button to
     */
    public void setButtonState(Boolean newState) {
        buttonState = newState;
    }

    private Boolean buttonState;
    private Boolean buttonWasJustPressed;
}
