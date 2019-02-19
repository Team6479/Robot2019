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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
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
  // The theoretical max RPM of the wheel
  // Raw RPM of the motor * by gear ratio of gearbox
  public final double RPM = 5840 / 10.71;

  // The number of PID intervals / minute
  public final double INTERVAL = 600;

  // the magnitude at which mecanum correction kicks in
  private final double MECANUM_CORRECTION_START = 0.1;

  private boolean reset = false;

  // Declare 4 Motor Controllers
  // Left Front Motor (Master)
  public TalonSRX leftMaster;
  // Left Back Motor (Slave)
  public TalonSRX leftSlave;
  // Right Front Motor (Master)
  public TalonSRX rightMaster;
  // Right Back Motor (Slave)
  public TalonSRX rightSlave;

  // which PID slot to use
  public static final int kSlotIdx = 0;
  // which PID loop to use
  public static final int kPIDLoopIdx = 0;
  /**
	 * Set to zero to skip waiting for confirmation, set to nonzero to wait and
	 * report to DS if action fails.
	 */
  public static final int kTimeoutMs = 0;

  // Gains
  // Tune them to modify the behaviour of the PID loop
  public final double kP = 0.25;
	public final double kI = 0.001;
	public final double kD = 20;
	public final double kF = 1023.0/7200.0;
	public final int kIzone = 300;
  public final double kPeakOutput = 1;
  public final int timeoutMs = 0;
  public final int pidID = 0;

  public Drivetrain() {
    // Init Motors
    leftMaster = new TalonSRX(RobotMap.LEFT_FRONT);
    rightMaster = new TalonSRX(RobotMap.RIGHT_FRONT);
    leftSlave = new TalonSRX(RobotMap.LEFT_BACK);
    rightSlave = new TalonSRX(RobotMap.RIGHT_BACK);

    // Set output direction
    leftMaster.setInverted(false);
    leftSlave.setInverted(false);
    rightMaster.setInverted(true);
    rightSlave.setInverted(true);

    leftMaster.getSensorCollection().getPulseWidthPosition();

    TalonSRXProfile.applyTalonSRXProfile(new DefaultTalonSRXProfile(), leftMaster, leftSlave, rightMaster, rightSlave);

    configTalonPID(leftMaster);
    configTalonPID(leftSlave);
    configTalonPID(rightMaster);
    configTalonPID(rightSlave);

    resetEncoders();
  }

  /**
   * Configures the PID loop according to the constants found in this class
   * @param talon The TalonSRX to configure
   * @author Leo Wilson
   */
  private void configTalonPID(TalonSRX talon) {
    // Add Mag Encoders
    leftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, pidID, timeoutMs);
    leftSlave.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, pidID, timeoutMs);
    rightMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, pidID, timeoutMs);
    rightSlave.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, pidID, timeoutMs);

    // Configure the peak and nominal outputs of the motors
    talon.configNominalOutputForward(0, timeoutMs);
		talon.configNominalOutputReverse(0, timeoutMs);
		talon.configPeakOutputForward(1, timeoutMs);
    talon.configPeakOutputReverse(-1, timeoutMs);

    // Configure the gains
    talon.config_kF(pidID, kF, timeoutMs);
		talon.config_kP(pidID, kP, timeoutMs);
		talon.config_kI(pidID, kI, timeoutMs);
    talon.config_kD(pidID, kD, timeoutMs);
  }

  public double percentToUnits(double percent, double interval, double rpm, double cpr) {
    return (percent * rpm * cpr) / interval;
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
   */
  public void rawMecnumDrive(double leftMasterSpeed, double leftSlaveSpeed, double rightMasterSpeed, double rightSlaveSpeed) {
    leftMaster.set(ControlMode.Velocity, percentToUnits(leftMasterSpeed, INTERVAL, RPM, CPR));
    leftSlave.set(ControlMode.Velocity, percentToUnits(leftSlaveSpeed, INTERVAL, RPM, CPR));
    rightMaster.set(ControlMode.Velocity, percentToUnits(rightMasterSpeed, INTERVAL, RPM, CPR));
    rightSlave.set(ControlMode.Velocity, percentToUnits(rightSlaveSpeed, INTERVAL, RPM, CPR));
  }

  /**
   * Drives using the full capabilities of the Mecanum wheels
   * @param magnitude The speed
   * @param speedLR The angle in which to travel
   * @param rotation The rotation
   * @author Leo Wilson
   */
  public void mecanumDrive(double speedLR, double rotation, double speedFB) {
    double g = Robot.gyro.getAngle();
    SmartDashboard.putNumber("gyro", g);
    if(reset) {
      Robot.gyro.reset();
    }
    else {
      reset = true;
    }
    /*if(Math.abs(speedLR) > MECANUM_CORRECTION_START) {
      rotation -= g / 18;
      SmartDashboard.putNumber("New Rotation", rotation);
      // Robot.gyro.reset();
    }*/

    SmartDashboard.putNumber("Velocity: Left Front", getVelocity(Side.Left, Place.Front, Unit.Meters));
    SmartDashboard.putNumber("Velocity: Left Back", getVelocity(Side.Left, Place.Back, Unit.Meters));
    SmartDashboard.putNumber("Velocity: Right Front", getVelocity(Side.Right, Place.Front, Unit.Meters));
    SmartDashboard.putNumber("Velocity: Right Back", getVelocity(Side.Right, Place.Back, Unit.Meters));

    SmartDashboard.putNumber("Left Front", (speedFB + speedLR + rotation));
    SmartDashboard.putNumber("Left Back", (speedFB - speedLR + rotation));
    SmartDashboard.putNumber("Right Front", (speedFB - speedLR - rotation));
    SmartDashboard.putNumber("Right Back", (speedFB + speedLR - rotation));

    /*SmartDashboard.putNumber("Position: Left Front", getPosition(Side.Left, Place.Front, Unit.Meters));
    SmartDashboard.putNumber("Position: Left Back", getPosition(Side.Left, Place.Back, Unit.Meters));
    SmartDashboard.putNumber("Position: Right Front", getPosition(Side.Right, Place.Front, Unit.Meters));
    SmartDashboard.putNumber("Position: Right Back", getPosition(Side.Right, Place.Back, Unit.Meters));*/

    rawMecnumDrive((speedFB + speedLR + rotation), (speedFB - speedLR + rotation), (speedFB - speedLR - rotation), (speedFB + speedLR - rotation));
  }

  public void mecanumDrivePolar(double magnitude, double angle, double rotation) {
    mecanumDrive(magnitude * Math.sin(angle * (Math.PI / 180.0)), rotation, (magnitude * Math.cos(angle * (Math.PI / 180.0))));
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
