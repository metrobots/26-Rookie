package frc.robot.subsystems.shooter.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.shooter.Shooter;

public class Shoot extends Command {
    private final Shooter shooterSubsystem;

    public Shoot(Shooter shooterSubsystem) {
        this.shooterSubsystem = shooterSubsystem;
    }

    @Override
    public void initialize() { 
    }

@Override
    public void execute() { 
    shooterSubsystem.ShooterSpinny(10);
    
    }

    @Override
    public void end(boolean interrupted) {
        shooterSubsystem.ShooterSpinny(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
