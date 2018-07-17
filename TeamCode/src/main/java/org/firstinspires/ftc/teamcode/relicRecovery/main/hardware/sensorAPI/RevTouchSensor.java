package org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.sensorAPI;

import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by David on 1/1/18.
 */

public class RevTouchSensor {
    private final String name;
    private final HardwareMap hMap;

    private DigitalChannel digitalChannel;

    public boolean isPressed(){
        return !digitalChannel.getState();
    }

    public boolean isReleased(){
        return !isPressed();
    }

    public RevTouchSensor(final String name, HardwareMap hMap){
        this.name = name;
        this.hMap = hMap;

        digitalChannel = hMap.get(DigitalChannel.class, name);
        digitalChannel.setMode(DigitalChannel.Mode.INPUT);
    }
}
