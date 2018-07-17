package org.firstinspires.ftc.teamcode.relicRecovery.main.autonomouses;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.DriveMethodsHolinomic;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.HardwareClass;

/**
 * Created by user on 3/1/2018.
 */

@Autonomous(name = "HolinomicEncoderDriveTest")
public class HolinomicEncoderDriveTest extends LinearOpMode{
    @Override
    public void runOpMode() throws InterruptedException{
        HardwareClass hardwareClass = new HardwareClass(this);
        hardwareClass.init(true);
        DriveMethodsHolinomic driveMethods = new DriveMethodsHolinomic(this, hardwareClass, hardwareClass);
        waitForStart();

        while(opModeIsActive()) {
            driveMethods.driveForward(1.0, 2000, 0, 0.03, AngleUnit.DEGREES);
            sleep(400);
            driveMethods.driveLeft(1.0, 2000, 0, 0.03, AngleUnit.DEGREES);
            sleep(400);
            driveMethods.driveBackward(1.0, 2000, 0, 0.03, AngleUnit.DEGREES);
            sleep(400);
            driveMethods.driveRight(1.0, 2000, 0, 0.03, AngleUnit.DEGREES);
            sleep(400);
        }
    }
}
