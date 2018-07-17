package org.firstinspires.ftc.teamcode.relicRecovery.trystorming;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;

/**
 * Created by user on 2/5/2018.
 */

@TeleOp(name = "BeamBreakHittingTest")
public class BeamBreakHittingTest extends LinearOpMode {
    AnalogInput analogInput;
    boolean hitting;
    boolean hit;
    double begginingReading = 0.0;

    @Override
    public void runOpMode() throws InterruptedException {
        analogInput = hardwareMap.analogInput.get("analogInput");
        telemetry.setMsTransmissionInterval(50);

        for (int i = 0; i < 100; i++)
            if (!isStopRequested()) {
                begginingReading += analogInput.getVoltage() / 100;
                idle();
            }


        waitForStart();
        while (opModeIsActive()) {

            hitting = Math.abs(analogInput.getVoltage() - begginingReading) > 0.05;

            if (hitting)
                hit = true;
            telemetry.addData("Voltage:", analogInput.getVoltage());
            telemetry.addData("Max Voltage:", analogInput.getMaxVoltage());
            telemetry.addData("hitting", hitting);
            telemetry.addData("hit", hit);
            telemetry.update();
        }
    }
}
