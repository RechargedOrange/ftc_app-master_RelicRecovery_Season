package org.firstinspires.ftc.teamcode.relicRecovery.api;

/**
 * Created by David on 11/2/17.
 */

public class PositionVector {
    double x;
    double y;

    @Deprecated
    public PositionVector(){}

    public PositionVector(double x, double y){
        this.x = x;
        this.y = y;
    }

    public PositionVector getPositonAfterTravel(double distance, double angle){

        class gainCalculations {
            double[] get(int quadrant, double theta, double distance) {
                double gains[] = new double[2];

                // calculate base gain
                gains[0] = Math.cos(theta) * distance;
                gains[1] = Math.sin(theta) * distance;

                // calculate signs
                if(quadrant == 0){
                    gains[0] *= 1;
                    gains[1] *= 1;
                }else if(quadrant == 1){
                    gains[0] *= 1;
                    gains[1] *= -1;
                }else if(quadrant == 2){
                    gains[0] *= -1;
                    gains[1] *= -1;
                }else if(quadrant == 3){
                    gains[0] *= -1;
                    gains[1] *= 1;
                }

                // return x and y gains
                return gains;
            }
        }

        // calculate heading clipped to 0 - 359 going up to the right
        double calculationAngle = -(angle % 360);
        if(calculationAngle < 0)
            calculationAngle = 360 + calculationAngle;

        // calculate quadrant
        final int anglesQuadrant = (int)(calculationAngle / 90);

        //calculate theta
        calculationAngle = calculationAngle % 90;

        // calculate gains to position variables
        double[] gains = new gainCalculations().get(anglesQuadrant, calculationAngle, distance);

        // return new position
        return new PositionVector(this.x + gains[0], this.y + gains[1]);
    }

    double headingToPosition(PositionVector otherVector){
        // calculate X distance and Y distance
        final double a = Math.abs(this.x - otherVector.x);
        final double b = Math.abs(this.y - otherVector.y);

        // reverse the tangent function on b/a
        double heading = Math.atan(b / a);

        return heading;
    }

    double distanceToPosition(PositionVector otherVector) {
        // c2 = a2 + b2 Pythagorean theorem
        // square rooted to c = sqrt(a2 + b2);

        // calculate X distance and Y distance
        final double a = Math.abs(this.x - otherVector.x);
        final double b = Math.abs(this.y - otherVector.y);

        // calculate diagonal distance using Pythagorean theorem
        double distance = Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));

        return distance;
    }
}
