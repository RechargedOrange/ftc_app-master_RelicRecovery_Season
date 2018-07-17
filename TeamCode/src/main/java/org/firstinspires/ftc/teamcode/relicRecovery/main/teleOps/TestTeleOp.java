package org.firstinspires.ftc.teamcode.relicRecovery.main.teleOps;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.relicRecovery.api.BooleanToggle;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.ControlDirection;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.HardwareClass;
import org.firstinspires.ftc.teamcode.relicRecovery.main.teleOps.api.tankDriveControls.TankDriveWithTurningTriggers;
import org.firstinspires.ftc.teamcode.typeInterfaces.BooleanInterface;
import org.firstinspires.ftc.teamcode.typeInterfaces.DoubleInterface;

/**
 * this is a testing teleop where we can make ugly code
 * Created by David on 10/12/2017.
 */

@TeleOp(name = "TestTeleOp")
//@Disabled

public class TestTeleOp extends LinearOpMode{

    HardwareClass hardwareClass;
    TankDriveWithTurningTriggers driveControl;

    private final double THRESHOLD = 0.1;
    private final double TURN_THRESHOLD = 0.1;

    //BooleanToggle toggleLiftMode = new BooleanToggle();
    //BooleanToggle toggle = new BooleanToggle();

    public void runOpMode() throws InterruptedException{
        hardwareClass = new HardwareClass(this);
        driveControl = new TankDriveWithTurningTriggers(hardwareClass);

        hardwareClass.init(false);

        //hardwareClass.liftMotor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        waitForStart();

        hardwareClass.controlDirection = ControlDirection.FORWARD;

        //toggle.setValue(false);
        //toggleLiftMode.setValue(false);

        while(opModeIsActive()){
            //hardwareClass.leftBallKnocker.setPosition(.8);
            driveControl.powerDrive(-gamepad1.left_stick_y, -gamepad1.right_stick_y, gamepad1.left_trigger, gamepad1.right_trigger, THRESHOLD, TURN_THRESHOLD);
            hardwareClass.glyphDumper.setPower(-gamepad2.left_stick_y);
            hardwareClass.powerLift(-gamepad2.right_stick_y);

            /*
            toggle.update(gamepad1.right_bumper);
            if(toggle.getValue()){
                hardwareClass.leftClaw.setPosition(hardwareClass.leftClawGrabDelta );
                hardwareClass.rightClaw.setPosition(hardwareClass.rightClawGrabDelta);
            }
            else{
                hardwareClass.leftClaw.setPosition(hardwareClass.leftClawReleaseDelta);
                hardwareClass.rightClaw.setPosition(hardwareClass.rightClawReleaseDelta);
            }
            */

            /*
            toggleLiftMode.update(gamepad2.a);
            int liftPosition = 0;
            double liftSpeed = toggleLiftMode.getValue() ? gamepad2.right_trigger - gamepad2.left_trigger : 0.3;
            if(liftSpeed < 0) liftSpeed = 0;
            liftPosition = liftPosition + (int) (-gamepad2.left_stick_y * 110 / 360 * 1120 * 2);

            if (liftPosition < 0) {
                liftPosition = 0;
            }
            //liftSpeed = Math.abs(liftSpeed) >= THRESHOLD ? liftSpeed : 0.0;
            hardwareClass.liftMotor1.setTargetPosition(liftPosition);
            hardwareClass.powerLift(liftSpeed);
            */

            /*
            double conveyerSpeed = -gamepad2.left_stick_y;
            double intakeSpeed = gamepad1.right_trigger - gamepad1.left_trigger;

            conveyerSpeed = conveyerSpeed >= THRESHOLD ? conveyerSpeed : 0.0;

            hardwareClass.conveyorMotor1.setPower(conveyerSpeed);


            hardwareClass.leftIntakeMotor.setPower(intakeSpeed);
            hardwareClass.rightIntakeMotor.setPower(intakeSpeed);
            */
        }
    }
}