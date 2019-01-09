import edu.wpi.first.wpilibj.shuufleboard.shuffleboard;
import edu.wpi.first.wpilibj.shuufleboard.shuffleboardLayout;
import edu.wpi.first.wpilibj.shuufleboard.shuffleboardTab;

public class ShuffleboardTabs()
{
    public ShuffleboardTab drive = Shuffleboard.getTab("Drive");
    public ShuffleboardTab config = Shuffleboard.getTab("Config");
    public ShuffleboardTab diagnostics = Shuffleboard.getTab("Diagnostics");

}