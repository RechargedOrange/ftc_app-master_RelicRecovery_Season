package org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.sensorAPI;

import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by user on 2/15/2018.
 */
public class BeamBreak {
    private final String configName;
    private final HardwareMap hMap;
    private DigitalChannel digitalChannel;

    public boolean beamBroken(){
        return !beamComplete();
    }

    public boolean beamComplete(){
        return digitalChannel.getState();
    }

    public BeamBreak(String configName, HardwareMap hMap) {
        this.configName = configName;
        this.hMap = hMap;

        this.digitalChannel = this.hMap.digitalChannel.get(this.configName);
    }
}