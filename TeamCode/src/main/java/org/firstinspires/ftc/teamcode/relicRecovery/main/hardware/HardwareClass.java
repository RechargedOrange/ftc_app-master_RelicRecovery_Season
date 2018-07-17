package org.firstinspires.ftc.teamcode.relicRecovery.main.hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.otherTeams.KNO3.AutoTransitioner;
import org.firstinspires.ftc.teamcode.otherTeams.FROGBots.I2CXLv2;
import org.firstinspires.ftc.teamcode.relicRecovery.Alliance;
import org.firstinspires.ftc.teamcode.relicRecovery.VoltageCalculations;
import org.firstinspires.ftc.teamcode.relicRecovery.api.Updatable;
import org.firstinspires.ftc.teamcode.relicRecovery.api.UpdatingManager;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.sensorAPI.BeamBreak;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.sensorAPI.LoopingRevGyro;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.sensorAPI.RevMagneticLimitSwitchSensor;
import org.firstinspires.ftc.teamcode.relicRecovery.main.hardware.sensorAPI.RevTouchSensor;
import org.firstinspires.ftc.teamcode.relicRecovery.main.teleOps.RobotV2CompetitionTeleOpHolinomicJacob;
import org.firstinspires.ftc.teamcode.typeInterfaces.BooleanInterface;

import static com.disnodeteam.dogecv.math.MathFTC.clip;
import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_TO_POSITION;
import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_USING_ENCODER;
import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_WITHOUT_ENCODER;
import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.STOP_AND_RESET_ENCODER;
import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;
import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.tan;
import static java.lang.Math.toRadians;
import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.RADIANS;

/**
 * Created by David on 10/12/2017.
 */

public class HardwareClass implements TankDriveHardwareMethods, LiftHardwareMethods, GyroMethods, HolonomicHardware{

    // switch release to ()
    // switch pushing to ()
    // switch grab to ()

    public UpdatingManager updatingManager;

    public DumperWithDualSensors dumper;

    public enum ClawState{
        RELEASE(0.0, 0.0),
        PUSHING(0.0, 0.0),
        GRAB(1.0, 1.0);
        public double leftClawDelta;
        public double rightClawDelta;
        ClawState(double leftClawDelta, double rightClawDelta) {
            this.leftClawDelta = leftClawDelta;
            this.rightClawDelta = rightClawDelta;
        }
    }

    public void changeClawState(ClawState clawState){
        /*leftClaw.setPosition(clawState.leftClawDelta);
        rightClaw.setPosition(clawState.rightClawDelta);*/
    }

    private Servo leftClaw;
    private Servo rightClaw;

    private Servo servoGate;

    final String TELEOP_NAME = RobotV2CompetitionTeleOpHolinomicJacob.name;

    private HardwareMap hardwareMap;

    boolean autonomous;

    private double gyroZDegrees;
    private double gyroZRadians;
    private double gyroXDegrees;
    private double gyroXRadians;
    private double gyroYDegrees;
    private double gyroYRadians;

    public RevTouchSensor glyphDumperDownTouch;
    public RevTouchSensor glyphDumperUpTouch;

    public BooleanInterface bucketDownByTouch = new BooleanInterface() {
        @Override
        public boolean isTrue() {
            return glyphDumperDownTouch.isPressed();
        }
    };
    public BooleanInterface bucketUpByTouch = new BooleanInterface() {
        @Override
        public boolean isTrue() {
            return glyphDumperUpTouch.isPressed();
        }
    };

    public Servo jewelArm;
    public Servo jewelKnocker;
    public double jewelArmUp = 0.1;
    public double jewelArmDown = 1.0;
    public double jewelKnockerLeft = 0;
    public double jewelKnockerRight = 1.0;
    public double jewelKnockerCenter = 0.5;

    public DcMotor.RunMode driveRunMode;

    public ControlDirection controlDirection = ControlDirection.FORWARD;

    public volatile RevMagneticLimitSwitchSensor downLimitSwitch;
    public volatile RevMagneticLimitSwitchSensor upLimitSwitch;

    public DcMotor leftFront;
    public DcMotor leftBack;
    public DcMotor rightFront;
    public DcMotor rightBack;

    public volatile DcMotor glyphLift;
    private volatile double liftPower = 0.0;

    public DcMotor.RunMode liftRunMode;

    public ColorSensor jewelColorSensor;

