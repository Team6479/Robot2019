/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Drivetrain.Place;
import frc.robot.subsystems.Drivetrain.Side;
import frc.robot.subsystems.Drivetrain.Unit;

public class EncoderDrive extends Command {
  private double distance;

  public EncoderDrive(double distance) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.drivetrain);

    // Distance in meters
    this.distance = distance;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.drivetrain.resetEncoders();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double ticksDistance = (distance / (Math.PI * 0.1524)) * 4096;
    Robot.drivetrain.set(ControlMode.Position, ticksDistance);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    double pos = Robot.drivetrain.getPosition(Side.Average, Place.Average, Unit.Meters);
    return pos >= distance;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    DriverStation.reportWarning("Turning motots off", false);
    Robot.drivetrain.arcadeDrive(0, 0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
