package org.firstinspires.ftc.teamcode.relicRecovery.main.teleOps.api.tankDriveControls;

import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.TankDriveHardwareMethods;

import static java.lang.Math.abs;

/**
 * Created by recharged on 12/7/17.
 */

public class TankDrive {
    private TankDriveHardwareMethods hardware;
    public void powerDrive(double left, double right, double threshold){
        if(abs(left) < threshold)
            left = 0;
        if(abs(right) < threshold)
            right = 0;
        hardware.powerLeft(left);
        hardware.powerRight(right);
    }

    public TankDrive(TankDriveHardwareMethods hardware) {
        this.hardware = hardware;
    }

    @Deprecated
    public TankDrive(){}
}
