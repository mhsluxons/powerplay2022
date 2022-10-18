package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Lux Tele-OpMode New", group="Tele-Opmode-New")
@Disabled
public class LuxonsTeleOpModeNew extends OpMode {
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

    LuxonsRobot robot = new LuxonsRobot();

    //Code Run Once on INIT:
    @Override
    public void init() {
      robot.init(hardwareMap);
    }

    //Code Repeatedly run on INIT:
    @Override
    public void init_loop() {
      resetClaw();
      //resetClaw_Encoders("in", 0.5);

      /*DO NOT PRESS START RIGHT AWAY,
      WAIT FOR DRIVERS TO RESET CLAW!!!!*/
    }

    //Code Run oNce on START:
    @Override
    public void start() {
      robot.claw_motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
      robot.basket_motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    //Code Run Repeatedly on START:
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

        // gamepad2.left_stick_y - Basket up/down
        //Basket
        moveBasket();

        // gamepad2.right_stick_y - Claw hand move forward / backward
        //Claw Motor
        moveClaw();

        //Backup Claw
        //resetClaw();

        //Intake Methods
        intake_old();
        //intake();

        //Move Servos
        //moveServos();
    }

    public void resetClaw() {
      if (gamepad2.right_stick_y > 0.0) {
        robot.claw_motor.setPower(0.4);
      }

      if (gamepad2.right_stick_y < 0.0) {
        robot.claw_motor.setPower(-0.4);
      }

      if (gamepad2.right_stick_y == 0.0) {
        robot.claw_motor.setPower(0);
      }
    }

