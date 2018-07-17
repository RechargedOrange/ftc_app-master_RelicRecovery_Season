package org.firstinspires.ftc.teamcode.relicRecovery.sensors;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.sensorAPI.RevMagneticLimitSwitchSensor;

/**
 * Created by user on 3/10/2018.
 */

@TeleOp(name = "RevMagneticLimitSwitchTest")
public class RevMagneticLimitSwitchTest extends LinearOpMode{
    @Override
    public void runOpMode() throws InterruptedException{
        RevMagneticLimitSwitchSensor downLimitSwitch = new RevMagneticLimitSwitchSensor("liftDownLimitSwitch", hardwareMap);
        RevMagneticLimitSwitchSensor upLimitSwitch = new RevMagneticLimitSwitchSensor("liftUpLimitSwitch", hardwareMap);
        waitForStart();

        while(opModeIsActive()){
            telemetry.addData("down near", downLimitSwitch.isNear());
            telemetry.addData("down far", downLimitSwitch.isFar());
            telemetry.addData("up near", upLimitSwitch.isNear());
            telemetry.addData("up far", upLimitSwitch.isFar());
            telemetry.update();
        }
    }
}
