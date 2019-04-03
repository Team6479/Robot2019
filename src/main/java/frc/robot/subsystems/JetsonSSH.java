/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.io.InputStream;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * @author Leo Wilson
 */
public class JetsonSSH extends Subsystem {

  private JSch jsch;
  private Session session;

  /**
   * @param ip       The IP address to connect to
   * @param port     The SSH port number
   * @param username The username
   * @param password The password
   */
  public JetsonSSH(String ip, int port, String username, String password) {
    try {
      jsch = new JSch();
      session = jsch.getSession(username, ip, port);
      session.setPassword(password);
      session.setConfig("StrictHostKeyChecking", "no");
      session.connect();
    } catch (Exception e) {
      DriverStation.reportError(e.getMessage(), true);
    }
    // System.out.println(run("bash /home/nvidia/bin/run-vision-code"));
    run("/home/nvidia/.local/bin/vision-2019 tape");
    // try {
    //   Thread.sleep(30000);
    // } catch (InterruptedException e) {
    //   DriverStation.reportError(e.getMessage(), true);
    // }
  }
  
  /**
   * @param ip The IP address to connect to (assumes port 22)
   * @param username The username
   * @param password The password
   */
  public JetsonSSH(String ip, String username, String password) {
    this(ip, 22, username, password);
  }
  /**
   * @param ip The IP address to connect to (assumes port 22)
   * Assumes port 22 and login "nvidia"/"nvidia"
   */
  public JetsonSSH(String ip) {
    this(ip, 22, "nvidia", "nvidia");
  }
  /**
   * Assumes port 192.168.1.7:22 and login "nvidia"/"nvidia"
   */
  public JetsonSSH() {
    this("192.168.1.7", 22, "nvidia", "nvidia");
  }

  public int run(String command) {
    StringBuilder outputBuffer = new StringBuilder();
    try {
      ChannelExec channelExec = (ChannelExec)session.openChannel("exec");
      channelExec.setCommand(command);
      InputStream commandOutput = channelExec.getInputStream();
      channelExec.connect();
      int readByte = commandOutput.read();
      while(readByte != 0xffffffff)
      {
        System.out.print((Character.toString((char)readByte)));
        outputBuffer.append((char)readByte);
        readByte = commandOutput.read();
      }
      channelExec.disconnect();
      // System.out.println("Ran \"" + command +"\" with output: \"" + outputBuffer.toString() +"\" and exit code: " + channelExec.getExitStatus());
      return channelExec.getExitStatus();
    } catch (Exception e) {
      DriverStation.reportError(e.getMessage(), true);
      return -1;
    }
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new StartJetsonCode());
  }
}
