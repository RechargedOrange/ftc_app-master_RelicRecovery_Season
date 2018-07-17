package org.firstinspires.ftc.teamcode.otherTeams.FROGBots;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.HardwareClass;

/**
 * Created by user on 2/9/2018.
 */

@TeleOp(name = I2CXLv2_Test.name)
public class I2CXLv2_Test extends LinearOpMode{
    final static String name = "I2CXLv2_Test";
    HardwareClass hardwareClass;
    @Override
    public void runOpMode() throws InterruptedException{
        hardwareClass = new HardwareClass(this);
        hardwareClass.init(true);
        telemetry.setMsTransmissionInterval(50);
        waitForStart();

        hardwareClass.leftFlexModule.deploy();
        hardwareClass.rightFlexModule.deploy();
        while (opModeIsActive()){
            telemetry.addData("Distance", hardwareClass.sonar.getDistance());
            telemetry.addData("Distance with math", hardwareClass.distanceFromWall(0, AngleUnit.DEGREES));
            telemetry.update();
        }
    }
}