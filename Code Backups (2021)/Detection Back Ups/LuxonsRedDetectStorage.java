package org.firstinspires.ftc.robotcontroller.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.vuforia.CameraDevice;

import java.util.*;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.Camera;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;


//by Adi
//FACES BACKWARDS
@Autonomous(name = "Storage Red Detect")
@Disabled
public class LuxonsRedDetectStorage extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    //Declares Motors on Wheels
    private DcMotor leftFrontMotor;
    private DcMotor leftBackMotor;
    private DcMotor rightFrontMotor;
    private DcMotor rightBackMotor;


    //intake inputs
    //private DcMotor intake_Motor;
    private DcMotor intake_motor;
    private final double intakeSpeed = 0.8;

    private DcMotor duck_motor;
    private final double duckSpeed = 0.9;

    private DcMotor shoot_motor;
    private final double shootSpeed = 0.4;


    //Other Motors
    public Servo rightGrab;
    public Servo leftGrab;
    public Servo rightClaw;
    public Servo leftClaw;
    public Servo cap;
    public Servo block_motor;
    public Servo plop_motor;

    //public Servo sidearm;

    //Values for the block deliverer
    public double BLOCK_INITIAL = 0.06;
    public double BLOCK_END = 0.56;

    public double PLOP_MOTOR_INITIAL = 0.5;
    public double PLOP_MOTOR_END = 1.0;

    //Values for detection
    private String duck = "";
    private int duckPos;
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

    //Declares Motor Function Arrays
    private final double gearRatio = 1.0;
    private final double wheelRadius = 2.0;
    private final double TicksperRev = 1500.0;
    private double powerForward[] = {0.6, 0.6};
    private double powerSpin[] = {1.0, 1.0};
    private double wheelPower = (powerForward[0]*0.1);
    private double fastWheels = (powerForward[0]*0.3);

    //OpMode Variables:
    private double toBridge = (40); //the robot is 18 in width and 18 in length
    private double toStones = (25); //each grey square is 24 inches

    //Runs the OpMode
    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        initVuforia();
        initTfod();

        leftFrontMotor = hardwareMap.dcMotor.get("drive_left_front");
        rightFrontMotor = hardwareMap.dcMotor.get("drive_right_front");
        leftBackMotor = hardwareMap.dcMotor.get("drive_left_back");
        rightBackMotor = hardwareMap.dcMotor.get("drive_right_back");

        intake_motor = hardwareMap.dcMotor.get("intake_motor");
        duck_motor = hardwareMap.dcMotor.get("duck_motor");
        shoot_motor = hardwareMap.dcMotor.get("shoot_motor");
        plop_motor = hardwareMap.servo.get("plop_motor");
        //sidearm = hardwareMap.servo.get("sidearm");
        //sidearm.setPosition(SIDE_ARM_INITIAL);

        leftFrontMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        leftBackMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);


        //*****
        intake_motor.setDirection(DcMotorSimple.Direction.REVERSE);
        intake_motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intake_motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        duck_motor.setDirection(DcMotorSimple.Direction.FORWARD);
        duck_motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        duck_motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        shoot_motor.setDirection(DcMotorSimple.Direction.FORWARD);
        shoot_motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        shoot_motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //******

        leftFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBackMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        if (tfod != null) {
            tfod.activate();
            tfod.setZoom(1.5, 16.0/9.0);
        }

        //waits for start.
        waitForStart();
        runtime.reset();


        //moves away from the wall
        run("front", 1);
        idle();
        sleep(700);
        finishRun();
        idle();


        //turns on camera flash
        CameraDevice.getInstance().setFlashTorchMode(true);


        if (tfod != null) //detects
        {
            // getUpdatedRecognitions() will return null if no new information is available since
            // the last time that call was made.
            List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
            if (updatedRecognitions != null)
            {
                telemetry.addData("# Object Detected", updatedRecognitions.size());

                // step through the list of recognitions and display boundary info.
                int i = 0; //number of object detected
                for (Recognition recognition : updatedRecognitions)
                {
                    telemetry.addData(String.format("label (%d)", i), recognition.getLabel()); //detects the object
                    duck = recognition.getLabel();
                    telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                            recognition.getLeft(), recognition.getTop());
                    telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                            recognition.getRight(), recognition.getBottom());
                    i++;
                }
                telemetry.update();
            }
        }

        //the robot pauses to detect
        try {
            Thread.sleep(2000);
        } catch (Exception e){}


        for (int x=1; x<=2; x++)
        {
            if (x==1)
            {
                if (duck.equals("Duck") || duck.equals("Cube"))
                {
                    duckPos = 2;
                    break;
                }
                else
                {
                    //moves to third barcode
                    shiftRobot("right", 1);
                    idle();
                    sleep(1100);
                    finishRun();
                    idle();

                    if (tfod != null) //detects
                    {
                        // getUpdatedRecognitions() will return null if no new information is available since
                        // the last time that call was made.
                        List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                        if (updatedRecognitions != null)
                        {
                            telemetry.addData("# Object Detected", updatedRecognitions.size());

                            // step through the list of recognitions and display boundary info.
                            int i = 0; //number of object detected
                            for (Recognition recognition : updatedRecognitions)
                            {
                                telemetry.addData(String.format("label (%d)", i), recognition.getLabel()); //detects the object
                                duck = recognition.getLabel();
                                telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                                        recognition.getLeft(), recognition.getTop());
                                telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                                        recognition.getRight(), recognition.getBottom());
                                i++;
                            }
                            telemetry.update();
                        }
                    }

                    //the robot pauses to detect
                    try {
                        Thread.sleep(2000);
                    } catch (Exception e){}
                }
            }
            if (x==2)
            {
                if (duck.equals("Duck") || duck.equals("Cube"))
                {
                    duckPos = 3;
                    break;
                }
                else
                {
                    duckPos = 1;
                    break;
                }
            }
        }

        //turns off camera flash
        CameraDevice.getInstance().setFlashTorchMode(false);

        if(duckPos == 1||duckPos==3)
        {
            //moves back a bit
            run("back", 1);
            idle();
            sleep(1400);
            finishRun();
            idle();

            //moves to shipping hub
            shiftRobot("right", 1);
            idle();
            sleep(600);
            finishRun();
            idle();

            //moves closer to hub
            run("front", 1);
            idle();
            sleep(1400);
            finishRun();
            idle();

            if(duckPos==1)
            {
                //places block on level 1
                plop_motor.setPosition(PLOP_MOTOR_INITIAL);
                idle();
                sleep(2000);
                finishRun();
                idle();

                plop_motor.setPosition(PLOP_MOTOR_END);
                idle();
                sleep(2000);
                finishRun();
                idle();

            }
            else
            {
                //places block on level 3
                shoot_motor.setPower(shootSpeed);
                idle();
                sleep(2000);
                finishRun();
                idle();

                //places block on level 3
                plop_motor.setPosition(PLOP_MOTOR_INITIAL);
                idle();
                sleep(2000);
                finishRun();
                idle();

                plop_motor.setPosition(PLOP_MOTOR_END);
                idle();
                sleep(2000);
                finishRun();
                idle();

            }

        }
        if(duckPos == 2)
        {

            //moves back a bit
            run("back", 1);
            idle();
            sleep(1400);
            finishRun();
            idle();

            //moves to shipping hub
            shiftRobot("right", 2);
            idle();
            sleep(1800);
            finishRun();
            idle();

            //moves closer to hub
            run("front", 1);
            idle();
            sleep(1400);
            finishRun();
            idle();

            //places block on level 2
            shoot_motor.setPower(shootSpeed);
            idle();
            sleep(700);
            finishRun();
            idle();

            plop_motor.setPosition(PLOP_MOTOR_END);
            idle();
            sleep(2000);
            finishRun();
            idle();

            plop_motor.setPosition(PLOP_MOTOR_INITIAL);
            idle();
            sleep(2000);
            finishRun();
            idle();
        }

        //moves back a bit
        run("back", 1);
        idle();
        sleep(900);
        finishRun();
        idle();

        //moves to carousel wall
        fastshiftRobot("left", 3);
        idle();
        sleep(1900);
        finishRun();
        idle();

        //moves to park
        fastRun("front", 1);
        idle();
        sleep(1100);
        finishRun();
        idle();

        //if pulley was used, puts it down
        /*if(duckPos == 2 || duckPos == 3)
        {
            //puts pulley down
            shoot_motor.setPower(-shootSpeed);
            idle();
            sleep(800);
            finishRun();
            idle();
        }*/

    }

    //***Autonomous Functions***
    public void run(String type, double distance)
    {

        int ticks = (int)((distance / (2 * Math.PI * wheelRadius) * gearRatio) * TicksperRev);

//        int backLeftPos = leftBackMotor.getCurrentPosition();
//        backLeftPos = backLeftPos + (ticks/8);
//
        int frontRightTicks = ticks*25/100;
        int backRightTicks = ticks*25/100;


        if (type.equalsIgnoreCase("front")) {
            leftFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() + ticks);
            leftBackMotor.setTargetPosition(leftBackMotor.getCurrentPosition() + ticks);
            rightFrontMotor.setTargetPosition(rightFrontMotor.getCurrentPosition() + ticks);
            rightBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition() + ticks);
        }

        else if (type.equalsIgnoreCase("back")) {
            leftFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() - ticks);
            leftBackMotor.setTargetPosition(leftBackMotor.getCurrentPosition() - ticks);
            rightFrontMotor.setTargetPosition(rightFrontMotor.getCurrentPosition() - ticks);
            rightBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition() - ticks);
        }

        leftFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        double frontRPowertemp = powerForward[0] *25/100;
        double backRPowertemp = powerForward[0] *25/100;


        leftFrontMotor.setPower(wheelPower);
        leftBackMotor.setPower(wheelPower);
        rightFrontMotor.setPower(wheelPower);
        rightBackMotor.setPower(wheelPower);
    }

    public void fastRun(String type, double distance)
    {

        int ticks = (int)((distance / (2 * Math.PI * wheelRadius) * gearRatio) * TicksperRev);

//        int backLeftPos = leftBackMotor.getCurrentPosition();
//        backLeftPos = backLeftPos + (ticks/8);
//
        int frontRightTicks = ticks*25/100;
        int backRightTicks = ticks*25/100;


        if (type.equalsIgnoreCase("front")) {
            leftFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() + ticks);
            leftBackMotor.setTargetPosition(leftBackMotor.getCurrentPosition() + ticks);
            rightFrontMotor.setTargetPosition(rightFrontMotor.getCurrentPosition() + ticks);
            rightBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition() + ticks);
        }

        else if (type.equalsIgnoreCase("back")) {
            leftFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() - ticks);
            leftBackMotor.setTargetPosition(leftBackMotor.getCurrentPosition() - ticks);
            rightFrontMotor.setTargetPosition(rightFrontMotor.getCurrentPosition() - ticks);
            rightBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition() - ticks);
        }

        leftFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        double frontRPowertemp = powerForward[0] *25/100;
        double backRPowertemp = powerForward[0] *25/100;


        leftFrontMotor.setPower(fastWheels);
        leftBackMotor.setPower(fastWheels);
        rightFrontMotor.setPower(fastWheels);
        rightBackMotor.setPower(fastWheels);
    }

    public void finishRun()
    {
        leftFrontMotor.setPower(0);
        leftBackMotor.setPower(0);
        rightFrontMotor.setPower(0);
        rightBackMotor.setPower(0);
    }

    public void spin(String type, double distance)
    {
        int ticks = (int)((distance / (2 * Math.PI * wheelRadius) * gearRatio) * TicksperRev);

        if (type.equalsIgnoreCase("right")) {
            leftFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() + ticks);
            leftBackMotor.setTargetPosition(leftBackMotor.getCurrentPosition() + ticks);
            rightFrontMotor.setTargetPosition(rightFrontMotor.getCurrentPosition() - ticks);
            rightBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition() - ticks);
        }

        else if (type.equalsIgnoreCase("left")) {
            leftFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() - ticks);
            leftBackMotor.setTargetPosition(leftBackMotor.getCurrentPosition()  - ticks);
            rightFrontMotor.setTargetPosition(rightFrontMotor.getCurrentPosition() + ticks);
            rightBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition() + ticks);
        }

        leftFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftFrontMotor.setPower(wheelPower);
        leftBackMotor.setPower(wheelPower);
        rightFrontMotor.setPower(wheelPower);
        rightBackMotor.setPower(wheelPower);
    }

    public void turnRobot(String type, double distanceInRotations)
    {
        int partsRotationsLeft = (int)(distanceInRotations*TicksperRev);
        int partsRotationsRight = (int)(distanceInRotations*TicksperRev);

        if (type.equalsIgnoreCase("left")) {
            leftFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() - partsRotationsLeft);
            leftBackMotor.setTargetPosition(leftBackMotor.getCurrentPosition() - partsRotationsLeft);
            rightFrontMotor.setTargetPosition(rightFrontMotor.getCurrentPosition() + partsRotationsRight);
            rightBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition() + partsRotationsRight);
        }

        else if (type.equalsIgnoreCase("right")) {
            leftFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() + partsRotationsLeft);
            leftBackMotor.setTargetPosition(leftBackMotor.getCurrentPosition() + partsRotationsLeft);
            rightFrontMotor.setTargetPosition(rightFrontMotor.getCurrentPosition() - partsRotationsRight);
            rightBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition() - partsRotationsRight);
        }

        leftFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftFrontMotor.setPower(powerForward[0]);
        leftBackMotor.setPower(powerForward[0]);
        rightFrontMotor.setPower(powerForward[1]);
        rightBackMotor.setPower(powerForward[1]);
    }

    public void shiftRobot(String type, double distance)
    {
//        double tempDistance = distance * 32/25;
        int ticks = (int)((distance / (2 * Math.PI * wheelRadius) * gearRatio) * TicksperRev);

//        int backLeftPos = leftBackMotor.getCurrentPosition();
//        backLeftPos = backLeftPos + (ticks/8);
//
//        int frontRightTicks = ticks*70/100;
//        int backRightTicks = ticks*80/100;


        if (type.equalsIgnoreCase("left")) {
            leftFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() - ticks);
            leftBackMotor.setTargetPosition(leftBackMotor.getCurrentPosition() + ticks);
            rightFrontMotor.setTargetPosition(rightFrontMotor.getCurrentPosition() + ticks);
            rightBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition() - ticks);
        }

        else if (type.equalsIgnoreCase("right")) {
            leftFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() + ticks);
            leftBackMotor.setTargetPosition(leftBackMotor.getCurrentPosition()  - ticks);
            rightFrontMotor.setTargetPosition(rightFrontMotor.getCurrentPosition() - ticks);
            rightBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition() + ticks);
        }

        leftFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

