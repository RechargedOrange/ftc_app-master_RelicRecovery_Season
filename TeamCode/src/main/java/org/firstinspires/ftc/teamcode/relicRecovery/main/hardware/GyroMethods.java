package org.firstinspires.ftc.teamcode.relicRecovery.main.hardware;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

/**
 * Created by recharged on 12/8/17.
 */

public interface GyroMethods {
    double getGyroZ(AngleUnit angleUnit);
    double getGyroX(AngleUnit angleUnit);
    double getGyroY(AngleUnit angleUnit);
    Orientation getGyroAngles(AxesOrder axesOrder, AngleUnit angleUnit);
    void resetGyroZ();
    void resetGyroX();
    void resetGyroY();
}
