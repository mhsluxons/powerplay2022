package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="TeleOp")
@Disabled
public class TeleOpMode extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();

    //Doubles
    static final double COUNTS_PER_MOTOR_REV    = 900 ;    // eg: TETRIX Motor Encoder
    //static final double SCISSOR_COUNTS_PER_CLICK = (COUNTS_PER_MOTOR_REV * 50) / (360 * 8);  // scissor lift can go only 50 degrees
    //static final double     SCISSOR_DRIVE_POWER         = 0.8; //the amount of power the fake scissor lift can use
    // static final double     CLAW_DRIVE_POWER         = 0.6;
    static final double     LIFT_DRIVE_POWER         = 0.4;

    //static int scissorNewTarget;
    //static int scissorCurrentPosition;
    //static double scissorDrivePower = SCISSOR_DRIVE_POWER;

/*
    static final double CLAW_FULL_ROTATION = COUNTS_PER_MOTOR_REV * 3.5;
    static final double CLAW_COUNTS_PER_CLICK = CLAW_FULL_ROTATION / 10;
    static int clawNewTarget;
    static int clawCurrentPosition;
    static double clawDrivePower = CLAW_DRIVE_POWER;
*/
    static final double LIFT_FULL_ROTATION = 1440 * 2.6;
    static final double LIFT_COUNTS_PER_CLICK = LIFT_FULL_ROTATION / 5;
    static int liftNewTarget1;
    static int liftCurrentPosition1;
    static int liftNewTarget2;
    static int liftCurrentPosition2;
    static double liftDrivePower = LIFT_DRIVE_POWER;

    //Robot Object
    LuxonsRobot robot = new LuxonsRobot();

    @Override
    public void init() {
        robot.init(hardwareMap);
    }

    @Override
    public void loop() {
        //Gamepad 1:
        //=========================================

        //Wheels
        /*
            gamepad1.left_stick_y  -- Move forward / backward
            gamepad1.left_trigger - Move sideways left
            gamepad1.right_trigger - Move sideways right
            gamepad1.right_stick_x  - Spin left / right
        */
        moveRobot();

        //Lift Motors
        /*
           gamepad1.dpad_up - Lift motor up
           gamepad1.dpad_down  - Lift motor down

         */

        //Lifter
        lift();

        //Gamepad 2:
        //====================================

        // gamepad2.left_stick_y - scissor up/down
        //scissor
        //moveScissor();

        // gamepad2.right_stick_y - Claw hand move forward / backward
        //Claw Motor
        //!!!!!!!moveClaw();

        //Intake Method
        //intake();

        //Move Servos
        moveServos();
    }

    public void lift() {
        if (gamepad1.dpad_up) {
            robot.lift_motor1.setPower(0.4);
            robot.lift_motor2.setPower(0.4);
        }
        else if (gamepad1.dpad_down) {
            robot.lift_motor1.setPower(-0.4);
            robot.lift_motor2.setPower(-0.4);
        }else if (!gamepad1.dpad_up && !gamepad1.dpad_down){
            robot.lift_motor1.setPower(0);
            robot.lift_motor2.setPower(0);
        }
    }

    public final void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

/*
    public void moveClaw(){
        clawDrivePower =  CLAW_DRIVE_POWER;
        if (gamepad2.right_stick_y == -1) {
            clawNewTarget = clawCurrentPosition + (int) CLAW_COUNTS_PER_CLICK;
            if (clawNewTarget > CLAW_FULL_ROTATION){
                clawNewTarget = (int) CLAW_FULL_ROTATION;
            }
            robot.claw_motor.setTargetPosition(clawNewTarget);
            robot.claw_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.claw_motor.setPower(0.4);

        }else if (gamepad2.right_stick_y == 1) {
            clawNewTarget = clawCurrentPosition - (int)CLAW_COUNTS_PER_CLICK;
            if (clawNewTarget < 1){
                clawNewTarget = 10;
            }
            robot.claw_motor.setTargetPosition(clawNewTarget);
            robot.claw_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.claw_motor.setPower(clawDrivePower);
        }

        clawCurrentPosition = robot.claw_motor.getCurrentPosition();
}
*/

    public void moveRobot() { //Edited By: Sushen Kolakaleti
        //Stick Values
        double positiveY = 1.0 * ((gamepad1.left_stick_y) * (gamepad1.left_stick_y));
        double negativeY = -1.0 * ((gamepad1.left_stick_y) * (gamepad1.left_stick_y));
        double positiveX = 1.0 * ((gamepad1.right_stick_x) * (gamepad1.right_stick_x));
        double negativeX = -1.0 * ((gamepad1.right_stick_x) * (gamepad1.right_stick_x));

        //Trigger Values
        double moveLeftTrigger = 1.0 * ((gamepad1.left_trigger) * (gamepad1.left_trigger));
        double moveRightTrigger = 1.0 * ((gamepad1.right_trigger) * (gamepad1.right_trigger));

        //Gamepad 1:

        //Wheels
        if (gamepad1.left_stick_y < 0.0) {
            robot.drive_left_front.setPower(positiveY);
            robot.drive_left_back.setPower(positiveY);
            robot.drive_right_front.setPower(positiveY);
            robot.drive_right_back.setPower(positiveY);
        }

        if (gamepad1.left_stick_y > 0.0) {
            robot.drive_left_front.setPower(negativeY);
            robot.drive_left_back.setPower(negativeY);
            robot.drive_right_front.setPower(negativeY);
            robot.drive_right_back.setPower(negativeY);
        }

        if (gamepad1.right_stick_x < 0.0) {
            robot.drive_left_front.setPower(negativeX);
            robot.drive_left_back.setPower(negativeX);
            robot.drive_right_front.setPower(positiveX);
            robot.drive_right_back.setPower(positiveX);
        }

        if (gamepad1.right_stick_x > 0.0) {
            robot.drive_left_front.setPower(positiveX);
            robot.drive_left_back.setPower(positiveX);
            robot.drive_right_front.setPower(negativeX);
            robot.drive_right_back.setPower(negativeX);
        }

        //Lateral Movement
        if (gamepad1.left_trigger > 0.0) {
            robot.drive_left_front.setPower(-1.0 * (moveLeftTrigger));
            robot.drive_left_back.setPower(moveLeftTrigger);
            robot.drive_right_front.setPower(moveLeftTrigger);
            robot.drive_right_back.setPower(-1.0 * (moveLeftTrigger));
        }

        if (gamepad1.right_trigger > 0.0) {
            robot.drive_left_front.setPower(moveRightTrigger);
            robot.drive_left_back.setPower(-1.0 * (moveRightTrigger));
            robot.drive_right_front.setPower(-1.0 * (moveRightTrigger));
            robot.drive_right_back.setPower(moveRightTrigger);
        }

        //Stop Robot
        if (gamepad1.left_stick_y == 0.0 || gamepad1.left_stick_x == 0.0 || gamepad1.left_trigger == 0.0 || gamepad1.right_trigger == 0.0) {
            robot.drive_left_front.setPower(0);
            robot.drive_left_back.setPower(0);
            robot.drive_right_front.setPower(0);
            robot.drive_right_back.setPower(0);
        }
    }
