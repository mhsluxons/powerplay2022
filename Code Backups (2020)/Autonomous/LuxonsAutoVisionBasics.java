package org.firstinspires.ftc.teamcode.Autonomous;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Vision.Camera;


@Autonomous(name="LuxonsAutoVisionBasics") //basics of detection on wobbles
@Disabled
public class LuxonsAutoVisionBasics extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    //Declares Motors on Wheels
    private DcMotor leftFrontMotor;
    private DcMotor leftBackMotor;
    private DcMotor rightFrontMotor;
    private DcMotor rightBackMotor;

    //Other Motors
    public Servo rightGrab;
    public Servo leftGrab;
    public Servo rightClaw;
    public Servo leftClaw;
    public Servo cap;
    //public Servo sidearm;

    //Detection
    public Camera camera = new Camera();
    private static final String VUFORIA_KEY = "Ad4IdFr/////AAABmQfmLScrK0DGonZfbL1sfTQ2tVSJ8ZIyMRVYsinvjW7RphHb4VAf+20Jnu9bDcMwu4x0iTiGWGrQTIoSFkRRVNC+kxUeuLJ+4wi2MW/4cdB+vDgtS3IcoJhB4oFExvk/SHMNeapM2nUb5SAtmd+69syuyZpFBSwDUAqnPdSHHAtKh0qq6+v3QilZ0NOfUqdj9GA1372wggFrm8ZQGmnfq5rwjtaxhcfMZVjrL/O3mwGJBYki1nZkfaS4ULjFv86MOkLZSB8BPqG82tgMCiLJaqMwAT12i+K7eD4nOYeQv4MF1FJtjd17oIjLVBRPEL19mSKRl4rsJkqaROalHUwtYHmQPNnMpEFf+uw/YzX/zgTL";
    public int detector[] = {253, 168, 53};
    private static int offset[] = {100, 100, 100};

    //public double SIDE_ARM_INITIAL = 0.06;
    //public double SIDE_ARM_END = 0.56;
    public double CAP_INITIAL = 0.25;

    //Declares Motor Function Arrays
    private final double gearRatio = 1.0;
    private final double wheelRadius = 2.0;
    private final double TicksperRev = 1500.0;
    private double powerForward[] = {0.6, 0.6};
    private double powerSpin[] = {1.0, 1.0};

    //OpMode Variables:
    private double toBridge = (40); //the robot is 18 in width and 18 in length
    private double toStones = (25); //each grey square is 24 inches

    //Runs the OpMode
    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        leftFrontMotor = hardwareMap.dcMotor.get("drive_left_front");
        rightFrontMotor = hardwareMap.dcMotor.get("drive_right_front");
        leftBackMotor = hardwareMap.dcMotor.get("drive_left_back");
        rightBackMotor = hardwareMap.dcMotor.get("drive_right_back");

        //Vision Initialization:
        camera.initVuforia(hardwareMap, VUFORIA_KEY, 20, true);
        camera.initDetector("ringDetector", detector);


        leftFrontMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        leftBackMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightFrontMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBackMotor.setDirection(DcMotorSimple.Direction.REVERSE);


        leftFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBackMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBackMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //waits for start.
        waitForStart();
        runtime.reset();

        int height = getPixelsPosition();
        telemetry.addData("position:", height);
        telemetry.update();
        sleep(10000);


    }

    //***Autonomous Functions***
    public void run(String type, double distance)
    {

        int ticks = (int)((distance / (2 * Math.PI * wheelRadius) * gearRatio) * TicksperRev);

//        int backLeftPos = leftBackMotor.getCurrentPosition();
//        backLeftPos = backLeftPos + (ticks/8);
//
//        int frontRightTicks = ticks*70/100;
//        int backRightTicks = ticks*80/100;


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

//        double frontRPowertemp = powerForward[0] *65/100;
//        double backRPowertemp = powerForward[0] *65/100;

        leftFrontMotor.setPower(powerForward[0]);
        leftBackMotor.setPower(powerForward[0]);
        rightFrontMotor.setPower(powerForward[0]);
        rightBackMotor.setPower(powerForward[0]);
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

        leftFrontMotor.setPower(powerSpin[0]);
        leftBackMotor.setPower(powerSpin[0]);
        rightFrontMotor.setPower(powerSpin[0]);
        rightBackMotor.setPower(powerSpin[0]);
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

        leftFrontMotor.setPower(powerForward[0]);
        leftBackMotor.setPower(powerForward[0]);
        rightFrontMotor.setPower(powerForward[0]);
        rightBackMotor.setPower(powerForward[0]);
    }

    public void diagRobot(String type, double distanceInRotations)
    {
        int partsRotationsLeft = (int)(distanceInRotations*TicksperRev);
        int partsRotationsRight = (int)(distanceInRotations*TicksperRev);

        double LF = 0, LB = 0, RF = 0, RB = 0;

        if (type.equalsIgnoreCase("left and up")) {
            LF = 0.0;
            RF = 0.4;
            RB = 0.0;
            LB = 0.4;
            rightFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() + partsRotationsRight);
            leftBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition() + partsRotationsLeft);
        }

        else if (type.equalsIgnoreCase("right and up")) {
            LF = 0.4;
            RF = 0.0;
            RB = 0.4;
            LB = 0.0;
            leftFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() + partsRotationsLeft);
            rightBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition() + partsRotationsRight);
        }

        else if (type.equalsIgnoreCase("left and down")) {
            LF = 0.4;
            RF = 0.0;
            RB = 0.4;
            LB = 0.0;
            leftFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() - partsRotationsLeft);
            rightBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition() - partsRotationsRight);
        }

        else if (type.equalsIgnoreCase("right and down")) {
            LF = 0.0;
            RF = 0.4;
            RB = 0.0;
            LB = 0.4;
            rightFrontMotor.setTargetPosition(leftFrontMotor.getCurrentPosition() - partsRotationsRight);
            leftBackMotor.setTargetPosition(rightBackMotor.getCurrentPosition() - partsRotationsLeft);
        }

        leftFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFrontMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftFrontMotor.setPower(LF);
        leftBackMotor.setPower(LB);
        rightFrontMotor.setPower(RF);
        rightBackMotor.setPower(RB);
    }

    public int getVision()
    {
        //Analyzes and Returns the Boolean Count:
        Bitmap image = camera.getImage(0.2);
        int rgb[][] = camera.getBitmapRGB(image, 0, 0, 100, 100); //CHANGE NUMBERS WHEN ON FIELD
        int booleanCount = camera.detectPixelCount(rgb, offset, 0);
        return booleanCount;
    }

    public int getPixelsPosition()
    {
        //Vision Positions:
        int position = 1;
        int booleanCount = getVision();

        //Checks the Case:
        if (booleanCount <= 100) //CHANGE WHEN ON FIELD
        {
            //Sets the Position:
            position = 1; //position A
        }

        else if (booleanCount > 100 && booleanCount <= 5000) //CHANGE ON FIELD
        {
            //Sets the Position:
            position = 2; //position B
        }

        else {
            //Sets the Position:
            position = 3; //position C
        }

        //Returns the Position:
        return position;
    }
}