package org.firstinspires.ftc.teamcode.relicRecovery.trystorming;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.HardwareClass;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.sensorAPI.LoopingRevGyro;

/**
 * Created by user on 4/17/2018.
 */

public class EncoderExample extends LinearOpMode {
    DcMotor motor1;
    DcMotor motor2;
    DcMotor motor3;
    DcMotor motor4;
    BNO055IMU imu;

    HardwareClass hardwareClass = new HardwareClass(this);
    @Override
    public void runOpMode() throws InterruptedException {
        motor1 = hardwareMap.dcMotor.get("m1");
        motor2 = hardwareMap.dcMotor.get("m2");
        motor3 = hardwareMap.dcMotor.get("m3");
        motor4 = hardwareMap.dcMotor.get("m4");
        imu = hardwareMap.get(BNO055IMU.class, "imu");

        motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        waitForStart();
        while (opModeIsActive()){
            telemetry.addData("IMU Z", imu.getPosition().x);
            telemetry.addData("IMU Z", imu.getPosition().y);
            telemetry.addData("IMU Z", imu.getPosition().z);



            telemetry.update();

            if(gamepad1.a) {
                motor1.setTargetPosition(1120);
                motor1.setPower(1);
            }
        }
    }
}
