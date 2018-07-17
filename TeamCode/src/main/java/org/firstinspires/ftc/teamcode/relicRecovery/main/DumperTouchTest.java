package org.firstinspires.ftc.teamcode.relicRecovery.main;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.HardwareClass;

/**
 * Created by recharged on 1/1/18.
 */

@TeleOp(name = DumperTouchTest.NAME)
public class DumperTouchTest extends LinearOpMode {
    public static final String NAME = "DumperTouchTest";

    @Override
    public void runOpMode() throws InterruptedException {
        HardwareClass hardwareClass = new HardwareClass(this);
        hardwareClass.init(false);

        waitForStart();
        while (opModeIsActive()) {
            if(gamepad1.y)
                hardwareClass.dumper.setDumping(true);
            if(gamepad1.a)
                hardwareClass.dumper.setDumping(false);
            if(gamepad1.b)
                hardwareClass.dumper.setPartlyUp(true);
            if(gamepad1.x)
                hardwareClass.dumper.setPartlyUp(false);
            telemetry.addData("hardwareClass.bucketDownByTouch.isTrue(): ", hardwareClass.bucketDownByTouch.isTrue());
            telemetry.addData("hardwareClass.glyphDumperTouch.isPressed(): ", hardwareClass.glyphDumperDownTouch.isPressed());
            telemetry.update();
        }
    }
}
