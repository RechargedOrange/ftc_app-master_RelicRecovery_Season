package org.firstinspires.ftc.teamcode.relicRecovery.main.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.relicRecovery.api.BooleanToggle;
import org.firstinspires.ftc.teamcode.relicRecovery.api.Updatable;
import org.firstinspires.ftc.teamcode.typeInterfaces.BooleanInterface;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_TO_POSITION;
import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_WITHOUT_ENCODER;
import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.STOP_AND_RESET_ENCODER;

/**
 * Created by recharged on 12/7/17.
 */

public class DumperWithDualSensors implements Updatable {
    volatile private boolean dumping = false;
    volatile private boolean partlyUp = false;
    private final int DUMPING_POSITION = 365;
    private final int PARTLYUP_POSITION = 220;//150 for NeveRest 40s

    final double shakePower = 0.5;

    private BooleanInterface down;
    private BooleanInterface up;
    private boolean autonomous;
    private boolean vibrating;

    private DcMotor hardware;

    volatile private boolean updating = true;

    public void setUpdating(boolean updating) {
        this.updating = updating;
    }

    public boolean isVibrating() {
        return vibrating;
    }

    public void setVibrating(boolean vibrating) {
        this.vibrating = vibrating;
    }

    public boolean isDumping() {
        return dumping;
    }

    public void setDumping(boolean dumping) {
        this.dumping = dumping;
        //update();
    }

    public void setPartlyUp(boolean partlyUp) {
        this.partlyUp = partlyUp;
        //update();
    }

    public void update() {
        if (updating) { // Check if it's updating (If stopped it wont error)
            if (dumping) { // Check if dumping. Basic stuff
                hardware.setMode(RUN_WITHOUT_ENCODER);
                hardware.setPower(up.isTrue() ? 0.0 : 0.5); // Honestly no clue what the heck this is.
            } else if (partlyUp) { // Partly up? Of course!
                hardware.setMode(RUN_TO_POSITION);
                hardware.setTargetPosition(PARTLYUP_POSITION);
                hardware.setPower(0.7);
            } else if (vibrating) { // Same here, Common sense.
                hardware.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                hardware.setPower(down.isTrue() ? shakePower : -shakePower);
            } else {
                if (down.isTrue()) {
                    hardware.setPower(0.0);
                    hardware.setMode(STOP_AND_RESET_ENCODER);
                } else {
                    hardware.setMode(RUN_WITHOUT_ENCODER);
                    hardware.setPower(-0.3);
                }
            }
        }
    }

    public void init(boolean dumping) {
        autonomous = true;
        init();
        setDumping(dumping);
    }

    public void init() {
        hardware.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public DumperWithDualSensors(DcMotor hardware, BooleanInterface down, BooleanInterface up) {
        this.hardware = hardware;
        this.down = down;
        this.up = up;
    }

    @Deprecated
    public DumperWithDualSensors() {
    }
}
