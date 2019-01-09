package frc.robot.util;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public interface TalonSRXProfile {
  // Static method that applies the profile to an array of TalonSRXs
  public static void applyTalonSRXProfile(TalonSRXProfile profile, TalonSRX... talons) {
    for (TalonSRX talon : talons) {
      talon.setNeutralMode(profile.getNeutralMode());
    }
  }

  public NeutralMode getNeutralMode();
}
