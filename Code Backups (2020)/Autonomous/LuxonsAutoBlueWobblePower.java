package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "LuxonsBLWobblePower") //moves the wobble goal to b with powershots
@Disabled
public class LuxonsAutoBlueWobblePower extends LinearOpMode {
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    //Declares Motors on Wheels
    private DcMotor leftFrontMotor;
    private DcMotor leftBackMotor;
    private DcMotor rightFrontMotor;
    private DcMotor rightBackMotor;

    //Shoot motor
    public DcMotor shoot_motor;
    public DcMotor wobble_motor;

    //Other Motors

    public CRServo ringPush;
    public CRServo wobbleGrab;

    //Declares Motor Function Arrays
    private final double gearRatio = 1.0;
    private final double wheelRadius = 2.0;
    private final double TicksperRev = 1500.0;
    private final double powerShoot = 0.85;
    private double powerForward[] = {0.8, 0.8};
    private double powerSpin[] = {1.0, 1.0};


    //OpMode Variables:
    private double toBridge = (40); //the robot is 18 in width and 18 in length
    private double toStones = (25); //each grey square is 24 inches

    //Runs the OpMode
    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        leftFrontMotor = hardwareMap.dcMotor.get("drive_left_front");
        rightFrontMotor = hardwareMap.dcMotor.get("drive_right_front");
        leftBackMotor = hardwareMap.dcMotor.get("drive_left_back");
        rightBackMotor = hardwareMap.dcMotor.get("drive_right_back");

        shoot_motor = hardwareMap.dcMotor.get("shoot_motor");
        wobble_motor = hardwareMap.dcMotor.get("wobble_motor");

        ringPush = hardwareMap.crservo.get("ring_push");
        wobbleGrab = hardwareMap.crservo.get("wobble_grab");

        leftFrontMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        leftBackMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        shoot_motor.setDirection(DcMotorSimple.Direction.FORWARD);

        wobble_motor.setDirection(DcMotorSimple.Direction.FORWARD);

        wobble_motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        wobble_motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBackMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //waits for start.
        waitForStart();
        runtime.reset();

        wobbleGrab.setPower(-0.6);

        run("front", 65); //moves forward across over half the field
        idle();
        sleep(4000);
        finishRun();
        idle();

        spin("right", 3); //shifts to face the wobble goal place
        idle();
        sleep(500);
        finishRun();
        idle();

        dropWobble();

        spin("left", 3); //shifts to face the goals
        idle();
        sleep(500);
        finishRun();
        idle();

        run("back", 20); //leaves the wobble goal
        idle();
        sleep(2000);
        finishRun();
        idle();

        shiftRobot("right", 22); //moves towards the powershots
        idle();
        sleep(1500);
        finishRun();
        idle();

        shoot_motor.setPower(1.0);
        sleep(1000);
        shoot_motor.setPower(powerShoot);
        shootRing(); //1st ring

        shiftRobot("right", 6);
        idle();
        sleep(1000);
        finishRun();
        idle();

        shoot_motor.setPower(powerShoot);
        shootRing(); //2nd ring

        shiftRobot("right", 6);
        idle();
        sleep(1000);
        finishRun();
        idle();

        shoot_motor.setPower(powerShoot);
        shootRing(); //3rd ring

        shoot_motor.setPower(0);

        run("front", 5);
        idle();
        sleep(1000);
        finishRun();
        idle();

