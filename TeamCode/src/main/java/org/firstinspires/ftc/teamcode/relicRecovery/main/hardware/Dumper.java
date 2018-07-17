package org.firstinspires.ftc.teamcode.relicRecovery.main.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_TO_POSITION;

/**
 * Created by recharged on 12/7/17.
 */

public class Dumper {
    volatile private boolean dumping;
    private final int DUMPING_POSITION = 330;

    private boolean autonomous;

    private DcMotor hardware;

    public boolean isDumping() {
        return dumping;
    }

    public void setDumping(boolean dumping) {
        this.dumping = dumping;
        update();
    }

    private void update(){
        hardware.setMode(RUN_TO_POSITION);
        if(dumping) {
            hardware.setTargetPosition(DUMPING_POSITION);
            hardware.setPower(0.3);
        }
        else{
            hardware.setTargetPosition(0);
            hardware.setPower(0.5);
        }
    }

    public void init(boolean dumping){
        autonomous = true;
        init();
        setDumping(dumping);
    }
    public void init(){
        hardware.setMode(RUN_TO_POSITION);
    }

    public Dumper(DcMotor hardware) {
        this.hardware = hardware;
    }

    @Deprecated
    public Dumper(){
    }
}
