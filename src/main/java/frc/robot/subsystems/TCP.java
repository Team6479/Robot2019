/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;

/* 
 * 
 * Implementation of a basic TCP client
 * @author Aiden Onstott and Leo Wilson
 */
public class TCP extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private Socket client;
  private PrintWriter output;
  private BufferedReader input;
  private final String HOST_IP_ADDR = "192.168.1.7";
  private final int HOST_PORT = 5005;

  enum Keys {
    PING("PING");
    public final String key;
    private Keys(String key) {
      this.key = key;
    }
  }

  public TCP() {
    try {
      client = new Socket(HOST_IP_ADDR, HOST_PORT);
      output = new PrintWriter(client.getOutputStream(), true);
      input = new BufferedReader(new InputStreamReader(client.getInputStream()));
    } catch (Exception e) {
      DriverStation.reportError("TCP Client Connection Failed: " + e.getMessage(), true);
    }
  }

  public String set(Keys key, String value) {
    try {
      output.println("SET " + key.key + " " + value);
      String res = input.readLine().trim();
      if(res.substring(0, 3) == "+OK") {
        return res;
      }
      else {
        DriverStation.reportError("Server returned error: " + res, true);
        return res;
      }
    } catch (Exception e) {
      DriverStation.reportError("Error getting data:" + e.getMessage(), true);
      return "";
    }
  }

  public String get(Keys key) {
    try {
      output.println("GET " + key.key);
      String res = input.readLine().trim();
      if(res.substring(0, 3) == "+OK") {
        return res;
      }
      else {
        DriverStation.reportError("Server returned error: " + res, true);
        return res;
      }
    } catch (Exception e) {
      DriverStation.reportError("Error getting data:" + e.getMessage(), true);
      return "";
    }
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
