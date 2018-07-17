package org.firstinspires.ftc.teamcode.relicRecovery.main.autonomouses.RobotV2;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.relicRecovery.Alliance;
import org.firstinspires.ftc.teamcode.relicRecovery.main.autonomouses.RobotV2.Vision.vuforia.MasterVuforia;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.DriveMethods;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.Dumper;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.HardwareClass;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark.CENTER;
import static org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark.LEFT;
import static org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark.RIGHT;
import static org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark.UNKNOWN;
import static org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.LiftPositions.AUTODUMP;

/**
 * Created by David on 12/9/17.
 */
@Autonomous(name = "Blue_SidePad_85PTS_Jewel_CorrectColumn_Park")
@Disabled
public class Blue_SidePad_85PTS_Jewel_CorrectColumn_Park extends LinearOpMode {
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

        //vuMark = vuforia.vuMarks.getVuMark(5);
        if(vuMark == UNKNOWN)
            vuMark = CENTER;
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
        driveMethods.driveForward(0.5, 3500, 0, 0.02, DEGREES);
        sleep(400);
        driveMethods.turnRight(0.3, -90, DEGREES);
        sleep(400);
        driveMethods.driveForward(0.3, 200, -90, 0.0, DEGREES);
        sleep(400);
        if(vuMark == LEFT)
            driveMethods.turnLeft(0.3, -35, DEGREES);
        else if(vuMark == RIGHT)
            driveMethods.turnRight(0.3, -110, DEGREES);

        sleep(400);

        /**
         * Dump
         */
        /*hardwareClass.glyphLift.setTargetPosition(AUTODUMP.position);
        hardwareClass.glyphLift.setPower(1.0);
        //driveMethods.driveBackward(0.3, 400, -90, 0.02, DEGREES);
        while (hardwareClass.glyphLift.isBusy() && opModeIsActive()) ;*/


        hardwareClass.dumper.setPartlyUp(true);
        timer.reset();
        sleep(1500);
        hardwareClass.dumper.setDumping(true);
        sleep(1000);

        //sleep(2000);
        hardwareClass.powerLeft(-0.3);
        hardwareClass.powerRight(-0.3);
        sleep(1000);
        hardwareClass.powerLeft(0.0);
        hardwareClass.powerRight(0.0);
        sleep(2000);
        hardwareClass.powerLeft(0.8);
        hardwareClass.powerRight(0.8);
        sleep(200);
        hardwareClass.powerLeft(0.0);
        hardwareClass.powerRight(0.0);

        hardwareClass.dumper.setDumping(false);
        hardwareClass.dumper.setPartlyUp(false);
        timer.reset();
        while((!hardwareClass.bucketDownByTouch.isTrue()) && opModeIsActive());
        //dumper.setDumping(false);
        /*hardwareClass.powerIntake(1.0);
        sleep(3000);
        hardwareClass.powerIntake(0.0);*/
        /*sleep(1000);
        hardwareClass.glyphLift.setTargetPosition(100);
        while(hardwareClass.glyphLift.isBusy() && opModeIsActive());*/
    }
}
