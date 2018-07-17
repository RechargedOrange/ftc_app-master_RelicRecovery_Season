package org.firstinspires.ftc.teamcode.relicRecovery.trystorming;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.GyroMethods;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.HardwareClass;

/**
 * Created by someone who should be banned from the world, on 4/2/2018.
 */

public class SelfLearningPrototype {
    HardwareClass hardwareClass;
    GyroMethods gyroMethods;
    boolean selfLearning = false;
    double correction;
    double startingCorrection = 20;
    double currentPostion;
    ElapsedTime time = new ElapsedTime();

    public void selfLearning(boolean selfLearning){
        this.selfLearning = selfLearning;
    }
    public void setPosition(int targetPosition){
        currentPostion = gyroMethods.getGyroZ(AngleUnit.DEGREES);
        if(selfLearning) {


            correction = currentPostion - targetPosition + startingCorrection;


            if(currentPostion <= correction){
                time.startTime();
                if(time.milliseconds() >= 10){
                    // Decrease power
                    time.reset();
                }
            } else if(currentPostion <= targetPosition){
                time.startTime();
                if(time.milliseconds() >= 10){
                    // Add power
                    time.reset();
                }
            }

        } else {
            if(currentPostion <= correction){
                time.startTime();
                if(time.milliseconds() >= 10){
                    // Decrease power
                    time.reset();
                }

            } else if(currentPostion <= targetPosition){
                time.startTime();
                if(time.milliseconds() >= 10){
                    // Add power
                    time.reset();
                }
            }
        }
    }
}