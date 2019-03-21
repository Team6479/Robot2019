/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import edu.wpi.first.wpilibj.DriverStation;

/**
 * Handles logging to a file
 * @author Leo Wilson
 */
public class Logger {
    private Path path;

    /**
     * @param p The path of the log file to write to (accepts the ~ character for home)
     * @param clear Whether or not to clear the file when the method begins.
     */
    public Logger(String p) {
        path = Paths.get(p.replaceFirst("^~", System.getProperty("user.home")));
        if (Files.exists(path)) {
            try {
                Files.delete(path);
            }
            catch(IOException e) {

            }
        }
    }

    /**
     * Logs to a file
     * @param ln The line to log
     * @param overwrite Whether or not to overwrite the existing log file
     * @return Whether or not it was successful
     */
    public boolean log(String ln, boolean overwrite) {
        try {
            Files.write(path, ln.getBytes(), new OpenOption[]{StandardOpenOption.CREATE, ((overwrite)?StandardOpenOption.WRITE:StandardOpenOption.APPEND)});
            return true;
        } catch (Exception e) {
            DriverStation.reportError(e.getMessage(), true);
            return false;
        }
    }

    /**
     * Logs to a file without overwriting it
     * @param ln The line to log
     * @return Whether or not it was successful
     */
    public boolean log(String ln) {
        return log(ln, false);
    }
}
