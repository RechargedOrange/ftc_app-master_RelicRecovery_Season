package org.firstinspires.ftc.teamcode.relicRecovery.main.teleOps;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.HardwareClass;

/**
 * Created by recharged on 12/16/17.
 */
@TeleOp(name = "JewelArmTester")
public class JewelArmTester extends LinearOpMode {
    @Override
    public void runOpMode(){
        HardwareClass hardwareClass = new HardwareClass(this);
        hardwareClass.init(true);
        waitForStart();
        while(opModeIsActive()){
            if(gamepad1.x)
                hardwareClass.jewelKnocker.setPosition(hardwareClass.jewelKnockerLeft);
            else if(gamepad1.y)
                hardwareClass.jewelKnocker.setPosition(hardwareClass.jewelKnockerCenter);
            else if(gamepad1.b)
                hardwareClass.jewelKnocker.setPosition(hardwareClass.jewelKnockerRight);

            if(gamepad1.a)
                hardwareClass.jewelArm.setPosition(hardwareClass.jewelArmDown);
            else
                hardwareClass.jewelArm.setPosition(hardwareClass.jewelArmUp);
        }
    }
}
