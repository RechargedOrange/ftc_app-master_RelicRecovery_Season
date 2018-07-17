package org.firstinspires.ftc.teamcode.relicRecovery.api;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by user on 2/15/2018.
 */

public class ToggleTest extends LinearOpMode{
    BooleanToggle booleanToggle = new BooleanToggle(false);
    IncrementalToggle incrementalToggle = new IncrementalToggle();

    @Override
    public void runOpMode() throws InterruptedException{
        waitForStart();
        while(opModeIsActive()){
            booleanToggle.update(gamepad1.a);
            incrementalToggle.update(gamepad1.b);
            telemetry.addData("BooleanToggle", booleanToggle.getValue());
            telemetry.addData("IncrementalToggle", incrementalToggle.getValue());
            telemetry.update();
        }
    }
}
