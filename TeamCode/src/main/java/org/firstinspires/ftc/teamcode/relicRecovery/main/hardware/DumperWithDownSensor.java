package org.firstinspires.ftc.teamcode.relicRecovery.main.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.typeInterfaces.BooleanInterface;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_TO_POSITION;
import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_WITHOUT_ENCODER;
import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.STOP_AND_RESET_ENCODER;

/**
 * Created by recharged on 12/7/17.
 */

public class DumperWithDownSensor {
    volatile private boolean dumping = false;
    volatile private boolean partlyUp = false;
    private final int DUMPING_POSITION = 365;
    private final int PARTLYUP_POSITION = 150;

    private BooleanInterface down;
    private boolean autonomous;

    private DcMotor hardware;

    public boolean isDumping() {
        return dumping;
    }

    public void setDumping(boolean dumping) {
        this.dumping = dumping;
        update();
    }

    public void setPartlyUp(boolean partlyUp) {
        this.partlyUp = partlyUp;
        //update();
    }

    private void update() {
        if (dumping) {
            hardware.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            hardware.setTargetPosition(DUMPING_POSITION);
            hardware.setPower(0.3);
        } else if (partlyUp) {
            hardware.setMode(RUN_TO_POSITION);
            hardware.setTargetPosition(PARTLYUP_POSITION);
            hardware.setPower(0.3);
        } else {
            boolean isDown = down.isTrue();
            hardware.setPower(isDown ? 0 : -0.5);
            hardware.setMode(isDown ? STOP_AND_RESET_ENCODER : RUN_WITHOUT_ENCODER);
        }
    }

    public void init(boolean dumping) {
        autonomous = true;
        init();
        setDumping(dumping);
    }

    public void init() {
        hardware.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public DumperWithDownSensor(DcMotor hardware, BooleanInterface down) {
        this.hardware = hardware;
        this.down = down;
    }

    @Deprecated
    public DumperWithDownSensor() {
    }
}
