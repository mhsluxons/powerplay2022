package org.firstinspires.ftc.robotcontroller.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

//by Yoonjae
//FACES BACKWARDS
@Autonomous(name = "Storage Red Hub Duck Park") //parks in the red storage area only
//@Disabled
public class LuxonsRedHubDuckPark extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    //Declares Motors on Wheels
    private DcMotor leftFrontMotor;
    private DcMotor leftBackMotor;
    private DcMotor rightFrontMotor;
    private DcMotor rightBackMotor;


    //intake inputs
    //private DcMotor intake_Motor;
    private DcMotor intake_motor;
    private final double intakeSpeed = 0.8;

    private DcMotor duck_motor;
    private final double duckSpeed = 0.9;

    private DcMotor shoot_motor;
    private final double shootSpeed = 0.8;


    //Other Motors
    public Servo rightGrab;
    public Servo leftGrab;
    public Servo rightClaw;
    public Servo leftClaw;
    public Servo cap;
    public Servo block_motor;
    public Servo plop_motor;

    //public Servo sidearm;

    //Values for the block deliverer
    public double BLOCK_INITIAL = 0.06;
    public double BLOCK_END = 0.56;

    public double PLOP_MOTOR_INITIAL = 0.5;
    public double PLOP_MOTOR_END = 1.0;

    //Declares Motor Function Arrays
    private final double gearRatio = 1.0;
    private final double wheelRadius = 2.0;
    private final double TicksperRev = 1500.0;
    private double powerForward[] = {0.6, 0.6};
    private double powerSpin[] = {1.0, 1.0};
    private double wheelPower = (powerForward[0]*0.1);

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

        intake_motor = hardwareMap.dcMotor.get("intake_motor");
        duck_motor = hardwareMap.dcMotor.get("duck_motor");
        shoot_motor = hardwareMap.dcMotor.get("shoot_motor");
        plop_motor = hardwareMap.servo.get("plop_motor");
        //sidearm = hardwareMap.servo.get("sidearm");
        //sidearm.setPosition(SIDE_ARM_INITIAL);

        leftFrontMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        leftBackMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);


        //*****
        intake_motor.setDirection(DcMotorSimple.Direction.REVERSE);
        intake_motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intake_motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        duck_motor.setDirection(DcMotorSimple.Direction.FORWARD);
        duck_motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        duck_motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        shoot_motor.setDirection(DcMotorSimple.Direction.FORWARD);
        shoot_motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        shoot_motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //******

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

        //move away from the wall
        run("front", 3);
        idle();
        sleep(1750);
        finishRun();
        idle();

        //pulls up lift
        shoot_motor.setPower(-shootSpeed);
        idle();
        sleep(2000);
        finishRun();
        idle();

        //plops down block
        plop_motor.setPosition(PLOP_MOTOR_END);
        idle();
        sleep(1500);
        finishRun();
        idle();

        //resets plop motor
        plop_motor.setPosition(PLOP_MOTOR_INITIAL);
        idle();
        sleep(1500);
        finishRun();
        idle();

        /*//brings down lift
        shoot_motor.setPower(shootSpeed);
        idle();
        sleep(4000);
        finishRun();
        idle();*/

        //moves away from the shipping hub
        run("back", 2);
        idle();
        sleep(1600);
        finishRun();
        idle();

        //moves towards duck spinner
        shiftRobot("left", 2);
        idle();
        sleep(1900);
        finishRun();
        idle();

        //turns around
        spin("left", 35);
        idle();
        sleep(2500);
        finishRun();
        idle();

        //adjusts correctly
        run("back", 1);
        idle();
        sleep(550);
        finishRun();
        idle();

        //goes towards duck spinner
        shiftRobot("right", 3);
        idle();
        sleep(2400);
        finishRun();
        idle();

        /*
        //moves to the duck spinner
        run("front", 1);
        idle();
        sleep(800);
        finishRun();
        idle();
        */


        //delivers duck
        duck_motor.setPower(-duckSpeed);
        idle();
        sleep(3000);
        finishRun();
        idle();

        duck_motor.setPower(0);
        idle();
        sleep(1000);
        finishRun();
        idle();

        //aligns robot to the storage park
        run("back", 3);
        idle();
        sleep(1900);
        finishRun();
        idle();

    }




    //***Autonomous Functions***
    public void run(String type, double distance)
    {

        int ticks = (int)((distance / (2 * Math.PI * wheelRadius) * gearRatio) * TicksperRev);

//        int backLeftPos = leftBackMotor.getCurrentPosition();
//        backLeftPos = backLeftPos + (ticks/8);
//
        int frontRightTicks = ticks*25/100;
        int backRightTicks = ticks*25/100;


        if (type.equalsIgnoreCase("front")) {
            leftFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() + ticks);
            leftBackMotor.setTargetPosition(leftBackMotor.getCurrentPosition() + ticks);
            rightFrontMotor.setTargetPosition(rightFrontMotor.getCurrentPosition() + ticks);
            rightBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition() + ticks);
        }

        else if (type.equalsIgnoreCase("back")) {
            leftFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() - ticks);
            leftBackMotor.setTargetPosition(leftBackMotor.getCurrentPosition() - ticks);
            rightFrontMotor.setTargetPosition(rightFrontMotor.getCurrentPosition() - ticks);
            rightBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition() - ticks);
        }

        leftFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        double frontRPowertemp = powerForward[0] *25/100;
        double backRPowertemp = powerForward[0] *25/100;


        leftFrontMotor.setPower(wheelPower);
        leftBackMotor.setPower(wheelPower);
        rightFrontMotor.setPower(wheelPower);
        rightBackMotor.setPower(wheelPower);
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
            leftFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() + ticks);
            leftBackMotor.setTargetPosition(leftBackMotor.getCurrentPosition() + ticks);
            rightFrontMotor.setTargetPosition(rightFrontMotor.getCurrentPosition() - ticks);
            rightBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition() - ticks);
        }

        else if (type.equalsIgnoreCase("left")) {
            leftFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() - ticks);
            leftBackMotor.setTargetPosition(leftBackMotor.getCurrentPosition()  - ticks);
            rightFrontMotor.setTargetPosition(rightFrontMotor.getCurrentPosition() + ticks);
            rightBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition() + ticks);
        }

        leftFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftFrontMotor.setPower(wheelPower);
        leftBackMotor.setPower(wheelPower);
        rightFrontMotor.setPower(wheelPower);
        rightBackMotor.setPower(wheelPower);
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
//        double tempDistance = distance * 32/25;
        int ticks = (int)((distance / (2 * Math.PI * wheelRadius) * gearRatio) * TicksperRev);

