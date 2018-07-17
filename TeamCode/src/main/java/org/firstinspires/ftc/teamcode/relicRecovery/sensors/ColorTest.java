package org.firstinspires.ftc.teamcode.relicRecovery.sensors;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.HardwareClass;

/**
 * Created by Lukens on 10/26/2017.
 */

@TeleOp(name = "ColorTest")

public class ColorTest extends LinearOpMode{

    HardwareClass hardwareClass;

    @Override
    public void runOpMode(){
        hardwareClass = new HardwareClass(this);

        hardwareClass.init(false);

        waitForStart();

        while(opModeIsActive()){
            telemetry.addData("hardwareClass.leftBallColorChecker.alpha() = ", hardwareClass.jewelColorSensor.alpha());
            telemetry.addData("hardwareClass.leftBallColorChecker.argb() = ", hardwareClass.jewelColorSensor.argb());
            telemetry.addData("hardwareClass.leftBallColorChecker.red() = ", hardwareClass.jewelColorSensor.red());
            telemetry.addData("hardwareClass.leftBallColorChecker.green() = ", hardwareClass.jewelColorSensor.green());
            telemetry.addData("hardwareClass.leftBallColorChecker.blue() = ", hardwareClass.jewelColorSensor.blue());
            telemetry.update();
        }
    }
}
