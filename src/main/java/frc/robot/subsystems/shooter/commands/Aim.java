package frc.robot.subsystems.shooter.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.shooter.Shooter;

public class Aim extends Command {
    private final Shooter shooterSubsystem;

    public Aim(Shooter shooterSubsystem) {
        this.shooterSubsystem = shooterSubsystem;
    }

    @Override
    public void initialize() { 
    }



@Override
    public void execute() { 
    shooterSubsystem.aimingAuto(6.5+-0.5);
    //UPDATE WITH TARGET LOCATION BASED ON VISION
    }

    @Override
    public void end(boolean interrupted) {
        shooterSubsystem.aimingMotor.set(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}