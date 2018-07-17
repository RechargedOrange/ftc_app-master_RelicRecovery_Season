package org.firstinspires.ftc.teamcode.relicRecovery.main.hardware;

/**
 * Created by user on 2/12/2018.
 */

public interface HolonomicHardware extends TankDriveHardwareMethods{
    void powerHolonomicDrive(double forward, double strafeLeft, double turnClockwise);
    int getFrontLeftEncoder();
    int getFrontRightEncoder();
    int getBackLeftEncoder();
    int getBackRightEncoder();
}
