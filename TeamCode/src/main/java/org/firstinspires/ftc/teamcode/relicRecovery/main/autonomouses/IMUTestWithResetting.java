package org.firstinspires.ftc.teamcode.relicRecovery.main.autonomouses;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.HardwareClass;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;

/**
 * Created by recharged on 12/27/17.
 */

@TeleOp(name = IMUTestWithResetting.name)
public class IMUTestWithResetting extends LinearOpMode{
    static final String name = "IMUTestWithResetting";
    @Override
    public void runOpMode() throws InterruptedException {
        HardwareClass hardwareClass = new HardwareClass(this);
        hardwareClass.init(true);

        waitForStart();

        while(opModeIsActive()){
            if(gamepad1.a)
                hardwareClass.resetGyroZ();
            telemetry.addData("X: ", hardwareClass.getGyroX(DEGREES));
            telemetry.addData("Y: ", hardwareClass.getGyroY(DEGREES));
            telemetry.addData("Z: ", hardwareClass.getGyroZ(DEGREES));
            telemetry.update();
        }
    }
}
