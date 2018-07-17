package org.firstinspires.ftc.teamcode.relicRecovery.main.autonomouses.RobotV2;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.HardwareClass;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark.CENTER;
import static org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark.LEFT;
import static org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark.RIGHT;

/**
 * Created by recharged on 12/8/17.
 */

@Autonomous(name = "RedCornerColumnDumpTesting")
@Disabled
public class RedCornerColumnDumpTesting extends LinearOpMode{

    HardwareClass hardwareClass;

    double startZ = 0;

    @Override
    public void runOpMode() throws InterruptedException{
        hardwareClass = new HardwareClass(this);
        hardwareClass.init(true);

        for(int i = 0; i < 100; i++)
            startZ += hardwareClass.getGyroZ(DEGREES);
        startZ /= 100;

        waitForStart();

        RelicRecoveryVuMark vuMark = LEFT;
        int target = 0;
        if(vuMark == LEFT)
            target = 3000;
        if(vuMark == CENTER)
            target = 2000;
        if(vuMark == RIGHT)
            target = 1000;


        double speed = 0.5;
        double correction = 0.02;
        while(opModeIsActive() && hardwareClass.leftFront.getCurrentPosition() + hardwareClass.rightFront.getCurrentPosition() < target){
            double z = hardwareClass.getGyroZ(DEGREES) - startZ;
            double powerOffSet = z * correction;
            hardwareClass.powerLeft(speed + powerOffSet);
            hardwareClass.powerRight(speed - powerOffSet);
        }
        hardwareClass.powerLeft(0.0);
        hardwareClass.powerRight(0.0);

        sleep(1000);
        hardwareClass.powerRight(0.3);
        while(opModeIsActive() && hardwareClass.getGyroZ(DEGREES) < 90);
        hardwareClass.powerRight(0.0);

        sleep(400);
        hardwareClass.powerRight(-0.15);
        while(opModeIsActive() && hardwareClass.getGyroZ(DEGREES) - startZ > 92);
        hardwareClass.powerRight(0.0);
    }
}
