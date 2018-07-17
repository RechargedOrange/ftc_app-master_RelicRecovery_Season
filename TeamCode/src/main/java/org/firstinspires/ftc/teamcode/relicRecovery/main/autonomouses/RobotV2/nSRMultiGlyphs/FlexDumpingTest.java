package org.firstinspires.ftc.teamcode.relicRecovery.main.autonomouses.RobotV2.nSRMultiGlyphs;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.DriveMethodsHolinomic;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.HardwareClass;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.ServoGateState;

import static com.qualcomm.robotcore.util.Range.clip;
import static java.lang.Math.abs;
import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;

/**
 * Created by user on 3/13/2018.
 */

@TeleOp(name = "FlexDumpingTest", group = "DumpingTest")
public class FlexDumpingTest extends LinearOpMode{
    HardwareClass hardwareClass;
    DriveMethodsHolinomic driveMethods;

    ElapsedTime timer = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException{
        hardwareClass = new HardwareClass(this);

        driveMethods = new DriveMethodsHolinomic(this, hardwareClass, hardwareClass);

        hardwareClass.init(true);

        hardwareClass.leftFlexModule.deploy();
        hardwareClass.leftFlexModule.flexSensor.recalibrate();

        waitForStart();

        while(opModeIsActive()) {
            flexDump(RelicRecoveryVuMark.CENTER, false);
            hardwareClass.leftFlexModule.deploy();
            while(!gamepad1.b && opModeIsActive());
            hardwareClass.leftFlexModule.flexSensor.recalibrate();
        }
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
                distance = 800;
                break;
            case RIGHT:
                distance = 1200;
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

        /*double gyro = abs(hardwareClass.getGyroZ(DEGREES));

        int sonar = hardwareClass.sonar.getDistance();

        double forward = 0;
        double strafeLeft = 0.3;

        double correctionPerDegree = 0.015;

        if(sonar < 25)
            forward = 0.15;
        else if(sonar > 30) {
            forward = -0.1;
            correctionPerDegree = 0.0075;
        }

        if(forward != 0)
            strafeLeft = 0;

        if(gyro >= 10) {
            forward = 0.0;
            strafeLeft = 0.0;
        }

        driveMethods.updateGyroStraight(forward, strafeLeft, 0, correctionPerDegree, DEGREES);*/

        /*int sonar = hardwareClass.sonar.getDistance();*/
        double sonar = hardwareClass.distanceFromWall(0, DEGREES);

        double offTarget = 25 - sonar;

        double forward = clip(offTarget * 0.025, -0.2, 0.2);
        if(abs(offTarget) <= 1)
            forward = 0.0;
        double strafeLeft = abs(offTarget) >= 3 ? 0.0 : 0.03 / offTarget == 0 ? 1 : abs(offTarget);
        double gyro = abs(hardwareClass.getGyroZ(DEGREES));

        /*double gyroDivision = clip(gyro / 10, 0, 1);
        forward -= forward * gyroDivision;
        strafeLeft += strafeLeft * gyroDivision;*/
        if(gyro >= 7){
            forward = 0.0;
            strafeLeft = 0.0;
        }

        driveMethods.updateGyroStraight(forward, strafeLeft, 0, 0.005, DEGREES);
    }
}
