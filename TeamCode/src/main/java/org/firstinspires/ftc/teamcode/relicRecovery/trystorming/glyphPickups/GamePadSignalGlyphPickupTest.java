package org.firstinspires.ftc.teamcode.relicRecovery.trystorming.glyphPickups;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.DriveMethods;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.HardwareClass;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.teamcode.relicRecovery.main.teleOps.RobotV2CompetitionTeleOp.name;

/**
 * Created by user on 1/26/2018.
 */

@TeleOp(name = GamePadSignalGlyphPickupTest.name)
public class GamePadSignalGlyphPickupTest extends LinearOpMode{
    static final String name = "GamePadSignalGlyphPickupTest";
    HardwareClass robot;
    DriveMethods driveMethods;
    @Override
    public void runOpMode(){
        robot = new HardwareClass(this);
        robot.init(true);

        driveMethods = new DriveMethods(this, robot, robot);
        waitForStart();

        telemetry.addLine("Press A to start");
        telemetry.addLine("B to interrupt picking up");
        telemetry.update();
        while(opModeIsActive() && !gamepad1.a);

        robot.powerIntake(0.8);

        double z = 0.0;
        double correctionPerDegree = 0.02;
        double power = 0.5;

        while(opModeIsActive() && !gamepad1.b){
            double zOff = robot.getGyroZ(DEGREES) - z;
            double powerOffSet = correctionPerDegree * zOff;
            double leftPower = Range.clip(power + powerOffSet, -1.0, 1.0);
            double rightPower = Range.clip(power - powerOffSet, -1.0, 1.0);
            robot.powerLeft(leftPower);
            robot.powerRight(rightPower);
        }
        driveMethods.driveBackward(0.8, 500, 0, 0.02, AngleUnit.DEGREES);

        sleep(2000);

        robot.powerLeft(0.5);
        robot.powerRight(0.5);
        while(opModeIsActive() && !gamepad1.b){
            double zOff = robot.getGyroZ(DEGREES) - z;
            double powerOffSet = correctionPerDegree * zOff;
            double leftPower = Range.clip(power + powerOffSet, -1.0, 1.0);
            double rightPower = Range.clip(power - powerOffSet, -1.0, 1.0);
            robot.powerLeft(leftPower);
            robot.powerRight(rightPower);
        }
        driveMethods.driveBackward(0.8, 500, 0, 0.02, AngleUnit.DEGREES);
    }
}
