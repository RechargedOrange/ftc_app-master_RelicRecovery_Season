package org.firstinspires.ftc.teamcode.relicRecovery.trystorming;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.HardwareClass;

/**
 * Created by user on 4/16/2018.
 */
@Autonomous(name = "My name is Lane", group = "HI :DDDDDD")
public class SonarLinearMotionTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        double value = 0;
        double currentMax = -1;
        double lastValue = -1;
        boolean delay = true;
        HardwareClass hardwareClass = new HardwareClass(this);
        hardwareClass.init(true);
        double rateOfChange;
        double dt;
        double lastTime = 0;
        double time;
        double lastValue2 = 0;
        waitForStart();

        while(opModeIsActive()){
            time = System.currentTimeMillis();
            dt = time - lastTime;
            lastTime = dt;
            rateOfChange = value - lastValue2 / dt;
            lastValue2 = value;
                if (delay) {
                    sleep(1000);
                    delay = false;
                }
                value = hardwareClass.sonar.getDistance();
                if (value > currentMax) {
                    currentMax = value;
                    telemetry.addLine("Continue going forward.");
                } else if (value < currentMax) {
                    if (value < lastValue) {
                        // reverse the direction it was going
                        telemetry.addLine("Reverse.");
                    } else {
                        telemetry.addLine("Continue going forward.");
                    }
                } else if(currentMax == value) {
                    // stop
                    telemetry.addLine("Stop.");
                }
            if(rateOfChange < 0){
                    lastValue = value;
            }
            telemetry.addData("Sonar", value);
            telemetry.addData("Current Max", currentMax);
            telemetry.update();

        }
    }
}
