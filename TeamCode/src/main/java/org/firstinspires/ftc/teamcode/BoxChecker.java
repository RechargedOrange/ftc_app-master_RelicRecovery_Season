package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.HardwareClass;

/**
 * Created by recharged on 11/17/17.
 */

@TeleOp(name = "BoxChecker")
public class BoxChecker extends LinearOpMode{
    @Override
    public void runOpMode(){
        HardwareClass hardwareClass = new HardwareClass(this);
        hardwareClass.init(true);
        waitForStart();
    }
}
