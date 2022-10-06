package org.firstinspires.ftc.robotcontroller.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.List;

@TeleOp(name="Detection TeleOp", group="Tele-Opmode-2019")
@Disabled
public class DetectionTeleOp extends OpMode {

    //Creates most variables used throughout the program
    //Variables created include motor counts, targeting, and positioning
    double grabPower = 0.8;

    //detection variables
    private static final String TFOD_MODEL_ASSET = "FreightFrenzy_BCDM.tflite";
    private static final String[] LABELS = {
            "Ball",
            "Cube",
            "Duck",
            "Marker"
    };
    private static final String VUFORIA_KEY = "Ad4IdFr/////AAABmQfmLScrK0DGonZfbL1sfTQ2tVSJ8ZIyMRVYsinvjW7RphHb4VAf+20Jnu9bDcMwu4x0iTiGWGrQTIoSFkRRVNC+kxUeuLJ+4wi2MW/4cdB+vDgtS3IcoJhB4oFExvk/SHMNeapM2nUb5SAtmd+69syuyZpFBSwDUAqnPdSHHAtKh0qq6+v3QilZ0NOfUqdj9GA1372wggFrm8ZQGmnfq5rwjtaxhcfMZVjrL/O3mwGJBYki1nZkfaS4ULjFv86MOkLZSB8BPqG82tgMCiLJaqMwAT12i+K7eD4nOYeQv4MF1FJtjd17oIjLVBRPEL19mSKRl4rsJkqaROalHUwtYHmQPNnMpEFf+uw/YzX/zgTL";
    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;

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


        pulleyLift();
        blackBox();
        intake();
        duckSpinner();
    }

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
        double turnLeft  =  gamepad1.left_trigger;
        double turnRight  =  gamepad1.right_trigger;
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
            turnLeft = 1.0;
            turnValue = 0.4;
        }else if (gamepad1.dpad_right){
            turnRight = 1.0;
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
            frontRPower = turnValue;
            backLPower = turnValue;
            frontLPower = -turnValue;
            backRPower = -turnValue;
        } else if (turnLeft == 1) {
            frontRPower = -turnValue;
            backLPower = -turnValue;
            frontLPower = turnValue;
            backRPower = turnValue;
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

    //black box servo
    public void blackBox() {
        if (gamepad2.x) {
            robot.plop_motor.setPosition(robot.PLOP_MOTOR_INITIAL); //plops down elements
            sleep(1000); // pause for servos to move
        } else if (gamepad2.b) {
            robot.plop_motor.setPosition(robot.PLOP_MOTOR_END); //returns back to normal
            sleep(1000); // pause for servos to move
        }
    }

    //Pulley Lift
    public void pulleyLift() {
        if (gamepad2.y) {
            robot.shoot_motor.setPower(grabPower*.80);
        }else if (gamepad2.a){
            robot.shoot_motor.setPower(-grabPower*.65);
        }else {
            robot.shoot_motor.setPower(0);
        }
    }

    //Flywheel intake system
    public void intake() {
        double intakeSpeed = 1.0;
        if (gamepad2.left_trigger > 0) { //pulls elements in
            robot.intake_motor.setPower(intakeSpeed);
        } else {
            robot.intake_motor.setPower(0);
        }
        if (gamepad2.right_trigger > 0) { //pushes elements out (in case of emergency)
            robot.intake_motor.setPower(-intakeSpeed);
        } else {
            robot.intake_motor.setPower(0);
        }

        //SLOW intake system (last minute entry 1/28/22)
        double slowSpeed = 0.4;
        if (gamepad2.dpad_up) { //pulls elements in
            robot.intake_motor.setPower(slowSpeed);
        }
        else if (gamepad2.dpad_down) { //slower than slow speed
            robot.intake_motor.setPower(0.2);
        }
        else {
            robot.intake_motor.setPower(0);
        }
    }


    //duck spinner servo thing
    public void duckSpinner() {
        double duckSpeed = 1.0;
        if (gamepad2.right_bumper){
            robot.duck_motor.setPower(duckSpeed);
        } else {
            robot.duck_motor.setPower(0);
        }
        if (gamepad2.left_bumper){
            robot.duck_motor.setPower(-duckSpeed);
        } else {
            robot.duck_motor.setPower(0);
        }
    }

    //quick detection
    public void detection() {
        if(gamepad1.a)
        {
            initVuforia();
            initTfod();

            if (tfod != null)
            {
                tfod.activate();
                tfod.setZoom(1.25, 16.0/9.0);
            }
            if (tfod != null) {
                List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                if (updatedRecognitions != null) {
                    telemetry.addData("# Object Detected", updatedRecognitions.size());
                    int i = 0;
                    for (Recognition recognition : updatedRecognitions) {
                        telemetry.addData(String.format("label (%d)", i), recognition.getLabel()); //detects the object
                        telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                                recognition.getLeft(), recognition.getTop());
                        telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                                recognition.getRight(), recognition.getBottom());
                        i++;
                    }
                    telemetry.update();
                }
            }
        }
    }

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


    private void initVuforia()
    {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
    }


    private void initTfod()
    {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.8f;
        tfodParameters.isModelTensorFlow2 = true; //set to false if not wanting position
        tfodParameters.inputSize = 320;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABELS);
    }


    //Stop function
    @Override
    public void stop() {
    }
}