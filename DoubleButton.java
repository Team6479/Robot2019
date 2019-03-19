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
        } else if (!buttonA.isPressed() || !buttonB.isPressed() && buttonWasJustPressed) {
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
    private ButtonTracker buttonA, buttonB;
}