package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;

/**
 * Created by user on 2/5/2018.
 */

@TeleOp(name = "AnalogInputTest")
public class AnalogInputTest extends LinearOpMode{
    AnalogInput analogInput;
    @Override
    public void runOpMode() throws InterruptedException{
        analogInput = hardwareMap.analogInput.get("analogInput");
        telemetry.setMsTransmissionInterval(50);
        waitForStart();
        while (opModeIsActive()){
            telemetry.addData("Voltage:", analogInput.getVoltage());
            telemetry.addData("Max Voltage:", analogInput.getMaxVoltage());
            telemetry.update();
        }
    }
}
