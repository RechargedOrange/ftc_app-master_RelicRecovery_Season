package org.firstinspires.ftc.teamcode.relicRecovery.main.autonomouses.RobotV2;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.relicRecovery.main.autonomouses.RobotV2.Vision.vuforia.MasterVuforia;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.DriveMethods;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.Dumper;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.HardwareClass;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.LiftPositions;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark.CENTER;
import static org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark.LEFT;
import static org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark.UNKNOWN;

/**
 * Created by recharged on 12/9/17.
 */
@Autonomous(name = "Red_SidePad_55PTS_CorrectColumn_Park")
//@Disabled
public class Red_SidePad_55PTS_CorrectColumn_Park extends LinearOpMode{
    HardwareClass hardwareClass;
    MasterVuforia vuforia;
    RelicRecoveryVuMark vuMark = LEFT;
    DriveMethods driveMethods;
    Dumper dumper;

    @Override
    public void runOpMode(){
        hardwareClass = new HardwareClass(this);
        hardwareClass.init(true);

        dumper = new Dumper(hardwareClass.glyphDumper);
        driveMethods = new DriveMethods(this, hardwareClass, hardwareClass);

        //vuforia = new MasterVuforia(this);
        //vuforia.init(VuforiaLocalizer.CameraDirection.FRONT);

        waitForStart();
        hardwareClass.resetGyroZ();

        //vuforia.activate();
        //vuMark = vuforia.vuMarks.getVuMark(5);
        //vuforia.deactivate();

        telemetry.addData("VuMark", vuMark);

        /**
         * Jewel
         */

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
                    distance = 4500;
                    break;
                case RIGHT:
                    distance = 3500;
                    break;
            }
            driveMethods.driveBackward(0.5, distance, 0, 0.02, DEGREES);
            sleep(100);
        }
        driveMethods.turnRight(0.3, -92, DEGREES);
        sleep(400);
        driveMethods.turnLeft(0.10, -93, DEGREES);
        sleep(500);

        /**
         * Dump
         */
        /*hardwareClass.glyphLift.setTargetPosition(LiftPositions.AUTODUMP.position);
        hardwareClass.glyphLift.setPower(1.0);
        while(hardwareClass.glyphLift.isBusy() && opModeIsActive());*/

        dumper.setDumping(true);
        sleep(2000);
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
    }
}
