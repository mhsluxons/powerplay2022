package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Lux Tele-OpMode", group="Tele-Opmode")
@Disabled
public class LuxonsTeleOpMode extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();

     //Doubles
    static final double COUNTS_PER_MOTOR_REV    = 900 ;    // eg: TETRIX Motor Encoder
    static final double BASKET_COUNTS_PER_CLICK = (COUNTS_PER_MOTOR_REV * 120) / (360 * 8);  // basket can go only 120 degrees
    static final double     BASKET_DRIVE_POWER         = 0.8;
    static final double     CLAW_DRIVE_POWER         = 0.6;
    static final double     LIFT_DRIVE_POWER         = 0.4;

    static int basketNewTarget;
    static int basketCurrentPosition;
    static double basketDrivePower = BASKET_DRIVE_POWER;

    static final double CLAW_FULL_ROTATION = COUNTS_PER_MOTOR_REV * 3.5;
    static final double CLAW_COUNTS_PER_CLICK = CLAW_FULL_ROTATION / 10;
    static int clawNewTarget;
    static int clawCurrentPosition;
    static double clawDrivePower = CLAW_DRIVE_POWER;

    static final double LIFT_FULL_ROTATION = 1440 * 2.6;
    static final double LIFT_COUNTS_PER_CLICK = LIFT_FULL_ROTATION / 5;
    static int liftNewTarget1;
    static int liftCurrentPosition1;
    static int liftNewTarget2;
    static int liftCurrentPosition2;
    static double liftDrivePower = LIFT_DRIVE_POWER;


    //Function Variables
    private int bClaw1;
    private int bClaw2;
    private int bLock;

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
        robotMove();

        //Lift Motors
        /*
           gamepad1.dpad_up - Lift motor up
           gamepad1.dpad_down  - Lift motor down

         */
  //      lift();
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

        // Team Marker dropper
        if (gamepad1.left_bumper) {
            robot.teamMD.setPosition(0.0);
            sleep(1000);     // pause for servos to move
        }

        if (gamepad1.right_bumper){
            robot.teamMD.setPosition(1.0);
            sleep(1000);     // pause for servos to move
        }

        //Gamepad 2:
        //====================================

        // gamepad2.left_stick_y - Basket up/down
        //Basket
        moveBasket();

        // gamepad2.right_stick_y - Claw hand move forward / backward
        //Claw Motor
        moveClaw();

        //Claw Servos
        /* if (gamepad2.dpad_up) {
            robot.leftClaw.setPosition(1.0);
             sleep(1000);     // pause for servos to move
        }

        if (gamepad2.dpad_down){
            robot.leftClaw.setPosition(LuxonsRobot.START_SERVO);
            sleep(1000);     // pause for servos to move
        } */

        //Claw Servos
        if (gamepad2.y) {
            robot.rightClaw.setPosition(1.0);
            sleep(1000);     // pause for servos to move
        }

        if (gamepad2.a){
            robot.rightClaw.setPosition(0.0);
            sleep(1000);     // pause for servos to move
        }

        //Precise Movements
//        if (gamepad1.y) {
//          robot.drive_left_front.setPower(0.2);
//          robot.drive_left_back.setPower(0.2);
//          robot.drive_right_front.setPower(0.2);
//          robot.drive_right_back.setPower(0.2);
//        }
//
//        else if (gamepad1.a) {
//            robot.drive_left_front.setPower(-0.2);
//            robot.drive_left_back.setPower(-0.2);
//            robot.drive_right_front.setPower(-0.2);
//            robot.drive_right_back.setPower(-0.2);
//        }
//
//        else if (gamepad1.x) {
//            robot.drive_left_front.setPower(-0.2);
//            robot.drive_left_back.setPower(0.2);
//            robot.drive_right_front.setPower(0.2);
//            robot.drive_right_back.setPower(-0.2);
//        }
//
//        else if (gamepad1.b) {
//            robot.drive_left_front.setPower(0.2);
//            robot.drive_left_back.setPower(-0.2);
//            robot.drive_right_front.setPower(-0.2);
//            robot.drive_right_back.setPower(0.2);
//        }


//        telemetry.addData("Status", robot.rightClaw.getPosition());
//        telemetry.addData("Status", robot.leftClaw.getPosition());
    }

//    private int addPosition(int liftCurrentPosition ){
//        int liftNewTarget = liftCurrentPosition + (int) LIFT_COUNTS_PER_CLICK;
//            if (liftNewTarget > LIFT_FULL_ROTATION){
//                liftNewTarget = (int) LIFT_FULL_ROTATION;
//            }
//         return liftNewTarget;
//
//    }
//
//    private int subtractPosition(int liftCurrentPosition){
//        int liftNewTarget = liftCurrentPosition - (int) LIFT_COUNTS_PER_CLICK;
//            if (liftNewTarget < 1){
//                liftNewTarget = 10;
//            }
//        return liftNewTarget;
//
//    }

