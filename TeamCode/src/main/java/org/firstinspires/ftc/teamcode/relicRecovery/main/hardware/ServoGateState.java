package org.firstinspires.ftc.teamcode.relicRecovery.main.hardware;

/**
 * Created by David on 2/21/2018.
 */

public enum ServoGateState {
    OPEN(0.15),
    CLOSED(0.65);

    public double position;

    ServoGateState(double position){
        this.position = position;
    }
}
