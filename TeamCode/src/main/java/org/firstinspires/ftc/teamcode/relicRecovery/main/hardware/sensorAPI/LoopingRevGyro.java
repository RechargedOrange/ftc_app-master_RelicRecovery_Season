package org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.sensorAPI;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.firstinspires.ftc.teamcode.relicRecovery.api.Updatable;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.GyroMethods;

import static java.lang.Math.toRadians;
import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;

/**
 * Created by user on 2/19/2018.
 */

public class LoopingRevGyro implements GyroMethods, Updatable{

    private final String configName;
    volatile public BNO055IMU imu;
    private final HardwareMap hMap;
    volatile double globalAngle = 0.0;
    volatile Orientation lastAngles;

    double resetAngle = 0.0;

    @Override
    public void update(){
        Orientation angles = getGyroAngles(AxesOrder.ZYX, DEGREES);

        double deltaAngle = angles.firstAngle - lastAngles.firstAngle;

        if (deltaAngle < -180)
            deltaAngle += 360;
        else if (deltaAngle > 180)
            deltaAngle -= 360;

        globalAngle += deltaAngle;

        lastAngles = angles;
    }

    @Override
    public double getGyroZ(AngleUnit angleUnit) {
        return angleUnit == DEGREES ? globalAngle - resetAngle: toRadians(globalAngle - toRadians(resetAngle));
    }

    @Override
    public double getGyroX(AngleUnit angleUnit) {
        return 0;
    }

    @Override
    public double getGyroY(AngleUnit angleUnit) {
        return 0;
    }

    @Override
    public Orientation getGyroAngles(AxesOrder axesOrder, AngleUnit angleUnit) {
        return imu.getAngularOrientation(AxesReference.INTRINSIC, axesOrder, angleUnit);
    }

    @Override
    public void resetGyroZ() {
        resetAngle = globalAngle;
    }

    @Override
    public void resetGyroX() {

    }

    @Override
    public void resetGyroY() {

    }

    public LoopingRevGyro(HardwareMap hMap, String configName) {
        this.configName = configName;
        this.hMap = hMap;

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
        // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
        // and named "imu".
        imu = this.hMap.get(BNO055IMU.class, this.configName);
        imu.initialize(parameters);

        // Start the logging of measured acceleration
        imu.startAccelerationIntegration(new Position(), new Velocity(), 1000);

        lastAngles = getGyroAngles(AxesOrder.ZYX, DEGREES);
    }
}
