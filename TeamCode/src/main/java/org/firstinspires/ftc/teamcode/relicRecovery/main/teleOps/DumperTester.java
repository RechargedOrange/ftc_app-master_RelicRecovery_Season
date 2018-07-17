package org.firstinspires.ftc.teamcode.relicRecovery.main.teleOps;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.Dumper;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.HardwareClass;

/**
 * Created by recharged on 11/27/17.
 */

@TeleOp(name = "Dumper tester")
public class DumperTester extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        DcMotor motor = hardwareMap.dcMotor.get("glyphDumper");
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        telemetry.setMsTransmissionInterval(30);

        waitForStart();

        while(opModeIsActive()) {
            telemetry.addData("Position", motor.getCurrentPosition());
            telemetry.update();
        }

    }
}
