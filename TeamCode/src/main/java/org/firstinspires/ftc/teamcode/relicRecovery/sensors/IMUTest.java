package org.firstinspires.ftc.teamcode.relicRecovery.sensors;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.HardwareClass;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;

/**
 * Created by Lukens on 10/26/2017.
 */

@TeleOp(name = "IMUTest")

public class IMUTest extends LinearOpMode{
    HardwareClass hardwareClass;

    @Override
    public void runOpMode(){
        hardwareClass = new HardwareClass(this);

        hardwareClass.init(false);

        telemetry.setMsTransmissionInterval(30);

        waitForStart();

        while(opModeIsActive()){
            telemetry.addData("Z", hardwareClass.getGyroZ(DEGREES));
            telemetry.addData("X", hardwareClass.getGyroX(DEGREES));
            telemetry.addData("Y", hardwareClass.getGyroY(DEGREES));
            telemetry.update();
        }
    }
}