//    public void lift(){
//        liftDrivePower =  LIFT_DRIVE_POWER;
//        if (gamepad1.dpad_up) {
//            liftNewTarget1 = addPosition (liftCurrentPosition1 );
//            robot.lift_motor1.setTargetPosition(liftNewTarget1);
//            robot.lift_motor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//            liftNewTarget2 = addPosition (liftCurrentPosition2 );
//            robot.lift_motor2.setTargetPosition(liftNewTarget2);
//            robot.lift_motor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            robot.lift_motor2.setPower(liftDrivePower);
//            robot.lift_motor1.setPower(liftDrivePower);
//
//        }else if (gamepad1.dpad_down) {
//            liftNewTarget1 = subtractPosition (liftCurrentPosition1);
//            robot.lift_motor1.setTargetPosition(liftNewTarget1);
//            robot.lift_motor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            liftNewTarget2 = subtractPosition (liftCurrentPosition2 );
//            robot.lift_motor2.setTargetPosition(liftNewTarget2);
//            robot.lift_motor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//            robot.lift_motor2.setPower(liftDrivePower);
//            robot.lift_motor1.setPower(liftDrivePower);
//
//        }
//        liftCurrentPosition1 = robot.lift_motor1.getCurrentPosition();
//        liftCurrentPosition2 = robot.lift_motor2.getCurrentPosition();
//
//        if (robot.lift_motor1.isBusy()) {
//            telemetry.addData("Status", liftCurrentPosition1 + "=="+liftNewTarget1);
//        }
//        if (robot.lift_motor2.isBusy()) {
//            telemetry.addData("Status 2", liftCurrentPosition2 + "=="+liftNewTarget1);
//        }
//
//
//    }

    public final void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


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

    public void robotMove() {
        double drivePower;
        double turnPower;

        double drive = -gamepad1.left_stick_y;
        double turnLeft  =  gamepad1.left_trigger;
        double turnRight  =  gamepad1.right_trigger;
        double spin = gamepad1.right_stick_x;
        double turnValue = 1.0;

        drivePower    = Range.clip(drive, -1.0, 1.0) ;

        double frontLPower = drivePower;
        double frontRPower = drivePower;
        double backLPower = drivePower;
        double backRPower = drivePower;

        if (spin > 0 || spin < 0) {
            frontRPower = -spin;
            backRPower = -spin;
            frontLPower = spin;
            backLPower = spin;
        }

        else if (turnRight == 1) {
            frontRPower = -turnValue;
            backLPower = -turnValue;
            frontLPower = turnValue;
            backRPower = turnValue;
        }

        else if (turnLeft == 1) {
            frontRPower = turnValue;
            backLPower = turnValue;
            frontLPower = -turnValue;
            backRPower = -turnValue;
        }

        // Send calculated power to wheels
        robot.drive_right_back.setPower(backRPower);
        robot.drive_left_back.setPower(backLPower);
        robot.drive_right_front.setPower(frontRPower);
        robot.drive_left_front.setPower(frontLPower);
      }

      public void moveBasket(){
          basketDrivePower =  BASKET_DRIVE_POWER;
          if (gamepad2.left_stick_y == 1) {
              basketNewTarget = basketCurrentPosition + (int)(BASKET_COUNTS_PER_CLICK);
              if (basketNewTarget > 320){
                  basketNewTarget = 320;
              }
              robot.basket_motor.setTargetPosition(basketNewTarget);
              if (basketNewTarget > 240){
                  basketDrivePower = 0.6;
              }
              robot.basket_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
              robot.basket_motor.setPower(basketDrivePower);

          }else if (gamepad2.left_stick_y == -1) {
              basketDrivePower = 0.2;
              basketNewTarget = basketCurrentPosition - (int)(0.5 * BASKET_COUNTS_PER_CLICK);
              if (basketNewTarget < 1){
                  basketNewTarget = -20;
              }
              robot.basket_motor.setTargetPosition(basketNewTarget);
              if (basketNewTarget < 100){
                  basketDrivePower = 0.1;
              }
              robot.basket_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
              robot.basket_motor.setPower(basketDrivePower);

              if (basketNewTarget < 100){
                  sleep(150);
              }

          }
          basketCurrentPosition = robot.basket_motor.getCurrentPosition();

      }

    @Override
    public void stop() {

    }
}