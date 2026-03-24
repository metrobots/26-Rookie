package frc.robot.subsystems.intake.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.intake.Intake;

public class IntakeIn extends Command {
    private final Intake intakeSubsystem;

    public IntakeIn(Intake intakeSubsystem) {
        this.intakeSubsystem = intakeSubsystem;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        // intakeSubsystem.IntakeIn(speed);
    }

    @Override
    public void end(boolean interrupted) {
        //  intakeSubsystem.intakeMotor.stopMotor();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
