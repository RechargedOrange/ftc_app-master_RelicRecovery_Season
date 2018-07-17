package org.firstinspires.ftc.teamcode.relicRecovery.main.autonomouses.RobotV2.nSRMultiGlyphs;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.HardwareClass;

/**
 * Created by user on 3/13/2018.
 */

@TeleOp(name = "zRightFlexThresholdFinder")
public class RightFlexThresholdFinder extends LinearOpMode {
    @Override
    public void runOpMode() {
        HardwareClass hardwareClass = new HardwareClass(this);
        hardwareClass.init(true);
        hardwareClass.rightFlexModule.deploy();

        waitForStart();

        hardwareClass.rightFlexModule.flexSensor.recalibrate();

        while (opModeIsActive()) {
            double voltage = hardwareClass.rightFlexModule.flexSensor.getFlexVoltage();
            telemetry.addData("== 0", voltage == 0);
            telemetry.addData(">", voltage > -0.003);
            telemetry.addData("<", voltage < 0.005);
            telemetry.update();
        }
    }
}
