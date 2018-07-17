package org.firstinspires.ftc.teamcode.relicRecovery.main.autonomouses.RobotV2;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.relicRecovery.Alliance;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.HardwareClass;

import static org.firstinspires.ftc.teamcode.relicRecovery.Alliance.RED;

/**
 * Created by recharged on 12/14/17.
 */

public class Jewel {
    HardwareClass hardwareClass;
    LinearOpMode linearOpMode;

    public void doJewel(Alliance alliance){
        /*hardwareClass.jewelKnocker.setPosition(hardwareClass.jewelKnockerCenter);
        linearOpMode.sleep(500);
        hardwareClass.jewelArm.setPosition(hardwareClass.jewelArmDown);
        linearOpMode.sleep(2000);

        int red = hardwareClass.jewelColorSensor.red();
        int blue = hardwareClass.jewelColorSensor.blue();
        boolean correctBallForward = alliance == RED ? red > blue : blue > red;

        hardwareClass.jewelKnocker.setPosition(correctBallForward ? hardwareClass.jewelKnockerRight : hardwareClass.jewelKnockerLeft);
        linearOpMode.sleep(500);
        //hardwareClass.jewelKnocker.setPosition(hardwareClass.jewelKnockerCenter);
        hardwareClass.jewelArm.setPosition(hardwareClass.jewelArmUp);
        linearOpMode.sleep(3000);
        hardwareClass.jewelKnocker.setPosition(hardwareClass.jewelKnockerCenter);*/

        hardwareClass.jewelKnocker.setPosition(hardwareClass.jewelKnockerCenter);
        hardwareClass.jewelArm.setPosition(hardwareClass.jewelArmDown);
        ElapsedTime timer = new ElapsedTime();
        while(timer.milliseconds() < 2000 && !betweenBalls());
        int red = hardwareClass.jewelColorSensor.red();
        int blue = hardwareClass.jewelColorSensor.blue();
        boolean correctBallForward = alliance == RED ? red > blue : blue > red;
        hardwareClass.jewelKnocker.setPosition(correctBallForward ? hardwareClass.jewelKnockerRight : hardwareClass.jewelKnockerLeft);

        linearOpMode.sleep(500);

        hardwareClass.jewelArm.setPosition(hardwareClass.jewelArmUp);
    }

    private boolean betweenBalls(){
        return false;
    }

    public Jewel(HardwareClass hardwareClass, LinearOpMode linearOpMode){
        this.hardwareClass = hardwareClass;
        this.linearOpMode = linearOpMode;
    }

    @Deprecated
    public Jewel(){
    }
}
