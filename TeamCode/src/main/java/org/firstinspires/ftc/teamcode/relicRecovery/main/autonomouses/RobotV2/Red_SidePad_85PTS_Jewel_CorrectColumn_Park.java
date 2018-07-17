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
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.LiftPositions;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark.CENTER;
import static org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark.LEFT;
import static org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark.RIGHT;
import static org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark.UNKNOWN;

/**
 * Created by recharged on 12/9/17.
 */
@Autonomous(name = "Red_SidePad_85PTS_CorrectColumn_Park")
@Disabled
public class Red_SidePad_85PTS_Jewel_CorrectColumn_Park extends LinearOpMode{
    HardwareClass hardwareClass;
    MasterVuforia vuforia;
    RelicRecoveryVuMark vuMark = RIGHT;
    DriveMethods driveMethods;
    //Dumper dumper;
    ElapsedTime timer = new ElapsedTime();

    @Override
    public void runOpMode(){
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
        if (vuMark == UNKNOWN)
             vuMark = CENTER;
        vuforia.deactivate();

        telemetry.addData("VuMark", vuMark);
        telemetry.update();

        /**
         * Jewel
         */

        hardwareClass.powerIntake(1.0);
        new Jewel(hardwareClass, this).doJewel(Alliance.RED);
        hardwareClass.powerIntake(0.0);

        /**
         * Drive to column
         */
        {
            if(vuMark == UNKNOWN)
                vuMark = CENTER;
            int distance = 0;
            switch (vuMark){
                case LEFT:
                    distance = 5500;
                    break;
                case CENTER:
                    distance = 4300;
                    break;
                case RIGHT:
                    distance = 3500;
                    break;
            }
            driveMethods.driveBackward(0.5, distance, 0, 0.02, DEGREES);
            sleep(100);
        }
        driveMethods.turnRight(0.3, -93, DEGREES);
        sleep(400);
        driveMethods.turnLeft(0.10, -93.5, DEGREES);
        sleep(500);
        //driveMethods.driveBackward(0.5, 500, 0, 0, DEGREES);

        /**
         * Dump
         */
        /*hardwareClass.glyphLift.setTargetPosition(LiftPositions.AUTODUMP.position);
        hardwareClass.glyphLift.setPower(1.0);
        driveMethods.driveBackward(0.3, 400, -90, 0.02, DEGREES);
        while(hardwareClass.glyphLift.isBusy() && opModeIsActive());*/

        hardwareClass.dumper.setPartlyUp(true);
        timer.reset();
        sleep(1500);
        hardwareClass.dumper.setDumping(true);
        sleep(1000);

        //sleep(2000);
        hardwareClass.powerLeft(-0.3);
        hardwareClass.powerRight(-0.3);
        timer.reset();
        sleep(3000);
        hardwareClass.powerLeft(0.0);
        hardwareClass.powerRight(0.0);
        timer.reset();
        sleep(1000);
        hardwareClass.powerLeft(0.8);
        hardwareClass.powerRight(0.8);
        timer.reset();
        sleep(200);
        hardwareClass.powerLeft(0.0);
        hardwareClass.powerRight(0.0);

        hardwareClass.dumper.setDumping(false);
        hardwareClass.dumper.setPartlyUp(false);
        timer.reset();
        while((!hardwareClass.bucketDownByTouch.isTrue()) && opModeIsActive());


        /*hardwareClass.powerIntake(1.0);
        sleep(3000);
        hardwareClass.powerIntake(0.0);*/
        /*hardwareClass.glyphLift.setTargetPosition(100);
        while(hardwareClass.glyphLift.isBusy() && opModeIsActive());*/
    }
}
