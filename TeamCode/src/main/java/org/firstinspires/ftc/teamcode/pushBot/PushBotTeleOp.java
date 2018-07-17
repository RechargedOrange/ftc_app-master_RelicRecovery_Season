package org.firstinspires.ftc.teamcode.pushBot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.relicRecovery.main.teleOps.api.tankDriveControls.TankDriveWithTurningTriggers;

/**
 * Created by recharged on 11/27/17.
 */
@TeleOp(name = "PushBotTeleOp")
public class PushBotTeleOp extends LinearOpMode{
    final double THRESHOLD = 0.1;
    final double TURN_THRESHOLD = 0.1;
    PushBotHardwareClass hardwareClass = new PushBotHardwareClass(this);
    TankDriveWithTurningTriggers driveControl = new TankDriveWithTurningTriggers(hardwareClass);
    @Override
    public void runOpMode() throws InterruptedException{
        hardwareClass.init(false);
        waitForStart();

        while(opModeIsActive()){
            driveControl.powerDrive(-gamepad1.left_stick_y, -gamepad1.right_stick_y, gamepad1.left_trigger, gamepad1.right_trigger, THRESHOLD, TURN_THRESHOLD);
        }
    }
}
