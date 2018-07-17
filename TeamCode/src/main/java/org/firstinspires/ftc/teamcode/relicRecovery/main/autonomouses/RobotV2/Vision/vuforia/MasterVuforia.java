package org.firstinspires.ftc.teamcode.relicRecovery.main.autonomouses.RobotV2.Vision.vuforia;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.otherTeams.EnderBots.ClosableVuforiaLocalizer;

/**
 * Created by recharged on 12/9/17.
 */

public class MasterVuforia {
    private LinearOpMode linearOpMode;
    public VuMarks vuMarks;
    public VuforiaLocalizer.Parameters parameters;
    final String LICENSE_KEY = "AXi/CxP/////AAAAGV4xMjmD2EwntmuvBtxZnj8AOji5oAG2lxjzOJIGA9IASLd1EtX7KzZ6BpH6J0FWgEcjd8O/6mWD1rvLoAZ1R3KJcxH/xss+scSbd/U8d7/cZDupryfSH7lbRv94ZmPPwduAaQOkxyZfX0Gv+IsMUtIGqTZ5WIHYpqRSHIsGQQ6nlslCi5x/NRu0tnV1t6YgX6svoenYGXpbktnCYZB5BwO7OTfw7XrMMWtqSCJrd3PZha8rgiN1VvqvdEok//H0d9Vh5pnAMa8XwMEXx0N/0V1uEGUEcQvQA+fK7zghPqxjiXBQoZxcUUGkSbNGaIfTPBEoNoOi8QzHo4N6QN1TrgLnJW9J6tgbz9xzTpnRahqU";

    public static final String TAG = "Vuforia VuMark Sample";
    OpenGLMatrix lastLocation = null;
    ClosableVuforiaLocalizer vuforia;
    VuforiaTrackable relicTemplate;
    VuforiaTrackables relicTrackables;
    MasterVuforia masterVuforia;

    public void close(){
        vuMarks.close();
        vuforia.close();
    }

    public MasterVuforia(LinearOpMode linearOpMode) {
        this.linearOpMode = linearOpMode;
        vuMarks = new VuMarks(linearOpMode, LICENSE_KEY, this);
    }

    public void init(VuforiaLocalizer.CameraDirection vuMarkCameraDirection){

        int cameraMonitorViewId = linearOpMode.hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", linearOpMode.hardwareMap.appContext.getPackageName());
        parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = LICENSE_KEY;
        parameters.cameraDirection = vuMarkCameraDirection;
        parameters.useExtendedTracking = true;

        this.vuforia = new ClosableVuforiaLocalizer(parameters);

        relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");

        relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary

        vuMarks.init(vuMarkCameraDirection);
    }

    public void activate(){
        vuMarks.activate();
    }

    public void deactivate(){
        vuMarks.deactivate();
    }

    @Deprecated
    public MasterVuforia(){}
}
