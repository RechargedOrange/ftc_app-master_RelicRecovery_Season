package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.EnumMap;
import java.util.LinkedList;

import static org.firstinspires.ftc.teamcode.Motors.M1;
import static org.firstinspires.ftc.teamcode.Motors.M2;
import static org.firstinspires.ftc.teamcode.Motors.M3;
import static org.firstinspires.ftc.teamcode.Motors.M4;

/**
 * Created by recharged on 12/7/17.
 */

enum Motors{
    M1,
    M2,
    M3,
    M4
}

@TeleOp(name = "QuadMotorTestBed")
public class QuadMotorTestBed extends LinearOpMode{
    private DcMotor m1;
    private DcMotor m2;
    private DcMotor m3;
    private DcMotor m4;

    private EnumMap<Motors, DcMotor> motorMap = new EnumMap<>(Motors.class);

    LinkedList<Motors> activeMotors;
;
    @Override
    public void runOpMode(){

        motorMap.put(M1, hardwareMap.dcMotor.get("m1"));
        motorMap.put(M2, hardwareMap.dcMotor.get("m2"));
        motorMap.put(M3, hardwareMap.dcMotor.get("m3"));
        motorMap.put(M4, hardwareMap.dcMotor.get("m4"));

        waitForStart();

        while(opModeIsActive()) {
            activeMotors = new LinkedList<>();
            if (gamepad1.y)
                activeMotors.add(M1);
            if (gamepad1.b)
                activeMotors.add(M2);
            if (gamepad1.a)
                activeMotors.add(M3);
            if (gamepad1.x)
                activeMotors.add(M4);

            for(Motors el : Motors.values()){
                if(activeMotors.contains(el))
                    motorMap.get(el).setPower(gamepad1.right_trigger - gamepad1.left_trigger);
                else
                    motorMap.get(el).setPower(0.0);
            }

            telemetry.addData("Active motors: ", activeMotors);
            telemetry.update();
        }
    }
}
