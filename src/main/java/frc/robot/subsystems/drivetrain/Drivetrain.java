package frc.robot.subsystems.drivetrain;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;
import com.studica.frc.AHRS;
import com.studica.frc.AHRS.NavXComType;
import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

/**
 * The robot uses four swerve modules in a square configuration. The code is
 * based on the template
 * provided by Rev Robotics at
 * https://github.com/REVrobotics/MAXSwerve-Java-Template/tree/main.
 */
public class Drivetrain extends SubsystemBase {
    /** Define the modules. */
    private final SwerveModule frontLeft = new SwerveModule(
            DriveConstants.frontLeftDrivingId,
            DriveConstants.frontLeftTurningId,
            DriveConstants.frontLeftAngularOffset);
    private final SwerveModule backRight = new SwerveModule(
            DriveConstants.backRightDrivingId,
            DriveConstants.backRightTurningId,
            DriveConstants.backRightAngularOffset);
    /**
     * The robot uses a Studica NavX2 MXP IMU accelerometer connected to the MXP
     * port on the RoboRio.
     * All measurements are given in terms of degrees.
     */
    private final AHRS gyro = new AHRS(NavXComType.kMXP_SPI);
    /** Pose estimation */
    private final SwerveDrivePoseEstimator poseEstimator = new SwerveDrivePoseEstimator(
            DriveConstants.driveKinematics,
            gyro.getRotation2d(),
            new SwerveModulePosition[] {
                    new SwerveModulePosition(0.0, new Rotation2d()),
                    new SwerveModulePosition(0.0, new Rotation2d())
            },
            new Pose2d() // Assumes that the robot starts at the origin
    );
    /** Dashboard */
    private final Field2d field2d = new Field2d();
    private final Sendable swerveDriveSendable = new Sendable() {
        @Override
        public void initSendable(SendableBuilder builder) {
            builder.setSmartDashboardType("SwerveDrive");
            builder.addDoubleProperty("Front Left Angle", () -> frontLeft.getPosition().angle.getRadians(), null);
            builder.addDoubleProperty("Front Left Velocity", () -> frontLeft.getState().speedMetersPerSecond, null);
            builder.addDoubleProperty("Back Right Angle", () -> backRight.getPosition().angle.getRadians(), null);
            builder.addDoubleProperty("Back Right Velocity", () -> backRight.getState().speedMetersPerSecond, null);
            builder.addDoubleProperty("Robot Angle", () -> getHeading().getRadians(), null);
        }
    };

    public Drivetrain() {
        /* Reset upon robot initialization. */
        resetEncoders();
        zeroHeading();
        configureAutoBuilder();
        /* Send data to dashboard. */
        SmartDashboard.putData("Swerve Drive", swerveDriveSendable);
        SmartDashboard.putData("Field", field2d);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Gyro", getHeading().getDegrees());
        poseEstimator.update(gyro.getRotation2d(), getModulePositions());
        field2d.setRobotPose(getPose());
    }

    /**
     * Configure auto builder for use with PathPlanner.
     */
    private void configureAutoBuilder() {
        RobotConfig config;
        try {
            config = RobotConfig.fromGUISettings();
            AutoBuilder.configure(
                    this::getPose, // Robot pose supplier
                    this::resetPose, // Method to reset odometry (will be called if your auto has a starting pose)
                    this::getRobotRelativeSpeeds, // ChassisSpeeds supplier. MUST BE ROBOT RELATIVE
                    this::driveRobotRelative, // Method that will drive the robot given ROBOT RELATIVE ChassisSpeeds.
                                              // Also optionally outputs individual module feedforwards
                    new PPHolonomicDriveController( // PPHolonomicController is the built in path following controller
                                                    // for holonomic drive trains
                            new PIDConstants(5.0, 0.0, 0.0), // Translation PID constants
                            new PIDConstants(5.0, 0.0, 0.0) // Rotation PID constants
                    ),
                    config, // The robot configuration
                    () -> {
                        // Boolean supplier that controls when the path will be mirrored for the red
                        // alliance
                        // This will flip the path being followed to the red side of the field.
                        // THE ORIGIN WILL REMAIN ON THE BLUE SIDE
                        var alliance = DriverStation.getAlliance();
                        if (alliance.isPresent()) {
                            return alliance.get() == DriverStation.Alliance.Red;
                        } else {
                            return false;
                        }
                    },
                    this // Reference to this subsystem to set requirements
            );
        } catch (Exception e) {
            // Handle exception as needed
            e.printStackTrace();
        }
    }

