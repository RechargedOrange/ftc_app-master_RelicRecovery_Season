package org.firstinspires.ftc.teamcode.relicRecovery.main.hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.RADIANS;

/**
 * Created by recharged on 12/9/17.
 */

public class DriveMethods {

    LinearOpMode linearOpMode;
    GyroMethods gyro;
    TankDriveHardwareMethods drive;

    public void driveForward(double power, int distance, double targetZ, double correctionPerDegree, AngleUnit angleUnit) {
        if (angleUnit == RADIANS)
            targetZ = Math.toDegrees(targetZ);
        drive.resetDriveEncoders();
        while (linearOpMode.opModeIsActive() && drive.getLeftPosition() + drive.getRightPosition() < distance) {
            double zOff = gyro.getGyroZ(DEGREES) - targetZ;
            double powerOffSet = correctionPerDegree * zOff;
            double leftPower = Range.clip(power + powerOffSet, -1.0, 1.0);
            double rightPower = Range.clip(power - powerOffSet, -1.0, 1.0);
            drive.powerLeft(leftPower);
            drive.powerRight(rightPower);
        }
        drive.powerLeft(0.0);
        drive.powerRight(0.0);
    }

    public void driveBackward(double power, int distance, double z, double correctionPerDegree, AngleUnit angleUnit) {
        if (angleUnit == RADIANS)
            z = Math.toDegrees(z);
        distance = -distance;
        power = -power;
        drive.resetDriveEncoders();
        while (linearOpMode.opModeIsActive() && drive.getLeftPosition() + drive.getRightPosition() > distance) {
            double zOff = gyro.getGyroZ(DEGREES) - z;
            double powerOffSet = correctionPerDegree * zOff;
            double leftPower = Range.clip(power + powerOffSet, -1.0, 1.0);
            double rightPower = Range.clip(power - powerOffSet, -1.0, 1.0);
            drive.powerLeft(leftPower);
            drive.powerRight(rightPower);
        }
        drive.powerLeft(0.0);
        drive.powerRight(0.0);
    }

    public void turnLeft(double power, double z, AngleUnit angleUnit) {
        if (angleUnit == RADIANS)
            z = Math.toDegrees(z);
        drive.powerDrive(-power, power);
        while (linearOpMode.opModeIsActive() && gyro.getGyroZ(DEGREES) < z) {
            linearOpMode.telemetry.addData("Turning left: ", gyro.getGyroZ(DEGREES));
            linearOpMode.telemetry.update();
        }
        drive.powerLeft(0.0);
        drive.powerRight(0.0);
    }

    public void turnFowardLeft(double power, double z, AngleUnit angleUnit) {
        if (angleUnit == RADIANS)
            z = Math.toDegrees(z);
        drive.powerDrive(0.0, power);
        while (linearOpMode.opModeIsActive() && gyro.getGyroZ(DEGREES) < z) {
        }
        drive.powerDrive(0.0, 0.0);
    }

    public void turnBackwardLeft(double power, double z, AngleUnit angleUnit) {
        if (angleUnit == RADIANS)
            z = Math.toDegrees(z);
        drive.powerDrive(-power, 0.0);
        while (linearOpMode.opModeIsActive() && gyro.getGyroZ(DEGREES) < z) {
            linearOpMode.telemetry.addData("turnBackwardLeft", gyro.getGyroZ(angleUnit));
            linearOpMode.telemetry.update();
        }
        drive.powerDrive(0.0, 0.0);
    }

    public void turnRight(double power, double z, AngleUnit angleUnit) {
        if (angleUnit == RADIANS)
            z = Math.toDegrees(z);
        drive.powerLeft(power);
        drive.powerRight(-power);
        drive.powerDrive(power, -power);
        while (linearOpMode.opModeIsActive() && gyro.getGyroZ(DEGREES) > z) {
            linearOpMode.telemetry.addData("Turning right: ", gyro.getGyroZ(DEGREES));
            linearOpMode.telemetry.update();
        }
        drive.powerDrive(0.0, 0.0);
    }

    public void turnFowardRight(double power, double z, AngleUnit angleUnit) {
        if (angleUnit == RADIANS)
            z = Math.toDegrees(z);
        drive.powerDrive(power, 0.0);
        while (linearOpMode.opModeIsActive() && gyro.getGyroZ(DEGREES) > z) {
            linearOpMode.telemetry.addData("turnFowardRight", gyro.getGyroZ(angleUnit));
            linearOpMode.telemetry.update();
        }
        drive.powerDrive(0.0, 0.0);
    }

    public void turnBackwardRight(double power, double z, AngleUnit angleUnit) {
        if (angleUnit == RADIANS)
            z = Math.toDegrees(z);
        drive.powerDrive(0.0, -power);
        while (linearOpMode.opModeIsActive() && gyro.getGyroZ(DEGREES) > z) {
        }
        drive.powerDrive(0.0, 0.0);
    }


    public DriveMethods(LinearOpMode linearOpMode, GyroMethods gyro, TankDriveHardwareMethods drive) {
        this.linearOpMode = linearOpMode;
        this.gyro = gyro;
        this.drive = drive;
    }

    @Deprecated
    public DriveMethods() {
    }
}
