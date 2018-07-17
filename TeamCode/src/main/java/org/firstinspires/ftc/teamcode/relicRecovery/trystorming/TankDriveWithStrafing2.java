package org.firstinspires.ftc.teamcode.relicRecovery.trystorming;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by David on 9/21/2017.
 */


@TeleOp(name = "TankDriveWithStrafing2")
@Disabled

public class TankDriveWithStrafing2 extends LinearOpMode {
    HolonomicDriveTestLinear drive = new HolonomicDriveTestLinear();

    @Override
    public void runOpMode() throws InterruptedException {
        drive.initialize(hardwareMap);
        waitForStart();
        while (opModeIsActive()){

            double foward = -gamepad1.left_stick_y / 2 + -gamepad1.right_stick_y / 2;
            double strafe = gamepad1.left_trigger - gamepad1.right_trigger;
            double rotation = (-gamepad1.left_stick_y - -gamepad1.right_stick_y) /2;

            drive.power(rotation, foward, strafe);
        }
    }
}
