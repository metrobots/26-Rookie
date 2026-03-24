package frc.robot.subsystems.climb;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ClimbConstants;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

public class Climb extends SubsystemBase {
    /** Use a closed loop, positional controller for controlling the climb mechanism. See
     * https://docs.revrobotics.com/revlib/spark/closed-loop for documentation. See
     * https://github.com/metrobots/26-final/tree/main/src/main/java/frc/robot/subsystems/climb for
     * an example. */
    private final SparkMax climbMotor;

    public Climb() {
        climbMotor = new SparkMax(ClimbConstants.climbMotorId, MotorType.kBrushless);
    }
}
//public class Climber extends SubsystemBase {
  
//  private final PWMSparkMax climberMotor = new PWMSparkMax(0); // Assuming channel 0 for the motor
  
//  public void extendClimber() { // Extend climber
//    climberMotor.set(1);
//  }
//  public void extendClimberSlow() { // Extend the climber at half speed
//    climberMotor.set(0.5);
//  }
// public void stopClimber() { // Stop the climber
//    climberMotor.set(0);
// }
//  public void retractClimberSlow() { // Retract the climber at half speed
//    climberMotor.set(-0.5);
//  }
// public void retractClimber() { // Retract climber
//    climberMotor.set(-1);
// }
// public void waitHalfSecond() {
//    try {
//        Thread.sleep(500); // Wait for 500 milliseconds (0.5 seconds)
//    } catch (InterruptedException e) {
//        Thread.currentThread().interrupt(); // Restore the interrupted status
//    }
// }

