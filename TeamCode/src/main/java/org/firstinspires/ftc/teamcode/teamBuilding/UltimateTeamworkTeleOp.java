package org.firstinspires.ftc.teamcode.teamBuilding;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.HardwareClass;

/**
 * Created by David on 10/30/2017.
 */

@TeleOp(name = "UltimateTeamworkTeleOp")
public class UltimateTeamworkTeleOp extends LinearOpMode{

    HardwareClass hardwareClass;

    final double THRESHOLD = 0.1;



    @Override
    public void runOpMode() throws InterruptedException {
        hardwareClass = new HardwareClass(this);

        hardwareClass.init(false);

        double leftSpeed;
        double rightSpeed;

        while(opModeIsActive()){
            leftSpeed = -gamepad1.left_stick_y;
            rightSpeed = -gamepad2.right_stick_y;

            hardwareClass.powerLeft(Math.abs(leftSpeed) >= THRESHOLD ? leftSpeed : 0.0);
            hardwareClass.powerRight(Math.abs(rightSpeed) >= THRESHOLD ? rightSpeed : 0.0);

            idle();
        }

        waitForStart();
    }
}
