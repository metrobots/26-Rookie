package frc.robot.subsystems.climb.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.climb.ClimbSubsystem;

public class Climb extends Command {
    private final ClimbSubsystem climbSubsystem;

    public Climb(ClimbSubsystem climbSubsystem) {
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
