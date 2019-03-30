/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.util.control.buttonTypes;

import edu.wpi.first.wpilibj.GenericHID;

/**
 * Button that is based on the pov angle
 */
public class POVButton {
    /**
     * Makes a button that can be toggled on and off
     * 
     * @param controller The controller
     * @param direction  The direction for the pov button
     */
    public POVButton(GenericHID controller, int direction) {
        this.controller = controller;
        this.direction = direction;
    }

    /**
     * @return the button's state
     */
    public boolean getButtonState() {
        if (controller.getPOV() == direction) {
            return true;
        }
        return false;
    }

    private GenericHID controller;
    private int direction;
}
