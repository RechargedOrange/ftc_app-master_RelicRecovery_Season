package org.firstinspires.ftc.teamcode.relicRecovery.main.hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by user on 2/15/2018.
 */
@TeleOp(name = "BeamBreakTest")
public class BeamBreakTest extends LinearOpMode{
    HardwareClass hardwareClass;
    @Override
    public void runOpMode() throws InterruptedException{
        hardwareClass = new HardwareClass(this);
        hardwareClass.init(true);

        waitForStart();

        while(opModeIsActive()){
            telemetry.addData("BeamBroken", hardwareClass.intakeBeamBreak.beamBroken());
            telemetry.addData("BeamComplete", hardwareClass.intakeBeamBreak.beamComplete());
            telemetry.update();
        }
    }
}
