package org.firstinspires.ftc.teamcode.relicRecovery.main.teleOps.api.tankDriveControls;

import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.TankDriveHardwareMethods;

/**
 * Created by David on 10/15/2017.
 */

public class BasicTankDrive {
    TankDriveHardwareMethods drive;

    @Deprecated
    public BasicTankDrive(){}

    public BasicTankDrive(TankDriveHardwareMethods drive) {
        this.drive = drive;
    }

    public void power(double leftSpeed, double rightSpeed, final double threshold){
        double leftPower = Math.abs(leftSpeed) >=  threshold ? leftSpeed : 0.0;
        double rightPower = Math.abs(rightSpeed) >= threshold ? rightSpeed : 0.0;
        drive.powerLeft(leftPower);
        drive.powerRight(rightPower);
    }
}
