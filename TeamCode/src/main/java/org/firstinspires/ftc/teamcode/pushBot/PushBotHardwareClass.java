package org.firstinspires.ftc.teamcode.pushBot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.TankDriveHardwareMethods;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_USING_ENCODER;
import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_WITHOUT_ENCODER;
import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.STOP_AND_RESET_ENCODER;
import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

/**
 * Created by recharged on 11/27/17.
 */

public class PushBotHardwareClass implements TankDriveHardwareMethods{
    LinearOpMode linearOpMode;
    boolean autonomous;

    DcMotor left;
    DcMotor right;

    @Deprecated
    public PushBotHardwareClass(){
    }

    public PushBotHardwareClass(LinearOpMode linearOpMode){
        this.linearOpMode = linearOpMode;
    }

    public void init(boolean autonomous){
        this.autonomous = autonomous;
        left = linearOpMode.hardwareMap.dcMotor.get("left");
        right = linearOpMode.hardwareMap.dcMotor.get("right");

        left.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void powerLeft(double power) {
        left.setPower(power);
    }

    @Override
    public void powerRight(double power) {
        right.setPower(power);
    }

    @Override
    public void powerDrive(double leftPower, double rightPower) {
        powerLeft(leftPower);
        powerRight(rightPower);
    }

    @Override
    public void resetDriveEncoders() {
        setDriveRunMode(STOP_AND_RESET_ENCODER);
        linearOpMode.idle();
        setDriveRunMode(autonomous ? RUN_USING_ENCODER : RUN_WITHOUT_ENCODER);
    }

    @Override
    public int getLeftPosition() {
        return 0;
    }

    @Override
    public int getRightPosition() {
        return 0;
    }

    @Override
    public void setDriveRunMode(DcMotor.RunMode runMode) {
        left.setMode(runMode);
        right.setMode(runMode);
    }

    @Override
    public void setDriveZeroPowerBehavior(DcMotor.ZeroPowerBehavior zeroPowerBehavior) {
        left.setZeroPowerBehavior(BRAKE);
        right.setZeroPowerBehavior(BRAKE);
    }
}
