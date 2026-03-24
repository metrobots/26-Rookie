package frc.robot.subsystems.climb.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.climb.Climb;

public class ClimbUp extends Command {
    private final Climb climbSubsystem;

    public ClimbUp(Climb climbSubsystem) {
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
