
package frc.robot.subsystems.shooter;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class ShooterSubsystem extends SubsystemBase {
    private final SparkMax shooterMotor;

    public ShooterSubsystem() {
        shooterMotor = new SparkMax(ShooterConstants.shooterMotorId, MotorType.kBrushless);
    }
}
