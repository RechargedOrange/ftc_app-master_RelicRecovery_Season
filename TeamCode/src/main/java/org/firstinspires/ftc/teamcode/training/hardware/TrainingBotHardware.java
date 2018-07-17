package org.firstinspires.ftc.teamcode.training.hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Lukens on 8/7/2017.
 */

public class TrainingBotHardware {
    public DcMotor left;
    public DcMotor right;

    // case-sensitive
    private LinearOpMode linearOpMode;

    public TrainingBotHardware(LinearOpMode linearOpMode) {
        this.linearOpMode = linearOpMode;
    }
    @Deprecated
    public TrainingBotHardware(){

    }


    public void init(){
        left = linearOpMode.hardwareMap.dcMotor.get("left");
        right = linearOpMode.hardwareMap.dcMotor.get("right");
        left.setDirection(DcMotorSimple.Direction.REVERSE);
    }
}
