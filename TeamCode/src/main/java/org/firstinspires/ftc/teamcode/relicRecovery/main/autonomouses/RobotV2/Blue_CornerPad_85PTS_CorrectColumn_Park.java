package org.firstinspires.ftc.teamcode.relicRecovery.main.autonomouses.RobotV2;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.relicRecovery.Alliance;
import org.firstinspires.ftc.teamcode.relicRecovery.main.autonomouses.RobotV2.Vision.vuforia.MasterVuforia;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.DriveMethods;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.HardwareClass;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.LiftPositions;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.ServoGateState;

import static com.qualcomm.robotcore.util.Range.clip;
import static java.lang.Math.abs;
import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark.CENTER;
import static org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark.LEFT;
import static org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark.RIGHT;
import static org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark.UNKNOWN;

/**
 * Created by recharged on 12/9/17.
 */

@Autonomous(name = "Blue_CornerPad_85PTS_CorrectColumn_Park")
//@Disabled
public class Blue_CornerPad_85PTS_CorrectColumn_Park extends LinearOpMode {
    HardwareClass hardwareClass;
    MasterVuforia vuforia;
    RelicRecoveryVuMark vuMark = UNKNOWN;
    DriveMethods driveMethods;
    ElapsedTime timer = new ElapsedTime();
    ElapsedTime runtime = new ElapsedTime();
    @Override
    public void runOpMode() throws InterruptedException {
        hardwareClass = new HardwareClass(this);
        hardwareClass.init(true);
        hardwareClass.driveRunMode = DcMotor.RunMode.RUN_USING_ENCODER;
        hardwareClass.setDriveRunMode(DcMotor.RunMode.RUN_USING_ENCODER);
        driveMethods = new DriveMethods(this, hardwareClass, hardwareClass);

        vuforia = new MasterVuforia(this);
        vuforia.init(VuforiaLocalizer.CameraDirection.FRONT);

        vuforia.activate();
        waitForStart();

        runtime.reset();

        //if(opModeIsActive())

        vuMark = vuforia.vuMarks.getVuMark(5);
        vuforia.deactivate();

        if (vuMark == UNKNOWN) //Lane approves
            vuMark = CENTER;

        hardwareClass.resetGyroZ();

        telemetry.addData("VuMark", vuMark);
        telemetry.update();

        //Jewels
        hardwareClass.setServoGateState(ServoGateState.OPEN);
        hardwareClass.powerIntake(1.0);
        new Jewel(hardwareClass, this).doJewel(Alliance.BLUE);
        hardwareClass.powerIntake(0.0);

        // drive off ramp - 2500 // for state KEEP the distance at 33000. Carpet changes things
        driveMethods.driveForward(0.5, 2000, 0, 0.02, DEGREES);
        sleep(200);
        //bang into wall
        driveMethods.turnRight(0.2, -90, DEGREES);
        /*double target = -90;
        while(opModeIsActive() && abs(target - hardwareClass.getGyroZ(DEGREES)) >= 3)
            hardwareClass.powerHolonomicDrive(0, 0, clip((hardwareClass.getGyroZ(DEGREES) - target) * 0.02, -0.2, 0.2));*/
        /*driveMethods.turnRight(0.5, -90, DEGREES);
        sleep(200);
        driveMethods.turnLeft(0.15, -92, DEGREES);*/
        sleep(200);
        {
            double power = -0.5;
            double correction = 0.025;
            int targetZ = -94;
            ElapsedTime timer = new ElapsedTime();
            while (opModeIsActive() && timer.milliseconds() < 2500) {
                double powerOffSet = (hardwareClass.getGyroZ(DEGREES) - targetZ) * correction;
                hardwareClass.powerLeft(power + powerOffSet);
                hardwareClass.powerRight(power - powerOffSet);
            }
            hardwareClass.powerLeft(0.0);
            hardwareClass.powerRight(0.0);
        }
        //hardwareClass.updatingManager.start();
        sleep(1000);
        hardwareClass.resetGyroZ();
        double zBase = hardwareClass.getGyroAngles(AxesOrder.ZXY, DEGREES).firstAngle;

        //drive to column

        {
            int distance = 0;
            switch (vuMark) {
                case LEFT:
                    distance = 2000;//2450 before NSR
                    break;
                case CENTER:
                    distance = 2500;// 3150 before NSR
                    break;
                case RIGHT:
                    distance = 3600;//3850 before NSR
                    break;
            }
            driveMethods.driveForward(0.5, distance, 0, 0.02, DEGREES);
            sleep(500);
        }

        driveMethods.turnRight(0.5, -92, DEGREES);
        sleep(400);
        driveMethods.turnLeft(0.1, -92, DEGREES);

        /*{
            {
                double power = 0.5;
                hardwareClass.powerDrive(power, -power);
                while (opModeIsActive()) {
                    double z = hardwareClass.getGyroAngles(AxesOrder.ZXY, DEGREES).firstAngle;
                    if (z > 0)
                        z = -360 + z;
                    z -= zBase;
                    if (z < -92)
                        break;
                    telemetry.addLine("Going right");
                    telemetry.addData("Z base: ", zBase);
                    telemetry.addData("Z: ", z);
                    telemetry.update();
                }
                hardwareClass.powerDrive(0, 0);
            }
            sleep(400);
            {
                double power = 0.1;
                hardwareClass.powerDrive(-power, power);
                while (opModeIsActive()) {
                    double z = hardwareClass.getGyroAngles(AxesOrder.ZXY, DEGREES).firstAngle;
                    if (z > 0)
                        z = -360 + z;
                    z -= zBase;
                    if (z > -92)
                        break;
                    telemetry.addLine("Going left");
                    telemetry.addData("Z base: ", zBase);
                    telemetry.addData("Z: ", z);
                    telemetry.update();
                }
                hardwareClass.powerDrive(0, 0);
            }
            sleep(200);
        }*/

        // hardwareClass.resetGyroZ();// temp testing
        /*driveMethods.turnRight(0.3, -92, DEGREES);
        sleep(400);
        driveMethods.turnLeft(0.15, -92, DEGREES);
        sleep(200);*/

        //dump
        /*hardwareClass.glyphLift.setTargetPosition(LiftPositions.AUTODUMP.position);
        hardwareClass.glyphLift.setPower(1.0);
        while(hardwareClass.glyphLift.isBusy() && opModeIsActive());*/

        hardwareClass.dumper.setDumping(true);
        timer.reset();
        while (opModeIsActive() && !hardwareClass.bucketUpByTouch.isTrue() && timer.milliseconds() < 2000) {
        }
        driveMethods.driveForward(0.3, 200, 0.0, 0.0, DEGREES);
        sleep(1000);
        hardwareClass.powerLeft(-0.3);
        hardwareClass.powerRight(-0.3);
        sleep(2000);
        hardwareClass.powerDrive(0.0, 0.0);
        sleep(500);

        driveMethods.driveForward(0.3, 500, 0, 0, DEGREES);

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
        sleep(1000); // was 200
        hardwareClass.powerLeft(0.0);
        hardwareClass.powerRight(0.0);

        hardwareClass.dumper.setPartlyUp(false);
        hardwareClass.dumper.setDumping(false);
        timer.reset();
        while((!hardwareClass.bucketDownByTouch.isTrue()) && opModeIsActive());
        hardwareClass.glyphDumper.setPower(0.0);*/
        hardwareClass.updatingManager.kill();
    }
}
