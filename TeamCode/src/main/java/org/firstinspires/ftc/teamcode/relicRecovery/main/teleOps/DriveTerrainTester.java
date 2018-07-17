package org.firstinspires.ftc.teamcode.relicRecovery.main.teleOps;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.HardwareClass;

/**
 * Created by David on 10/19/2017.
 */

@TeleOp(name = "DriveTerrainTester")
//@Disabled

public class DriveTerrainTester extends LinearOpMode{
    @Override
    public void runOpMode() throws InterruptedException{
        DcMotor leftFront = hardwareMap.dcMotor.get("leftFront");
        DcMotor leftBack = hardwareMap.dcMotor.get("leftBack");
        DcMotor rightFront = hardwareMap.dcMotor.get("rightFront");
        DcMotor rightBack = hardwareMap.dcMotor.get("rightBack");

        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        while(opModeIsActive()){
            double leftSpeed = -gamepad1.left_stick_y;
            double rightSpeed = -gamepad1.right_stick_y;

            leftFront.setPower(leftSpeed);
            leftBack.setPower(leftSpeed);
            rightFront.setPower(rightSpeed);
            rightBack.setPower(rightSpeed);

            idle();
        }
    }
}
