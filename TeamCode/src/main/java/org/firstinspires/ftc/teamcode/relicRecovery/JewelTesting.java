
package org.firstinspires.ftc.teamcode.relicRecovery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.detectors.*;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.relicRecovery.api.BooleanToggle;

import java.io.IOException;


@TeleOp(name="JewelTesting")

enum Camera{
    FRONT_CAM(0),
    BACK_CAM(1);

    int index;

    Camera(int index){
        this.index = index;
    }
}
public class JewelTesting extends OpMode
{
    Camera camera = Camera.FRONT_CAM;
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    boolean lastState;
    boolean state;
    BooleanToggle rotateMatToggle = new BooleanToggle(false);
    boolean lastRotateMat = rotateMatToggle.getValue();

    private JewelDetector jewelDetector = null;
    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");


        jewelDetector = new JewelDetector();
        jewelDetector.init(hardwareMap.appContext, CameraViewDisplay.getInstance());

        //Jewel Detector Settings
        jewelDetector.areaWeight = 0.02;
        jewelDetector.detectionMode = JewelDetector.JewelDetectionMode.MAX_AREA; // PERFECT_AREA
        //jewelDetector.perfectArea = 6500; <- Needed for PERFECT_AREA
        jewelDetector.debugContours = true;
        jewelDetector.maxDiffrence = 15;
        jewelDetector.ratioWeight = 15;
        jewelDetector.minArea = 700;

        jewelDetector.getCameraView().setCameraIndex(camera.index);
        jewelDetector.enable();


    }

    @Override
    public void init_loop() {
        telemetry.addData("Status", "Initialized.");
    }

    @Override
    public void start() {
        runtime.reset();
    }

    @Override
    public void loop() {

        state = gamepad1.a;
        if(state && !lastState){
            lastState = true;
            camera = camera == Camera.FRONT_CAM ? Camera.BACK_CAM : Camera.FRONT_CAM;
            jewelDetector.enable();
        }
        if(!state)
            lastState = false;

        telemetry.addData("Status", "Run Time: " + runtime.toString());

        telemetry.addData("Current Order", "Jewel Order: " + jewelDetector.getCurrentOrder().toString()); // Current Result
        telemetry.addData("Last Order", "Jewel Order: " + jewelDetector.getLastOrder().toString()); // Last Known Result
        telemetry.addData("camera: ", camera);
    }

    @Override
    public void stop() {
        jewelDetector.disable();
    }

}
