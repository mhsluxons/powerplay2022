//Imports necessary items for the robot to run
package org.firstinspires.ftc.robotcontroller.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Lux Tele-OpMode 2019", group="Tele-Opmode-2019")
@Disabled
public class LuxonsTeleOpMode2019 extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();

    //Creates most variables used throughout the program
    //Variables created include motor counts, targeting, and positioning
    static final double COUNTS_PER_MOTOR_REV = 900 ;
    static final double LIFT_COUNTS_PER_CLICK = COUNTS_PER_MOTOR_REV;
    static final double LIFT_DRIVE_POWER = 0.8;

    private int leftLiftNewTarget;
    private int leftLiftCurrentPosition;
    private int rightLiftNewTarget;
    private int rightLiftCurrentPosition;
    private double liftDrivePower = LIFT_DRIVE_POWER;
    double grabPower = 0.6;

    double currentRPos;
    double currentLPos;
    double currentGPos;

    //Defines all pieces necessary
    //Defines the robot, and motors
    LuxonsRobot2019 robot = new LuxonsRobot2019();
    @Override
    public void init() {
          robot.init(hardwareMap);
      }


    //Loop updates all functions necessary
    //Loop updates positions and robot movement
    @Override
    public void loop() {

//        telemetry.addData("Status", "cap:" + robot.cap.getPosition());
//
//        telemetry.addData("Status", "left_lift :" + leftLiftCurrentPosition + ": "+ rightLiftCurrentPosition);
//        telemetry.addData("Status", "Claw:" + currentRPos + ": "+ currentLPos);


        telemetry.update();
        robotMove();


        //Press Y to use grabbers
        //Press A to reverse grabbers
        //If not pressing stop
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


        //Stone Grab Servos
//        if (gamepad2.x) {
//
//            currentGPos = robot.STONE_GRAB_INITIAL + 0.3;
//
//            robot.stoneGrab.setPosition(currentGPos);
//            sleep(1000); // pause for servos to move
//        }
//
//        if (gamepad2.b){
//            robot.stoneGrab.setPosition(robot.STONE_GRAB_INITIAL);
//            sleep(1000);     // pause for servos to move
//        }
//
//        //capstone
//        if (gamepad2.left_bumper){
//
//            currentGPos = robot.STONE_GRAB_INITIAL + 0.3;
//            robot.stoneGrab.setPosition(currentGPos);
//            sleep(1000);
//            robot.cap.setPosition(robot.CAP_END);
//            sleep(1000);     // pause for servos to move
//        }
//
//        if (gamepad2.right_bumper){
//            robot.cap.setPosition(robot.CAP_INITIAL);
//            sleep(1000);     // pause for servos to move
//        }


       //Lift
//        moveLift();
        grabShoot();

        //Controls used for foundation "claws"
        //Press Y to move claws up
        //Press A to move claws down
//        if (gamepad1.y) {
//            robot.rightClaw.setPosition(robot.RIGHT_CLAW_END);
//            robot.leftClaw.setPosition(robot.LEFT_CLAW_END);
//            sleep(1000); // pause for servos to move
//        }
//
//        if (gamepad1.a){
//            robot.rightClaw.setPosition(robot.RIGHT_CLAW_INITIAL);
//           robot.leftClaw.setPosition(robot.LEFT_CLAW_INITIAL);
//            sleep(1000);     // pause for servos to move
//        }
    }
