package org.firstinspires.ftc.teamcode.relicRecovery.sensors;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.relicRecovery.api.Updatable;
import org.firstinspires.ftc.teamcode.relicRecovery.api.UpdatingManager;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.sensorAPI.LoopingRevGyro;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;

/**
 * Created by user on 2/19/2018.
 */
@TeleOp(name = "LoopingGyroTest")
public class LoopingGyroTest extends LinearOpMode{
    @Override
    public void runOpMode() throws InterruptedException{
        LoopingRevGyro gyro = new LoopingRevGyro(this.hardwareMap, "imu");

        UpdatingManager updatingManager = new UpdatingManager(this);
        updatingManager.addUpdatable(gyro);
        updatingManager.start();

        waitForStart();

        while(opModeIsActive()){
            telemetry.addData("Z", gyro.getGyroZ(DEGREES));
            telemetry.update();
        }
    }
}