//        double frontRPowertemp = powerForward[0] *65/100;
//        double backRPowertemp = powerForward[0] *65/100;

        leftFrontMotor.setPower(wheelPower);
        leftBackMotor.setPower(wheelPower);
        rightFrontMotor.setPower(wheelPower);
        rightBackMotor.setPower(wheelPower);
    }

    public void fastshiftRobot(String type, double distance)
    {
//        double tempDistance = distance * 32/25;
        int ticks = (int)((distance / (2 * Math.PI * wheelRadius) * gearRatio) * TicksperRev);

//        int backLeftPos = leftBackMotor.getCurrentPosition();
//        backLeftPos = backLeftPos + (ticks/8);
//
//        int frontRightTicks = ticks*70/100;
//        int backRightTicks = ticks*80/100;


        if (type.equalsIgnoreCase("left")) {
            leftFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() - ticks);
            leftBackMotor.setTargetPosition(leftBackMotor.getCurrentPosition() + ticks);
            rightFrontMotor.setTargetPosition(rightFrontMotor.getCurrentPosition() + ticks);
            rightBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition() - ticks);
        }

        else if (type.equalsIgnoreCase("right")) {
            leftFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() + ticks);
            leftBackMotor.setTargetPosition(leftBackMotor.getCurrentPosition()  - ticks);
            rightFrontMotor.setTargetPosition(rightFrontMotor.getCurrentPosition() - ticks);
            rightBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition() + ticks);
        }

        leftFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

//        double frontRPowertemp = powerForward[0] *65/100;
//        double backRPowertemp = powerForward[0] *65/100;

        leftFrontMotor.setPower(fastWheels);
        leftBackMotor.setPower(fastWheels);
        rightFrontMotor.setPower(fastWheels);
        rightBackMotor.setPower(fastWheels);
    }

    private void initVuforia()
    {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
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



}