package org.firstinspires.ftc.teamcode.relicRecovery;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.VoltageSensor;

/**
 * Created by David on 3/22/2018.
 */

public class VoltageCalculations {

    private final LinearOpMode linearOpMode;
    private final VoltageSensor voltageSensor;
    private boolean enabled;

    Double defaultVoltage;

    public VoltageCalculations(LinearOpMode linearOpMode, Double defaultVoltage) {
        this.linearOpMode = linearOpMode;
        setDefaultVoltage(defaultVoltage);

        voltageSensor = this.linearOpMode.hardwareMap.voltageSensor.iterator().next();
    }

    public double getRawVoltage() {
        return voltageSensor.getVoltage();
    }

    public double getMultiplier(Double targetVoltage) {
        return (targetVoltage == null ? defaultVoltage : targetVoltage) / getRawVoltage();
    }

    public void setDefaultVoltage(Double defaultVoltage) {
        defaultVoltage = defaultVoltage == null ? 0.0 : defaultVoltage;
    }

    public double calculatePower(double motorPower, Double targetVoltage, Double bias) {
        if (enabled)
            return motorPower * getMultiplier(targetVoltage == null ? defaultVoltage : targetVoltage) * bias;
        else
            return motorPower;
    }

    public void enabled(boolean enabled) {
        this.enabled = enabled;
    }

}
