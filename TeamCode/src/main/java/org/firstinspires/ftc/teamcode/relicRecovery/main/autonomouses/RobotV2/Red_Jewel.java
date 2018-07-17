package org.firstinspires.ftc.teamcode.relicRecovery.main.autonomouses.RobotV2;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.relicRecovery.Alliance;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.HardwareClass;

/**
 * Created by recharged on 12/14/17.
 */

@Autonomous(name = "Red Jewel")
public class Red_Jewel extends LinearOpMode{
    HardwareClass hardwareClass;
    public void runOpMode(){
        HardwareClass hardwareClass = new HardwareClass(this);
        hardwareClass.init(true);
        waitForStart();
        new Jewel(hardwareClass, this).doJewel(Alliance.RED);
        sleep(10000);
        //sleep(5000);
    }
}
