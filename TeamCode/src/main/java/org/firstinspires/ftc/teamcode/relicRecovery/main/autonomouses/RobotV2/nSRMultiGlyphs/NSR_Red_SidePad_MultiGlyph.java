package org.firstinspires.ftc.teamcode.relicRecovery.main.autonomouses.RobotV2.nSRMultiGlyphs;

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
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.DriveMethodsHolinomic;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.HardwareClass;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.ServoGateState;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark.CENTER;
import static org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark.LEFT;
import static org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark.RIGHT;
import static org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark.UNKNOWN;

/**
 * Created by recharged on 12/9/17.
 */
@Autonomous(name = "NSR_Red_SidePad_MultiGlyph", group = "MG")
//@Disabled
public class NSR_Red_SidePad_MultiGlyph extends LinearOpMode{
    HardwareClass hardwareClass;
    MasterVuforia vuforia;
    RelicRecoveryVuMark vuMark = UNKNOWN;
    DriveMethodsHolinomic driveMethods;
    //Dumper dumper;
    ElapsedTime timer = new ElapsedTime();

    @Override
    public void runOpMode(){
        hardwareClass = new HardwareClass(this);
        hardwareClass.init(true);

        //dumper = new Dumper(hardwareClass.glyphDumper);
        driveMethods = new DriveMethodsHolinomic(this, hardwareClass, hardwareClass);

        vuforia = new MasterVuforia(this);
        vuforia.init(VuforiaLocalizer.CameraDirection.FRONT);

        vuforia.activate();
        waitForStart();
        hardwareClass.resetGyroZ();

        vuMark = vuforia.vuMarks.getVuMark(5);
        if (vuMark == UNKNOWN)
             vuMark = CENTER;
        vuforia.deactivate();

        telemetry.addData("VuMark", vuMark);
        telemetry.update();

        /**
         * Jewel
         */

        hardwareClass.powerIntake(1.0);
        //new Jewel(hardwareClass, this).doJewel(Alliance.RED);
        hardwareClass.powerIntake(0.0);

        hardwareClass.setServoGateState(ServoGateState.OPEN);

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
            driveMethods.driveBackward(0.8, distance, 0, 0.03, DEGREES);
            sleep(100);
        }
        driveMethods.turnRight(0.5, -85, DEGREES);

        hardwareClass.dumper.setDumping(true);
        while(opModeIsActive() && !hardwareClass.bucketUpByTouch.isTrue());
        hardwareClass.powerDrive(-0.8, -0.8);
        sleep(1000);
        hardwareClass.powerDrive(0.0, 0.0);
        driveMethods.driveForward(0.5, 800, -90, 0.03, DEGREES);

        hardwareClass.dumper.setDumping(false);

        pickUpTwo();

        hardwareClass.sonar.getDistance();
        hardwareClass.leftFlexModule.deploy();
        sleep(500);
        hardwareClass.leftFlexModule.flexSensor.recalibrate();
        hardwareClass.sonar.getDistance();
        while(hardwareClass.sonar.getDistance() > 30 && opModeIsActive())
            driveMethods.updateGyroStraight(-0.8, 0.0, -90, 0.04, DEGREES);
        hardwareClass.powerHolonomicDrive(0.0, 0.0, 0.0);
        sleep(500);
        timer.reset();
        while(opModeIsActive() && timer.milliseconds() < 2000)
            driveMethods.updateGyroStraight(hardwareClass.sonar.getDistance() < 23 ? 0.3 : -0.1, -0.5, -90, 0.03, DEGREES);

        //flexDump(LEFT, false);
    }

    private void pickUpTwo(){

        driveMethods.driveForward(0.8, 2000, -90, 0.03, DEGREES);

        hardwareClass.powerIntake(1.0);

        ElapsedTime timeBroken = new ElapsedTime();
        while(opModeIsActive() && timeBroken.milliseconds() < 300){
            driveMethods.updateGyroStraight(0.4, 0, -90, 0.03, DEGREES);
            if(hardwareClass.intakeBeamBreak.beamComplete())
                timeBroken.reset();
        }
        hardwareClass.powerHolonomicDrive(0.0, 0.0, 0.0);

        int distance = 700;
        driveMethods.driveBackward(0.8, distance, -90, 0.02, DEGREES);

        timeBroken.reset();
        while(opModeIsActive() && timeBroken.milliseconds() < 300){
            driveMethods.updateGyroStraight(0.2, 0, -90, 0.03, DEGREES);
            if(hardwareClass.intakeBeamBreak.beamComplete())
                timeBroken.reset();
        }
        hardwareClass.powerHolonomicDrive(0.0, 0.0, 0.0);

        distance = 700;
        driveMethods.driveBackward(0.8, distance, -90, 0.02, DEGREES);
    }

    private void flexDump(RelicRecoveryVuMark column, boolean raised){

        hardwareClass.leftFlexModule.deploy();
        hardwareClass.leftFlexModule.flexSensor.recalibrate();
        hardwareClass.setServoGateState(ServoGateState.OPEN);

        int distance = 0;
        switch (column) {
            case LEFT:
                distance = 300;
                break;
            case CENTER:
                break;
            case RIGHT:
                break;
        }

        while(opModeIsActive() && hardwareClass.leftFlexModule.flexSensor.getFlexVoltage() < 0.007){
            driveMethods.updateGyroStraight(hardwareClass.sonar.getDistance() < 23 ? 0.1 : -0.1, hardwareClass.sonar.getDistance() < 26 ? 0.5 : 0.0, -90, 0.03, DEGREES);
            telemetry.addData("Flex", hardwareClass.leftFlexModule.flexSensor.getFlexVoltage());
            telemetry.addData("Sonar", hardwareClass.sonar.getDistance());
            telemetry.update();
        }
        hardwareClass.leftFlexModule.retract();
        //hardwareClass.powerHolonomicDrive(0.0, 0.0, 0.0);
        driveMethods.driveLeft(0.5, distance, -90, 0.03, DEGREES);
        sleep(200);
        hardwareClass.dumper.setDumping(true);
        driveMethods.driveForward(0.3, 600, -90, 0.02, DEGREES);
        timer.reset();
        while (opModeIsActive() && !hardwareClass.bucketUpByTouch.isTrue() && timer.milliseconds() < 2000){
        }
        hardwareClass.powerLeft(-0.3);
        hardwareClass.powerRight(-0.3);
        sleep(1000);
        hardwareClass.dumper.setDumping(false);
        driveMethods.driveForward(0.8, 1000, -90, 0.02, DEGREES);
        hardwareClass.setServoGateState(ServoGateState.CLOSED);
    }
}
