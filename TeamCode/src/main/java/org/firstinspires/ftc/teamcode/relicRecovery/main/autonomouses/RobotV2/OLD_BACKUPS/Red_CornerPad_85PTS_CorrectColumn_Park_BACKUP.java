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
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.HardwareClass;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark.CENTER;
import static org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark.RIGHT;
import static org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark.UNKNOWN;

/**
 * Created by recharged on 12/9/17.
 */

@Autonomous(name = "Red_CornerPad_85PTS_CorrectColumn_Park_BACKUP")
@Disabled
public class Red_CornerPad_85PTS_CorrectColumn_Park_BACKUP extends LinearOpMode{
    HardwareClass hardwareClass;
    MasterVuforia vuforia;
    RelicRecoveryVuMark vuMark = RIGHT;
    DriveMethods driveMethods;
    ElapsedTime timer = new ElapsedTime();
    //Dumper dumper;

    @Override
    public void runOpMode() throws InterruptedException{
        hardwareClass = new HardwareClass(this);
        hardwareClass.init(true);
        driveMethods = new DriveMethods(this, hardwareClass, hardwareClass);
        //dumper = new Dumper(hardwareClass.glyphDumper);
        //dumper.init(false);

        vuforia = new MasterVuforia(this);
        vuforia.init(VuforiaLocalizer.CameraDirection.FRONT);

        vuforia.activate();
        waitForStart();
        vuMark = vuforia.vuMarks.getVuMark(5);
        vuforia.deactivate();
        hardwareClass.resetGyroZ();

        telemetry.addData("VuMark", vuMark);
        telemetry.update();

        //Jewels
        hardwareClass.powerIntake(1.0);
        new Jewel(hardwareClass, this).doJewel(Alliance.RED);
        hardwareClass.powerIntake(0.0);

        // drive off ramp
        driveMethods.driveBackward(0.5, 3300, 0, 0.02, DEGREES);
        sleep(200);
        //bang into wall
        driveMethods.turnRight(0.5, -90, DEGREES);
        sleep(200);
        driveMethods.turnLeft(0.15, -92, DEGREES);
        sleep(200);
        {
            double power = -0.2;
            double correction = 0.015;
            int targetZ = -90;
            ElapsedTime timer = new ElapsedTime();
            while(opModeIsActive() && timer.milliseconds() < 2500){
                double powerOffSet = (hardwareClass.getGyroZ(DEGREES) - targetZ) * correction;
                hardwareClass.powerLeft(power + powerOffSet);
                hardwareClass.powerRight(power - powerOffSet);
            }
            hardwareClass.powerLeft(0.0);
            hardwareClass.powerRight(0.0);
        }
        sleep(1000);
        hardwareClass.resetGyroZ();
        sleep(500);

        //drive to column

        {
            if(vuMark == UNKNOWN)
                vuMark = CENTER;
            int distance = 0;
            switch (vuMark){
                case LEFT:
                    distance = 2800;
                    break;
                case CENTER:
                    distance = 1900;
                    break;
                case RIGHT:
                    distance = 800;
                    break;
            }
            driveMethods.driveForward(0.5, distance, 0, 0.02, DEGREES);
            sleep(500);
        }
        driveMethods.turnFowardLeft(0.3, 90, DEGREES);
        sleep(400);
        driveMethods.turnBackwardRight(0.15, 92, DEGREES);
        sleep(200);

        driveMethods.driveBackward(0.3 , 200, 90, 0.0, DEGREES);
        sleep(200);
        //dump
        /*hardwareClass.glyphLift.setTargetPosition(LiftPositions.AUTODUMP.position);
        hardwareClass.glyphLift.setPower(1.0);
        while(hardwareClass.glyphLift.isBusy() && opModeIsActive());*/

        /*dumper.setDumping(true);
        sleep(2500);*/
        hardwareClass.dumper.setDumping(true);
        timer.reset();
        while(timer.milliseconds() < 2500 && opModeIsActive())
            hardwareClass.dumper.update();
        hardwareClass.glyphDumper.setPower(0.0);

        hardwareClass.powerLeft(-0.3);
        hardwareClass.powerRight(-0.3);
        timer.reset();
        while(timer.milliseconds() < 3000 && opModeIsActive())
            hardwareClass.dumper.update();
        hardwareClass.powerLeft(0.0);
        hardwareClass.powerRight(0.0);
        timer.reset();
        while(timer.milliseconds() < 1000 && opModeIsActive())
            hardwareClass.dumper.update();
        hardwareClass.powerLeft(0.8);
        hardwareClass.powerRight(0.8);
        timer.reset();
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
        //dumper.setDumping(false);
    }
}