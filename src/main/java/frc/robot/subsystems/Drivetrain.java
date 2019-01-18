/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.TeleopDrive;
import frc.robot.talonsrxprofiles.DefaultTalonSRXProfile;
import frc.robot.util.TalonSRXProfile;

/**
 * Drivetrain class
 */
public class Drivetrain extends Subsystem {
  public enum Side {
    Left, Right, Average;
  }
  public enum Place {
    Front, Back, Average;
  }

  // Units are in metric
  public enum Unit {
    Rotations, Meters;
  }

  // Diameter of the wheel in meters
  private final double WHEEL_DIAMETER = 0.1524;
  // Cycles per rotation of the encoder
  private final double CPR = 4096;

  // Declare 4 Motor Controllers
  // Left Front Motor (Master)
  private TalonSRX leftMaster;
  // Left Back Motor (Slave)
  private TalonSRX leftSlave;
  // Right Front Motor (Master)
  private TalonSRX rightMaster;
  // Right Back Motor (Slave)
  private TalonSRX rightSlave;

  public Drivetrain() {
    // Init Master Motors
    leftMaster = new TalonSRX(RobotMap.LEFT_FRONT);
    rightMaster = new TalonSRX(RobotMap.RIGHT_FRONT);
    // Init Slave Motors and tell them to follow their respective masters
    leftSlave = new TalonSRX(RobotMap.LEFT_BACK);
    rightSlave = new TalonSRX(RobotMap.RIGHT_BACK);

    // Set output direction
    leftMaster.setInverted(false);
    leftSlave.setInverted(false);
    rightMaster.setInverted(true);
    rightSlave.setInverted(true);

    leftMaster.getSensorCollection().getPulseWidthPosition();

    TalonSRXProfile.applyTalonSRXProfile(new DefaultTalonSRXProfile(), leftMaster, leftSlave, rightMaster, rightSlave);

    // Add Mag Encoders
    int timeoutMs = 0;
    leftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, timeoutMs);
    leftSlave.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, timeoutMs);
    rightMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, timeoutMs);
    rightSlave.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, timeoutMs);
    resetEncoders();
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new TeleopDrive());
  }

  public void set(ControlMode controlMode, double speed) {
    leftMaster.set(controlMode, speed);
    leftSlave.set(controlMode, speed);
    rightMaster.set(controlMode, speed);
    rightSlave.set(controlMode, speed);
  }

  /**
   * Drives normally w/o mecanum capabilities
   * Included for backwards-compatibility only.
   * @see #mecanumDrive
   * @param speed The forward-backward speed
   * @param rotation The rotation
   */
  public void arcadeDrive(double speed, double rotation) {
    leftMaster.set(ControlMode.PercentOutput, speed, DemandType.ArbitraryFeedForward, rotation);
    leftSlave.set(ControlMode.PercentOutput, speed, DemandType.ArbitraryFeedForward, rotation);
    rightMaster.set(ControlMode.PercentOutput, speed, DemandType.ArbitraryFeedForward, -rotation);
    rightSlave.set(ControlMode.PercentOutput, speed, DemandType.ArbitraryFeedForward, -rotation);
  }

  /**
   * Sets the motors in a mecanum-compatible way
   * @param leftMasterSpeed What it looks like
   * @param leftSlaveSpeed What it looks like
   * @param rightMasterSpeed What it looks like
   * @param rightSlaveSpeed What it looks like
   * @param leftOffset You probably know it as "rotation"
   * @param rightOffset You probably know it as "-rotation"
   */
  public void rawMecnumDrive(double leftMasterSpeed, double leftSlaveSpeed, double rightMasterSpeed, double rightSlaveSpeed, double leftOffset, double rightOffset) {
    leftMaster.set(ControlMode.PercentOutput, leftMasterSpeed, DemandType.ArbitraryFeedForward, leftOffset);
    leftSlave.set(ControlMode.PercentOutput, leftSlaveSpeed, DemandType.ArbitraryFeedForward, leftOffset);
    rightMaster.set(ControlMode.PercentOutput, rightMasterSpeed, DemandType.ArbitraryFeedForward, rightOffset);
    rightSlave.set(ControlMode.PercentOutput, rightSlaveSpeed, DemandType.ArbitraryFeedForward, rightOffset);
  }

  /**
   * Drives using the full capabilities of the Mecanum wheels
   * @param speedFB The forward-backward speed
   * @param speedLR The left-right speed
   * @param rotation The rotation
   * @author Leo Wilson
   */
  public void mecanumDrive(double speedFB, double speedLR, double rotation) {
    if(speedLR == 0) { // forward-backward only
      arcadeDrive(speedFB, rotation);
    }
    else if(speedLR > 0) { // right
      rawMecnumDrive((speedFB - speedLR) / 2, (speedFB + speedLR) / 2, (speedFB + speedLR) / 2, (speedFB - speedLR) / 2, rotation, -rotation);
    }
    else { // left
      speedLR = Math.abs(speedLR);
      rawMecnumDrive((speedFB + speedLR) / 2, (speedFB - speedLR) / 2, (speedFB - speedLR) / 2, (speedFB + speedLR) / 2, rotation, -rotation);
    }
    
  }

  public void resetEncoders() {
    leftMaster.setSelectedSensorPosition(0, 0, 0);
    leftSlave.setSelectedSensorPosition(0, 0, 0);
    rightMaster.setSelectedSensorPosition(0, 0, 0);
    rightSlave.setSelectedSensorPosition(0, 0, 0);
  }

  public double getPosition(Side side, Place place) {
    if (side == Side.Left) {
      if(place == Place.Front) {
        return leftMaster.getSelectedSensorPosition(0);
      }
      else if(place == Place.Back) {
        return leftSlave.getSelectedSensorPosition(0);
      }
      else {
        return (Math.abs(leftMaster.getSelectedSensorPosition(0)) + Math.abs(leftSlave.getSelectedSensorPosition(0))) / 2;
      }
    } else if (side == Side.Right) {
      if(place == Place.Front) {
        return rightMaster.getSelectedSensorPosition(0);
      }
      else if(place == Place.Back) {
        return rightSlave.getSelectedSensorPosition(0);
      }
      else {
        return (Math.abs(rightMaster.getSelectedSensorPosition(0)) + Math.abs(rightSlave.getSelectedSensorPosition(0))) / 2;
      }
    } else {
      if(place == Place.Front) {
        return (Math.abs(leftMaster.getSelectedSensorPosition(0)) + Math.abs(rightMaster.getSelectedSensorPosition(0))) / 2;
      }
      else if(place == Place.Back) {
        return (Math.abs(leftSlave.getSelectedSensorPosition(0)) + Math.abs(rightSlave.getSelectedSensorPosition(0))) / 2;
      }
      else {
        return (Math.abs(leftMaster.getSelectedSensorPosition(0)) + Math.abs(rightMaster.getSelectedSensorPosition(0)) + Math.abs(leftSlave.getSelectedSensorPosition(0)) + Math.abs(rightSlave.getSelectedSensorPosition(0))) / 4;
      }
    }
  }

  public double getPosition(Side side, Place place, Unit unit) {
    double rawPosition = getPosition(side, place);
    double rotationsPosition = rawPosition / CPR;

    if (unit == Unit.Rotations) {
      return rotationsPosition;
    } else if (unit == Unit.Meters) {
      return rotationsPosition * (Math.PI * WHEEL_DIAMETER);
    } else {
      // If for some reason the unit does not match any of these return the raw value
      return rawPosition;
    }
  }

  public double getVelocity(Side side, Place place) {
    if (side == Side.Left) {
      if(place == Place.Front) {
        return leftMaster.getSelectedSensorVelocity(0);
      }
      else if(place == Place.Back) {
        return leftSlave.getSelectedSensorVelocity(0);
      }
      else {
        return (Math.abs(leftMaster.getSelectedSensorVelocity(0)) + Math.abs(leftSlave.getSelectedSensorVelocity(0))) / 2;
      }
    } else if (side == Side.Right) {
      if(place == Place.Front) {
        return rightMaster.getSelectedSensorVelocity(0);
      }
      else if(place == Place.Back) {
        return rightSlave.getSelectedSensorVelocity(0);
      }
      else {
        return (Math.abs(rightMaster.getSelectedSensorVelocity(0)) + Math.abs(rightSlave.getSelectedSensorVelocity(0))) / 2;
      }
    } else {
      if(place == Place.Front) {
        return (Math.abs(leftMaster.getSelectedSensorVelocity(0)) + Math.abs(rightMaster.getSelectedSensorVelocity(0))) / 2;
      }
      else if(place == Place.Back) {
        return (Math.abs(leftSlave.getSelectedSensorVelocity(0)) + Math.abs(rightSlave.getSelectedSensorVelocity(0))) / 2;
      }
      else {
        return (Math.abs(leftMaster.getSelectedSensorVelocity(0)) + Math.abs(rightMaster.getSelectedSensorVelocity(0)) + Math.abs(leftSlave.getSelectedSensorVelocity(0)) + Math.abs(rightSlave.getSelectedSensorVelocity(0))) / 4;
      }
    }
  }

  public double getVelocity(Side side, Place place, Unit unit) {
    double rawVelocity = getVelocity(side, place);
    double rotationsVelocity = rawVelocity / CPR;

    if (unit == Unit.Rotations) {
      return rotationsVelocity;
    } else if (unit == Unit.Meters) {
      return rotationsVelocity * (Math.PI * WHEEL_DIAMETER);
    } else {
      // If for some reason the unit does not match any of the above return the raw
      // velocity
      return rawVelocity;
    }
  }
}
