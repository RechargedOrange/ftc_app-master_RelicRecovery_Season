package org.firstinspires.ftc.teamcode.relicRecovery.sensors;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.HardwareClass;

/**
 * Created by user on 3/5/2018.
 */

@TeleOp(name = "FlexModulesTest")
public class FlexModulesTest extends LinearOpMode{
    HardwareClass hardwareClass;
    @Override
    public void runOpMode() throws InterruptedException{
        hardwareClass = new HardwareClass(this);
        hardwareClass.init(true);
        waitForStart();

        hardwareClass.leftFlexModule.retract();
        hardwareClass.rightFlexModule.retract();

        while(opModeIsActive()) {
            telemetry.addLine("a to recalibrate left");
            telemetry.addLine("b to recalibrate right");
            telemetry.addLine("x to deploy left");
            telemetry.addLine("y to deploy right");
            telemetry.addLine("leftBumper to retract left");
            telemetry.addLine("rightBumper to retract right");
            telemetry.addLine();
            telemetry.addData("left calibration voltage", hardwareClass.leftFlexModule.flexSensor.getCalibrationVoltage());
            telemetry.addData("left raw voltage", hardwareClass.leftFlexModule.flexSensor.getRawVoltage());
            telemetry.addData("right calibration voltage", hardwareClass.rightFlexModule.flexSensor.getCalibrationVoltage());
            telemetry.addData("right raw voltage", hardwareClass.rightFlexModule.flexSensor.getRawVoltage());
            telemetry.addData("left flex voltage", hardwareClass.leftFlexModule.flexSensor.getFlexVoltage());
            telemetry.addData("right flex voltage", hardwareClass.rightFlexModule.flexSensor.getFlexVoltage());
            telemetry.addLine();
            telemetry.addData("Distance", hardwareClass.sonar.getDistance());
            telemetry.addData("Math distance", hardwareClass.distanceFromWall(0, AngleUnit.DEGREES));
            telemetry.update();

            if(gamepad1.a)
                hardwareClass.leftFlexModule.flexSensor.recalibrate();
            if(gamepad1.b)
                hardwareClass.rightFlexModule.flexSensor.recalibrate();
            if(gamepad1.x)
                hardwareClass.leftFlexModule.deploy();
            if(gamepad1.y)
                hardwareClass.rightFlexModule.deploy();
            if (gamepad1.left_bumper)
                hardwareClass.leftFlexModule.retract();
            if(gamepad1.right_bumper)
                hardwareClass.rightFlexModule.retract();
        }
    }
}