/*
    public void moveScissor(){
        //motor 1 on the fake scissor lift
        scissorDrivePower =  SCISSOR_DRIVE_POWER;
        if (gamepad2.left_stick_y == 1) {
            scissorNewTarget = scissorCurrentPosition + (int)(SCISSOR_COUNTS_PER_CLICK);
            if (scissorNewTarget > 320){
                scissorNewTarget = 320;
            }

            robot.basket_motor.setTargetPosition(scissorNewTarget);
            if (scissorNewTarget > 240){
                scissorDrivePower = 0.6;
            }
            robot.basket_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.basket_motor.setPower(scissorDrivePower);

        }else if (gamepad2.left_stick_y == -1) {
            scissorDrivePower = 0.2;
            scissorNewTarget = scissorCurrentPosition - (int)(0.5 * SCISSOR_COUNTS_PER_CLICK);
            if (scissorNewTarget < 1){
                scissorNewTarget = -20;
            }
            robot.basket_motor.setTargetPosition(scissorNewTarget);
            if (scissorNewTarget < 100){
                scissorDrivePower = 0.1;
            }
            robot.basket_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.basket_motor.setPower(scissorDrivePower);

            if (scissorNewTarget < 100){
                sleep(150);
            }
        }

        scissorCurrentPosition = robot.basket_motor.getCurrentPosition();
*/

    public void intake() {
        //Sets Rotation Values (on click of y):
        if (gamepad2.y) {
            if (robot.startRotationIntake == false) {
                robot.startRotationIntake = true;
            }

            else if (robot.startRotationIntake == true) {
                robot.startRotationIntake = false;
            }
        }

        //Opens the Cargo Hatch, and then Closes it:
        if (gamepad2.b) {
            if (robot.startRotationRelease == false) {
                robot.startRotationIntake = false;
                robot.startRotationRelease = true;
            }

            else if (robot.startRotationRelease == true) {
                robot.startRotationRelease = false;
            }
        }

        //Runs the Motors if the Intake Value is True:
        if (robot.startRotationIntake == true) {
            robot.intakeServo1.setPower(0);
            robot.intakeServo2.setPower(0);

            sleep(500); //Servo Pause

            robot.intakeServo1.setPower(0.4);
            robot.intakeServo2.setPower(-0.4);
            sleep(500); //Servo Pause
        }

        else if (robot.startRotationIntake == false) {
            sleep(100); //Servo Pause

            robot.intakeServo1.setPower(0);
            robot.intakeServo2.setPower(0);
            sleep(500); //Servo Pause
        }
    }

    public void moveServos() {
        //Claw Servos
        if (gamepad2.y) {
            //Moving Servos
            if (robot.clawOpen == false) {
                robot.clawOpen = true;

                robot.rightClaw.setPosition(0.2);
                sleep(500); //Servo Pause
            }

            else if (robot.clawOpen == true) {
                robot.clawOpen = false;

                robot.rightClaw.setPosition(1.0);
                sleep(500); //Servo Pause
            }
        }

        // Team Marker Dropper
        if (gamepad2.a) {
            if (robot.markerDown == false) {
                robot.markerDown = true;

                robot.teamMD.setPosition(robot.START_SERVO);
            }

            else if (robot.markerDown == true) {
                robot.markerDown = false;

                robot.teamMD.setPosition(robot.MID_SERVO);
            }
        }
    }

    @Override
    public void stop() {

    }
}
