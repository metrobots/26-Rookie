// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.drivetrain.Drivetrain;
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
    /* Subsystems */
    private final Drivetrain drivetrain = new Drivetrain();
    /* Auto Chooser */
    // Auto's need to be initialized AFTER all other subsystems.
    private final Autos autos = new Autos();
    private final SendableChooser<Command> autoChooser = new SendableChooser<>();
    /* Controller */
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

    /**
     * Set the options of the auto chooser.
     */
    private void configureAutoChooser() {
        autoChooser.setDefaultOption("Default", autos.getDefaultAuto());
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
                    true);
        }, drivetrain));
    }

    public Command getSelectedAuto() {
        return autoChooser.getSelected();
    }
}
