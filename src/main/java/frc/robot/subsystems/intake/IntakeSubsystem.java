package frc.robot.subsystems.intake;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

public class IntakeSubsystem extends SubsystemBase {
    private final SparkMax intakeMotor;

    public IntakeSubsystem() {
        intakeMotor = new SparkMax(IntakeConstants.intakeMotorId, MotorType.kBrushless);
    }
}
