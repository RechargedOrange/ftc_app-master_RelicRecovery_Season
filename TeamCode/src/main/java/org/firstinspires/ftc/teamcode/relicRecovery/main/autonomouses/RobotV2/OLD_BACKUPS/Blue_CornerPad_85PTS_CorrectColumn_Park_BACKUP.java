package org.firstinspires.ftc.teamcode.relicRecovery.main.autonomouses.RobotV2.OLD_BACKUPS;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.relicRecovery.Alliance;
import org.firstinspires.ftc.teamcode.relicRecovery.main.autonomouses.RobotV2.Jewel;
import org.firstinspires.ftc.teamcode.relicRecovery.main.autonomouses.RobotV2.Vision.vuforia.MasterVuforia;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.DriveMethods;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.HardwareClass;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark.CENTER;
import static org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark.UNKNOWN;

/**
 * Created by recharged on 12/9/17.
 */

@Autonomous(name = "Blue_CornerPad_85PTS_CorrectColumn_Park_BACKUP")
@Disabled
public class Blue_CornerPad_85PTS_CorrectColumn_Park_BACKUP extends LinearOpMode {
    HardwareClass hardwareClass;
    MasterVuforia vuforia;
    RelicRecoveryVuMark vuMark = UNKNOWN;
    DriveMethods driveMethods;
    ElapsedTime timer = new ElapsedTime();
    @Override
    public void runOpMode() throws InterruptedException {
        hardwareClass = new HardwareClass(this);
        hardwareClass.init(true);
        driveMethods = new DriveMethods(this, hardwareClass, hardwareClass);

        vuforia = new MasterVuforia(this);
        vuforia.init(VuforiaLocalizer.CameraDirection.FRONT);

        vuforia.activate();
        waitForStart();

        //if(opModeIsActive())
        //    hardwareClass.updatingManager.start();

        vuMark = vuforia.vuMarks.getVuMark(5);
        vuforia.deactivate();
        hardwareClass.resetGyroZ();

        telemetry.addData("VuMark", vuMark);
        telemetry.update();

        //Jewels
        hardwareClass.powerIntake(1.0);
        new Jewel(hardwareClass, this).doJewel(Alliance.BLUE);
        hardwareClass.powerIntake(0.0);

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
            while (opModeIsActive() && timer.milliseconds() < 2500) {
                double powerOffSet = (hardwareClass.getGyroZ(DEGREES) - targetZ) * correction;
                hardwareClass.powerLeft(power + powerOffSet);
                hardwareClass.powerRight(power - powerOffSet);
            }
            hardwareClass.powerLeft(0.0);
            hardwareClass.powerRight(0.0);
        }
        sleep(1000);
        hardwareClass.resetGyroZ();
        double zBase = hardwareClass.getGyroAngles(AxesOrder.ZXY, DEGREES).firstAngle;

        if(vuMark == CENTER)

        //drive to column

        {
            if (vuMark == UNKNOWN) //Lane approves
                vuMark = CENTER;
            int distance = 0;
            switch (vuMark) {
                case LEFT:
                    distance = 1600;
                    break;
                case CENTER:
                    distance = 2600;
                    break;
                case RIGHT:
                    distance = 3500;
                    break;
            }
            driveMethods.driveForward(0.5, distance, 0, 0.02, DEGREES);
            sleep(500);
        }


        {
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
        }

        // hardwareClass.resetGyroZ();// temp testing
        /*driveMethods.turnRight(0.3, -92, DEGREES);
        sleep(400);
        driveMethods.turnLeft(0.15, -92, DEGREES);
        sleep(200);/

        //dump
        /*hardwareClass.glyphLift.setTargetPosition(LiftPositions.AUTODUMP.position);
        hardwareClass.glyphLift.setPower(1.0);
        while(hardwareClass.glyphLift.isBusy() && opModeIsActive());*/

        hardwareClass.dumper.setDumping(true);
        timer.reset();
        while(timer.milliseconds() < 2500 && opModeIsActive())
            hardwareClass.dumper.update();
        hardwareClass.glyphDumper.setPower(0.0);
        //sleep(2500);


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

        //hardwareClass.updatingManager.kill();
    }
}
