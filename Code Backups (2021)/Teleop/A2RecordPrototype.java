package org.firstinspires.ftc.robotcontroller.TeleOp;

import java.text.Format;
import java.util.*;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import java.math.*;
import java.text.DecimalFormat;

@TeleOp(name="A2RecordPrototype", group="Tele-Opmode-2019")
@Disabled
public class A2RecordPrototype extends OpMode {

    final float TRIGGER_DEADZONE = 0;
    final float JOYSTICK_DEADZONE = 0;
    boolean continuous = true;

    DecimalFormat df = new DecimalFormat("#.####");
    String outputCode;
    private ElapsedTime runtime = new ElapsedTime();
    LuxonsRobot2019 robot = new LuxonsRobot2019();

    @Override
    public void init() {
        robot.init(hardwareMap);
    }

    @Override
    public void loop() {
        if (runtime.milliseconds() > 1000 && runtime.milliseconds() % 50 == 0 && runtime.milliseconds() < 30000 && continuous) {

            if (gamepad1.y) {
                outputCode += 1;
            }
            else {
                outputCode += 0;
            }
            outputCode += ",";

            if (gamepad1.x) {
                outputCode += 1;
            }
            else {
                outputCode += 0;
            }
            outputCode += ",";

            if (gamepad1.a) {
                outputCode += 1;
            }
            else {
                outputCode += 0;
            }
            outputCode += ",";

            if (gamepad1.b) {
                outputCode += 1;
            }
            else {
                outputCode += 0;
            }
            outputCode += ",";

            if (gamepad1.dpad_up) {
                outputCode += 1;
            }
            else {
                outputCode += 0;
            }
            outputCode += ",";

            if (gamepad1.dpad_down) {
                outputCode += 1;
            }
            else {
                outputCode += 0;
            }
            outputCode += ",";

            if (gamepad1.dpad_left) {
                outputCode += 1;
            }
            else {
                outputCode += 0;
            }
            outputCode += ",";

            if (gamepad1.dpad_right) {
                outputCode += 1;
            }
            else {
                outputCode += 0;
            }
            outputCode += ",";

            if (gamepad1.left_bumper) {
                outputCode += 1;
            }
            else {
                outputCode += 0;
            }
            outputCode += ",";

            if (gamepad1.right_bumper) {
                outputCode += 1;
            }
            else {
                outputCode += 0;
            }
            outputCode += ",";

            if (gamepad1.left_trigger > TRIGGER_DEADZONE) {
                outputCode += df.format(gamepad1.left_trigger);
            }
            else
            {
                outputCode += df.format(0);
            }
            outputCode += ",";

            if (gamepad1.right_trigger > TRIGGER_DEADZONE) {
                outputCode += df.format(gamepad1.right_trigger);
            }
            else
            {
                outputCode += df.format(0);
            }
            outputCode += ",";

            if (gamepad1.left_stick_x > JOYSTICK_DEADZONE || gamepad1.left_stick_x < -JOYSTICK_DEADZONE) {
                outputCode += df.format(gamepad1.left_stick_x);
            }
            else
            {
                outputCode += df.format(0);
            }
            outputCode += ",";

            if (gamepad1.left_stick_y > JOYSTICK_DEADZONE || gamepad1.left_stick_y < -JOYSTICK_DEADZONE) {
                outputCode += df.format(gamepad1.left_stick_y);
            }
            else
            {
                outputCode += df.format(0);
            }
            outputCode += ",";

            if (gamepad1.right_stick_x > JOYSTICK_DEADZONE || gamepad1.right_stick_x < -JOYSTICK_DEADZONE) {
                outputCode += df.format(gamepad1.right_stick_x);
            }
            else
            {
                outputCode += df.format(0);
            }
            outputCode += ",";

            if (gamepad1.right_stick_y > JOYSTICK_DEADZONE || gamepad1.right_stick_y < -JOYSTICK_DEADZONE) {
                outputCode += df.format(gamepad1.right_stick_y);
            }
            else
            {
                outputCode += df.format(0);
            }
            outputCode += ";";

            if (gamepad1.back)
            {
                continuous = false;
            }

        }
    }
}

