package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.relicRecovery.main.autonomouses.RobotV2.Vision.vuforia.MasterVuforia;

/**
 * Created by user on 1/22/2018.
 */

public class VuforiaDistanceExample extends LinearOpMode{
    MasterVuforia vuforia;
    RelicRecoveryVuMark column;
    @Override
    public void runOpMode() {

        // vuforia setup
        vuforia = new MasterVuforia(this);// instantiation
        vuforia.init(VuforiaLocalizer.CameraDirection.FRONT);// initialization and camera binding happens here.
        vuforia.activate();// activate tracking


        waitForStart();

        // store the VuMark in a variable
        column = vuforia.vuMarks.getVuMark(5);

        // clean up
        vuforia.deactivate();// stop tracking
        vuforia.close();// release the camera processor
    }
}