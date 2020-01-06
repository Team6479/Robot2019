package frc.robot.util.buttons;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.button.Button;

/**
 * A {@link Button} that gets its state from a {@link GenericHID}'s POV angle.
 */
public class POVButton extends Button {
  private final GenericHID joystick;
  private final int pov;
  private final int angle;
  private final boolean fuzzy;

  /**
   * Create a POV button for triggering commands.
   *
   * @param joystick The GenericHID object that has the button (e.g. Joystick, KinectStick, etc)
   * @param pov The button number (see {@link GenericHID#getPOV(int) })
   * @param angle The angle to check the POV for
   * @param fuzzy If set to True angle will be checked to be within plus or minus 45 degrees
   */
  public POVButton(GenericHID joystick, int angle, boolean fuzzy, int pov) {
    this.joystick = joystick;
    this.pov = pov;
    this.angle = angle;
    this.fuzzy = fuzzy;
  }

  /**
   * Create a POV button for triggering commands.
   *
   * @param joystick The GenericHID object that has the button (e.g. Joystick, KinectStick, etc)
   * @param angle The angle to check the POV for
   * @param fuzzy If set to True angle will be checked to be within plus or minus 45 degrees
   */
  public POVButton(GenericHID joystick, int angle, boolean fuzzy) {
    this.joystick = joystick;
    this.pov = 0;
    this.angle = angle;
    this.fuzzy = fuzzy;
  }

  /**
   * Create a POV button for triggering commands.
   *
   * @param joystick The GenericHID object that has the button (e.g. Joystick, KinectStick, etc)
   * @param angle The angle to check the POV for
   */
  public POVButton(GenericHID joystick, int angle) {
    this.joystick = joystick;
    this.pov = 0;
    this.angle = angle;
    this.fuzzy = false;
  }

  @Override
  public boolean get() {
    int povValue = joystick.getPOV(pov);

    if (fuzzy) {
      return (povValue == angle || povValue + 45 == angle || povValue - 45 == angle);
    }
    else {
      return povValue == angle;
    }
  }
}
