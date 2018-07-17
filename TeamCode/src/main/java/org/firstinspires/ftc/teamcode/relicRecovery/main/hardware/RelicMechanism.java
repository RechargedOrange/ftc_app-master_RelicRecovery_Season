package org.firstinspires.ftc.teamcode.relicRecovery.main.hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.relicRecovery.api.Updatable;
import org.opencv.core.Range;

import static java.lang.Math.abs;

/**
 * Created by user on 3/5/2018.
 */

public class RelicMechanism implements Updatable{

    enum ServoRanges{
        ARM(1.0, 0.0),
        CLAW(1.0, 0.4),
        RELEASE(1.0, 0.0);

        double high;
        double low;

        ServoRanges(double high, double low){
            this.high = high;
            this.low = low;
        }
    }

    private final LinearOpMode linearOpMode;
    private final HardwareMap hMap;

    private Servo release;
    private Servo claw;
    private Servo arm;

    private boolean grabbing = false;
    private boolean released = false;
    private double armPos = 0.0;

    private final boolean autonomous;

    public void release(){
        released = true;
    }

    public RelicMechanism(LinearOpMode linearOpMode, boolean autonomous){
        this.linearOpMode = linearOpMode;
        this.hMap = linearOpMode.hardwareMap;
        this.autonomous = autonomous;

        claw = hMap.servo.get("claw");
        arm = hMap.servo.get("arm");
        release = hMap.servo.get("release");

        arm.setDirection(Servo.Direction.REVERSE);
        release.setDirection(Servo.Direction.REVERSE);
    }

    public void update(){
        release.setPosition(released ? ServoRanges.RELEASE.high : ServoRanges.RELEASE.low);
        claw.setPosition(autonomous ? ServoRanges.CLAW.high : grabbing ? ServoRanges.CLAW.high : ServoRanges.CLAW.low);
        arm.setPosition(scale(armPos, ServoRanges.ARM.high, ServoRanges.ARM.low));
    }

    public void setGrabbing(boolean grabbing){
        this.grabbing = grabbing;
    }

    public void setArmPos(double armPos) {
        this.armPos = armPos;
    }

    private double scale(double number, double high, double low){
        double range = abs(high - low);

        double gain = range * number;

        return low + gain;
    }
}
