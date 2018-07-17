package org.firstinspires.ftc.teamcode.relicRecovery.main.autonomouses.RobotV2.nSRMultiGlyphs;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.relicRecovery.main.autonomouses.RobotV2.Vision.vuforia.MasterVuforia;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.DriveMethodsHolinomic;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.HardwareClass;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.ServoGateState;

import static com.qualcomm.robotcore.util.Range.clip;
import static java.lang.Math.abs;
import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark.CENTER;
import static org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark.LEFT;
import static org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark.UNKNOWN;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.FRONT;

/**
 * Created by user on 2/15/2018.
 */
@Autonomous(name = "NSRTwoGlyphsBlue")
public class NSRMultiGlyphsBlue extends LinearOpMode {
    volatile HardwareClass hardwareClass;
    DriveMethodsHolinomic driveMethods;
    ElapsedTime timer = new ElapsedTime();
    MasterVuforia vuforia;
    RelicRecoveryVuMark correctColumn = UNKNOWN;

    @Override
    public void runOpMode() {
        hardwareClass = new HardwareClass(this);
        hardwareClass.init(true);

        driveMethods = new DriveMethodsHolinomic(this, hardwareClass, hardwareClass);

        vuforia = new MasterVuforia(this);
        vuforia.init(FRONT);
        vuforia.activate();

        waitForStart();

        hardwareClass.leftFlexModule.retract();
        hardwareClass.rightFlexModule.retract();

        correctColumn = vuforia.vuMarks.getVuMark(5);
        vuforia.deactivate();
        vuforia.close();
        telemetry.addData("VuMark", correctColumn);
        telemetry.update();
        if (correctColumn == UNKNOWN)
            correctColumn = CENTER;

        //new Jewel(hardwareClass, this).doJewel(RED);

        driveOffBoard();

        pickupGlyph();

        goTowardsWall();

        flexDump(correctColumn, false);

        /*secondPickupGlyph();

        goTowardsWall();

        flexDump(correctColumn == LEFT ? CENTER : LEFT, false);*/
    }

    private void driveOffBoard() {
        driveMethods.driveRight(0.8, 2000, 0, 0.04, DEGREES);
    }

    private void pickupGlyph() {

        driveMethods.turnLeft(0.8, 190, DEGREES);
        sleep(200);

        hardwareClass.powerIntake(1.0);

        hardwareClass.resetDriveEncoders();

        timer.reset();
        ElapsedTime timeBroken = new ElapsedTime();
        while (opModeIsActive() && timeBroken.milliseconds() < 100) {
            driveMethods.updateGyroStraight(0.4, 0, 205, 0.03, DEGREES);
            if (hardwareClass.intakeBeamBreak.beamComplete())
                timeBroken.reset();
        }
        hardwareClass.powerHolonomicDrive(0.0, 0.0, 0.0);

        int distance = 500;
        driveMethods.driveBackward(0.8, distance, 205, 0.02, DEGREES);
    }

    private void secondPickupGlyph() {

        hardwareClass.setServoGateState(ServoGateState.CLOSED);

        hardwareClass.powerIntake(0.0);

        driveMethods.driveForward(0.8, 3000, 205, 0.03, DEGREES);

        hardwareClass.powerIntake(1.0);

        timer.reset();
        ElapsedTime timeBroken = new ElapsedTime();
        while (opModeIsActive() && timeBroken.milliseconds() < 100) {
            driveMethods.updateGyroStraight(0.3, 0, 205, 0.03, DEGREES);
            if (hardwareClass.intakeBeamBreak.beamComplete())
                timeBroken.reset();
        }
        hardwareClass.powerHolonomicDrive(0.0, 0.0, 0.0);

        int distance = 500;
        driveMethods.driveBackward(0.8, distance, 205, 0.02, DEGREES);

        timer.reset();
        timeBroken.reset();
        while (opModeIsActive() && timeBroken.milliseconds() < 100) {
            driveMethods.updateGyroStraight(0.3, 0, 205, 0.03, DEGREES);
            if (hardwareClass.intakeBeamBreak.beamComplete())
                timeBroken.reset();
        }
        hardwareClass.powerHolonomicDrive(0.0, 0.0, 0.0);

        distance = 500;
        driveMethods.driveBackward(0.8, distance, 205, 0.02, DEGREES);
    }