    //public BNO055IMU imu;
    public LoopingRevGyro imu;

    public DcMotor glyphDumper;
    public DcMotor intakeMotorL;
    public DcMotor intakeMotorR;

    public Orientation angles;
    public Acceleration gravity;

    public Alliance alliance = Alliance.NUETRAL;

    public LinearOpMode linearOpMode;

    public BeamBreak intakeBeamBreak;

    public I2CXLv2 sonar;

    public FlexModule leftFlexModule;
    public FlexModule rightFlexModule;

    public RelicMechanism relicMechanism;

    public VoltageCalculations voltageCalculations;

    public HardwareClass(LinearOpMode linearOpMode) {
        this.hardwareMap = linearOpMode.hardwareMap;
        this.linearOpMode = linearOpMode;
        updatingManager = new UpdatingManager(this.linearOpMode);

        this.linearOpMode.telemetry.setMsTransmissionInterval(30);

        voltageCalculations = new VoltageCalculations(this.linearOpMode, 12.5);
    }

    public void init(boolean autonomous, Alliance alliance){
        this.alliance = alliance;
        init(autonomous);
    }

    public void init(boolean autonomous){

        this.autonomous = autonomous;

        driveRunMode = autonomous ? RUN_USING_ENCODER : RUN_WITHOUT_ENCODER;

        downLimitSwitch = new RevMagneticLimitSwitchSensor("liftDownLimitSwitch", hardwareMap);
        upLimitSwitch = new RevMagneticLimitSwitchSensor("liftUpLimitSwitch", hardwareMap);

        servoGate = hardwareMap.servo.get("servoGate");

        /*leftClaw = hardwareMap.servo.get("leftClaw");
        rightClaw = hardwareMap.servo.get("rightClaw");
        rightClaw.setDirection(Servo.Direction.REVERSE);*/

        leftFront = hardwareMap.dcMotor.get("leftFront");
        leftBack = hardwareMap.dcMotor.get("leftBack");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        rightBack = hardwareMap.dcMotor.get("rightBack");

        leftFront.setDirection(REVERSE);
        leftBack.setDirection(REVERSE);


        glyphLift = hardwareMap.dcMotor.get("glyphLift");
        //glyphLift.setDirection(DcMotor.Direction.REVERSE);

        glyphDumper = hardwareMap.dcMotor.get("glyphDumper");
        glyphDumper.setDirection(REVERSE);

        intakeMotorL = hardwareMap.dcMotor.get("intakeMotorL");
        intakeMotorR = hardwareMap.dcMotor.get("intakeMotorR");
        intakeMotorR.setDirection(REVERSE);

        imu = new LoopingRevGyro(hardwareMap, "imu");
        updatingManager.addUpdatable(imu);

        updatingManager.addUpdatable(new Updatable() {
            @Override
            public void update() {
                double power = liftPower;
                power = Range.clip(power,  downLimitSwitch.isNear() ? 0.0 : -1.0, upLimitSwitch.isNear() ? 0.0 : 1.0);
                glyphLift.setPower(power);
            }
        });

        // Set up the parameters with which we will use our IMU. Note that integration
        // algorithm here just reports accelerations to the logcat log; it doesn't actually
        // provide positional information.
/*        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();*/

        // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
        // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
        // and named "imu".
        /*imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        // Start the logging of measured acceleration
        imu.startAccelerationIntegration(new Position(), new Velocity(), 1000);*/

        setDriveZeroPowerBehavior(BRAKE);
        glyphDumper.setZeroPowerBehavior(BRAKE);
        glyphLift.setZeroPowerBehavior(BRAKE);
        intakeMotorL.setZeroPowerBehavior(BRAKE);
        intakeMotorR.setZeroPowerBehavior(BRAKE);

        jewelColorSensor = hardwareMap.colorSensor.get("jewelColorSensor");
        jewelArm = hardwareMap.servo.get("jewelArm");
        jewelArm.setDirection(Servo.Direction.REVERSE);
        jewelKnocker = hardwareMap.servo.get("jewelKnocker");
        //jewelKnocker.setDirection(Servo.Direction.REVERSE);

        glyphDumperDownTouch = new RevTouchSensor("glyphDumperDownTouch", hardwareMap);
        glyphDumperUpTouch = new RevTouchSensor("glyphDumperUpTouch", hardwareMap);

        dumper = new DumperWithDualSensors(glyphDumper, bucketDownByTouch, bucketUpByTouch);

        intakeBeamBreak = new BeamBreak("intakeBeamBreak", hardwareMap);

        sonar = hardwareMap.get(I2CXLv2.class, "sonar");

        leftFlexModule = new FlexModule(linearOpMode, "leftFlexServo", "leftFlexSensor", 0.2, 0.9, 0.9, this.autonomous);
        rightFlexModule = new FlexModule(linearOpMode, "rightFlexServo", "rightFlexSensor", 0.6, 0.0, 0.0, this.autonomous);
        /*leftFlexModule = new FlexModule(linearOpMode, "leftFlexServo", "leftFlexSensor", 0.4, 1.0, 0.0, this.autonomous);
        rightFlexModule = new FlexModule(linearOpMode, "rightFlexServo", "rightFlexSensor", 1.0, 0.0, 0.6, this.autonomous);*/

        relicMechanism = new RelicMechanism(linearOpMode, autonomous);
        updatingManager.addUpdatable(relicMechanism);

        resetDriveEncoders();

        updatingManager.addUpdatable(dumper);
        /*updatingManager.addUpdatable(new Updatable() {
            @Override
            public void update() {
                sonar.getDistance();
            }
        });*/

        setDriveRunMode(driveRunMode);

        if(autonomous){
            AutoTransitioner.transitionOnStop(linearOpMode, TELEOP_NAME);
            //changeClawState(ClawState.RELEASE);
            glyphDumper.setMode(STOP_AND_RESET_ENCODER);
            jewelArm.setPosition(0.05);
            jewelKnocker.setPosition(jewelKnockerRight);
            glyphLift.setMode(STOP_AND_RESET_ENCODER);
            dumper.setPartlyUp(false);
            dumper.setDumping(false);
            setServoGateState(ServoGateState.CLOSED);
            updatingManager.start();
        }
        else{
            setDriveRunMode(RUN_WITHOUT_ENCODER);
        }
        linearOpMode.idle();
        glyphLift.setMode(RUN_WITHOUT_ENCODER);
        glyphDumper.setMode(RUN_TO_POSITION);
    }

