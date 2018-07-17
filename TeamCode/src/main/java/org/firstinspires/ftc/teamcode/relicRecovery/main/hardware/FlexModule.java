package org.firstinspires.ftc.teamcode.relicRecovery.main.hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.sensorAPI.FlexSensor;

/**
 * Created by user on 3/5/2018.
 */

public class FlexModule {
    private final HardwareMap hMap;
    private final String servoConfig;
    private final String flexConfig;
    private final double initPos;

    public Servo servo;
    public FlexSensor flexSensor;

    public double deployedPos;
    public double retractedPos;

    private final LinearOpMode linearOpMode;
    private final boolean autonomous;

    public FlexModule(LinearOpMode linearOpMode, String servoConfig, String flexConfig, double deployedPos, double initPos, double retractedPos, boolean autonomous) {
        this.hMap = linearOpMode.hardwareMap;
        this.linearOpMode = linearOpMode;
        this.servoConfig = servoConfig;
        this.flexConfig = flexConfig;

        this.deployedPos = deployedPos;
        this.initPos = initPos;
        this.retractedPos = retractedPos;

        this.autonomous = autonomous;

        servo = hMap.servo.get(servoConfig);
        if(autonomous)
            init();


        flexSensor = new FlexSensor(this.linearOpMode, flexConfig);
    }

    public void deploy(){
        servo.setPosition(deployedPos);
    }

    public void retract(){
        servo.setPosition(retractedPos);
    }

    public void init(){
        servo.setPosition(initPos);
    }
}
