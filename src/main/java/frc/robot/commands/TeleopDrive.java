/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class TeleopDrive extends CommandBase {
  private final Drivetrain drivetrain;
  private final DoubleSupplier speedLR;
  private final DoubleSupplier rotation;
  private final DoubleSupplier speedFB;

  public TeleopDrive(Drivetrain drivetrain, DoubleSupplier speedLR, DoubleSupplier rotation, DoubleSupplier speedFB) {
    this.drivetrain = drivetrain;
    this.speedLR = speedLR;
    this.rotation = rotation;
    this.speedFB = speedFB;
    addRequirements(this.drivetrain);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    drivetrain.mecanumDrive(speedLR.getAsDouble(), rotation.getAsDouble(), speedFB.getAsDouble());
    // drivetrain.mecanumDrive(
    //     Robot.oi.xbox.getTriggerAxis(Hand.kRight) - Robot.oi.xbox.getTriggerAxis(Hand.kLeft),
    //     Robot.oi.xbox.getX(Hand.kRight), -Robot.oi.xbox.getY(Hand.kLeft));
  }

  // Called once after isFinished returns true
  @Override
  public void end(boolean interrupted) {
    drivetrain.stop();
  }
}
