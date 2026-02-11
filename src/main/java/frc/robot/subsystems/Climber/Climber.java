// Need: motor. moves smaller tube into larger tube, MUST be bound to "B" button
// What motor does: Winch moving motor, extends when B pressed, retracts when B pressed again.

// V V V V V V V
// Conduct testing on a motor.
// Ask Evergreen how to test the motor.
// If it doesn't work, scream internally, then troubleshoot.
// ^ ^ ^ ^ ^ ^ ^



// checklist:
  // Import needed libraries - done 
  // ID a motor - done
  // Make it motion - needs testing
  // ID Xbox controller - done
  // Bind it to "B" - needs testing

package frc.robot.subsystems.Climber;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.XboxController;



public class Climber extends SubsystemBase {
  
  private final PWMSparkMax climberMotor = new PWMSparkMax(0); // Assuming channel 0 for the motor
  private final XboxController controller = new XboxController(1); // Assuming controller is on port 1

 public boolean getBButtonPressed() {
    return controller.getBButton(); // Returns true if B button is pressed
  }

  public void controlClimber() {
    if (getBButtonPressed()) {
      climberMotor.set(1.0); // Extend the climber
    } else {
      climberMotor.set(-1.0); // reverse the motor when B is not pressed
    }
  }
}
