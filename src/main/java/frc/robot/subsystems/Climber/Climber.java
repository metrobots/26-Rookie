// Need: motor. moves smaller tube into larger tube, MUST be bound to "B" button
// What motor does: motor running a winch, extends when B pressed, retracts when not.

// V V V V V V V
// Conduct testing on a motor.
// ^ ^ ^ ^ ^ ^ ^

// checklist:
  // 1. Import needed libraries - done 
  // 2. ID a motor - done
  // 3. Make it motion - needs testing
  // 4. ID Xbox controller - done
  // 5. Bind it to "B" - needs testing
  // 6. OHH NOO I NEED TO ADD LIMITS OR IT'LL RIP THE ROBOT APART FROM THE INSIDE OUT - that's rough bro. good luck.
  // 7. I think i did #6 - needs testing

package frc.robot.subsystems.Climber;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.XboxController;

public class Climber extends SubsystemBase {
  
  private final PWMSparkMax climberMotor = new PWMSparkMax(0); // Assuming channel 0 for the motor
  private final XboxController controller = new XboxController(1); // Assuming controller is on port 1

  int climberLimit = 0;

 public boolean getBButtonPressed() {
    return controller.getBButton(); // Returns true if B button is pressed
  }

  public void controlClimber() {
    if (getBButtonPressed()) {
      climberMotor.set(0.5); // Extend the climber
      climberLimit = climberLimit + 1;
      while (getBButtonPressed() && climberLimit >= 100) {
        climberMotor.set(0);
      }
    } 
    else {
      climberMotor.set(-0.5); // reverse the motor when B is not pressed
      climberLimit = climberLimit - 1; 
      while (!getBButtonPressed() && climberLimit <= 0) {
      climberMotor.set(0);
      }
    }
  }
}