/*
    //Unused functions
    //Remove soon?
    public static double roundAvoid(double value, int places) {
        double scale = Math.pow(10, places);
        return Math.round(value * scale) / scale;
    }

    public void foundationMove() {
        if(gamepad1.y) {
            double leftFoundMove = 0.85;
            double rightFoundMove = 0.15;
        }
    }
*/

    //Defines the sleep function
    public final void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


    //Creates function for robot movement
    //Creates variables used for movement
    public void robotMove() {
        double drivePower;

        double drive = gamepad1.left_stick_y;
        double turnLeft  =  gamepad1.right_trigger;
        double turnRight  =  gamepad1.left_trigger;
        double spin = -gamepad1.right_stick_x;
        double turnValue = 1.0;


        //Robot "slow" movement
        //Use Dpad for "slow" movement
        //Currently not slow
        if (gamepad1.dpad_up){
            drive = -0.4;
        }else if (gamepad1.dpad_down){
            drive = 0.4;
        }else if (gamepad1.dpad_left){
            turnRight = 1.0;
            turnValue = 0.4;
        }else if (gamepad1.dpad_right){
            turnLeft = 1.0;
            turnValue = 0.4;
        }


        //Defines drive power variables
        drivePower    = Range.clip(drive, -1.0, 1.0) ;
        double frontLPower = drivePower;
        double frontRPower = drivePower;
        double backRPower = drivePower;
        double backLPower = drivePower;


        //Robot spin movement
        if (spin > 0 || spin < 0) {
            frontRPower = -spin;
            backRPower = -spin;
            frontLPower = spin;
            backLPower = spin;
        } else if (turnRight == 1) {
            frontRPower = -turnValue;
            backLPower = -turnValue;
            frontLPower = turnValue;
            backRPower = turnValue;
        } else if (turnLeft == 1) {
            frontRPower = turnValue;
            backLPower = turnValue;
            frontLPower = -turnValue;
            backRPower = -turnValue;
        }


        //Creates wheel power motors
        double frontRPowertemp = frontRPower *90/100;//70/100;
        double backRPowertemp = backRPower *90/100;//70/100;


      //Adjusts power based on if spinning
      if(spin > 0 || spin < 0) {
          backRPowertemp = Range.clip(backRPowertemp, -1.0, 1.0);
          backLPower = Range.clip(backLPower, -1.0, 1.0);
          frontRPowertemp = Range.clip(frontRPowertemp, -1.0, 1.0);
          frontLPower = Range.clip(frontLPower, -1.0, 1.0);
        }


        // Send calculated power to wheels
        robot.drive_right_back.setPower(-backRPowertemp);
        robot.drive_left_back.setPower(-backLPower);
        robot.drive_right_front.setPower(-frontRPowertemp);
        robot.drive_left_front.setPower(-frontLPower);
      }

    public void grabShoot() {
          double drive = 0.8;
        if (gamepad2.left_trigger > 0) { //plops down freight
            robot.plop_motor.setPosition(drive);
       }else{
            robot.plop_motor.setPosition(0);
        }
        if (gamepad2.left_stick_x > 0){
            robot.plop_motor.setPosition(-drive); //returns servo to normal
        }else{
            robot.plop_motor.setPosition(0);
        }

        /*if (gamepad2.right_trigger > 0) {
            robot.duck_motor.setPower(-drive);
        }else{
            robot.duck_motor.setPower(0);
        }*/

           if (gamepad2.right_stick_y == -1) {
              robot.ringPush.setPower(0.4);
          } else if (gamepad2.right_stick_y == 1){
              robot.ringPush.setPower(-0.4);
          } else {
              robot.ringPush.setPower(0);
          }


    }

        //Lift movement
        //Sets lift position
