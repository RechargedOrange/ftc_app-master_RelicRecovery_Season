package org.firstinspires.ftc.teamcode.relicRecovery.main.teleOps;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.relicRecovery.api.BooleanToggle;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.ControlDirection;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.DumperWithDownSensor;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.DumperWithDualSensors;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.HardwareClass;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.Dumper;
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

@TeleOp(name = RobotV2CompetitionTeleOp.name)
public class RobotV2CompetitionTeleOp extends LinearOpMode{

    static final public String name = "Competition TeleOp";

    HardwareClass hardwareClass;
    //TankDriveWithTurningTriggers driveControl = new TankDriveWithTurningTriggers(this.hardwareClass);
    TankDrive driveControl;

    Dumper dumper;

    // Constants
    final double DRIVE_THRESHOLD = 0.1;
    final double TURN_THRESHOLD = 0.1;

    BooleanToggle reversedDriveControls = new BooleanToggle();
    BooleanToggle sensingDump = new BooleanToggle();
    BooleanToggle released = new BooleanToggle(true);
    BooleanToggle pushingGlyphs = new BooleanToggle(false);
    //DumperWithDownSensor sensorDumper;
    DumperWithDualSensors sensorDumper;
    boolean lastReleasedState;

    HardwareClass.ClawState clawState;

    ElapsedTime shakeTimer = new ElapsedTime();
    double shakePower = 0.5;

    BooleanToggle balancingJewelPosition = new BooleanToggle(false);
    BooleanToggle gateOpen = new BooleanToggle(true);

    @Override
    public void runOpMode() throws InterruptedException{
        initiate();

        waitForStart();

        hardwareClass.updatingManager.start();

        hardwareClass.jewelArm.setPosition(hardwareClass.jewelArmUp);
        hardwareClass.jewelKnocker.setPosition(hardwareClass.jewelKnockerCenter);
        while(opModeIsActive()){
            /*released.update(gamepad1.left_bumper);
            pushingGlyphs.update(gamepad2.a);
            if((!released.getValue()) && lastReleasedState)
                pushingGlyphs.setValue(false);
            if(pushingGlyphs.getValue())
                released.setValue(true);
            if(!hardwareClass.dumper.isDumping()) {
                pushingGlyphs.setValue(false);
            }
            lastReleasedState = released.getValue();

            clawState = pushingGlyphs.getValue() ? PUSHING : released.getValue() ? RELEASE : GRAB;

            hardwareClass.changeClawState(clawState);*/

            //reversedDriveControls.update(gamepad1.right_bumper);
            hardwareClass.controlDirection = reversedDriveControls.getValue() ? ControlDirection.BACKWARD : ControlDirection.FORWARD;
            driveControl.powerDrive(-gamepad1.left_stick_y, -gamepad1.right_stick_y, DRIVE_THRESHOLD);
            //driveControl.powerDrive(-gamepad1.left_stick_y, -gamepad1.right_stick_y, gamepad1.left_trigger, gamepad1.right_trigger, DRIVE_THRESHOLD, TURN_THRESHOLD);

            if(gamepad2.b || gamepad1.right_bumper){
                hardwareClass.glyphDumper.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                hardwareClass.glyphDumper.setPower(hardwareClass.bucketDownByTouch.isTrue() ? shakePower : -shakePower);
                /*hardwareClass.glyphDumper.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                if(shakeTimer.milliseconds() >= 100) {
                    shakeTimer.reset();
                    shakePower *= -1;
                }
                hardwareClass.glyphDumper.setPower(shakePower);
                */
            }
            else {
                hardwareClass.dumper.setDumping(gamepad1.right_trigger > 0.5);
                hardwareClass.dumper.setPartlyUp(abs(gamepad1.left_trigger) > 0.5);
                //hardwareClass.dumper.update();
            }

            double liftSpeed = -gamepad2.left_stick_y;
            hardwareClass.powerLift(abs(liftSpeed) > 0.1 ? liftSpeed : 0.0);
            //hardwareClass.powerIntake(gamepad1.right_trigger - gamepad1.left_trigger);
            hardwareClass.powerIntake(clip(gamepad2.right_trigger - gamepad2.left_trigger, -0.8, 0.8));

            balancingJewelPosition.update(gamepad2.a);
            hardwareClass.jewelKnocker.setPosition(balancingJewelPosition.getValue() ? hardwareClass.jewelKnockerRight : hardwareClass.jewelKnockerCenter);

            gateOpen.update(gamepad2.right_bumper);
            hardwareClass.setServoGateState(gateOpen.getValue() ? OPEN : CLOSED);

            /*telemetry.addData("LeftY: ", gamepad1.left_stick_y);
            telemetry.addData("LeftX: ", gamepad1.left_stick_x);
            telemetry.addData("RightY: ", gamepad1.right_stick_y);
            telemetry.addData("RightX: ", gamepad1.right_stick_x);*/
            /*telemetry.addData("Dumper pos: ", hardwareClass.glyphDumper.getCurrentPosition());
            telemetry.addData("ClawState:", clawState);
            telemetry.update();*/
        }
    }

    double clip(double n, double min, double max){
        if(n < min)
            return min;
        else if(n > max)
            return max;
        else
            return n;
    }

    public void initiate(){
        hardwareClass = new HardwareClass(this);

        hardwareClass.controlDirection = ControlDirection.FORWARD;
        hardwareClass.init(false);
        reversedDriveControls.setValue(false);

        driveControl = new TankDrive(this.hardwareClass);

        //sensorDumper = new DumperWithDownSensor(hardwareClass.glyphDumper, hardwareClass.bucketDownByTouch);

        //sensorDumper = new DumperWithDualSensors(hardwareClass.glyphDumper, hardwareClass.bucketDownByTouch, hardwareClass.bucketUpByTouch);
        //sensorDumper.init();
    }
}
