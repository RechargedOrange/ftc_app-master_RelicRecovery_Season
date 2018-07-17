package org.firstinspires.ftc.teamcode.training.teleops;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.training.hardware.TrainingBotHardware;

/**
 * Created by Lukens on 8/7/2017.
 */

@TeleOp(name = "TrainingTeleop")
@Disabled

public class TrainingTeleop extends LinearOpMode{
    ElapsedTime timer = new ElapsedTime();
    private double leftSpeed;
    private double rightSpeed;
    private double threshold = 0.1;
    double servoDelta = 1.0;
    Servo servo;


    TrainingBotHardware hardware = new TrainingBotHardware(this);

    public void runOpMode()throws InterruptedException{

        switch (hardware.left.getDirection()){

            case FORWARD:
                break;
            case REVERSE:
                break;
        }



        hardware.init();
        servo = hardwareMap.servo.cast("servo");
        waitForStart();
        while(opModeIsActive()){
            leftSpeed = -gamepad1.left_stick_y;
            rightSpeed = -gamepad1.right_stick_y;

            if(Math.abs(leftSpeed) < threshold){
                leftSpeed = 0.0;
            }

            if(Math.abs(rightSpeed) < threshold){
                rightSpeed = 0.0;
            }

            hardware.left.setPower(rightSpeed);
            hardware.right.setPower(leftSpeed);

            if(timer.milliseconds() > 1000) {
                timer.reset();
                servoDelta *= -1.0;
            }

            servo.setPosition(servoDelta);

            // helpful telemetry
            telemetry.addData("rightSpeed = ", rightSpeed);
            telemetry.addData("leftSpeed = ", leftSpeed);
            telemetry.addData("gamepad1.left_stick_y = ", gamepad1.left_stick_y);
            telemetry.addData("gamepad1.right_stick_y = ", gamepad1.right_stick_y);
        }

    }
}
