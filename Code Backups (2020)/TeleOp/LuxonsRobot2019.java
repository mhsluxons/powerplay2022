//Comments and stuff
/*
   Mother Code for Team #10588 THE LUXONS, of Marquette High School Robotics.
   All liscences, terms and lists will be found here.

   **CopyRight-LUXONS 2019**, USES PUBLIC DOMAIN CODE FROM GITHUB LLC, from other libraries.
   Authors: SAMEER IYER, Sam Lee, Adi Bhargava, Samir Faruq

   !!WARNING!! NO EDITS ARE TO BE MADE, BY ANYONE!!! WITHOUT THE CONSENT OF SAMEER IYER.

   CLASSES UNDER LICENCE:

   - Autonomous:
   LuxonsAutonomousRobot.java
   (Name TBD).java
   TMA.java
   TMA_C.java
   Autonomous_TBD.java
   Autonomous_TBD.java

   - TeleOp:
   LuxonsRobot.java
   LuxonsTeleOpMode.java
   LuxonsTeleOpModeNew.java
   TeleOpMode.java

   Last Edit By: Samir Faruq on 10/19/2019
 */

package org.firstinspires.ftc.teamcode.TeleOp;

//Imports
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Vision.Camera;


/**
 * This is NOT an opmode.
 *
 * This class can be used to define all the specific hardware for a single robot.
 * In this case that robot is a Pushbot.
 * See PushbotTeleopTank_Iterative and others classes starting with "Pushbot" for usage examples.
 *
 * This hardware class assumes the following device names have been configured on the robot:
 * Note:  All names are lower case and some have single spaces between words.
 *
 * Motor channel:  Left  drive motor:        "left_drive"
 * Motor channel:  Right drive motor:        "right_drive"
 * Motor channel:  Manipulator drive motor:  "left_arm"
 * Servo channel:  Servo to open left claw:  "left_hand"
 * Servo channel:  Servo to open right claw: "right_hand"
 *
 */

public class LuxonsRobot2019
{
    //Wheels
    public DcMotor drive_left_front;
    public DcMotor drive_left_back;
    public DcMotor drive_right_front;
    public DcMotor drive_right_back;

    public Camera camera = new Camera();

//Define motor numbers below
    //drive_left_front is motor 0
    //drive_right_front is motor 1
    //drive_right_back is motor 2
    //drive_left_back is motor 3

    //Other Motors
    public DcMotor grab_motor;
    public DcMotor shoot_motor;
    public DcMotor wobble_motor;

//    public DcMotor left_grab_motor;
//    public DcMotor right_grab_motor;

    //Servos
//    public Servo rightClaw;
//    public Servo leftClaw;
//    public Servo stoneGrab;
      public CRServo ringPush;
//    public CRServo wobbleLift;
      public CRServo wobbleGrab;
      public Servo sidearm;
      public Servo sidegrab;
//    public Servo cap;


    /*public double RIGHT_CLAW_INITIAL = 0.75;
    public double LEFT_CLAW_INITIAL =  0.82;
    public double RIGHT_CLAW_END = 0.15;
    public double LEFT_CLAW_END =  0.20;

    public double STONE_GRAB_INITIAL = 0.05;
    public double SIDE_ARM_INITIAL = 0.06;
    public double SIDE_ARM_END = 0.56;
    public double CAP_INITIAL = 0.25;
    public double CAP_END = 0.55;

    public int left_lift_initial = 0;
    public int right_lift_initial = 0;*/


    /* local OpMode members. */
    HardwareMap hardwareMap    =  null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public LuxonsRobot2019() {

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hardwareMap = ahwMap;

        drive_left_front = hardwareMap.dcMotor.get("drive_left_front");
        drive_left_back = hardwareMap.dcMotor.get("drive_left_back");
        drive_right_front = hardwareMap.dcMotor.get("drive_right_front");
        drive_right_back = hardwareMap.dcMotor.get("drive_right_back");

        //Motors
//        left_lift_motor = hardwareMap.dcMotor.get("left_lift");
//        right_lift_motor = hardwareMap.dcMotor.get("right_lift");
//
//        //Grabbers
            grab_motor = hardwareMap.dcMotor.get("grab_motor");
           shoot_motor = hardwareMap.dcMotor.get("shoot_motor");
           wobble_motor = hardwareMap.dcMotor.get("wobble_motor");
////        //Servos
//        rightClaw = hardwareMap.servo.get("right_claw");
//        leftClaw = hardwareMap.servo.get("left_claw");
//        stoneGrab = hardwareMap.servo.get("stone_grab");
        ringPush = hardwareMap.crservo.get("ring_push");
//      wobbleLift = hardwareMap.crservo.get("wobble_lift");
        wobbleGrab = hardwareMap.crservo.get("wobble_grab");
//        sidearm = hardwareMap.servo.get("sidearm");
//        cap = hardwareMap.servo.get("cap");


//        leftClaw.setDirection(Servo.Direction.REVERSE);
//        leftClaw.setPosition(LEFT_CLAW_INITIAL);
//        rightClaw.setPosition(RIGHT_CLAW_INITIAL);
//        stoneGrab.setPosition(STONE_GRAB_INITIAL);
//        ringPush.setDirection(CRServo.Direction.REVERSE);
//        sidearm.setPosition(SIDE_ARM_INITIAL);
//        cap.setPosition(CAP_INITIAL);


        drive_left_front.setDirection(DcMotorSimple.Direction.REVERSE);
        drive_left_back.setDirection(DcMotorSimple.Direction.REVERSE);
        drive_right_front.setDirection(DcMotor.Direction.FORWARD);
        drive_right_back.setDirection(DcMotor.Direction.FORWARD);

//        left_lift_motor.setDirection(DcMotorSimple.Direction.FORWARD);
//        left_lift_motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        left_lift_motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//        right_lift_motor.setDirection(DcMotorSimple.Direction.REVERSE);
//        right_lift_motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        right_lift_motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
        grab_motor.setDirection(DcMotorSimple.Direction.FORWARD);
        shoot_motor.setDirection(DcMotorSimple.Direction.FORWARD);
        wobble_motor.setDirection(DcMotorSimple.Direction.FORWARD);

        wobble_motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        wobble_motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//        right_grab_motor.setDirection(DcMotorSimple.Direction.REVERSE);

    }
 }

