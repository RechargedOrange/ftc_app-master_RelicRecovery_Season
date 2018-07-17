package org.firstinspires.ftc.teamcode.relicRecovery.main.autonomouses.RobotV2.OLD_BACKUPS;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.relicRecovery.Alliance;
import org.firstinspires.ftc.teamcode.relicRecovery.main.autonomouses.RobotV2.Jewel;
import org.firstinspires.ftc.teamcode.relicRecovery.main.autonomouses.RobotV2.Vision.vuforia.MasterVuforia;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.DriveMethods;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.Dumper;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.HardwareClass;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark.CENTER;
import static org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark.RIGHT;
import static org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark.UNKNOWN;

/**
 * Created by David on 12/9/17.
 */
@Autonomous(name = "Blue_SidePad_85PTS_Jewel_CorrectColumn_Park_OLD_DUMPING")
//@Disabled
public class Blue_SidePad_85PTS_Jewel_CorrectColumn_Park_OLD_DUMPING extends LinearOpMode {
    HardwareClass hardwareClass;
    MasterVuforia vuforia;
    RelicRecoveryVuMark vuMark = RIGHT;
    DriveMethods driveMethods;
    //Dumper dumper;
    ElapsedTime timer = new ElapsedTime();

    @Override
    public void runOpMode() {
        hardwareClass = new HardwareClass(this);
        hardwareClass.init(true);

        //dumper = new Dumper(hardwareClass.glyphDumper);
        driveMethods = new DriveMethods(this, hardwareClass, hardwareClass);

        vuforia = new MasterVuforia(this);
        vuforia.init(VuforiaLocalizer.CameraDirection.FRONT);

        vuforia.activate();
        waitForStart();
        hardwareClass.resetGyroZ();

        vuMark = vuforia.vuMarks.getVuMark(5);
        vuforia.deactivate();

        telemetry.addData("VuMark", vuMark);
        telemetry.update();

        /**
         * Jewel
         */

        hardwareClass.powerIntake(1.0);
        new Jewel(hardwareClass, this).doJewel(Alliance.BLUE);
        hardwareClass.powerIntake(0.0);

        /**
         * Drive to column
         */
        {
            if (vuMark == UNKNOWN)
                vuMark = CENTER;
            int distance = 0;
            switch (vuMark) {
                case LEFT:
                    distance = 2500;
                    break;
                case CENTER:
                    distance = 3500;
                    break;
                case RIGHT:
                    distance = 4400;
                    break;
            }
            driveMethods.driveForward(0.5, distance, 0, 0.02, DEGREES);
            sleep(100);
        }
        driveMethods.turnRight(0.3, -92, DEGREES);
        sleep(400);
        driveMethods.turnLeft(0.10, -93, DEGREES);
        sleep(500);

        /**
         * Dump
         */
        /*hardwareClass.glyphLift.setTargetPosition(AUTODUMP.position);
        hardwareClass.glyphLift.setPower(1.0);
        //driveMethods.driveBackward(0.3, 400, -90, 0.02, DEGREES);
        while (hardwareClass.glyphLift.isBusy() && opModeIsActive()) ;*/

        hardwareClass.dumper.setDumping(true);
        timer.reset();
        while(timer.milliseconds() < 2500 && opModeIsActive())
            hardwareClass.dumper.update();
        hardwareClass.glyphDumper.setPower(0.0);

        //sleep(2000);
        hardwareClass.powerLeft(-0.3);
        hardwareClass.powerRight(-0.3);
        while(timer.milliseconds() < 3000 && opModeIsActive())
            hardwareClass.dumper.update();
        hardwareClass.powerLeft(0.0);
        hardwareClass.powerRight(0.0);
        while(timer.milliseconds() < 1000 && opModeIsActive())
            hardwareClass.dumper.update();
        hardwareClass.powerLeft(0.8);
        hardwareClass.powerRight(0.8);
        while(timer.milliseconds() < 200 && opModeIsActive())
            hardwareClass.dumper.update();
        hardwareClass.powerLeft(0.0);
        hardwareClass.powerRight(0.0);

        hardwareClass.dumper.setDumping(false);
        hardwareClass.dumper.setPartlyUp(true);
        timer.reset();
        while(hardwareClass.glyphDumper.isBusy() && opModeIsActive())
            hardwareClass.dumper.update();
        hardwareClass.glyphDumper.setPower(0.0);
        /*hardwareClass.powerIntake(1.0);
        sleep(3000);
        hardwareClass.powerIntake(0.0);*/
        /*sleep(1000);
        hardwareClass.glyphLift.setTargetPosition(100);
        while(hardwareClass.glyphLift.isBusy() && opModeIsActive());*/
    }
}
