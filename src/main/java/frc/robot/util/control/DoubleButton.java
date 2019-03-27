/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.util.control;

import frc.robot.util.control.ButtonTracker;

/**
 * Add your docs here.
 */
public class DoubleButton {
    /**
     * Makes a button that can be toggled on and off
     * 
     * @param controller The controller
     * @param port       The port for the button
     */
    public DoubleButton(ButtonTracker buttonA, ButtonTracker buttonB) {
        buttonState = false;
        buttonWasJustPressed = false;
        this.buttonA = buttonA;
        this.buttonB = buttonB;
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
        if (buttonA.isPressed() && buttonB.isPressed() && !buttonWasJustPressed) {
            buttonState = !buttonState;
            buttonWasJustPressed = true;
        } else if (!(buttonA.isPressed() || buttonB.isPressed()) && buttonWasJustPressed) {
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

    /**
     * @return buttonA
     */
    public ButtonTracker getButtonA() {
        return buttonA;
    }

    /**
     * @return buttonB
     */
    public ButtonTracker getButtonB() {
        return buttonB;
    }

    private Boolean buttonState;
    private Boolean buttonWasJustPressed;
    private ButtonTracker buttonA, buttonB;
}
