package org.firstinspires.ftc.teamcode.relicRecovery.main;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.HardwareClass;

/**
 * Created by recharged on 12/20/17.
 */
@TeleOp(name = LiftPositionChecker.name)
public class LiftPositionChecker extends LinearOpMode{

    static final String name = "LiftPositionChecker";

    @Override
    public void runOpMode() throws InterruptedException{
        HardwareClass hardwareClass = new HardwareClass(this);
        hardwareClass.init(true);

        waitForStart();

        hardwareClass.glyphLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while(opModeIsActive()){
            hardwareClass.glyphLift.setPower(-gamepad1.right_stick_y);
            telemetry.addData("Counts: ", hardwareClass.glyphLift.getCurrentPosition());
            telemetry.update();
        }
    }
}
