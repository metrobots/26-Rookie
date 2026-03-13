// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.climb.ClimbSubsystem;
import frc.robot.subsystems.climb.commands.Climb;
import frc.robot.subsystems.climb.commands.Declimb;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;
import frc.robot.subsystems.intake.IntakeSubsystem;
import frc.robot.subsystems.shooter.ShooterSubsystem;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
    // The robot's subsystems and commands are defined here...
    private final DrivetrainSubsystem drivetrain = new DrivetrainSubsystem();
    private final ClimbSubsystem climb = new ClimbSubsystem();
    private final ShooterSubsystem shooter = new ShooterSubsystem();
    private final IntakeSubsystem intake = new IntakeSubsystem();

    // Replace with CommandPS4Controller or CommandJoystick if needed
    private final CommandXboxController primaryController = new CommandXboxController(
            OperatorConstants.primaryControllerPort);

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        configureBindings();
        // get to work -mateo
        // aye aye captain - malick 
        drivetrain.setDefaultCommand(new RunCommand(() -> {
            drivetrain.drive(
                    -MathUtil.applyDeadband(primaryController.getLeftY(), OperatorConstants.driveDeadband)
                            * DriveConstants.driveInputDampeningFactor,
                    -MathUtil.applyDeadband(primaryController.getLeftX(), OperatorConstants.driveDeadband)
                            * DriveConstants.driveInputDampeningFactor,
                    -MathUtil.applyDeadband(primaryController.getRightX(), OperatorConstants.driveDeadband)
                            * DriveConstants.driveInputDampeningFactor,
                    true /* TODO: test drive field relative. */);
        }, drivetrain));
    }

    /**
     * Use this method to define your trigger->command mappings. Triggers can be
     * created via the
     * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with
     * an arbitrary
     * predicate, or via the named factories in {@link
     * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
     * {@link
     * CommandXboxController
     * Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
     * PS4} controllers or
     * {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
     * joysticks}.
     */
    private void configureBindings() {
        // Example button bindings.
        primaryController.b().onTrue(new Climb(climb));
        primaryController.b().onFalse(new Declimb(climb));
    }

    // /**
    //  * Use this to pass the autonomous command to the main {@link Robot} class.
    //  *
    //  * @return the command to run in autonomous
    //  */
    // public Command getAutonomousCommand() {
    //     // An example command will be run in autonomous
    //     return Autos.exampleAuto(m_exampleSubsystem);
    // }
}
