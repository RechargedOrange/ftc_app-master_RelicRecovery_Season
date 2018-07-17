package org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.sensorAPI;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.HardwareClass;

import static java.lang.Math.abs;

/**
 * Created by user on 3/5/2018.
 */

public class FlexSensor {
    private final HardwareMap hMap;
    private AnalogInput analogInput;
    private final String config;
    private final LinearOpMode linearOpMode;

    private double calibrationVoltage;

    public FlexSensor(LinearOpMode linearOpMode, String config){
        this.hMap = linearOpMode.hardwareMap;
        this.linearOpMode = linearOpMode;
        this.config = config;

        analogInput = hMap.analogInput.get(config);

        recalibrate();
    }

    public double getRawVoltage(){
        return analogInput.getVoltage();
    }

    public double getCalibrationVoltage(){
        return calibrationVoltage;
    }

    public double getFlexVoltage(){
        double voltage = getRawVoltage() - getCalibrationVoltage();
        return abs(voltage) >= 1.0 ? 0 : voltage;
    }

    public void recalibrate(){
        int readings = 20;
        calibrationVoltage = 0;
        for(int i = 0; i < readings; i++) {
            if(linearOpMode.isStopRequested())
                break;
            calibrationVoltage += getRawVoltage() / readings + 0.0D;
        }
    }
}
