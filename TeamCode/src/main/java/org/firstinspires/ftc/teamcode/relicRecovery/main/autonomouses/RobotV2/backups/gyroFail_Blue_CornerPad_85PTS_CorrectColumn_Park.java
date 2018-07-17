package org.firstinspires.ftc.teamcode.relicRecovery.main.autonomouses.RobotV2.backups;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.relicRecovery.Alliance;
import org.firstinspires.ftc.teamcode.relicRecovery.main.autonomouses.RobotV2.Jewel;
import org.firstinspires.ftc.teamcode.relicRecovery.main.autonomouses.RobotV2.Vision.vuforia.MasterVuforia;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.DriveMethods;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.Dumper;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.HardwareClass;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark.CENTER;
import static org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark.UNKNOWN;

/**
 * Created by recharged on 12/9/17.
 */

@Autonomous(name = "Blue_CornerPad_85PTS_CorrectColumn_Park")
@Disabled
public class gyroFail_Blue_CornerPad_85PTS_CorrectColumn_Park extends LinearOpMode{
    HardwareClass hardwareClass;
    MasterVuforia vuforia;
    RelicRecoveryVuMark vuMark = UNKNOWN;
    DriveMethods driveMethods;
    Dumper dumper;

    @Override
    public void runOpMode() throws InterruptedException{
        hardwareClass = new HardwareClass(this);
        hardwareClass.init(true);
        driveMethods = new DriveMethods(this, hardwareClass, hardwareClass);
        dumper = new Dumper(hardwareClass.glyphDumper);
        dumper.init(false);

        /*vuforia = new MasterVuforia(this);
        vuforia.init(VuforiaLocalizer.CameraDirection.FRONT);*/

        waitForStart();
        //vuforia.activate();
        //vuMark = vuforia.vuMarks.getVuMark(5);
        //vuforia.deactivate();
        hardwareClass.resetGyroZ();

        //Jewels

        new Jewel(hardwareClass, this).doJewel(Alliance.BLUE);

        // drive off ramp
        driveMethods.driveForward(0.5, 2500, 0, 0.02, DEGREES);
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
        hardwareClass.resetGyroZ();

        //drive to column

        {
            if(vuMark == UNKNOWN) //Lane approves
                vuMark = CENTER;
            int distance = 0;
            switch (vuMark){
                case LEFT:
                    distance = 3000;
                    break;
                case CENTER:
                    distance = 1600;
                    break;
                case RIGHT:
                    distance = 1000;
                    break;
            }
            driveMethods.driveForward(0.5, distance, 0, 0.02, DEGREES);
            sleep(500);
        }


        hardwareClass.resetGyroZ();// temp testing
        driveMethods.turnFowardRight(0.3, -92, DEGREES);
        sleep(400);
        driveMethods.turnBackwardLeft(0.15, -92, DEGREES);
        sleep(200);
        /*driveMethods.turnRight(0.3, -92, DEGREES);
        sleep(400);
        driveMethods.turnLeft(0.15, -92, DEGREES);
        sleep(200);/

        //dump
        /*hardwareClass.glyphLift.setTargetPosition(LiftPositions.AUTODUMP.position);
        hardwareClass.glyphLift.setPower(1.0);
        while(hardwareClass.glyphLift.isBusy() && opModeIsActive());*/

        dumper.setDumping(true);
        sleep(2500);
        hardwareClass.powerLeft(-0.3);
        hardwareClass.powerRight(-0.3);
        sleep(3000);
        hardwareClass.powerLeft(0.0);
        hardwareClass.powerRight(0.0);
        sleep(1000);
        hardwareClass.powerLeft(0.8);
        hardwareClass.powerRight(0.8);
        sleep(200);
        hardwareClass.powerLeft(0.0);
        hardwareClass.powerRight(0.0);

        dumper.setDumping(false);
        hardwareClass.powerIntake(1.0);
        while(opModeIsActive());
    }
}