    public void resetClaw_Encoders(String type, double distanceInRotations) {
        int distanceInTicks = (int)(distanceInRotations*COUNTS_PER_MOTOR_REV);

        if (type.equalsIgnoreCase("out")) {
            robot.claw_motor.setTargetPosition(robot.claw_motor.getCurrentPosition() + distanceInTicks);
        }

        else if (type.equalsIgnoreCase("in")) {
            robot.claw_motor.setTargetPosition(robot.claw_motor.getCurrentPosition() - distanceInTicks);
        }

        robot.claw_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.claw_motor.setPower(CLAW_DRIVE_POWER);
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

    public void moveClaw(){
        clawDrivePower =  CLAW_DRIVE_POWER;
        if (gamepad2.right_stick_y < 0.0) {
            clawNewTarget = clawCurrentPosition + (int) CLAW_COUNTS_PER_CLICK;
            if (clawNewTarget > CLAW_FULL_ROTATION){
                clawNewTarget = (int) CLAW_FULL_ROTATION;
            }
            robot.claw_motor.setTargetPosition(clawNewTarget);
            robot.claw_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.claw_motor.setPower(0.4);

        } else if (gamepad2.right_stick_y > 0.0) {
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

    public void moveRobot() { //Edited By: Sushen Kolakaleti
        //Stick Values
        double positiveY = 1.0 * ((gamepad1.left_stick_y) * (gamepad1.left_stick_y));
        double negativeY = -1.0 * ((gamepad1.left_stick_y) * (gamepad1.left_stick_y));
        double positiveX = 1.0 * ((gamepad1.right_stick_x) * (gamepad1.right_stick_x));
        double negativeX = -1.0 * ((gamepad1.right_stick_x) * (gamepad1.right_stick_x));

        //Trigger Values
        double moveLeftTrigger = 1.0 * ((gamepad1.left_trigger) * (gamepad1.left_trigger));
        double moveRightTrigger = 1.0 * ((gamepad1.right_trigger) * (gamepad1.right_trigger));

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
            robot.drive_left_front.setPower(-moveLeftTrigger);
            robot.drive_left_back.setPower(moveLeftTrigger);
            robot.drive_right_front.setPower(moveLeftTrigger);
            robot.drive_right_back.setPower(-moveLeftTrigger);
        }

        if (gamepad1.right_trigger > 0.0) {
            robot.drive_left_front.setPower(moveRightTrigger);
            robot.drive_left_back.setPower(-moveRightTrigger);
            robot.drive_right_front.setPower(-moveRightTrigger);
            robot.drive_right_back.setPower(moveRightTrigger);
        }

        //Diagonal Movement
        if (gamepad1.y) {
            robot.drive_left_front.setPower(0.0);
            robot.drive_left_back.setPower(0.4);
            robot.drive_right_front.setPower(0.4);
            robot.drive_right_back.setPower(0.0);
        }

        if (gamepad1.b) {
            robot.drive_left_front.setPower(0.4);
            robot.drive_left_back.setPower(0.0);
            robot.drive_right_front.setPower(0.0);
            robot.drive_right_back.setPower(0.4);
        }

        if (gamepad1.a) {
            robot.drive_left_front.setPower(0.0);
            robot.drive_left_back.setPower(-0.4);
            robot.drive_right_front.setPower(-0.4);
            robot.drive_right_back.setPower(0.0);
        }

        if (gamepad1.x) {
            robot.drive_left_front.setPower(-0.4);
            robot.drive_left_back.setPower(0.0);
            robot.drive_right_front.setPower(0.0);
            robot.drive_right_back.setPower(-0.4);
        }

        //Stop Robot
        if (gamepad1.left_stick_y == 0.0 && gamepad1.left_stick_x == 0.0 && gamepad1.left_trigger == 0.0 && gamepad1.right_trigger == 0.0 &&
        gamepad1.y == false && gamepad1.a == false && gamepad1.b == false && gamepad1.x == false) {
            robot.drive_left_front.setPower(0);
            robot.drive_left_back.setPower(0);
            robot.drive_right_front.setPower(0);
            robot.drive_right_back.setPower(0);
        }
    }

      public void moveBasket(){
          basketDrivePower =  BASKET_DRIVE_POWER;
          if (gamepad2.left_stick_y > 0.0) {
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

          }else if (gamepad2.left_stick_y < 0.0) {
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

      public void intake() {
          //Team Marker Dropper:
          if (gamepad2.a) {
              if (robot.markerDown == true) {
                  robot.markerDown = false;

                  robot.teamMD.setPosition(0.0);
              }

              else if (robot.markerDown == false) {
                  robot.markerDown = true;

                  robot.teamMD.setPosition(0.5);
              }
          }

          //Setting Intake and release values (on trigger clicks):
          if (gamepad2.left_trigger > 0.0) {
              if (robot.startRotationIntake == false) {
                  robot.startRotationIntake = true;
                  robot.startRotationRelease = false;
              }

              else if (robot.startRotationIntake == true) {
                  robot.startRotationIntake = false;
                  robot.startRotationRelease = false;
              }
          }

          if (gamepad2.right_trigger > 0.0) {
              if (robot.startRotationRelease == false) {
                  robot.startRotationIntake = false;
                  robot.startRotationRelease = true;
              }

              else if (robot.startRotationRelease == true) {
                  robot.startRotationIntake = false;
                  robot.startRotationRelease = false;
              }
          }

          //Setting Powers (based on start values):
          if (robot.startRotationIntake == true && robot.startRotationRelease == false) {
              robot.intakeServo1.setPower(0.4);
              robot.intakeServo2.setPower(0.4);
              //robot.intakeServo2.setPower(0.4);
          }

          else if (robot.startRotationRelease == true && robot.startRotationIntake == false) {
              robot.intakeServo1.setPower(-0.4);
              robot.intakeServo2.setPower(-0.4);
              //robot.intakeServo2.setPower(-0.4);
          }

          else if (robot.startRotationIntake == false && robot.startRotationRelease == false) {
              robot.intakeServo1.setPower(0);
              robot.intakeServo2.setPower(0);
              //robot.intakeServo2.setPower(0);
          }

          else {
              robot.intakeServo1.setPower(0);
              //robot.intakeServo2.setPower(0);
          }
      }

      public void intake_old() {
        //Team Marker Code:
        if (gamepad2.a) {
          if (robot.markerDown == true) {
            robot.markerDown = false;

            robot.teamMD.setPosition(0.0);
          }

          else if (robot.markerDown == false) {
            robot.markerDown = true;

            robot.teamMD.setPosition(0.5);
          }
        }

        //Sets Rotation Values (on click of trigger):
        if (gamepad2.left_trigger > 0.0) {
          robot.intakeServo1.setPower(0.4);
          //robot.intakeServo2.setPoser(0.4);
        }

        //Release:
        if (gamepad2.right_trigger > 0.0) {
          robot.intakeServo1.setPower(-0.4);
          //robot.intakeServo2.setPower(-0.4);
        }

        if (gamepad2.left_trigger == 0 && gamepad2.right_trigger == 0) {
            robot.intakeServo1.setPower(0);
            //robot.intakeServo2.setPower(0);
        }
      }

      public void moveServos() {
          //Claw Servos
          if (gamepad2.y) {
              //Moving Servos
              if (robot.clawOpen == false) {
                  robot.clawOpen = true;

                  robot.rightClaw.setPosition(0.2);
                  sleep(1000); //Servo Pause
              }

              else if (robot.clawOpen == true) {
                  robot.clawOpen = false;

                  robot.rightClaw.setPosition(1.0);
                  sleep(1000); //Servo Pause
              }
          }

          // Team Marker Dropper
          if (gamepad2.a) {
              if (robot.markerDown == false) {
                robot.markerDown = true;

                robot.teamMD.setPosition(0.0);
              }

              else if (robot.markerDown == true) {
                robot.markerDown = false;

                robot.teamMD.setPosition(0.5);
              }
          }
      }

    @Override
    public void stop() {
      telemetry.addData("Run:", "Success");
      telemetry.update();
    }
}