package org.firstinspires.ftc.teamcode.relicRecovery;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.HardwareClass;

import static org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.HardwareClass.ClawState.GRAB;
import static org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.HardwareClass.ClawState.PUSHING;
import static org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.HardwareClass.ClawState.RELEASE;

/**
 * Created by user on 1/18/2018.
 */

@TeleOp(name = "ClawTester")
public class ClawTester extends LinearOpMode {
    HardwareClass hardwareClass;
    HardwareClass.ClawState clawState = RELEASE;
    @Override
    public void runOpMode() throws InterruptedException{
        hardwareClass = new HardwareClass(this);
        hardwareClass.init(false);
        waitForStart();
        while(opModeIsActive()){
            if(gamepad1.x)
                clawState = GRAB;
            else if(gamepad1.y)
                clawState = RELEASE;
            else if(gamepad1.b)
                clawState = PUSHING;
            hardwareClass.changeClawState(clawState);

            telemetry.addData("ClawState: ", clawState);
            telemetry.addLine("Press x to grab");
            telemetry.addLine("Press y to release");
            telemetry.addLine("Press b to push");
            telemetry.update();
        }
    }
}