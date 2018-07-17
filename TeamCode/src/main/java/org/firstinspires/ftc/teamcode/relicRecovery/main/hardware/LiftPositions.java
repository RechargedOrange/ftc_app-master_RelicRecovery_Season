package org.firstinspires.ftc.teamcode.relicRecovery.main.hardware;

/**
 * Created by recharged on 12/20/17.
 */

public enum LiftPositions {
    DOWN(0),
    AUTODUMP(1500);

    public int position;

    LiftPositions(int position){
        this.position = position;
    }
}
