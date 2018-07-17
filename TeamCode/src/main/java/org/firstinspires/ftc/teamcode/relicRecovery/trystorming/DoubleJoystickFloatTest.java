package org.firstinspires.ftc.teamcode.relicRecovery.trystorming;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.HardwareClass;

/**
 * Created by user on 3/12/2018.
 */

@TeleOp(name = "DoubleJoystickFloatTest")
//@Disabled
public class DoubleJoystickFloatTest extends LinearOpMode{
    @Override
    public void runOpMode() throws InterruptedException{
        HardwareClass robot = new HardwareClass(this);

        robot.init(true);

        waitForStart();

        while(opModeIsActive()){
            Gamepad gamepadReading = gamepad1;

            double flPower = -(gamepadReading.left_stick_y - gamepad1.left_stick_x);
            double frPower = -(gamepadReading.right_stick_y + gamepad1.right_stick_x);
            double blPower = -(gamepadReading.left_stick_y + gamepad1.left_stick_x);
            double brPower = -(gamepadReading.right_stick_y -gamepad1.right_stick_x);


            double largest = Math.abs(flPower);

            //The previous algorithm will return a value from -2 to positive 2, but our motors take -1 to 1. This will scale it down while preserving the ratio.

            if (Math.abs(frPower) > largest)
                largest = Math.abs(frPower);
            if (Math.abs(blPower) > largest)
                largest = Math.abs(blPower);
            if (Math.abs(brPower) > largest)
                largest = Math.abs(brPower);

            flPower = Math.sqrt(Math.pow(gamepadReading.left_stick_y, 2) + Math.pow(gamepadReading.left_stick_x, 2)) * (flPower / largest);
            frPower = Math.sqrt(Math.pow(gamepadReading.right_stick_y, 2) + Math.pow(gamepadReading.right_stick_x, 2)) * (frPower / largest);
            blPower = Math.sqrt(Math.pow(gamepadReading.left_stick_y, 2) + Math.pow(gamepadReading.left_stick_x, 2)) * (blPower / largest);
            brPower = Math.sqrt(Math.pow(gamepadReading.right_stick_y, 2) + Math.pow(gamepadReading.right_stick_x, 2)) * (brPower / largest);

            robot.leftFront.setPower(flPower);
            robot.leftBack.setPower(blPower);
            robot.rightFront.setPower(frPower);
            robot.rightBack.setPower(brPower);
        }
    }
}
