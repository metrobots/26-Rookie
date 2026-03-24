
package frc.robot.subsystems.shooter;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {
    private final SparkMax shooterMotor;
    private final SparkMax loaderMotor;
    public final SparkMax aimingMotor;
    private final RelativeEncoder aimingEncoder;
    PIDController aimingPidController = new PIDController(0.01, 0, 0);
    double WEEEE = 10;

    public Shooter() {
        shooterMotor = new SparkMax(ShooterConstants.shooterMotorId, MotorType.kBrushless);
        loaderMotor = new SparkMax(ShooterConstants.shooterMotorId, MotorType.kBrushless);
        aimingMotor = new SparkMax(ShooterConstants.shooterMotorId, MotorType.kBrushless);
        aimingEncoder = aimingMotor.getEncoder();
    }

    public void ShooterSpinny(double speed) {
        shooterMotor.set(speed);
    }
    public void LoaderSpinny(double speed) {
        loaderMotor.set(speed);
    }

    public void aimingSpinny(double speed) {
        aimingMotor.set(speed);
    }


    public void aimingAuto(double target){
        aimingMotor.set(aimingPidController.calculate(aimingEncoder.getPosition(),target));
        
    }
//CHANGE TARGET BASED ON VISION
    public void aimNShoot(double whichTarget){
        aimingAuto(0);
        if((aimingEncoder.getPosition() <= whichTarget+0.5) && (whichTarget-0.5 <= aimingEncoder.getPosition())){
            ShooterSpinny(WEEEE);
        }
    }
}