        /*run("front", 65);
        idle();
        sleep(4000);
        finishRun();
        idle();

        spin("right", 3); //shifts to face the wobble goal place
        idle();
        sleep(500);
        finishRun();
        idle();

        //dropWobble();

        spin("left", 3); //shifts to face the wobble goal place
        idle();
        sleep(500);
        finishRun();
        idle();

        run("back", 20); //leaves the wobble goal
        idle();
        sleep(2000);
        finishRun();
        idle();

        shiftRobot("right", 22); //moves towards the powershots
        idle();
        sleep(1500);
        finishRun();
        idle();

        shoot_motor.setPower(1.0);
        sleep(1000);
        shoot_motor.setPower(powerShoot);
        shootRing(); //1st ring

        shiftRobot("right", 6);
        idle();
        sleep(1000);
        finishRun();
        idle();

        shoot_motor.setPower(powerShoot);
        shootRing(); //2nd ring

        shiftRobot("right", 6);
        idle();
        sleep(1000);
        finishRun();
        idle();

        shoot_motor.setPower(powerShoot);
        shootRing(); //3rd ring

        shoot_motor.setPower(0);

        run("front", 5);
        idle();
        sleep(1000);
        finishRun();
        idle();*/

    }

    //***Autonomous Functions***
    public void shootRing(){

        //shoot ring
        sleep(2500);
        ringPush.setPower(-0.4);
        sleep(2000);
        ringPush.setPower(0);

    }

    public void run(String type, double distance)
    {

        int ticks = (int)((distance / (2 * Math.PI * wheelRadius) * gearRatio) * TicksperRev);



        if (type.equalsIgnoreCase("front")) {
            leftFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() - ticks);
            leftBackMotor.setTargetPosition(leftBackMotor.getCurrentPosition() - ticks);
            rightFrontMotor.setTargetPosition(rightFrontMotor.getCurrentPosition() - ticks);
            rightBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition() - ticks);
        }

        else if (type.equalsIgnoreCase("back")) {
            leftFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() + ticks);
            leftBackMotor.setTargetPosition(leftBackMotor.getCurrentPosition() + ticks);
            rightFrontMotor.setTargetPosition(rightFrontMotor.getCurrentPosition() + ticks);
            rightBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition() + ticks);
        }

        leftFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        leftFrontMotor.setPower(powerForward[0]);
        leftBackMotor.setPower(powerForward[0]);
        rightFrontMotor.setPower(powerForward[0]);
        rightBackMotor.setPower(powerForward[0]);
    }

    public void finishRun()
    {
        leftFrontMotor.setPower(0);
        leftBackMotor.setPower(0);
        rightFrontMotor.setPower(0);
        rightBackMotor.setPower(0);
    }

    public void spin(String type, double distance)
    {
        int ticks = (int)((distance / (2 * Math.PI * wheelRadius) * gearRatio) * TicksperRev);

        if (type.equalsIgnoreCase("right")) {
            leftFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() - ticks);
            leftBackMotor.setTargetPosition(leftBackMotor.getCurrentPosition() - ticks);
            rightFrontMotor.setTargetPosition(rightFrontMotor.getCurrentPosition() + ticks);
            rightBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition() + ticks);
        }

        else if (type.equalsIgnoreCase("left")) {
            leftFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() + ticks);
            leftBackMotor.setTargetPosition(leftBackMotor.getCurrentPosition() + ticks);
            rightFrontMotor.setTargetPosition(rightFrontMotor.getCurrentPosition() - ticks);
            rightBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition() - ticks);
        }

        leftFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftFrontMotor.setPower(powerSpin[0]);
        leftBackMotor.setPower(powerSpin[0]);
        rightFrontMotor.setPower(powerSpin[0]);
        rightBackMotor.setPower(powerSpin[0]);
    }

    public void turnRobot(String type, double distanceInRotations)
    {
        int partsRotationsLeft = (int)(distanceInRotations*TicksperRev);
        int partsRotationsRight = (int)(distanceInRotations*TicksperRev);

        if (type.equalsIgnoreCase("left")) {
            leftFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() - partsRotationsLeft);
            leftBackMotor.setTargetPosition(leftBackMotor.getCurrentPosition() - partsRotationsLeft);
            rightFrontMotor.setTargetPosition(rightFrontMotor.getCurrentPosition() + partsRotationsRight);
            rightBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition() + partsRotationsRight);
        }

        else if (type.equalsIgnoreCase("right")) {
            leftFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() + partsRotationsLeft);
            leftBackMotor.setTargetPosition(leftBackMotor.getCurrentPosition() + partsRotationsLeft);
            rightFrontMotor.setTargetPosition(rightFrontMotor.getCurrentPosition() - partsRotationsRight);
            rightBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition() - partsRotationsRight);
        }

        leftFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftFrontMotor.setPower(powerForward[0]);
        leftBackMotor.setPower(powerForward[0]);
        rightFrontMotor.setPower(powerForward[1]);
        rightBackMotor.setPower(powerForward[1]);
    }

    public void shiftRobot(String type, double distance)
    {
        int ticks = (int)((distance / (2 * Math.PI * wheelRadius) * gearRatio) * TicksperRev);


        if (type.equalsIgnoreCase("left")) {
            leftFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() + ticks);
            leftBackMotor.setTargetPosition(leftBackMotor.getCurrentPosition() - ticks);
            rightFrontMotor.setTargetPosition(rightFrontMotor.getCurrentPosition() - ticks);
            rightBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition() + ticks);
        }

        else if (type.equalsIgnoreCase("right")) {
            leftFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() - ticks);
            leftBackMotor.setTargetPosition(leftBackMotor.getCurrentPosition()  + ticks);
            rightFrontMotor.setTargetPosition(rightFrontMotor.getCurrentPosition() + ticks);
            rightBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition() - ticks);
        }

        leftFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        leftFrontMotor.setPower(powerForward[0]);
        leftBackMotor.setPower(powerForward[0]);
        rightFrontMotor.setPower(powerForward[0]);
        rightBackMotor.setPower(powerForward[0]);
    }

    public void diagRobot(String type, double distanceInRotations)
    {
        int partsRotationsLeft = (int)(distanceInRotations*TicksperRev);
        int partsRotationsRight = (int)(distanceInRotations*TicksperRev);

        double LF = 0, LB = 0, RF = 0, RB = 0;

        if (type.equalsIgnoreCase("left and up")) {
            LF = 0.0;
            RF = 0.4;
            RB = 0.0;
            LB = 0.4;
            rightFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() + partsRotationsRight);
            leftBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition() + partsRotationsLeft);
        }

        else if (type.equalsIgnoreCase("right and up")) {
            LF = 0.4;
            RF = 0.0;
            RB = 0.4;
            LB = 0.0;
            leftFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() + partsRotationsLeft);
            rightBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition() + partsRotationsRight);
        }

        else if (type.equalsIgnoreCase("left and down")) {
            LF = 0.4;
            RF = 0.0;
            RB = 0.4;
            LB = 0.0;
            leftFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() - partsRotationsLeft);
            rightBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition() - partsRotationsRight);
        }

        else if (type.equalsIgnoreCase("right and down")) {
            LF = 0.0;
            RF = 0.4;
            RB = 0.0;
            LB = 0.4;
            rightFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() - partsRotationsRight);
            leftBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition() - partsRotationsLeft);
        }

        leftFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftFrontMotor.setPower(LF);
        leftBackMotor.setPower(LB);
        rightFrontMotor.setPower(RF);
        rightBackMotor.setPower(RB);
    }

    public void dropWobble() {

        wobble_motor.setTargetPosition(wobble_motor.getCurrentPosition() + 300);
        sleep(1000);
        wobbleGrab.setPower(0.5);
        wobble_motor.setTargetPosition(wobble_motor.getCurrentPosition() - 300);

    }
}
