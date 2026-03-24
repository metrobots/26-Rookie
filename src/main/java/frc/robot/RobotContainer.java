// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.climb.Climb;
import frc.robot.subsystems.drivetrain.DrivetrainSubsystem;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.shooter.Shooter;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

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
    private final Climb climb = new Climb();
    private final Shooter shooter = new Shooter();
    private final Intake intake = new Intake();
    // Auto Chooser
    SendableChooser<Command> autoChooser = new SendableChooser<>();

    // Replace with CommandPS4Controller or CommandJoystick if needed
    private final CommandXboxController primaryController = new CommandXboxController(
            OperatorConstants.primaryControllerPort);

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        configureAutoChooser();
        configureBindings();
        SmartDashboard.putData("Auto Choices", autoChooser);
        // get to work -mateo
        // aye aye captain - malick
    }

    private void configureAutoChooser() {
        autoChooser.setDefaultOption("Default", Autos.defaultAuto);
    }

    /**
    * Configure controller bindings.
    */
    private void configureBindings() {
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
}
