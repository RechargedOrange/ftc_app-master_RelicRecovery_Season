package org.firstinspires.ftc.teamcode.relicRecovery.main.autonomouses.RobotV2.Vision.vuforia;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.teamcode.vectors.Vector3f;

import static org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark.UNKNOWN;

/**
 * Created by recharged on 12/9/17.
 */
public class VuMarks {

    final String LICENSE_KEY;
    LinearOpMode linearOpMode;
    MasterVuforia masterVuforia;

    public void close(){
    }

    public VuMarks(LinearOpMode linearOpMode, String LICENSE_KEY, MasterVuforia masterVuforia) {
        this.linearOpMode = linearOpMode;
        this.LICENSE_KEY = LICENSE_KEY;
        this.masterVuforia = masterVuforia;
    }

    public void init(VuforiaLocalizer.CameraDirection cameraDirection){
    }

    public RelicRecoveryVuMark getVuMark(int readings) {
        //return RelicRecoveryVuMark.from(relicTemplate);
        RelicRecoveryVuMark vuMark = UNKNOWN;
        if(readings <= 0)
            readings = 1;
        for (int i = 0; i < readings; i++) {
            vuMark = RelicRecoveryVuMark.from(masterVuforia.relicTemplate);
            if (vuMark != UNKNOWN)
                break;
            if (!linearOpMode.opModeIsActive())
                break;
            linearOpMode.sleep(100);
        }
        return vuMark;
    }

    public void activate(){
        masterVuforia.relicTrackables.activate();
    }

    public void deactivate(){
        masterVuforia.relicTrackables.deactivate();
    }

    Vector3f getDistance(){
        Vector3f vector = new Vector3f(0, 0, 0);
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(masterVuforia.relicTemplate);
        if (vuMark != RelicRecoveryVuMark.UNKNOWN) {
            OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) masterVuforia.relicTemplate.getListener()).getPose();
            if (pose != null) {
                VectorF trans = pose.getTranslation();
                vector.x = trans.get(0);
                vector.y = trans.get(1);
                vector.z = trans.get(2);
            }
        }
        return vector;
    }

    Vector3f getAngle(){
        Vector3f vector = new Vector3f(0, 0, 0);
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(masterVuforia.relicTemplate);
        if (vuMark != RelicRecoveryVuMark.UNKNOWN) {
            OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) masterVuforia.relicTemplate.getListener()).getPose();
            if (pose != null) {
                Orientation rot = Orientation.getOrientation(pose, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);

                vector.x = rot.firstAngle;
                vector.y = rot.secondAngle;
                vector.z = rot.thirdAngle;
            }
        }
        return vector;
    }

    @Deprecated
    public VuMarks(){
        this.LICENSE_KEY = null;
    }

    public void extendedTracking(boolean active){
        masterVuforia.parameters.useExtendedTracking = active;
    }
}
