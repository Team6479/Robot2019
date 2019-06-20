/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.TeleopDrive;

/**
 * Add your docs here.
 */
public class Drivetrain extends Subsystem {

  private TalonSRX leftFront;
  private TalonSRX leftBack;
  private TalonSRX rightFront;
  private TalonSRX rightBack;

  public Drivetrain() {
    leftFront = new TalonSRX(RobotMap.DRIVETRAIN_LEFT_FRONT);
    leftBack  = new TalonSRX(RobotMap.DRIVETRAIN_LEFT_BACK);
    rightFront = new TalonSRX(RobotMap.DRIVETRAIN_RIGHT_FRONT);
    rightBack  = new TalonSRX(RobotMap.DRIVETRAIN_RIGHT_BACK);

    // Restore each talonSRX to factory defaults prior to configuration
    leftFront.configFactoryDefault();
    leftBack.configFactoryDefault();
    rightFront.configFactoryDefault();
    rightBack.configFactoryDefault();

    // Set output direction
    leftFront.setInverted(false);
    leftBack.setInverted(false);
    rightFront.setInverted(true);
    rightBack.setInverted(true);

    // Set neutral mode to Brake
    leftFront.setNeutralMode(NeutralMode.Brake);
    leftBack.setNeutralMode(NeutralMode.Brake);
    rightFront.setNeutralMode(NeutralMode.Brake);
    rightBack.setNeutralMode(NeutralMode.Brake);


    // Add Mag Encoders
    leftFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
    leftBack.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
    rightFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
    rightBack.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);

    // Set the sensor phase so encoder readings match direction
    leftFront.setSensorPhase(true);
    leftBack.setSensorPhase(true);
    rightFront.setSensorPhase(true);
    rightBack.setSensorPhase(true);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new TeleopDrive());
  }

  public void mecanumDrive(double speedLR, double rotation, double speedFB) {
    leftFront.set(ControlMode.PercentOutput, (speedFB + speedLR + rotation));
    leftBack.set(ControlMode.PercentOutput, (speedFB - speedLR + rotation));
    rightFront.set(ControlMode.PercentOutput, (speedFB - speedLR - rotation));
    rightBack.set(ControlMode.PercentOutput, (speedFB + speedLR - rotation));
  }

  public void mecanumDrivePolar(double magnitude, double angle, double rotation) {
    mecanumDrive(magnitude * Math.sin(angle * (Math.PI / 180.0)), rotation, (magnitude * Math.cos(angle * (Math.PI / 180.0))));
  }

  public void stop() {
    leftFront.neutralOutput();
    leftBack.neutralOutput();
    rightFront.neutralOutput();
    rightBack.neutralOutput();
  }

  public void resetEncoders() {
    leftFront.setSelectedSensorPosition(0, 0, 0);
    leftBack.setSelectedSensorPosition(0, 0, 0);
    rightFront.setSelectedSensorPosition(0, 0, 0);
    rightBack.setSelectedSensorPosition(0, 0, 0);
  }
}
