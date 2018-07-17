package org.firstinspires.ftc.teamcode.relicRecovery.trystorming;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by Lukens on 9/21/2017.
 */

public class FindingCryptoboxWithRange extends LinearOpMode{
    HolonomicDriveTestLinear holonomicDriveTestLinear;



    @Override
    public void runOpMode() throws InterruptedException {
        holonomicDriveTestLinear.initialize(hardwareMap);
        waitForStart();



        while (opModeIsActive()){
            boolean found = true;

            if (found)
                holonomicDriveTestLinear.power(0,0,1);
            else
                holonomicDriveTestLinear.power(0,0,0);

            telemetry.addData("","");
        }
    }
}
