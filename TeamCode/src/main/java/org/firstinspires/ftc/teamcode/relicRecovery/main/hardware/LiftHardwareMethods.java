package org.firstinspires.ftc.teamcode.relicRecovery.main.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by David on 10/16/2017.
 */

public interface LiftHardwareMethods {
    void powerLift(double power);
    void setLiftRunMode(DcMotor.RunMode liftRunMode);
    DcMotor.RunMode getLiftRunMode();
    void setLiftTargetCounts(int targetCounts);
}