    public void setServoGateState(ServoGateState servoGateState){
        servoGate.setPosition(servoGateState.position);
    }

    @Override
    public void resetDriveEncoders(){
        setDriveRunMode(STOP_AND_RESET_ENCODER);
        linearOpMode.idle();
        setDriveRunMode(driveRunMode);
    }

    /*@Override
    public void powerHolonomicDrive(double forward, double strafeLeft, double turnClockwise){

        *//*double frontLeft = forward + strafeLeft + turnClockwise;
        double frontRight = forward - strafeLeft - turnClockwise;
        double backRight = forward + strafeLeft - turnClockwise;
        double backLeft = forward - strafeLeft + turnClockwise;*//*
        double frontLeft = forward - strafeLeft + turnClockwise;
        double frontRight = forward + strafeLeft - turnClockwise;
        double backRight = forward - strafeLeft - turnClockwise;
        double backLeft = forward + strafeLeft + turnClockwise;

       *//** if(abs(strafeLeft) >= 0.1 && abs(forward) < 0.1 && abs(turnClockwise) < 0.1) {//0.6 does work
            backLeft *= 0.8;
            backRight *= 0.8;
        } *//*

       //////////////
        double largest = frontLeft;
        if (Math.abs(backLeft) > largest)
            largest = Math.abs(backLeft);
        if (Math.abs(frontRight) > largest)
            largest = Math.abs(frontRight);
        if (Math.abs(backRight) > largest)
            largest = Math.abs(backRight);

        frontLeft = Math.sqrt(Math.pow(forward, 2) + Math.pow(strafeLeft, 2) + Math.pow(turnClockwise, 2)) * (frontLeft / largest);
        backLeft = Math.sqrt(Math.pow(forward, 2) + Math.pow(strafeLeft, 2) + Math.pow(turnClockwise, 2)) * (backLeft / largest);
        frontRight = Math.sqrt(Math.pow(forward, 2) + Math.pow(strafeLeft, 2) + Math.pow(turnClockwise, 2)) * (frontRight / largest);
        backRight = Math.sqrt(Math.pow(forward, 2) + Math.pow(strafeLeft, 2) + Math.pow(turnClockwise, 2)) * (backRight / largest);
       //////////////

        leftFront.setPower(frontLeft);
        leftBack.setPower(backLeft);
        rightFront.setPower(frontRight);
        rightBack.setPower(backRight);

        linearOpMode.telemetry.addData("frontLeft speed", frontLeft);
        linearOpMode.telemetry.addData("backLeft speed", backLeft);
        linearOpMode.telemetry.addData("frontRight speed", frontRight);
        linearOpMode.telemetry.addData("backRight speed", backRight);
    }*/

