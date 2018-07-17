package org.firstinspires.ftc.teamcode.relicRecovery;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.HardwareClass;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_USING_ENCODER;

/**
 * Created by user on 3/22/2018.
 */

@TeleOp(name = "Oof GOTEEM")
public class VoltageCalculationsTesting extends LinearOpMode {
    HardwareClass robot;
    VoltageCalculations voltageCalculations;
    ElapsedTime timer = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new HardwareClass(this);
        robot.init(false);
        robot.setDriveRunMode(RUN_USING_ENCODER);
        robot.resetDriveEncoders();
        voltageCalculations = new VoltageCalculations(this, 12.5);
        voltageCalculations.calculatePower(0.5, 12.5, 0.0);
        voltageCalculations.enabled(false);

        waitForStart();

        timer.reset();
        while (opModeIsActive() && timer.milliseconds() < 10000){
            telemetry.addData("voltageCalculations.getRawVoltage()", voltageCalculations.getRawVoltage());
            telemetry.addData("voltageCalculations.getMultiplier(12.5)", voltageCalculations.getMultiplier(12.5));

            double power = voltageCalculations.calculatePower(0.5, 11.5, 0.0);
            telemetry.addData("calculated power", power);
            robot.leftFront.setPower(power);

            telemetry.update();
        }

        int counts = robot.leftFront.getCurrentPosition();
        double countsPerSecond = counts / 10.0;

        robot.leftFront.setPower(0.0);

        while(opModeIsActive()){
            telemetry.addData("raw", counts);
            telemetry.addData("per second", countsPerSecond);
            telemetry.update();
        }
    }
}
