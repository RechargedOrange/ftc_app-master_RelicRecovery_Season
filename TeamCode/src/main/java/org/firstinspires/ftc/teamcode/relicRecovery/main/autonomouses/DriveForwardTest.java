package org.firstinspires.ftc.teamcode.relicRecovery.main.autonomouses;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.HardwareClass;

/**
 * Created by recharged on 12/8/17.
 */

@Autonomous(name = "DriveForwardTest")
@Disabled
public class DriveForwardTest extends LinearOpMode{
    HardwareClass hardwareClass;

    @Override
    public void runOpMode() throws InterruptedException{
        hardwareClass = new HardwareClass(this);
        hardwareClass.init(true);

        waitForStart();

        hardwareClass.powerLeft(0.5);
        hardwareClass.powerRight(0.5);
        while(hardwareClass.leftFront.getCurrentPosition() + hardwareClass.rightFront.getCurrentPosition() < 5000 && opModeIsActive()){
            telemetry.addData("Pos: ", hardwareClass.leftFront.getCurrentPosition() + hardwareClass.rightFront.getCurrentPosition());
        }
        hardwareClass.powerLeft(0.0);
        hardwareClass.powerRight(0.0);
    }
}
