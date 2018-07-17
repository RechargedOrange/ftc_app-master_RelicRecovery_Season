package org.firstinspires.ftc.teamcode.relicRecovery.main;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.relicRecovery.api.UpdatingManager;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.HardwareClass;

/**
 * Created by user on 1/24/2018.
 */

@TeleOp(name = "UpdatingManagerTest")
public class UpdatingManagerTest extends LinearOpMode{
    HardwareClass hardwareClass;
    @Override
    public void runOpMode(){
        //hardwareClass = new HardwareClass(this);
        //hardwareClass.init(false);

        //hardwareClass.updatingManager.addUpdatable(hardwareClass.dumper);

        UpdatingManager updatingManager = new UpdatingManager(this);

        waitForStart();

        updatingManager.start();

        //if(opModeIsActive())
          //  hardwareClass.updatingManager.start();

        while(opModeIsActive()){
            //if(gamepad1.a)
            //    hardwareClass.dumper.setDumping(true);
            //if(gamepad1.b)
             //   hardwareClass.dumper.setDumping(false);
        }

//        hardwareClass.updatingManager.kill();
    }
}
