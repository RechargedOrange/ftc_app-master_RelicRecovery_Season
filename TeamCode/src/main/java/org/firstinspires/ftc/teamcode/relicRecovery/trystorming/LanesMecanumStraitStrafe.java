package org.firstinspires.ftc.teamcode.relicRecovery.trystorming;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.HardwareClass;

import static java.lang.Math.abs;
import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;

/**
 * Created by user on 2/16/2018.
 */

@TeleOp(name = "LanesMecanumStraitStrafe")
public class LanesMecanumStraitStrafe extends LinearOpMode {
    HardwareClass hardwareClass;
    double lastImuPos;
    double currentImuPos;
    double imuUsePos;

    @Override
    public void runOpMode() throws InterruptedException {
        hardwareClass = new HardwareClass(this);
        hardwareClass.init(false);

        waitForStart();

        while (opModeIsActive()) {
            imuUsePos = currentImuPos - lastImuPos;
            currentImuPos = hardwareClass.getGyroZ(DEGREES) / 90;

            double forward = (-gamepad1.left_stick_y + -gamepad1.right_stick_y) / 2;
            if (abs(forward) < 0.1)
                forward = 0.0;
            double strafeLeft = gamepad1.left_trigger - gamepad1.right_trigger;
            if (abs(strafeLeft) < 0.1)
                strafeLeft = 0.0;
            double turnRight = (-gamepad1.left_stick_y - -gamepad1.right_stick_y) / 2;
            if (abs(turnRight) < 0.1)
                turnRight = 0.0;

            if (abs(strafeLeft) < 0.1 || abs(forward) >= 0.1 || abs(turnRight) > 0.1) {
                lastImuPos = currentImuPos;
                hardwareClass.powerHolonomicDrive(forward, strafeLeft, turnRight);
            } else {
                double adjustment = imuUsePos * abs(strafeLeft);
                if (adjustment <= 0.05) {
                    adjustment = imuUsePos * .8;
                }
                telemetry.addData("Correction", adjustment);
                hardwareClass.powerHolonomicDrive(0.0, strafeLeft, adjustment);
            }
            telemetry.update();
        }
    }
}
