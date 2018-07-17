package org.firstinspires.ftc.teamcode.relicRecovery.trystorming;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by David on 9/21/2017.
 */


@TeleOp(name = "TankDriveWithStrafing")
@Disabled

public class TankDriveWithStrafing extends LinearOpMode {
    HolonomicDriveTestLinear drive = new HolonomicDriveTestLinear();

    @Override
    public void runOpMode() throws InterruptedException {
        drive.initialize(hardwareMap);
        waitForStart();
        while (opModeIsActive()){

            double foward = -gamepad1.left_stick_y / 2 + -gamepad1.right_stick_y / 2;
            double strafe = gamepad1.left_trigger - gamepad1.right_trigger;//Not  -
            double rotation = -gamepad1.left_stick_y - -gamepad1.right_stick_y;// NOt -

            /*double FrontLeft = -foward - strafe - rotation;
            double FrontRight = foward - strafe - rotation;
            double BackRight = foward + strafe - rotation;
            double BackLeft = -foward + strafe - rotation;


            FrontRight = Range.clip(FrontRight, -1, 1);
            FrontLeft = Range.clip(FrontLeft, -1, 1);
            BackLeft = Range.clip(BackLeft, -1, 1);
            BackRight = Range.clip(BackRight, -1, 1);


            motorFrontRight.setPower(FrontRight);//         write the values to the motors
            motorFrontLeft.setPower(FrontLeft);
            motorBackLeft.setPower(BackLeft);
            motorBackRight.setPower(BackRight);*/

            drive.power(rotation, foward, strafe);
        }
    }
}