//        int backLeftPos = leftBackMotor.getCurrentPosition();
//        backLeftPos = backLeftPos + (ticks/8);
//
//        int frontRightTicks = ticks*70/100;
//        int backRightTicks = ticks*80/100;


        if (type.equalsIgnoreCase("left")) {
            leftFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() - ticks);
            leftBackMotor.setTargetPosition(leftBackMotor.getCurrentPosition() + ticks);
            rightFrontMotor.setTargetPosition(rightFrontMotor.getCurrentPosition() + ticks);
            rightBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition() - ticks);
        }

        else if (type.equalsIgnoreCase("right")) {
            leftFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() + ticks);
            leftBackMotor.setTargetPosition(leftBackMotor.getCurrentPosition()  - ticks);
            rightFrontMotor.setTargetPosition(rightFrontMotor.getCurrentPosition() - ticks);
            rightBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition() + ticks);
        }

        leftFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

//        double frontRPowertemp = powerForward[0] *65/100;
//        double backRPowertemp = powerForward[0] *65/100;

        leftFrontMotor.setPower(wheelPower);
        leftBackMotor.setPower(wheelPower);
        rightFrontMotor.setPower(wheelPower);
        rightBackMotor.setPower(wheelPower);
    }

    public void diagRobot(String type, double distanceInRotations)
    {
        int partsRotationsLeft = (int) (distanceInRotations * TicksperRev);
        int partsRotationsRight = (int) (distanceInRotations * TicksperRev);

        double LF = 0, LB = 0, RF = 0, RB = 0;

        if (type.equalsIgnoreCase("left and up")) {
            LF = 0.0;
            RF = 0.4;
            RB = 0.0;
            LB = 0.4;
            rightFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() + partsRotationsRight);
            leftBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition() + partsRotationsLeft);
        } else if (type.equalsIgnoreCase("right and up")) {
            LF = 0.4;
            RF = 0.0;
            RB = 0.4;
            LB = 0.0;
            leftFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() + partsRotationsLeft);
            rightBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition() + partsRotationsRight);
        } else if (type.equalsIgnoreCase("left and down")) {
            LF = 0.4;
            RF = 0.0;
            RB = 0.4;
            LB = 0.0;
            leftFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() - partsRotationsLeft);
            rightBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition() - partsRotationsRight);
        } else if (type.equalsIgnoreCase("right and down")) {
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





}