    private Pose2d getPose() {
        return poseEstimator.getEstimatedPosition();
    }

    private void resetPose(Pose2d pose) {
        poseEstimator.resetPose(pose);
    }

    private ChassisSpeeds getRobotRelativeSpeeds() {
        return DriveConstants.driveKinematics.toChassisSpeeds(getModuleStates());
    }

    private void driveRobotRelative(ChassisSpeeds chassisSpeeds) {
        setModuleStates(DriveConstants.driveKinematics.toSwerveModuleStates(chassisSpeeds));
    }

    /**
     * Drive the robot using inputs from the joysticks. The x-speed is from the
     * x-axis of the left joystick.
     * The y-speed (left-to-right) is from the y-axis of the left joystick. The
     * rotation is from the y-axis
     * of the right joystick.
     * 
     * @param xSpeed        The speed of the robot in the x-direction
     *                      (front-to-back) on a [-1.0, 1.0] scale.
     * @param ySpeed        The speed of the robot in the y-direction
     *                      (left-to-right) on a [-1.0, 1.0] scale.
     * @param rot           The rotation of the robot on a [-1.0, 1.0] scale.
     * @param fieldRelative Whether or not to drive the robot relative the current
     *                      field.
     */
    public void drive(double xSpeed, double ySpeed, double rot, boolean fieldRelative) {
        // Convert the commanded speeds into the correct units for the drivetrain
        double xSpeedDelivered = xSpeed * DriveConstants.maxSpeed;
        double ySpeedDelivered = ySpeed * DriveConstants.maxSpeed;
        double rotDelivered = rot * DriveConstants.maxAngularSpeed;

        var swerveModuleStates = DriveConstants.driveKinematics.toSwerveModuleStates(
                fieldRelative
                        ? ChassisSpeeds.fromFieldRelativeSpeeds(xSpeedDelivered,
                                ySpeedDelivered,
                                rotDelivered,
                                getHeading())
                        : new ChassisSpeeds(xSpeedDelivered, ySpeedDelivered, rotDelivered));
        setModuleStates(swerveModuleStates);
    }

    private SwerveModulePosition[] getModulePositions() {
        return new SwerveModulePosition[] {
                frontLeft.getPosition(),
                backRight.getPosition()
        };
    }

    private SwerveModuleState[] getModuleStates() {
        return new SwerveModuleState[] {
                frontLeft.getState(),
                backRight.getState(),
        };
    }

    /**
     * Sets the state of each module.
     * 
     * @param desiredStates An array of SwerveModuleStates in the order [frontLeft,
     *                      frontRight,
     *                      backLeft, backRight].
     */
    public void setModuleStates(SwerveModuleState[] desiredStates) {
        SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, DriveConstants.maxSpeed);
        frontLeft.setDesiredState(desiredStates[0]);
        backRight.setDesiredState(desiredStates[1]);
    }

    /**
     * Calls the resetEncoders() method on all modules. Might remove if unused.
     */
    public void resetEncoders() {
        frontLeft.syncAndResetEncoders();
        backRight.syncAndResetEncoders();
    }

    /**
     * Sets the yaw of the gyrometer to 0.
     */
    public void zeroHeading() {
        gyro.reset();
    }

    /**
     * @return The heading of the robot as Rotation2d object. The output is
     *         continuous (i.e. 361º does not wrap around to 1º). If the gyro is
     *         reversed
     *         (see Constants.java), then the direction is reversed.
     */
    public Rotation2d getHeading() {
        return Rotation2d.fromDegrees(gyro.getAngle());
    }
}
