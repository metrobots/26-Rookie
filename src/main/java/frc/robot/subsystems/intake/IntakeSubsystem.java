package frc.robot.subsystems.intake;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

public class IntakeSubsystem extends SubsystemBase {
    private final SparkMax intakeMotor;
    private final SparkMax pivotMotor;

    public IntakeSubsystem() {
        intakeMotor = new SparkMax(IntakeConstants.intakeMotorId, MotorType.kBrushless);
        pivotMotor = new SparkMax(IntakeConstants.pivotMotorId, MotorType.kBrushless);
    }
    public void PivotDown() {
        pivotMotor.set(5);
    return;
    }
    public void IntakeIn(double speed) {
        intakeMotor.set(speed);
    return;
    }
}
