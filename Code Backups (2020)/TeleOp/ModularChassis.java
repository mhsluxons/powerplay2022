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

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

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
 */

public class ModularChassis
{
    //Wheels
    public DcMotor drive_left_front;
    public DcMotor drive_left_back;
    public DcMotor drive_right_front;
    public DcMotor drive_right_back;

    //Foundation



//Define motor numbers below
    //drive_left_front is motor 0
    //drive_right_front is motor 1
    //drive_right_back is motor 2
    //drive_left_back is motor 3

    //Other Motors

    //Servos


    //Continuous Servos

    //Booleans

    /* local OpMode members. */
    HardwareMap hardwareMap    =  null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public ModularChassis() {

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

        //Servos
        //rightClaw = hardwareMap.servo.get("claw_servo2");

        //Continuous Servos
        //intakeServo2 = hardwareMap.crservo.get("intake_servo2");

        //Set Servo
        //rightClaw.setPosition(1.0);

        drive_left_front.setDirection(DcMotorSimple.Direction.REVERSE);
        drive_left_back.setDirection(DcMotorSimple.Direction.REVERSE);
        drive_right_front.setDirection(DcMotor.Direction.FORWARD);
        drive_right_back.setDirection(DcMotor.Direction.FORWARD);
    }
}