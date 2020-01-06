/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.commands.DeployClimber;
import frc.robot.commands.TeleopDrive;
import frc.robot.subsystems.ClimberDeploy;
import frc.robot.subsystems.ClimberGrabber;
import frc.robot.subsystems.ClimberWinch;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.HatchGrabber;
import frc.robot.subsystems.HatchPivot;
import frc.robot.util.buttons.MultiButton;
import frc.robot.util.controllers.CBXboxController;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ClimberDeploy climberDeploy = new ClimberDeploy();
  private final ClimberGrabber climberGrabber = new ClimberGrabber();
  private final ClimberWinch climberWinch = new ClimberWinch();
  private final Drivetrain drivetrain = new Drivetrain();
  private final HatchGrabber hatchGrabber = new HatchGrabber();
  private final HatchPivot hatchPivot = new HatchPivot();

  // Create compressor for pressure management (THIS IS NOT A SUBSYSTEM)
  private final Compressor compressor = new Compressor(0);

  private final CBXboxController xbox = new CBXboxController(0);


  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    drivetrain.setDefaultCommand(new TeleopDrive(drivetrain,
        () -> xbox.getTriggerAxis(Hand.kRight) - xbox.getTriggerAxis(Hand.kLeft),
        () -> xbox.getX(Hand.kRight), () -> -xbox.getY(Hand.kLeft)));

    // Start the camera server
    CameraServer.getInstance().startAutomaticCapture();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    xbox.getButton(XboxController.Button.kA)
        .whenPressed(new InstantCommand(hatchGrabber::toggle, hatchGrabber));
    xbox.getButton(XboxController.Button.kY)
        .whenPressed(new InstantCommand(hatchPivot::toggle, hatchPivot));
    xbox.getButton(XboxController.Button.kBumperLeft)
        .whenPressed(new InstantCommand(climberGrabber::toggle, climberGrabber));
    xbox.getPOVButton(0, true).whenPressed(new InstantCommand(climberWinch::pull, climberWinch))
        .whenReleased(new InstantCommand(climberWinch::stop, climberWinch));
    xbox.getPOVButton(180, true)
        .whenPressed(new InstantCommand(climberWinch::release, climberWinch))
        .whenReleased(new InstantCommand(climberWinch::stop, climberWinch));

    new MultiButton(xbox, XboxController.Button.kStart.value, XboxController.Button.kBack.value)
        .whenPressed(new DeployClimber(climberDeploy));
  }
}
