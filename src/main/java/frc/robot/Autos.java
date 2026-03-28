// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public final class Autos {
    private final Command defaultAuto;

    public Autos() {
        // defaultAuto = new PathPlannerAuto("Default");
        defaultAuto = new RunCommand(() -> {}, new SubsystemBase[] {});
    }

    public Command getDefaultAuto() {
        return defaultAuto;
    }
}
