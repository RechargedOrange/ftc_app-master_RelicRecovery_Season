package org.firstinspires.ftc.teamcode.relicRecovery.main.teleOps;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.relicRecovery.api.BooleanToggle;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.ControlDirection;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.Dumper;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.DumperWithDualSensors;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.HardwareClass;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.ServoGateState;
import org.firstinspires.ftc.teamcode.relicRecovery.main.teleOps.api.tankDriveControls.TankDrive;

import static java.lang.Math.abs;
import static org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.HardwareClass.ClawState.GRAB;
import static org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.HardwareClass.ClawState.PUSHING;
import static org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.HardwareClass.ClawState.RELEASE;
import static org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.ServoGateState.CLOSED;
import static org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.ServoGateState.OPEN;

/**
 * Created by David on 12/4/17.
 * code in here is to be kept as clean and well performing as can be
 * nothing should be below the level of competition code
 */

@TeleOp(name = RobotV2CompetitionTeleOpHolinomicJacob.name)
public class RobotV2CompetitionTeleOpHolinomicJacob extends LinearOpMode {

    static final public String name = "Competition TeleOp Holinomic Jacob";

    HardwareClass hardwareClass;

    BooleanToggle balancingJewelPosition = new BooleanToggle(true);
    BooleanToggle grabbingRelic = new BooleanToggle(false);
    BooleanToggle glyphMode = new BooleanToggle(true);


    @Override
    public void runOpMode() throws InterruptedException {
        initiate();

        waitForStart();

        hardwareClass.updatingManager.start();

        hardwareClass.jewelArm.setPosition(hardwareClass.jewelArmUp);
        hardwareClass.jewelKnocker.setPosition(hardwareClass.jewelKnockerCenter);
        hardwareClass.relicMechanism.setArmPos(0.5);
        hardwareClass.leftFlexModule.retract();
        //hardwareClass.rightFlexModule.retract();
        hardwareClass.rightFlexModule.init();

        while (opModeIsActive()) {

            double forward = (-gamepad1.left_stick_y + -gamepad1.right_stick_y) / 2;
            if (abs(forward) < 0.1)
                forward = 0.0;
            double strafeLeft = gamepad1.left_trigger - gamepad1.right_trigger;
            if (abs(strafeLeft) < 0.1)
                strafeLeft = 0.0;
            double turnRight = (-gamepad1.left_stick_y - -gamepad1.right_stick_y) / 2;
            if (abs(turnRight) < 0.1)
                turnRight = 0.0;

            hardwareClass.powerHolonomicDrive(forward, strafeLeft, turnRight);

            hardwareClass.dumper.setDumping(gamepad1.right_bumper);
            hardwareClass.dumper.setPartlyUp(gamepad1.left_bumper);
            hardwareClass.dumper.setVibrating(gamepad2.b);

            grabbingRelic.update(gamepad2.right_bumper);
            hardwareClass.relicMechanism.setGrabbing(grabbingRelic.getValue());
            hardwareClass.relicMechanism.setArmPos(Range.scale(Range.clip(-gamepad2.right_stick_y, 0.0, 1.0), 0.0, 1.0, 0.0, 1.0));
            if (gamepad2.left_bumper && gamepad2.y) {
                hardwareClass.relicMechanism.release();
                hardwareClass.leftFlexModule.deploy();
            }

            double liftSpeed = -gamepad2.left_stick_y;
            hardwareClass.powerLift(abs(liftSpeed) > 0.1 ? liftSpeed : 0.0);
            hardwareClass.powerIntake(gamepad2.right_trigger - gamepad2.left_trigger);

            balancingJewelPosition.update(gamepad2.a);
            hardwareClass.jewelKnocker.setPosition(balancingJewelPosition.getValue() ? hardwareClass.jewelKnockerRight : hardwareClass.jewelKnockerCenter);
            hardwareClass.jewelArm.setPosition(gamepad2.x ? hardwareClass.jewelArmDown : hardwareClass.jewelArmUp);

            ServoGateState servoGateState = (gamepad2.dpad_down || gamepad2.dpad_up || gamepad2.dpad_left || gamepad2.dpad_right) ? CLOSED : OPEN;
            hardwareClass.setServoGateState(servoGateState);

            /*telemetry.addData("LeftY: ", gamepad1.left_stick_y);
            telemetry.addData("LeftX: ", gamepad1.left_stick_x);
            telemetry.addData("RightY: ", gamepad1.right_stick_y);
            telemetry.addData("RightX: ", gamepad1.right_stick_x);*/
            /*telemetry.addData("Dumper pos: ", hardwareClass.glyphDumper.getCurrentPosition());
            telemetry.addData("ClawState:", clawState);
            telemetry.update();*/
            telemetry.addData("glyphMode", glyphMode.getValue());
            telemetry.addData("servoGateState", servoGateState);
            telemetry.addData("TurnRight", turnRight);
            telemetry.addData("StrafeLeft", strafeLeft);
            telemetry.addData("Forward", forward);
            telemetry.update();
        }
    }

    double clip(double n, double min, double max) {
        if (n < min)
            return min;
        else if (n > max)
            return max;
        else
            return n;
    }

    public void initiate() {
        hardwareClass = new HardwareClass(this);

        hardwareClass.controlDirection = ControlDirection.FORWARD;
        hardwareClass.init(false);

        //sensorDumper = new DumperWithDownSensor(hardwareClass.glyphDumper, hardwareClass.bucketDownByTouch);

        //sensorDumper = new DumperWithDualSensors(hardwareClass.glyphDumper, hardwareClass.bucketDownByTouch, hardwareClass.bucketUpByTouch);
        //sensorDumper.init();
    }
}
