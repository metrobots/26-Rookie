// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final class OperatorConstants {
        public static final int primaryControllerPort = 0;
        public static final double driveDeadband = 0.05;
    }

    public static final class DriveConstants {
        /* Note that these are not the maximum capable speeds of the robot, rather
        the allowed maximum speeds */
        /** Max speed in meters per second. */
        public static final double maxSpeed = 4.8;
        /** Max angular speed in radians per second. */
        public static final double maxAngularSpeed = 2 * Math.PI;
        // TODO: Measure chassis.
        /** Horizontal center distance in meters (left to right). */
        public static final double horizontalChassisWidth = Units.inchesToMeters(26.5);
        /** Vertical wheel center distance in meters (front to back). */
        public static final double verticalChassisWidth = Units.inchesToMeters(26.5);
        public static final SwerveDriveKinematics driveKinematics = new SwerveDriveKinematics(
                new Translation2d(verticalChassisWidth / 2, horizontalChassisWidth / 2),
                new Translation2d(verticalChassisWidth / 2, -horizontalChassisWidth / 2),
                new Translation2d(-verticalChassisWidth / 2, horizontalChassisWidth / 2),
                new Translation2d(-verticalChassisWidth / 2, -horizontalChassisWidth / 2));

        /* Angular offsets of the modules relative to the chassis in radians */
        // TODO: Zero encoders
        public static final double frontLeftAngularOffset = -Math.PI / 2;
        public static final double frontRightAngularOffset = 0;
        public static final double backLeftAngularOffset = Math.PI;
        public static final double backRightAngularOffset = Math.PI / 2;

        /* SPARK MAX CAN IDs */
        /* Driving IDs */
        public static final int frontLeftDrivingId = 3;
        public static final int frontRightDrivingId = 32;
        public static final int backRightDrivingId = 8;
        public static final int backLeftDrivingId = 22;
        /* Turning IDs */
        public static final int frontLeftTurningId = 5;
        public static final int frontRightTurningId = 2;
        public static final int backRightTurningId = 7;
        public static final int backLeftTurningId = 4;
        /* Other */
        public static final boolean isGyroReversed = false;
    }

    public static final class SwerveModuleConstants {
        // The number of teeth on the pinion gear for the driving motor is 13.
        public static final int drivingPinionTeeth = 13;
        // Calculations required for driving motor conversion factors and feed forward
        public static final double drivingMotorFreeSpeedRps = NeoMotorConstants.freeSpeedRpm / 60;
        public static final double wheelDiameterMeters = 0.0762;
        public static final double wheelCircumferenceMeters = wheelDiameterMeters * Math.PI;
        // 45 teeth on the wheel's bevel gear, 22 teeth on the first-stage spur gear, 15
        // teeth on the bevel pinion
        public static final double drivingMotorReduction = (45.0 * 22) / (drivingPinionTeeth * 15);
        public static final double driveWheelFreeSpeedRps = (drivingMotorFreeSpeedRps * wheelCircumferenceMeters)
                / drivingMotorReduction;
    }

    public static final class AutoConstants {
        public static final double maxSpeedsMetersPerSecond = 3;
        public static final double maxAccelerationMetersPerSecondSquared = 3;
        public static final double maxAngularSpeedRadiansPerSecond = Math.PI;
        public static final double maxAngularSpeedRadiansPerSecondSquared = Math.PI;

        public static final double kPXController = 1;
        public static final double kPYController = 1;
        public static final double kPThetaController = 1;

        // Constraint for the motion profiled robot angle controller
        public static final TrapezoidProfile.Constraints thetaControllerConstraints = new TrapezoidProfile.Constraints(
                maxAngularSpeedRadiansPerSecond, maxAngularSpeedRadiansPerSecondSquared);
    }

    public static final class NeoMotorConstants {
        public static final double freeSpeedRpm = 5676;
    }
}