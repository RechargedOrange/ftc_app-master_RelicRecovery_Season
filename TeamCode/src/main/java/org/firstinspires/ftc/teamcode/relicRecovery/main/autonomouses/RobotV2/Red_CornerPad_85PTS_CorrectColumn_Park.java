package org.firstinspires.ftc.teamcode.relicRecovery.main.autonomouses.RobotV2;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.relicRecovery.Alliance;
import org.firstinspires.ftc.teamcode.relicRecovery.main.autonomouses.RobotV2.Vision.vuforia.MasterVuforia;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.DriveMethods;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.Dumper;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.HardwareClass;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.LiftPositions;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.ServoGateState;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark.CENTER;
import static org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark.LEFT;
import static org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark.RIGHT;
import static org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark.UNKNOWN;

/**
 * Created by recharged on 12/9/17.
 */

@Autonomous(name = "Red_CornerPad_85PTS_CorrectColumn_Park")
//@Disabled
public class Red_CornerPad_85PTS_CorrectColumn_Park extends LinearOpMode{
    HardwareClass hardwareClass;
    MasterVuforia vuforia;
    RelicRecoveryVuMark vuMark = RIGHT;
    DriveMethods driveMethods;
    ElapsedTime timer = new ElapsedTime();
    ElapsedTime runtime = new ElapsedTime();
    //Dumper dumper;

    @Override
    public void runOpMode() throws InterruptedException{
        hardwareClass = new HardwareClass(this);
        hardwareClass.init(true);
        hardwareClass.driveRunMode = DcMotor.RunMode.RUN_USING_ENCODER;
        driveMethods = new DriveMethods(this, hardwareClass, hardwareClass);
        //dumper = new Dumper(hardwareClass.glyphDumper);
        //dumper.init(false);

        vuforia = new MasterVuforia(this);
        vuforia.init(VuforiaLocalizer.CameraDirection.FRONT);

        vuforia.activate();
        waitForStart();
        runtime.reset();
        vuMark = vuforia.vuMarks.getVuMark(5);
        vuforia.deactivate();
        hardwareClass.resetGyroZ();

        telemetry.addData("VuMark", vuMark);
        telemetry.update();

        //Jewels
        hardwareClass.setServoGateState(ServoGateState.OPEN);
        hardwareClass.powerIntake(1.0);
        new Jewel(hardwareClass, this).doJewel(Alliance.RED);
        hardwareClass.powerIntake(0.0);

        // drive off ramp
        driveMethods.driveBackward(0.5, 2600, 0, 0.02, DEGREES);
        sleep(200);
        //bang into wall
        driveMethods.turnRight(0.5, -94, DEGREES);
        sleep(200);
        driveMethods.turnLeft(0.15, -96, DEGREES);
        sleep(200);
        {
            double power = -0.5;
            double correction = 0.02;
            int targetZ = -90;
            timer.reset();
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
                    distance = 3700;// 3000 at beggining of Super-Quals//2800
                    break;
                case CENTER:
                    distance = 3100;// 2100 at beggining of Super-Quals //1900 - 1800 was tested Feburary 2nd for 55 points
                    break;
                case RIGHT:
                    distance = 2450;// 1200 at beggining of Super-Quals //800
                    break;
            }
            driveMethods.driveForward(0.5, distance, 0, 0.02, DEGREES);
            sleep(500);
        }
        driveMethods.turnLeft(0.3, 90, DEGREES);
        sleep(400);
        driveMethods.turnRight(0.15, 93, DEGREES);
        sleep(200);

        //driveMethods.driveBackward(0.3 , 400, 90, 0.0, DEGREES);
        sleep(200);
        //dump
        /*hardwareClass.glyphLift.setTargetPosition(LiftPositions.AUTODUMP.position);
        hardwareClass.glyphLift.setPower(1.0);
        while(hardwareClass.glyphLift.isBusy() && opModeIsActive());*/

        /*dumper.setDumping(true);
        sleep(2500);*/

        hardwareClass.dumper.setDumping(true);
        timer.reset();
        while (opModeIsActive() && !hardwareClass.bucketUpByTouch.isTrue() && timer.milliseconds() < 2000) {
        }
        driveMethods.driveForward(0.3, 200, 0.0, 0.0, DEGREES);
        sleep(1000);
        hardwareClass.powerLeft(-0.4);
        hardwareClass.powerRight(-0.4);
        sleep(2000);
        hardwareClass.powerDrive(0.0, 0.0);
        sleep(500);

        timer.reset();
        while(opModeIsActive() && runtime.milliseconds() < 2700 && timer.milliseconds() < 4000);
        hardwareClass.powerDrive(0.0, 0.0);
        sleep(500);
        driveMethods.driveForward(0.5, 500, 0, 0.0, DEGREES);

        hardwareClass.dumper.setDumping(false);

        while (opModeIsActive() && hardwareClass.glyphDumperDownTouch.isReleased());

        /*hardwareClass.dumper.setPartlyUp(true);
        sleep(1500);
        hardwareClass.dumper.setDumping(true);
        timer.reset();
        sleep(1500);
        hardwareClass.glyphDumper.setPower(0.0);

        hardwareClass.powerLeft(-0.3);
        hardwareClass.powerRight(-0.3);
        timer.reset();
        sleep(1000);
        hardwareClass.powerLeft(0.0);
        hardwareClass.powerRight(0.0);
        timer.reset();
        sleep(1000);
        hardwareClass.powerLeft(0.3);
        hardwareClass.powerRight(0.3);
        timer.reset();
        sleep(500); // was 200
        hardwareClass.powerLeft(0.0);
        hardwareClass.powerRight(0.0);

        hardwareClass.dumper.setPartlyUp(false);
        hardwareClass.dumper.setDumping(false);
        timer.reset();
        while((!hardwareClass.bucketDownByTouch.isTrue()) && opModeIsActive());
        hardwareClass.glyphDumper.setPower(0.0);*/

        hardwareClass.updatingManager.kill();
        //dumper.setDumping(false);
    }
}