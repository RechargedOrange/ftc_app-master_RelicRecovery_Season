package org.firstinspires.ftc.teamcode.relicRecovery.main.autonomouses.RobotV2.nSRMultiGlyphs;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.relicRecovery.api.Updatable;
import org.firstinspires.ftc.teamcode.relicRecovery.main.autonomouses.RobotV2.Jewel;
import org.firstinspires.ftc.teamcode.relicRecovery.main.autonomouses.RobotV2.Vision.vuforia.MasterVuforia;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.DriveMethods;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.DriveMethodsHolinomic;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.HardwareClass;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.ServoGateState;

import static com.qualcomm.robotcore.util.Range.clip;
import static java.lang.Math.abs;
import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.RADIANS;
import static org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark.CENTER;
import static org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark.LEFT;
import static org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark.UNKNOWN;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.FRONT;
import static org.firstinspires.ftc.teamcode.relicRecovery.Alliance.RED;

/**
 * Created by user on 2/15/2018.
 */
@Autonomous(name = "NSRTwoGlyphsRed")
public class NSRMultiGlyphsRed extends LinearOpMode {
    volatile HardwareClass hardwareClass;
    DriveMethodsHolinomic driveMethods;
    ElapsedTime timer = new ElapsedTime();
    MasterVuforia vuforia;
    RelicRecoveryVuMark correctColumn = LEFT;

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

        //correctColumn = vuforia.vuMarks.getVuMark(5);
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

        secondPickupGlyph();

        goTowardsWall();

        flexDump(correctColumn == LEFT ? CENTER : LEFT, false);
        //flexDump(LEFT, true/*correctColumn == LEFT*/);
    }

    private void driveOffBoard() {
        driveMethods.driveRight(0.8, 3000, 0, 0.04, DEGREES);
    }

    private void pickupGlyph() {

        driveMethods.turnRight(0.2, -25, DEGREES);
        sleep(200);

        hardwareClass.powerIntake(1.0);

        hardwareClass.resetDriveEncoders();

        timer.reset();
        ElapsedTime timeBroken = new ElapsedTime();
        while (opModeIsActive() && timeBroken.milliseconds() < 100) {
            driveMethods.updateGyroStraight(0.4, 0, -25, 0.03, DEGREES);
            if (hardwareClass.intakeBeamBreak.beamComplete())
                timeBroken.reset();
        }
        hardwareClass.powerHolonomicDrive(0.0, 0.0, 0.0);

        int distance = 500;
        driveMethods.driveBackward(0.8, distance, -25, 0.02, DEGREES);
    }

    private void secondPickupGlyph() {

        hardwareClass.setServoGateState(ServoGateState.CLOSED);

        hardwareClass.powerIntake(0.0);

        driveMethods.driveForward(0.8, 3000, -25, 0.03, DEGREES);

        hardwareClass.powerIntake(1.0);

        timer.reset();
        ElapsedTime timeBroken = new ElapsedTime();
        while (opModeIsActive() && timeBroken.milliseconds() < 300) {
            driveMethods.updateGyroStraight(0.3, 0, -25, 0.03, DEGREES);
            if (hardwareClass.intakeBeamBreak.beamComplete())
                timeBroken.reset();
        }
        hardwareClass.powerHolonomicDrive(0.0, 0.0, 0.0);

        int distance = 500;
        driveMethods.driveBackward(0.8, distance, -25, 0.02, DEGREES);

        timer.reset();
        timeBroken.reset();
        while (opModeIsActive() && timeBroken.milliseconds() < 300) {
            driveMethods.updateGyroStraight(0.3, 0, -25, 0.03, DEGREES);
            if (hardwareClass.intakeBeamBreak.beamComplete())
                timeBroken.reset();
        }
        hardwareClass.powerHolonomicDrive(0.0, 0.0, 0.0);

        distance = 500;
        driveMethods.driveBackward(0.8, distance, -25, 0.02, DEGREES);
    }

    private void goTowardsWall() {
        hardwareClass.rightFlexModule.deploy();
        driveMethods.turnLeft(0.2, -5, DEGREES);
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
        while (opModeIsActive() && hardwareClass.distanceFromWall(0, DEGREES) > 40) {
            telemetry.addLine("loop" + i++);
            telemetry.update();
            driveMethods.updateGyroStraight(hardwareClass.distanceFromWall(0, DEGREES) > 60 ? -0.5 : -0.3, 0, 0, 0.03, DEGREES);
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

        hardwareClass.rightFlexModule.deploy();
        hardwareClass.rightFlexModule.flexSensor.recalibrate();
        hardwareClass.setServoGateState(ServoGateState.OPEN);

        if (raised)
            hardwareClass.powerLift(1.0);

        int distance = 0;
        switch (column) {
            case LEFT:
                distance = -400;
                break;
            case CENTER:
                distance = 400;
                break;
            case RIGHT:
                distance = 800;
                break;
        }

        while (opModeIsActive() && hardwareClass.rightFlexModule.flexSensor.getFlexVoltage() < 0.003) {
            dumpWallFollow();
            telemetry.addData("Flex", hardwareClass.rightFlexModule.flexSensor.getFlexVoltage());
            telemetry.addData("Sonar", hardwareClass.sonar.getDistance());
            telemetry.update();
        }
        hardwareClass.rightFlexModule.retract();
        //hardwareClass.powerHolonomicDrive(0.0, 0.0, 0.0);
        //driveMethods.driveLeft(0.5, distance, 0, 0.03, DEGREES);
        hardwareClass.resetDriveEncoders();
        if (distance < 0) {
            hardwareClass.powerHolonomicDrive(0.0, 0.0, 0.0);
            sleep(200);
            driveMethods.driveRight(0.3, -distance, 0, 0.005, DEGREES);
        } else
            while (opModeIsActive() && hardwareClass.getBackLeftEncoder() - hardwareClass.getFrontLeftEncoder() < distance)
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
        driveMethods.driveForward(0.3, 600, 0, 0.02, DEGREES);
        timer.reset();
        while (opModeIsActive() && !hardwareClass.bucketUpByTouch.isTrue() && timer.milliseconds() < 2000) {
        }
        hardwareClass.powerLeft(-0.3);
        hardwareClass.powerRight(-0.3);
        sleep(1000);
        hardwareClass.dumper.setDumping(false);
        driveMethods.turnFowardRight(0.8, -30, DEGREES);
        hardwareClass.setServoGateState(ServoGateState.CLOSED);
    }

    private void dumpWallFollow() {
        double sonar = hardwareClass.distanceFromWall(0, DEGREES);

        double offTarget = 22 - sonar;

        double forward = clip(offTarget * 0.025, -0.2, 0.2);
        if (abs(offTarget) <= 1)
            forward = 0.0;
        double strafeLeft = abs(offTarget) >= 3 ? 0.0 : 0.03 / offTarget == 0 ? 1 : abs(offTarget);
        double gyro = abs(hardwareClass.getGyroZ(DEGREES));

        /*double gyroDivision = clip(gyro / 10, 0, 1);
        forward -= forward * gyroDivision;
        strafeLeft += strafeLeft * gyroDivision;*/
        if (gyro >= 7) {
            forward = 0.0;
            strafeLeft = 0.0;
        }

        driveMethods.updateGyroStraight(forward, strafeLeft, 0, 0.005, DEGREES);
    }

}