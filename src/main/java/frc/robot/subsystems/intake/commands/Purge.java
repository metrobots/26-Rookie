package frc.robot.subsystems.intake.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.intake.IntakeSubsystem;

public class Purge extends Command {
    private final IntakeSubsystem intakeSubsystem;

    public Purge(IntakeSubsystem intakeSubsystem) {
        this.intakeSubsystem = intakeSubsystem;
    }

    @Override
    public void initialize() {

    }
    
    @Override
    public void execute() {
        intakeSubsystem.IntakeIn(-speed);
    }

    @Override
    public void end(boolean interrupted) {
        intakeSubsystem.intakeMotor.stopMotor();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
