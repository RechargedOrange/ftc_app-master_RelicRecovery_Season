package org.firstinspires.ftc.teamcode.relicRecovery.main.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by David on 10/14/2017.
 */

public interface TankDriveHardwareMethods {
    void powerLeft(double power);
    void powerRight(double power);
    void powerDrive(double leftPower, double rightPower);
    void resetDriveEncoders();
    int getLeftPosition();
    int getRightPosition();
    void setDriveRunMode(DcMotor.RunMode runMode);
    void setDriveZeroPowerBehavior(DcMotor.ZeroPowerBehavior zeroPowerBehavior);
}
