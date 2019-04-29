package frc.robot.util.controllers;

import java.util.HashMap;
import java.util.Objects;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.util.buttons.POVButton;
import frc.robot.wpioverride.XboxController;

public class CBXboxController extends XboxController {
  /**
   * Represents a digital button on an XboxController.
   */
  public enum Buttons {
    kBumperLeft(5),
    kBumperRight(6),
    kStickLeft(9),
    kStickRight(10),
    kA(1),
    kB(2),
    kX(3),
    kY(4),
    kBack(7),
    kStart(8);

    public final int value;

    Buttons(int value) {
      this.value = value;
    }
  }

  private HashMap<Buttons, Button> buttons;
  private HashMap<Integer, Button> povButtons;

  public CBXboxController(int port) {
    super(port);
    buttons = new HashMap<Buttons, Button>();
    povButtons = new HashMap<Integer, Button>();
  }

  public Button getButton(Buttons button) {

    if (!buttons.containsKey(button)) {
      buttons.put(button, new JoystickButton(this, button.value));
    }

    return buttons.get(button);
  }

  public Button getPOVButton(int angle, boolean fuzzy) {
    int povHash = Objects.hash(angle, fuzzy);

    if (!povButtons.containsKey(povHash)) {
      povButtons.put(povHash, new POVButton(this, angle, fuzzy));
    }

    return povButtons.get(povHash);
  }
}
