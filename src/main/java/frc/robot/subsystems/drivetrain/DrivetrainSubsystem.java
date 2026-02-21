package frc.robot.subsystems.drivetrain;

import com.studica.frc.AHRS;
import com.studica.frc.AHRS.NavXComType;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

/**
 * The robot uses four swerve modules in a square configuration. The code is based on the template
 * provided by Rev Robotics at https://github.com/REVrobotics/MAXSwerve-Java-Template/tree/main.
 */
public class DrivetrainSubsystem extends SubsystemBase {
    private final SwerveModule frontLeft = new SwerveModule(
            DriveConstants.frontLeftDrivingId,
            DriveConstants.frontLeftTurningId,
            DriveConstants.frontLeftAngularOffset);
    private final SwerveModule frontRight = new SwerveModule(
            DriveConstants.frontRightDrivingId,
            DriveConstants.frontRightTurningId,
            DriveConstants.frontRightAngularOffset);
    private final SwerveModule backRight = new SwerveModule(
            DriveConstants.backRightDrivingId,
            DriveConstants.backRightTurningId,
            DriveConstants.backRightAngularOffset);
    private final SwerveModule backLeft = new SwerveModule(
            DriveConstants.backLeftDrivingId,
            DriveConstants.backLeftTurningId,
            DriveConstants.backLeftAngularOffset);

    /** The robot uses a Studica NavX2 MXP IMU accelerometer connected to the MXP port on the RoboRio.
     * All measurements are given in terms of degrees. */
    private final AHRS gyro = new AHRS(NavXComType.kMXP_SPI);

    public DrivetrainSubsystem() {
        zeroHeading();
    }

    /**
     * Drive the robot using inputs from the joysticks. The x-speed is from the x-axis of the left joystick.
     * The y-speed (left-to-right) is from the y-axis of the left joystick. The rotation is from the y-axis
     * of the right joystick.
     * 
     * @param xSpeed The speed of the robot in the x-direction (front-to-back) on a [-1.0, 1.0] scale.
     * @param ySpeed The speed of the robot in the y-direction (left-to-right) on  a [-1.0, 1.0] scale.
     * @param rot The rotation of the robot on a [-1.0, 1.0] scale.
     * @param fieldRelative Whether or not to drive the robot relative the current field.
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

    /**
     * Sets the state of each module.
     * 
     * @param desiredStates An array of SwerveModuleStates in the order [frontLeft, frontRight,
     * backLeft, backRight].
     */
    public void setModuleStates(SwerveModuleState[] desiredStates) {
        SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, DriveConstants.maxSpeed);
        frontLeft.setDesiredState(desiredStates[0]);
        frontRight.setDesiredState(desiredStates[1]);
        backLeft.setDesiredState(desiredStates[2]);
        backRight.setDesiredState(desiredStates[3]);
    }

    /**
     * Calls the resetEncoders() method on all modules. Might remove if unused.
     */
    public void resetEncoders() {
        frontLeft.syncAndResetEncoders();
        frontRight.syncAndResetEncoders();
        backLeft.syncAndResetEncoders();
        backRight.syncAndResetEncoders();
    }

    /**
     * Sets the yaw of the gyrometer to 0.
     */
    public void zeroHeading() {
        gyro.reset();
    }

    /**
     * @return The heading of the robot as Rotation2d object. The output is continuous (i.e. 361º
     * does not wrap around to 1º). If the gyro is reversed (see Constants.java), then the direction
     * is reversed.
     */
    public Rotation2d getHeading() {
        double direction = DriveConstants.isGyroReversed ? -1 : 1;
        return Rotation2d.fromDegrees(direction * gyro.getAngle());
    }
}
