package frc.robot.util.controllers;

import java.util.HashMap;
import java.util.Objects;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.util.buttons.POVButton;
import frc.robot.util.controllers.CBXboxController.Buttons;

public class CBJoystick extends Joystick{

  private HashMap<Buttons, Button> buttons;
  private HashMap<Integer, Button> povButtons;

  public CBJoystick(int port) {
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

  public Button getPOVButton(int pov, int angle, boolean fuzzy) {
    int povHash = Objects.hash(pov, angle, fuzzy);

    if (!povButtons.containsKey(povHash)) {
      povButtons.put(povHash, new POVButton(this, angle, fuzzy));
    }

    return povButtons.get(povHash);
  }

  public Button getPOVButton(int angle, boolean fuzzy) {
    return this.getPOVButton(0, angle, fuzzy);
  }
}