    public void powerHolonomicDrive(double forward, double strafeLeft, double turnClockwise){

        /*double frontLeft = forward + strafeLeft + turnClockwise;
        double frontRight = forward - strafeLeft - turnClockwise;
        double backRight = forward + strafeLeft - turnClockwise;
        double backLeft = forward - strafeLeft + turnClockwise;*/
        double frontLeft = forward - strafeLeft + turnClockwise;
        double frontRight = forward + strafeLeft - turnClockwise;
        double backRight = forward - strafeLeft - turnClockwise;
        double backLeft = forward + strafeLeft + turnClockwise;

        /** if(abs(strafeLeft) >= 0.1 && abs(forward) < 0.1 && abs(turnClockwise) < 0.1) {//0.6 does work
         backLeft *= 0.8;
         backRight *= 0.8;
         } */

        leftFront.setPower(voltageCalculations.calculatePower(frontLeft, 12.5, 0.0));
        leftBack.setPower(voltageCalculations.calculatePower(backLeft, 12.5,0.0));
        rightFront.setPower(voltageCalculations.calculatePower(frontRight, 12.5, 0.0));
        rightBack.setPower(voltageCalculations.calculatePower(backRight, 12.5, 0.0));

        /*leftFront.setPower(frontLeft);
        leftBack.setPower(backLeft);
        rightFront.setPower(frontRight);
        rightBack.setPower(backRight);*/

        linearOpMode.telemetry.addData("frontLeft speed", frontLeft);
        linearOpMode.telemetry.addData("backLeft speed", backLeft);
        linearOpMode.telemetry.addData("frontRight speed", frontRight);
        linearOpMode.telemetry.addData("backRight speed", backRight);
    }

    @Override
    public void powerLeft(double power){
        switch (controlDirection){
            case FORWARD:
                leftFront.setPower(power);
                leftBack.setPower(power);
                break;
            case BACKWARD:
                rightFront.setPower(-power);
                rightBack.setPower(-power);
                break;
            default:
                break;
        }
    }

    @Override
    public void powerRight(double power){
        switch (controlDirection){
            case FORWARD:
                rightFront.setPower(power);
                rightBack.setPower(power);
                break;
            case BACKWARD:
                leftFront.setPower(-power);
                leftBack.setPower(-power);
                break;
            default:
                break;
        }
    }

    @Override
    public void powerDrive(double leftPower, double rightPower){
        powerLeft(leftPower);
        powerRight(rightPower);
    }

    @Override
    public int getLeftPosition(){
        return (getFrontLeftEncoder() + getBackLeftEncoder()) / 2;
    }

    @Override
    public int getRightPosition(){
        return (getFrontRightEncoder() + getBackRightEncoder()) / 2;
    }

    @Override
    public int getFrontLeftEncoder(){
        return leftFront.getCurrentPosition();
    }

    @Override
    public int getFrontRightEncoder(){
        return rightFront.getCurrentPosition();
    }

    @Override
    public int getBackLeftEncoder(){
        return leftBack.getCurrentPosition();
    }

    @Override
    public int getBackRightEncoder(){
        return rightBack.getCurrentPosition();
    }

    @Override
    public void setDriveRunMode(DcMotor.RunMode runMode){
        leftFront.setMode(runMode);
        leftBack.setMode(runMode);
        rightFront.setMode(runMode);
        rightBack.setMode(runMode);
    }

    @Override
    public void setDriveZeroPowerBehavior(DcMotor.ZeroPowerBehavior zeroPowerBehavior){
        leftFront.setZeroPowerBehavior(zeroPowerBehavior);
        leftBack.setZeroPowerBehavior(zeroPowerBehavior);
        rightFront.setZeroPowerBehavior(zeroPowerBehavior);
        rightBack.setZeroPowerBehavior(zeroPowerBehavior);
    }

