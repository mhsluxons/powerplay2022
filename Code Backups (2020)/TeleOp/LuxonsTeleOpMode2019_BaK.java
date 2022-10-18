////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
////Imports necessary items for the robot to run
////Sets robot name and class
//package org.firstinspires.ftc.robotcontroller.TeleOp;
//
//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.util.ElapsedTime;
//import com.qualcomm.robotcore.util.Range;
//
//@TeleOp(name="Lux Tele-OpMode 2019", group="Tele-Opmode-2019-Bak")
//@Disabled
//public class LuxonsTeleOpMode2019_BaK extends OpMode {
//    private ElapsedTime runtime = new ElapsedTime();
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//    //Creates most variables used throughout the program
//    //Variables created include motor counts, targeting, and positioning
//    static final double COUNTS_PER_MOTOR_REV = 900 ;
//    static final double LIFT_COUNTS_PER_CLICK = COUNTS_PER_MOTOR_REV;
//    static final double LIFT_DRIVE_POWER = 0.8;
//
//    private int leftLiftNewTarget;
//    private int leftLiftCurrentPosition;
//    private int rightLiftNewTarget;
//    private int rightLiftCurrentPosition;
//    private double liftDrivePower = LIFT_DRIVE_POWER;
//    double grabPower = 0.6;
//
//    double currentRPos;
//    double currentLPos;
//    double currentGPos;
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//    //Defines all pieces necessary
//    //Defines the robot, and motors
//    LuxonsRobot2019 robot = new LuxonsRobot2019();
//    @Override
//    public void init() {
//          robot.init(hardwareMap);
//      }
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//    //Loop updates all functions necessary
//    //Loop updates positions and robot movement
//    @Override
//    public void loop() {
//
//        telemetry.addData("Status", "left_lift :" + leftLiftCurrentPosition + ": "+ rightLiftCurrentPosition);
//        telemetry.addData("Status", "Claw:" + currentRPos + ": "+ currentLPos);
//
//        telemetry.update();
//        robotMove();
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//        //Press Y to use grabbers
//        //Press A to reverse grabbers
//        //If not pressing stop
//        if (gamepad2.y) {
//            robot.left_grab_motor.setPower(grabPower);
//            robot.right_grab_motor.setPower(grabPower);
//        }else if (gamepad2.a){
//            robot.left_grab_motor.setPower(-grabPower);
//            robot.right_grab_motor.setPower(-grabPower);
//        }else {
//            robot.left_grab_motor.setPower(0);
//            robot.right_grab_motor.setPower(0);
//        }
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//        //Stone Grab Servos
//        if (gamepad2.x) {
//            currentGPos = robot.stoneGrab.getPosition();
//            currentGPos = robot.STONE_GRAB_INITIAL + 0.3;
//
//            robot.stoneGrab.setPosition(currentGPos);
//            sleep(1000); // pause for servos to move
//        }
//
//        if (gamepad2.b){
//            currentGPos = robot.stoneGrab.getPosition();
//            currentGPos = robot.STONE_GRAB_INITIAL;
//            robot.stoneGrab.setPosition(currentGPos);
//            sleep(1000);     // pause for servos to move
//        }
//
//        //capstone
//        if (gamepad2.left_bumper){
//
//            double currentCPos = robot.CAP_INITIAL - 0.05;
//            robot.cap.setPosition(currentCPos);
//            sleep(1000);     // pause for servos to move
//        }
//
//        if (gamepad2.right_bumper){
//
//            double currentCPos = robot.CAP_INITIAL;
//            robot.cap.setPosition(currentCPos);
//            sleep(1000);     // pause for servos to move
//        }
//
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//        //Lift
//        moveLift();
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//        //Controls used for foundation "claws"
//        //Press Y to move claws up
//        //Press A to move claws down
//        if (gamepad1.y) {
//            currentRPos = robot.rightClaw.getPosition();
//            currentRPos = robot.RIGHT_CLAW_INITIAL + 0.6;
//
//           robot.rightClaw.setPosition(currentRPos);
//
//            currentLPos = robot.leftClaw.getPosition();
//            currentLPos = robot.LEFT_CLAW_INITIAL + 0.6;
//
//          robot.leftClaw.setPosition(currentLPos);
//            sleep(1000); // pause for servos to move
//        }
//
//        if (gamepad1.a){
//            currentRPos = robot.rightClaw.getPosition();
//            currentRPos = robot.RIGHT_CLAW_INITIAL;
//            robot.rightClaw.setPosition(currentRPos);
//
//            currentLPos = robot.leftClaw.getPosition();
//            currentLPos = robot.LEFT_CLAW_INITIAL;
//            robot.leftClaw.setPosition(currentLPos);
//            sleep(1000);     // pause for servos to move
//        }
//    }
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//    //Unused functions
//    //Remove soon?
//    public static double roundAvoid(double value, int places) {
//        double scale = Math.pow(10, places);
//        return Math.round(value * scale) / scale;
//    }
//
//    public void foundationMove() {
//        if(gamepad1.y) {
//            double leftFoundMove = 0.85;
//            double rightFoundMove = 0.15;
//        }
//    }
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//    //Defines the sleep function
//    public final void sleep(long milliseconds) {
//        try {
//            Thread.sleep(milliseconds);
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//    }
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//    //Creates function for robot movement
//    //Creates variables used for movement
//    public void robotMove() {
//        double drivePower;
//
//        double drive = gamepad1.left_stick_y;
//        double turnLeft  =  gamepad1.left_trigger;
//        double turnRight  =  gamepad1.right_trigger;
//        double spin = -gamepad1.right_stick_x;
//        double turnValue = 1.0;
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//        //Robot "slow" movement
//        //Use Dpad for "slow" movement
//        //Currently not slow
//        if (gamepad1.dpad_up){
//            drive = -0.4;
//        }else if (gamepad1.dpad_down){
//            drive = 0.4;
//        }else if (gamepad1.dpad_right){
//            turnRight = 1.0;
//            turnValue = 0.4;
//        }else if (gamepad1.dpad_left){
//            turnLeft = 1.0;
//            turnValue = 0.4;
//        }
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//        //Defines drive power variables
//        drivePower    = Range.clip(drive, -1.0, 1.0) ;
//        double frontLPower = drivePower;
//        double frontRPower = drivePower;
//        double backRPower = drivePower;
//        double backLPower = drivePower;
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//        //Robot spin movement
//        if (spin > 0 || spin < 0) {
//            frontRPower = -spin;
//            backRPower = -spin;
//            frontLPower = spin;
//            backLPower = spin;
//        } else if (turnRight == 1) {
//            frontRPower = -turnValue;
//            backLPower = -turnValue;
//            frontLPower = turnValue;
//            backRPower = turnValue;
//        } else if (turnLeft == 1) {
//            frontRPower = turnValue;
//            backLPower = turnValue;
//            frontLPower = -turnValue;
//            backRPower = -turnValue;
//        }
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//        //Creates wheel power motors
//        double frontRPowertemp = frontRPower *90/100;//70/100;
//        double backRPowertemp = backRPower *90/100;//70/100;
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//      //Adjusts power based on if spinning
//      if(spin > 0 || spin < 0) {
//          backRPowertemp = Range.clip(backRPowertemp, -1.0, 1.0);
//          backLPower = Range.clip(backLPower, -1.0, 1.0);
//          frontRPowertemp = Range.clip(frontRPowertemp, -1.0, 1.0);
//          frontLPower = Range.clip(frontLPower, -1.0, 1.0);
//        }
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//        // Send calculated power to wheels
//        robot.drive_right_back.setPower(backRPowertemp);
//        robot.drive_left_back.setPower(backLPower);
//        robot.drive_right_front.setPower(frontRPowertemp);
//        robot.drive_left_front.setPower(frontLPower);
//      }
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//        //Lift movement
//        //Sets lift position
//        public void moveLift(){
//          liftDrivePower =  LIFT_DRIVE_POWER;
//          leftLiftCurrentPosition = robot.left_lift_motor.getCurrentPosition();
//          rightLiftCurrentPosition = robot.right_lift_motor.getCurrentPosition();
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//          //Lift movement
//          //Gamepad 2 left stick corrolates to lift movement
//          if (gamepad2.left_stick_y == 1) {
//              leftLiftNewTarget = leftLiftCurrentPosition + (int)(LIFT_COUNTS_PER_CLICK);
//              rightLiftNewTarget = rightLiftCurrentPosition +  (int)(LIFT_COUNTS_PER_CLICK);
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//              //Sets lift targeting
//              //Moves motors according to targeting
//              if (leftLiftNewTarget > 15000){
//                  leftLiftNewTarget = 15000;
//              }
//
//              if (rightLiftNewTarget > 15000){
//                  rightLiftNewTarget = 15000;
//              }
//
//              robot.left_lift_motor.setTargetPosition(leftLiftNewTarget);
//              robot.right_lift_motor.setTargetPosition(rightLiftNewTarget);
//
//              if (leftLiftNewTarget < 8000 | rightLiftNewTarget < 8000){
//                  liftDrivePower = 1.0;
//              }
//
//              robot.left_lift_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//              robot.right_lift_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//              robot.left_lift_motor.setPower(liftDrivePower);
//              robot.right_lift_motor.setPower(liftDrivePower);
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//              //Reverses lift according to gamepad 2 left stick
//          } else if (gamepad2.left_stick_y == -1) {
//              liftDrivePower = LIFT_DRIVE_POWER ;
//              leftLiftNewTarget = leftLiftCurrentPosition - (int)(LIFT_COUNTS_PER_CLICK);
//              rightLiftNewTarget = rightLiftCurrentPosition -  (int)(LIFT_COUNTS_PER_CLICK);
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//              //Sets lift targeting for reverse
//              //Moves motors according to targeting
//              if (leftLiftNewTarget < 100){
//                  leftLiftNewTarget = 0;
//              }
//
//              if (rightLiftNewTarget < 100){
//                  rightLiftNewTarget = 0;
//              }
//
//              robot.left_lift_motor.setTargetPosition(leftLiftNewTarget);
//              robot.right_lift_motor.setTargetPosition(rightLiftNewTarget);
//
//              robot.left_lift_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//              robot.right_lift_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//              robot.left_lift_motor.setPower(liftDrivePower);
//              robot.right_lift_motor.setPower(liftDrivePower);
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//          //Sets targeting positioning
//          } else {
//              leftLiftCurrentPosition = robot.left_lift_motor.getCurrentPosition();
//              rightLiftCurrentPosition = robot.right_lift_motor.getCurrentPosition();
//              robot.left_lift_motor.setTargetPosition(leftLiftCurrentPosition);
//              robot.right_lift_motor.setTargetPosition(rightLiftCurrentPosition);
//
//              robot.left_lift_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//              robot.right_lift_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//              robot.left_lift_motor.setPower(liftDrivePower);
//              robot.right_lift_motor.setPower(liftDrivePower);
//          }
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//          //Claw extension
//          //Gamepad 2 right stick controls extension
//          if (gamepad2.right_stick_y == 1) {
//              robot.stonePush.setPower(0.6);
//          } else if (gamepad2.right_stick_y == -1){
//              robot.stonePush.setPower(-0.6);
//          } else {
//              robot.stonePush.setPower(0);
//          }
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//          //Side arm movement
//          //Press dpad up for side arm movement
//          //Press dpad down for side arm retraction
//          if (gamepad2.dpad_up){
//              double pos = robot.SIDE_ARM_INITIAL + 0.5;
//              robot.sidearm.setPosition(pos);
//              sleep(1000);
//          } else if (gamepad2.dpad_down){
//              double pos = robot.SIDE_ARM_INITIAL;
//              robot.sidearm.setPosition(pos);
//              sleep(1000);
//          }
//
//     }
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//    //Stop function
//    @Override
//    public void stop() {
//
//    }
//}
//
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////