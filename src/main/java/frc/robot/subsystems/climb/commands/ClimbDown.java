package frc.robot.subsystems.climb.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.climb.Climb;

public class ClimbDown extends Command {
    private final Climb climbSubsystem;

    public ClimbDown(Climb climbSubsystem) {
        this.climbSubsystem = climbSubsystem;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void end(boolean interrupted) {
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