    @Override
    public void powerLift(double power) {
        liftPower = power;
    }

    @Override
    public void setLiftRunMode(DcMotor.RunMode liftRunMode) {
        glyphLift.setMode(liftRunMode);
        this.liftRunMode = liftRunMode;
    }

    @Override
    public DcMotor.RunMode getLiftRunMode() {
        return liftRunMode;
    }

    @Override
    public void setLiftTargetCounts(int targetCounts){
        glyphLift.setTargetPosition(targetCounts);
    }

    public void powerIntake(double power){
        clip(power, -1.0, 0.8);
        intakeMotorL.setPower(power);
        intakeMotorR.setPower(power/* * 0.80*/);
    }

    public double distanceFromWall(double angle, AngleUnit angleUnit){
        int i = 0;
        /*linearOpMode.telemetry.addLine("i" + i);// 0
        linearOpMode.telemetry.update();*/
        i++;
        if(angleUnit == DEGREES)
            angle = toRadians(angle);
        /*linearOpMode.telemetry.addLine("i" + i);// 1
        linearOpMode.telemetry.update();*/
        i++;
        double sonarDistanceFromCenter = 14.5;//CentiMeters
        //sonarDistanceFromCenter = 0;
        int sonarReading = sonar.getDistance();

        /*linearOpMode.telemetry.addLine("i" + i);// 2
        linearOpMode.telemetry.update();*/
        i++;

        double zOffset = getGyroZ(RADIANS) - angle;
        /*linearOpMode.telemetry.addLine("i" + i);// 3
        linearOpMode.telemetry.update();*/
        i++;
        double distanceFromSensor = cos(abs(zOffset)) * sonarReading;
        /*linearOpMode.telemetry.addLine("i" + i);// 4
        linearOpMode.telemetry.update();*/
        i++;
        double baseGainFromOffSet = tan(abs(zOffset) * sonarDistanceFromCenter);
        /*linearOpMode.telemetry.addLine("i" + i);// 5
        linearOpMode.telemetry.update();*/
        i++;

        double distance = angle < 0 ? distanceFromSensor + baseGainFromOffSet : distanceFromSensor - baseGainFromOffSet;
        /*linearOpMode.telemetry.addLine("i" + String.valueOf(i++));// 6
        linearOpMode.telemetry.update();*/
        return distance;
    }

    @Override
    public Orientation getGyroAngles(AxesOrder axesOrder, AngleUnit angleUnit){
        return imu.imu.getAngularOrientation(AxesReference.INTRINSIC, axesOrder, angleUnit);
    }

    @Override
    public double getGyroZ(AngleUnit angleUnit){
        return imu.getGyroZ(angleUnit);
        /*double angle = getGyroAngles(AxesOrder.ZXY, angleUnit).firstAngle;
        if(angleUnit == DEGREES)
            angle -= gyroZDegrees;
        else if(angleUnit == AngleUnit.RADIANS)
            angle -= gyroZRadians;
        return angle;*/
    }
    @Override
    public double getGyroX(AngleUnit angleUnit){
        double angle = getGyroAngles(AxesOrder.XZY, angleUnit).firstAngle;
        if(angleUnit == DEGREES)
            angle -= gyroXDegrees;
        else if(angleUnit == AngleUnit.RADIANS)
            angle -= gyroXRadians;
        return angle;
    }
    @Override
    public double getGyroY(AngleUnit angleUnit){
        double angle = getGyroAngles(AxesOrder.YZX, angleUnit).firstAngle;
        if(angleUnit == DEGREES)
            angle -= gyroYDegrees;
        else if(angleUnit == AngleUnit.RADIANS)
            angle -= gyroYRadians;
        return angle;
    }
    @Override
    public void resetGyroZ(){
        imu.resetGyroZ();
        /*gyroZDegrees = getGyroAngles(AxesOrder.ZXY, DEGREES).firstAngle;
        gyroZRadians = toRadians(gyroZDegrees);*/
    }
    @Override
    public void resetGyroX(){
        gyroXDegrees = getGyroAngles(AxesOrder.XZY, DEGREES).firstAngle;
        gyroXRadians = toRadians(gyroXDegrees);
    }
    @Override
    public void resetGyroY(){
        gyroYDegrees = getGyroAngles(AxesOrder.YXZ, DEGREES).firstAngle;
        gyroYRadians = toRadians(gyroYDegrees);
    }
}
