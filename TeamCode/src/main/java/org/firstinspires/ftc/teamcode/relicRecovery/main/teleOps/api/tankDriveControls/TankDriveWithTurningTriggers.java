package org.firstinspires.ftc.teamcode.relicRecovery.main.teleOps.api.tankDriveControls;

import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.TankDriveHardwareMethods;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.HardwareClass;

/**
 * Created by David on 10/12/2017.
 */

public class TankDriveWithTurningTriggers {

    TankDriveHardwareMethods hardware;

    @Deprecated
    public TankDriveWithTurningTriggers() {
    }

    public TankDriveWithTurningTriggers(TankDriveHardwareMethods hardware) {
        this.hardware = hardware;
    }

    public void powerDrive(double leftSpeed, double rightSpeed, double leftTurn, double rightTurn, final double threshold, final double turnThreshold) {

        double powerLeft;
        double powerRight;

        powerLeft = Math.abs(leftSpeed) >= threshold ? leftSpeed * (leftTurn >= turnThreshold ? 1.0 - leftTurn : 1.0) : 0.0;
        powerRight = Math.abs(rightSpeed) >= threshold ? rightSpeed * (rightTurn >= turnThreshold ? 1.0 - rightTurn : 1.0) : 0.0;

        hardware.powerLeft(powerLeft);
        hardware.powerRight(powerRight);
    }
}
