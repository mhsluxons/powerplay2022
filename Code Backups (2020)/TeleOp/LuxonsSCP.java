package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
//import java.time.*;

@TeleOp(name="SCP", group="Tele-Opmode-2019")
public abstract class LuxonsSCP extends OpMode {
//    private ElapsedTime runtime = new ElapsedTime();
//
//    //Doubles
//    static final double COUNTS_PER_MOTOR_REV    = 900 ;    // eg: TETRIX Motor Encoder
//    static final double LIFT_COUNTS_PER_CLICK = (COUNTS_PER_MOTOR_REV * 90) / (360 * 10);  // basket can go only 120 degrees
//    static final double     LIFT_DRIVE_POWER         = 0.8;
//
//    private int leftLiftNewTarget;
//    private int leftLiftCurrentPosition;
//    private int rightLiftNewTarget;
//    private int rightLiftCurrentPosition;
//    private double liftDrivePower = LIFT_DRIVE_POWER;
//
//    double currentRPos;
//    double currentLPos;
//
//    long start = System.currentTimeMillis();
//    long finish = System.currentTimeMillis();
//    long timeElapsed = finish - start;
//
//    public int msStuckDetectInit     = 10000;
//    public int msStuckDetectInitLoop = 10000;
//    public int msStuckDetectStart    = 10000;
//    public int msStuckDetectLoop     = 10000;
//    public int msStuckDetectStop     = 5000;
//
//
//
//    int[] foundL = {};
//    int[] liftL = {};
//    int[] intakeL = {};
//    int[] sRightL = {};
//    int[] sLeftL = {};
//    int[] sUpL = {};
//    int[] sDownL = {};
//
//    int foundT;
//    int liftT;
//    int intakeT;
//    int sRightT;
//    int sLeftT;
//    int sUpT;
//    int sDownT;
//
//    LuxonsRobot2019 robot = new LuxonsRobot2019();
//
//    @Override
//    public void init() {
//        robot.init(hardwareMap);
//
//
//    }
//
//    @Override
//    public void loop() {
//        telemetry.addData("Status", "left_lift :" + leftLiftCurrentPosition + ": "+ rightLiftCurrentPosition);
//        telemetry.addData("Status", "Claw:" + currentRPos + ": "+ currentLPos);
//
//        telemetry.update();
//
//        if(Math.sqrt(2)/2 < gamepad1.left_stick_x && gamepad1.left_stick_x < Math.sqrt(2)/2 && gamepad1.left_stick_y > Math.sqrt(2)/2) {
//
//        }
//
//        //Gamepad 1:
//        //=========================================
//
//        //Wheels
//        /*
//            gamepad1.left_stick_y  -- Move forward / backward
//            gamepad1.left_trigger - Move sideways left
//            gamepad1.right_trigger - Move sideways right
//            gamepad1.right_stick_x  - Spin left / right
//        */
////        if(gamepad1.x) {
////            if (motorMode == 1) {
////                motorMode = 2;
////            } else if (motorMode == 2) {
////                motorMode = 1;
////            }
////        }
//        robotMove();
//
////        if (gamepad1.y) {
////            robot.right_grab.setPosition(robot.RIGHT_GRAB_END);
////            robot.leftGrab.setPosition(robot.LEFT_GRAB_END);
////            sleep(1000); // pause for servos to move
////        }
////
////
////        if (gamepad1.a){
////            robot.rightGrab.setPosition(robot.RIGHT_GRAB_INITIAL);
////            robot.leftGrab.setPosition(robot.LEFT_GRAB_INITIAL);
////            sleep(1000);     // pause for servos to move
////        }
//
//
//        //Gamepad 2:
//        //====================================
//
//        // gamepad2.left_stick_y - Lift up/down
//        //Lift
//        moveLift();
//
//        //Claw Servos
//        if (gamepad2.y) {
//            currentRPos = robot.rightClaw.getPosition();
//            if (currentRPos <= 0.15){
//                currentRPos = currentRPos + 0.30;
//            }else {
//                currentRPos = currentRPos + 0.15;
//            }
//            currentRPos = Range.clip(currentRPos, 0.15, 0.6);
//            currentRPos = roundAvoid(currentRPos, 3);
//            robot.rightClaw.setPosition(currentRPos);
//
//            currentLPos = robot.leftClaw.getPosition();
//            if (currentLPos >= 0.85){
//                currentLPos = currentLPos - 0.30;
//            }else {
//                currentLPos = currentLPos - 0.15;
//            }
//            //currentLPos = currentLPos - 0.15;
//
//            currentLPos = Range.clip(currentLPos, 0.40, 0.85);
//            currentLPos = roundAvoid(currentLPos, 3);
//            robot.leftClaw.setPosition(currentLPos);
//            sleep(1000); // pause for servos to move
//        }
//
//        if (gamepad2.a){
//            currentRPos = robot.rightClaw.getPosition();
//            if (currentRPos >= 0.60){
//                currentRPos = currentRPos - 0.15;
//            }else if (currentRPos >= 0.30){
//                currentRPos = currentRPos - 0.30;
//            }
//            currentRPos = Range.clip(currentRPos, 0.15, 0.6);
//            currentRPos = roundAvoid(currentRPos, 3);
//            robot.rightClaw.setPosition(currentRPos);
//
//            currentLPos = robot.leftClaw.getPosition();
//            if (currentLPos <= 0.15){
//                currentLPos = currentLPos + 0.30;
//            }else {
//                currentLPos = currentLPos + 0.15;
//            }
//
//            //currentLPos = currentLPos + 0.15;
//
//            currentLPos = Range.clip(currentLPos, 0.40, 0.85);
//            currentLPos = roundAvoid(currentLPos, 3);
//            robot.leftClaw.setPosition(currentLPos);
//            sleep(1000);     // pause for servos to move
//        }
//
//    }
//
//    public static double roundAvoid(double value, int places) {
//        double scale = Math.pow(10, places);
//        return Math.round(value * scale) / scale;
//    }
//
////    public void foundationMove() {
////        if(gamepad1.y) {
////            double leftFoundMove = 0.85;
////            double rightFoundMove = 0.15;
////
////            robot.rightGrab.setPosition(leftFoundMove);
////            robot.leftGrab.setPosition(rightFoundMove);
////
////        }
////    }
//
//
//    public final void sleep(long milliseconds) {
//        try {
//            Thread.sleep(milliseconds);
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//    }
//
//
//
//    public void robotMove() {
//        double drivePower;
//
//
//        double drive = gamepad1.left_stick_y;
//        double turnLeft  =  gamepad1.right_trigger;
//        double turnRight  =  gamepad1.left_trigger;
//        double spin = -gamepad1.right_stick_x;
//        double turnValue = 1.0;
//
//        if (gamepad1.dpad_up){
//            drive = -1.0 * 40 / 100;
//        }else if (gamepad1.dpad_down){
//            drive = 1.0 * 40 / 100;
//        }else if (gamepad1.dpad_right){
//            turnLeft = 1.0 * 40 / 100;
//        }else if (gamepad1.dpad_left){
//            turnRight = 1.0 * 40 / 100;
//        }
//
//        drivePower    = Range.clip(drive, -1.0, 1.0) ;
//
//        double frontLPower = drivePower;
//        double frontRPower = drivePower;
//        double backLPower = drivePower;
//        double backRPower = drivePower;
//
//        if (spin > 0 || spin < 0) {
//            frontRPower = -spin;
//            backRPower = -spin;
//            frontLPower = spin;
//            backLPower = spin;
//        }
//
//        else if (turnRight == 1) {
//            frontRPower = -turnValue;
//            backLPower = -turnValue;
//            frontLPower = turnValue;
//            backRPower = turnValue;
//        }
//
//        else if (turnLeft == 1) {
//            frontRPower = turnValue;
//            backLPower = turnValue;
//            frontLPower = -turnValue;
//            backRPower = -turnValue;
//        }
//
//        double frontRPowertemp = frontRPower *70/100;
//        double backRPowertemp = backRPower *70/100;
//
//        if(spin > 0 || spin < 0) {
//            backRPowertemp =  backRPowertemp * 40/100;
//            backRPowertemp = Range.clip(backRPowertemp, -1.0, 1.0);
//
//            backLPower = backLPower * 40/100;
//            backLPower = Range.clip(backLPower, -1.0, 1.0);
//
//            frontRPowertemp = frontRPowertemp * 40/100;
//            frontRPowertemp = Range.clip(frontRPowertemp, -1.0, 1.0);
//
//            frontLPower = frontLPower * 40/100;
//            frontLPower = Range.clip(frontLPower, -1.0, 1.0);
//
//        }
//
//
//        // Send calculated power to wheels
//        robot.drive_right_back.setPower(backRPowertemp);
//        robot.drive_left_back.setPower(backLPower);
//        robot.drive_right_front.setPower(frontRPowertemp);
//        robot.drive_left_front.setPower(frontLPower);
//    }
//
//    public void moveLift(){
//        liftDrivePower =  LIFT_DRIVE_POWER;
//        leftLiftCurrentPosition = robot.left_lift_motor.getCurrentPosition();
//        rightLiftCurrentPosition = robot.right_lift_motor.getCurrentPosition();
//        if (gamepad2.left_stick_y == 1) {
//
//            leftLiftNewTarget = leftLiftCurrentPosition + (int)(LIFT_COUNTS_PER_CLICK);
//            rightLiftNewTarget = rightLiftCurrentPosition +  (int)(LIFT_COUNTS_PER_CLICK);
//            if (leftLiftNewTarget > 410){
//                leftLiftNewTarget = 410;
//            }
//            if (rightLiftNewTarget > 410){
//                rightLiftNewTarget = 410;
//            }
//            robot.left_lift_motor.setTargetPosition(leftLiftNewTarget);
//            robot.right_lift_motor.setTargetPosition(rightLiftNewTarget);
//            if (leftLiftNewTarget < 100 | rightLiftNewTarget < 100){
//                liftDrivePower = 1.0;
//            }
//            robot.left_lift_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            robot.right_lift_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            robot.left_lift_motor.setPower(liftDrivePower);
//            robot.right_lift_motor.setPower(liftDrivePower);
//
//        }else if (gamepad2.left_stick_y == -1) {
//            liftDrivePower = LIFT_DRIVE_POWER ;
//            leftLiftNewTarget = leftLiftCurrentPosition - (int)(LIFT_COUNTS_PER_CLICK);
//            rightLiftNewTarget = rightLiftCurrentPosition -  (int)(LIFT_COUNTS_PER_CLICK);
//
//            if (leftLiftNewTarget < 10){
//                leftLiftNewTarget = 0;
//            }
//            if (rightLiftNewTarget < 10){
//                rightLiftNewTarget = 0;
//            }
//
//            robot.left_lift_motor.setTargetPosition(leftLiftNewTarget);
//            robot.right_lift_motor.setTargetPosition(rightLiftNewTarget);
//
//            robot.left_lift_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            robot.right_lift_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            robot.left_lift_motor.setPower(liftDrivePower);
//            robot.right_lift_motor.setPower(liftDrivePower);
//        } else {
//            leftLiftCurrentPosition = robot.left_lift_motor.getCurrentPosition();
//            rightLiftCurrentPosition = robot.right_lift_motor.getCurrentPosition();
//            robot.left_lift_motor.setTargetPosition(leftLiftCurrentPosition);
//            robot.right_lift_motor.setTargetPosition(rightLiftCurrentPosition);
//
//            robot.left_lift_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            robot.right_lift_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            robot.left_lift_motor.setPower(liftDrivePower);
//            robot.right_lift_motor.setPower(liftDrivePower);
//        }
//
//    }
//
//    @Override
//    public void stop() {
//
//    }
}