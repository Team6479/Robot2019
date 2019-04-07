/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Drivetrain.Place;
import frc.robot.subsystems.Drivetrain.Side;
import frc.robot.util.Logger;

public class ConstantVelocity extends Command {
  private double speedLR, rotation, speedFB;
  private Logger logger;
  private Timer timer;

  public ConstantVelocity(double lr, double rot, double fb) {
    timer = new Timer();
    requires(Robot.drivetrain);
    speedLR = lr;
    rotation = rot;
    speedFB = fb;
    logger = new Logger("~/velocity.log");
    logger.log("", true);
  }

  public ConstantVelocity() {
    this(0.0, 0.0, 0.01);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    timer.start();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    // Robot.drivetrain.rawMecnumDrive(0.1, 0.1, rightMasterSpeed, rightSlaveSpeed);
    int number = 1000;

    // if(timer.get() < 2) {
      Robot.drivetrain.leftMaster.set(ControlMode.Velocity, number);
      Robot.drivetrain.leftSlave.set(ControlMode.Velocity, number);
      Robot.drivetrain.rightMaster.set(ControlMode.Velocity, number);
      Robot.drivetrain.rightSlave.set(ControlMode.Velocity, number);
    // }
    // else {
    //   Robot.drivetrain.leftMaster.set(ControlMode.PercentOutput, 0);
    //   Robot.drivetrain.leftSlave.set(ControlMode.PercentOutput, 0);
    //   Robot.drivetrain.rightMaster.set(ControlMode.PercentOutput, 0);
    //   Robot.drivetrain.rightSlave.set(ControlMode.PercentOutput, 0);
    // }

    logger.log(Robot.drivetrain.getVelocity(Side.Left, Place.Front) + "," + // Left Master
        Robot.drivetrain.getVelocity(Side.Left, Place.Back) + "," + // Left Slave
        Robot.drivetrain.getVelocity(Side.Right, Place.Front) + "," + // Right Master
        Robot.drivetrain.getVelocity(Side.Right, Place.Back) + "\n" // Right Slave
    );
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
