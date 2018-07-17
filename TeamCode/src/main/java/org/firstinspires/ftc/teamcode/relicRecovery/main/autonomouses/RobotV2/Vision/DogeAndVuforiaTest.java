package org.firstinspires.ftc.teamcode.relicRecovery.main.autonomouses.RobotV2.Vision;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.detectors.JewelDetector;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.relicRecovery.api.BooleanToggle;
import org.firstinspires.ftc.teamcode.relicRecovery.main.autonomouses.RobotV2.Vision.vuforia.MasterVuforia;

/**
 * Created by recharged on 12/21/17.
 */

@TeleOp(name = "Doge And Vuforia Test")
public class DogeAndVuforiaTest extends LinearOpMode{
    MasterVuforia vuforia;
    RelicRecoveryVuMark vuMark;

    JewelDetector jewelDetector;

    BooleanToggle rotateMat_DogeCV = new BooleanToggle();
    BooleanToggle backCamera_DogeCV = new BooleanToggle();
    boolean backCamera_DogeCV_lastState;

    @Override
    public void runOpMode(){
        rotateMat_DogeCV.setValue(false);
        backCamera_DogeCV.setValue(false);
        backCamera_DogeCV_lastState = backCamera_DogeCV.getValue();

        vuforia = new MasterVuforia(this);
        vuforia.init(VuforiaLocalizer.CameraDirection.FRONT);

        jewelDetector = new JewelDetector();
        jewelDetector.init(hardwareMap.appContext, CameraViewDisplay.getInstance(), backCamera_DogeCV.getValue() ? 0 : 1);
        //Jewel Detector Settings
        jewelDetector.areaWeight = 0.02;
        jewelDetector.detectionMode = JewelDetector.JewelDetectionMode.MAX_AREA; // PERFECT_AREA
        //jewelDetector.perfectArea = 6500; <- Needed for PERFECT_AREA
        jewelDetector.debugContours = true;
        jewelDetector.maxDiffrence = 15;
        jewelDetector.ratioWeight = 15;
        jewelDetector.minArea = 700;
        jewelDetector.rotateMat = rotateMat_DogeCV.getValue();

        waitForStart();

        vuforia.activate();
        vuMark = vuforia.vuMarks.getVuMark(5);
        vuforia.deactivate();
        vuforia.close();

        jewelDetector.enable();
        while(opModeIsActive()){
            rotateMat_DogeCV.update(gamepad1.a);
            jewelDetector.rotateMat = rotateMat_DogeCV.getValue();

            backCamera_DogeCV.update(gamepad1.b);
            jewelDetector.getCameraView().setCameraIndex(backCamera_DogeCV.getValue() ? 0 : 1);
            if(backCamera_DogeCV_lastState != backCamera_DogeCV.getValue()) {
                jewelDetector.enable();
                backCamera_DogeCV_lastState = backCamera_DogeCV.getValue();
            }

            telemetry.addData("Rotated mat: ", rotateMat_DogeCV.getValue());
            telemetry.addData("Camera: ", backCamera_DogeCV.getValue() ? "back" : "front");
            telemetry.addData("VuMark: ", vuMark);
            telemetry.addData("Last order: ", jewelDetector.getLastOrder());
            telemetry.addData("Current order: ", jewelDetector.getCurrentOrder());
            telemetry.update();
        }
        jewelDetector.disable();
    }
}