    private void goTowardsWall() {
        hardwareClass.leftFlexModule.deploy();
        driveMethods.turnRight(0.3, 185, DEGREES);
        /*while(opModeIsActive() && abs(hardwareClass.getGyroZ(DEGREES)) > 5) {
            driveMethods.updateGyroStraight(0.0, 0.0, 0.0, 0.04, DEGREES);
            telemetry.addData("Z", hardwareClass.getGyroZ(DEGREES));
            telemetry.update();
        }
        telemetry.update();
        hardwareClass.powerHolonomicDrive(0.0, 0.0, 0.0);*/

        //hardwareClass.distanceFromWall(0,DEGREES);
        hardwareClass.sonar.getDistance();
        sleep(200);

        //hardwareClass.distanceFromWall(0,DEGREES);
        telemetry.addLine("beforeLoop");
        telemetry.update();
        int i = 0;
        hardwareClass.sonar.getDistance();
        while (opModeIsActive() && hardwareClass.distanceFromWall(180, DEGREES) > 40) {
            telemetry.addLine("loop" + i++);
            telemetry.update();
            driveMethods.updateGyroStraight(hardwareClass.distanceFromWall(180, DEGREES) > 60 ? -0.5 : -0.3, 0, 180, 0.03, DEGREES);
        }
        hardwareClass.powerHolonomicDrive(0.3, 0.0, 0.0);
        sleep(100);
        hardwareClass.powerHolonomicDrive(0.0, 0.0, 0.0);

        hardwareClass.powerIntake(0.0);

        sleep(300);

        /*while (hardwareClass.sonar.getDistance() < 30 && opModeIsActive()) {
            driveMethods.updateGyroStraight(0.8, 0, 0, 0.03, DEGREES);
        }
        hardwareClass.powerHolonomicDrive(0.0, 0.0, 0.0);*/

        telemetry.addLine("afterLoop");
        telemetry.update();
    }

    private void flexDump(RelicRecoveryVuMark column, boolean raised) {
        hardwareClass.powerIntake(0.0);

        hardwareClass.leftFlexModule.deploy();
        hardwareClass.leftFlexModule.flexSensor.recalibrate();
        hardwareClass.setServoGateState(ServoGateState.OPEN);

        if (raised)
            hardwareClass.powerLift(1.0);

        int distance = 0;
        switch (column) {
            case LEFT:
                distance = 400;
                break;
            case CENTER:
                distance = 0;
                break;
            case RIGHT:
                distance = -200;
                break;
        }

        while (opModeIsActive() && hardwareClass.leftFlexModule.flexSensor.getFlexVoltage() < 0.007) {
            dumpWallFollow();
            telemetry.addData("Flex", hardwareClass.leftFlexModule.flexSensor.getFlexVoltage());
            telemetry.addData("Sonar", hardwareClass.sonar.getDistance());
            telemetry.update();
        }
        hardwareClass.leftFlexModule.retract();
        //hardwareClass.powerHolonomicDrive(0.0, 0.0, 0.0);
        //driveMethods.driveLeft(0.5, distance, 0, 0.03, DEGREES);
        hardwareClass.resetDriveEncoders();
        if (distance < 0) {
            hardwareClass.powerHolonomicDrive(0.0, 0.0, 0.0);
            sleep(200);
            driveMethods.driveLeft(0.3, -distance, 180, 0.005, DEGREES);
        } else
            while (opModeIsActive() && hardwareClass.getBackLeftEncoder() - hardwareClass.getFrontLeftEncoder() > -distance)
                dumpWallFollow();
        hardwareClass.powerHolonomicDrive(0.0, 0.0, 0.0);
        if (raised) {
            hardwareClass.powerLift(1.0);
            while (hardwareClass.upLimitSwitch.isFar()) ;
            hardwareClass.powerLift(0.0);
        } else {
            sleep(200);
        }
        hardwareClass.dumper.setDumping(true);
        driveMethods.driveForward(0.3, 600, 180, 0.02, DEGREES);
        timer.reset();
        while (opModeIsActive() && !hardwareClass.bucketUpByTouch.isTrue() && timer.milliseconds() < 2000) {
        }
        sleep(500);
        hardwareClass.powerLeft(-0.3);
        hardwareClass.powerRight(-0.3);
        sleep(1000);
        hardwareClass.dumper.setDumping(false);
        driveMethods.turnFowardLeft(0.8, 210, DEGREES);
        hardwareClass.setServoGateState(ServoGateState.CLOSED);
    }

    private void dumpWallFollow() {
        double sonar = hardwareClass.distanceFromWall(180, DEGREES);

        double offTarget = 22 - sonar;

        double forward = clip(offTarget * 0.025, -0.2, 0.2);
        if (abs(offTarget) <= 1)
            forward = 0.0;
        double strafeLeft = abs(offTarget) >= 3 ? 0.0 : 0.03 / offTarget == 0 ? 1 : abs(offTarget);
        strafeLeft *= -1;
        double gyro = abs(hardwareClass.getGyroZ(DEGREES)) - 180;

        /*double gyroDivision = clip(gyro / 10, 0, 1);
        forward -= forward * gyroDivision;
        strafeLeft += strafeLeft * gyroDivision;*/
        if (gyro >= 7) {
            forward = 0.0;
            strafeLeft = 0.0;
        }
        double correctionPerDegree = 0.005;
        if(gyro < 10 && gyro > 5){
            correctionPerDegree = 0.01;
        }


        driveMethods.updateGyroStraight(forward, strafeLeft, 180, 0.005, DEGREES);
    }

}