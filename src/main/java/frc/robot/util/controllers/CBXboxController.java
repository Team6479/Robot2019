package frc.robot.util.controllers;

import java.util.HashMap;
import java.util.Objects;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.util.buttons.POVButton;
import frc.robot.wpioverride.XboxController;

public class CBXboxController extends XboxController {
  private HashMap<Button, edu.wpi.first.wpilibj2.command.button.Button> buttons;
  private HashMap<Integer, edu.wpi.first.wpilibj2.command.button.Button> povButtons;

  public CBXboxController(int port) {
    super(port);
    buttons = new HashMap<Button, edu.wpi.first.wpilibj2.command.button.Button>();
    povButtons = new HashMap<Integer, edu.wpi.first.wpilibj2.command.button.Button>();
  }

  public edu.wpi.first.wpilibj2.command.button.Button getButton(Button button) {

    if (!buttons.containsKey(button)) {
      buttons.put(button, new JoystickButton(this, button.value));
    }

    return buttons.get(button);
  }

  public edu.wpi.first.wpilibj2.command.button.Button getPOVButton(int angle, boolean fuzzy) {
    int povHash = Objects.hash(angle, fuzzy);

    if (!povButtons.containsKey(povHash)) {
      povButtons.put(povHash, new POVButton(this, angle, fuzzy));
    }

    return povButtons.get(povHash);
  }
}
