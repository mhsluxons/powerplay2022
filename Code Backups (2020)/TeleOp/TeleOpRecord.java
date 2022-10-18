package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.util.RobotLog;

import java.util.ArrayList;

@TeleOp(name="TeleOpRecord")

//NOTE: Hi, it's Steven! I haven't updated the comments here since...well, I'm not sure what to
//replace! Sorry.
public class TeleOpRecord extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();

    //Doubles
    static final double COUNTS_PER_MOTOR_REV    = 900 ;    // eg: TETRIX Motor Encoder
    static final double     LIFT_DRIVE_POWER         = 0.4;
    static final double LIFT_FULL_ROTATION = 1440 * 2.6;
    static final double LIFT_COUNTS_PER_CLICK = LIFT_FULL_ROTATION / 5;
    static int liftNewTarget1;
    static int liftCurrentPosition1;
    static int liftNewTarget2;
    static int liftCurrentPosition2;
    static double liftDrivePower = LIFT_DRIVE_POWER;
    static ArrayList<Integer> arr;//=new ArrayList<Integer>();
    static int dpadState;//=0;
    static int dpadTickState;//=0;
    static int dpadCurrentTicks;//=0;
    static boolean printedYet;// = false;

    //Robot Object
    ModularChassis robot = new ModularChassis();

    @Override
    public void init() {
        robot.init(hardwareMap);
        arr=new ArrayList<Integer>();
        dpadState=0;
        dpadTickState=0;
        dpadCurrentTicks=0;
        printedYet = false;
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

        //Lifter
        //lift();

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
        //moveServos();
        updateArrays();
    }

    public final void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
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
        //Print record array
        if(gamepad1.dpad_up){
            for(int i=0;i<arr.size();i++)
            {
                System.out.print(arr.get(i)+(i==arr.size()-1?"":" "));
            }
        }
    }

    public void intake() {
        //Sets Rotation Values (on click of y)

        //Runs the Motors if the Intake Value is True:

    }

    public void moveServos() {

    }
    public void robotMove() {
        double drivePower;


        double drive = 0;// = gamepad1.left_stick_y;
        double turnLeft  =  gamepad1.right_trigger;
        double turnRight  =  gamepad1.left_trigger;
        double spin = -gamepad1.right_stick_x;
        double turnValue = 1.0;
        dpadState = 0;

        if (gamepad1.dpad_up){
            drive = -1.0 * 40 / 100;
            dpadState = 1;
        }else if (gamepad1.dpad_down){
            drive = 1.0 * 40 / 100;
            dpadState = 2;
        }else if (gamepad1.dpad_right){
            turnLeft = 1.0 * 40 / 100;
            dpadState = 3;
        }else if (gamepad1.dpad_left){
            turnRight = 1.0 * 40 / 100;
            dpadState = 4;
        }

        drivePower    = Range.clip(drive, -1.0, 1.0) ;

        double frontLPower = drivePower;
        double frontRPower = drivePower;
        double backLPower = drivePower;
        double backRPower = drivePower;
/*
        if (spin > 0 || spin < 0) {
            frontRPower = -spin;
            backRPower = -spin;
            frontLPower = spin;
            backLPower = spin;
        }
*/
        if (turnRight == 1) {
            frontRPower = -turnValue;
            backLPower = -turnValue;
            frontLPower = turnValue;
            backRPower = turnValue;
            dpadState = 5;
        }

        else if (turnLeft == 1) {
            frontRPower = turnValue;
            backLPower = turnValue;
            frontLPower = -turnValue;
            backRPower = -turnValue;
            dpadState = 6;
        }

        double frontRPowertemp = frontRPower *70/100;
        double backRPowertemp = backRPower *70/100;
/*
        if(spin > 0 || spin < 0) {
            backRPowertemp =  backRPowertemp * 40/100;
            backRPowertemp = Range.clip(backRPowertemp, -1.0, 1.0);

            backLPower = backLPower * 40/100;
            backLPower = Range.clip(backLPower, -1.0, 1.0);

            frontRPowertemp = frontRPowertemp * 40/100;
            frontRPowertemp = Range.clip(frontRPowertemp, -1.0, 1.0);

            frontLPower = frontLPower * 40/100;
            frontLPower = Range.clip(frontLPower, -1.0, 1.0);

        }
*/

        // Send calculated power to wheels
        robot.drive_right_back.setPower(backRPowertemp);
        robot.drive_left_back.setPower(backLPower);
        robot.drive_right_front.setPower(frontRPowertemp);
        robot.drive_left_front.setPower(frontLPower);
    }
    public void updateArrays(){
        if(gamepad1.a && !printedYet){
                printedYet = true;
                String s = "";

                RobotLog.d("WHY HELLO THERE");

            for(int i = 0;i<arr.size();i++)
                s+=("" + arr.get(i) + " ");
            s+="0 1";
            RobotLog.d(s);
        }
        if(dpadState==dpadTickState){
            dpadCurrentTicks++;
        }
        else
        {
            arr.add(dpadTickState);
            arr.add(dpadCurrentTicks);
            dpadCurrentTicks=0;
            dpadTickState=dpadState;
        }
    }
    @Override
    public void stop() {

    }
}