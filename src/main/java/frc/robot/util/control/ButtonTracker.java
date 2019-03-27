package frc.robot.util.control;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * <h1>Button Tracker</h1> This class handles button input for a joystick. It
 * allows for checking for presses and releases instead of just on/off.
 * <p>
 * <b>NOTE:</b> This is also implemented in GenricHID, which all joysticks
 * inherit from. However, it is my personal opinion that this is easier to work
 * with.
 * 
 * 
 * @author Jacob Abraham
 */
public class ButtonTracker extends JoystickButton {

    /**
     * Keeps track of all instances of ButtonTracker, allows for updates in
     * {@link #updateAll()}
     */
    static private ArrayList<ButtonTracker> allButtons = new ArrayList<ButtonTracker>();

    /**
     * The current state of the button
     */
    private boolean mNow;
    /**
     * The previous state of the button
     */
    private boolean mLast;

    /**
     * Constructor of a button, functions exactly as the superclass constructor with
     * an additional section adding the new button to the list of buttons.
     * 
     * @param joystick     what joystick this button is apart of
     * @param buttonNumber the port number of the button on the joystick
     * @see JoystickButton
     */
    public ButtonTracker(GenericHID joystick, int buttonNumber) {
        super(joystick, buttonNumber);
        allButtons.add(this);
        this.enabled = true;
        this.joystick = joystick;
        this.buttonNumber = buttonNumber;
    }
    /**
     * @return GenericHID joystick
     */
    public GenericHID getJoystick() {
        return joystick;
    }

    /**
     * @return buttonNumber
     */
    public int getButtonNumber() {
        return buttonNumber;
    }

    /**
     * Whether or not the button is currently pressed
     * 
     * @return true if the button is currently down and it is enabled
     */
    public boolean isPressed() {
        if (enabled) {
            return get();
        }
        return false;
    }

    /**
     * Enables button
     */
    public void disable() {
        enabled = false;
    }

    /**
     * Disables button
     */
    public void enable() {
        enabled = true;
    }

    /**
     * Checks if the button is enabled
     * 
     * @return true if the button is enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Checks if the button was just pressed on the last cycle
     * 
     * @return true if the button was just pressed
     */
    public boolean wasJustPressed() {
        return (mNow && (!mLast));
    }

    /**
     * Checks if the button was just released on the last cycle
     * 
     * @return true if the button was just released
     */
    public boolean wasJustReleased() {
        return (!mNow && mLast);
    }

    /**
     * Updates the state of the button
     */
    public void update() {
        mLast = mNow;
        mNow = get();
    }

    /**
     * Updates the state of all ButtonTrackers, calls {@link #update()} for all
     * buttons
     * <p>
     * <b>NOTE:</b> This method must be called every cycle of the robot loop or the
     * button will not function as expected
     */
    public static void updateAll() {
        for (ButtonTracker button : allButtons) {
            button.update();
        }
    }

    private boolean enabled;
    private GenericHID joystick;
    private int buttonNumber;
}
