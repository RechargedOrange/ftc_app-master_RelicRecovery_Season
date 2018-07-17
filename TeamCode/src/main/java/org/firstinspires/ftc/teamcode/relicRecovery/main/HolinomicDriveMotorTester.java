package org.firstinspires.ftc.teamcode.relicRecovery.main;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.HardwareClass;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_WITHOUT_ENCODER;
import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.STOP_AND_RESET_ENCODER;

/**
 * Created by user on 3/12/2018.
 */

@TeleOp(name = "HolinomicDriveMotorTester")

public class HolinomicDriveMotorTester extends LinearOpMode{
    @Override
    public void runOpMode() throws InterruptedException{
        HardwareClass robot = new HardwareClass(this);
        robot.init(true);

        waitForStart();

        while (opModeIsActive()){
            telemetry.addLine("a for leftFront");
            telemetry.addLine("b for leftBack");
            telemetry.addLine("x for rightFront");
            telemetry.addLine("y for rightBack");

            telemetry.addLine();

            telemetry.addLine("D-Pad up to reset leftFront");
            telemetry.addLine("D-Pad down to reset leftBack");
            telemetry.addLine("D-Pad left to reset rightFront");
            telemetry.addLine("D-Pad right to reset rightBack");

            telemetry.addLine();

            telemetry.addData("leftFrontEncoder", robot.getFrontLeftEncoder());
            telemetry.addData("leftBackEncoder", robot.getBackLeftEncoder());
            telemetry.addData("rightFrontEncoder", robot.getFrontRightEncoder());
            telemetry.addData("rightBackEncoder", robot.getBackRightEncoder());

            double speed = -gamepad1.right_stick_y;
            robot.leftFront.setPower(gamepad1.a ? speed : 0.0);
            robot.leftBack.setPower(gamepad1.b ? speed : 0.0);
            robot.rightFront.setPower(gamepad1.x ? speed : 0.0);
            robot.rightBack.setPower(gamepad1.y ? speed : 0.0);

            robot.leftFront.setMode(gamepad1.dpad_up ? STOP_AND_RESET_ENCODER : RUN_WITHOUT_ENCODER);
            robot.leftBack.setMode(gamepad1.dpad_down ? STOP_AND_RESET_ENCODER : RUN_WITHOUT_ENCODER);
            robot.rightFront.setMode(gamepad1.dpad_left ? STOP_AND_RESET_ENCODER : RUN_WITHOUT_ENCODER);
            robot.rightBack.setMode(gamepad1.dpad_right ? STOP_AND_RESET_ENCODER : RUN_WITHOUT_ENCODER);

            telemetry.update();
        }
    }
}