//        public void moveLift(){
//          liftDrivePower =  LIFT_DRIVE_POWER;
//          leftLiftCurrentPosition = robot.left_lift_motor.getCurrentPosition();
//          rightLiftCurrentPosition = robot.right_lift_motor.getCurrentPosition();
//
//
//          //Lift movement
//          //Gamepad 2 left stick corrolates to lift movement
//          if (gamepad2.left_stick_y == -1) {
//              leftLiftNewTarget = leftLiftCurrentPosition + (int)(LIFT_COUNTS_PER_CLICK); //Increase height by 1 rotation
//              rightLiftNewTarget = rightLiftCurrentPosition +  (int)(LIFT_COUNTS_PER_CLICK);
//
//
//              //Sets lift targeting
//              //Moves motors according to targeting
//              if (isAb0veMax(robot.left_lift_initial, leftLiftNewTarget)){
//                  leftLiftNewTarget = getMax(robot.left_lift_initial);
//              }
//
//              if (isAb0veMax(robot.right_lift_initial, rightLiftNewTarget )){
//                  rightLiftNewTarget = getMax(robot.right_lift_initial);
//              }
//
//              robot.left_lift_motor.setTargetPosition(leftLiftNewTarget);
//              robot.right_lift_motor.setTargetPosition(rightLiftNewTarget);
//
//              if (leftLiftNewTarget < 2000 | rightLiftNewTarget < 2000){
//                  liftDrivePower = 0.6;
//              }
//
//              robot.left_lift_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//              robot.right_lift_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//              robot.left_lift_motor.setPower(liftDrivePower);
//              robot.right_lift_motor.setPower(liftDrivePower);
//
//
//              //Reverses lift according to gamepad 2 left stick
//          } else if (gamepad2.left_stick_y == 1) {
//              liftDrivePower = LIFT_DRIVE_POWER ;
//              leftLiftNewTarget = leftLiftCurrentPosition - (int)(LIFT_COUNTS_PER_CLICK);
//              rightLiftNewTarget = rightLiftCurrentPosition -  (int)(LIFT_COUNTS_PER_CLICK);
//
//
//              //Sets lift targeting for reverse
//              //Moves motors according to targeting
//              if (isBelowMin(robot.left_lift_initial, leftLiftNewTarget)){
//                  leftLiftNewTarget = robot.left_lift_initial;
//              }
//
//              if (isBelowMin(robot.right_lift_initial, rightLiftNewTarget)){
//                  rightLiftNewTarget = robot.right_lift_initial;
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
//
//          //Sets targeting positioning
//           // Incase of emergency ONLY
//           //  E M E R G E N C Y ----------------------------
//           //
//          } else if (gamepad2.dpad_left && gamepad2.left_trigger > 0) {
//                leftLiftNewTarget = leftLiftCurrentPosition + (int)(LIFT_COUNTS_PER_CLICK); //Increase height by 1 rotation
//                rightLiftNewTarget = rightLiftCurrentPosition +  (int)(LIFT_COUNTS_PER_CLICK);
//                robot.left_lift_motor.setTargetPosition(leftLiftNewTarget);
//                robot.right_lift_motor.setTargetPosition(rightLiftNewTarget);
//                liftDrivePower = 0.6;
//                robot.left_lift_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                robot.right_lift_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                robot.left_lift_motor.setPower(liftDrivePower);
//                robot.right_lift_motor.setPower(liftDrivePower);
//
//
//                //Reverses lift according to gamepad 2 left stick
//            } else if (gamepad2.dpad_right && gamepad2.left_trigger > 0) {
//
//                leftLiftNewTarget = leftLiftCurrentPosition - (int)(LIFT_COUNTS_PER_CLICK);
//                rightLiftNewTarget = rightLiftCurrentPosition -  (int)(LIFT_COUNTS_PER_CLICK);
//                robot.left_lift_motor.setTargetPosition(leftLiftNewTarget);
//                robot.right_lift_motor.setTargetPosition(rightLiftNewTarget);
//                liftDrivePower = 0.6;
//
//                robot.left_lift_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                robot.right_lift_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//                robot.left_lift_motor.setPower(liftDrivePower);
//                robot.right_lift_motor.setPower(liftDrivePower);
//
//          } else if (gamepad2.right_trigger > 0 && gamepad2.left_trigger > 0) {
//              robot.left_lift_initial = leftLiftCurrentPosition;
//              robot.right_lift_initial = rightLiftCurrentPosition;
//
//              //  E M E R G E N C Y  ends here---------------------------
//            }  else {
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
//
//          //Claw extension
//          //Gamepad 2 right stick controls extension
//          if (gamepad2.right_stick_y == -1) {
//              robot.stonePush.setPower(0.8);
//          } else if (gamepad2.right_stick_y == 1){
//              robot.stonePush.setPower(-0.8);
//          } else {
//              robot.stonePush.setPower(0);
//          }
//
//
//          //Side arm movement
//          //Press dpad up for side arm movement
//          //Press dpad down for side arm retraction
//          if (gamepad2.dpad_up){
//               robot.sidearm.setPosition(robot.SIDE_ARM_END);
//              sleep(1000);
//          } else if (gamepad2.dpad_down){
//               robot.sidearm.setPosition(robot.SIDE_ARM_INITIAL);
//              sleep(1000);
//          }

     //}

     private boolean isAb0veMax(int initial, int current){
         return (getMax(initial) > current);
    }

    private int getMax(int initial){
       return initial + 8400;
    }


    private boolean isBelowMin(int initial, int current){
        return (current < getMin(initial));
    }

    private int getMin(int initial){
        return initial + 100;
    }


    //Stop function
    @Override
    public void stop() {

    }
}

