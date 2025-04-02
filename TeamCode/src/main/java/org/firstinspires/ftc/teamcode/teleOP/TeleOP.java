package org.firstinspires.ftc.teamcode.teleOP;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Robot.Robot;
import org.firstinspires.ftc.teamcode.utils.GamepadEvents;
import org.firstinspires.ftc.vision.VisionPortal;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvWebcam;

@TeleOp(name = "A GOATED TeleOP")
public class TeleOP extends LinearOpMode {
    Robot robot;
    GamepadEvents controller1, controller2;
    public static GamepadEvents.GamepadButton controlBinding1[];
    public static GamepadEvents.GamepadButton controlBinding2[];
    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        robot = new Robot(hardwareMap);
        controller1 = new GamepadEvents(gamepad1);
        controller2 = new GamepadEvents(gamepad2);



        // Get the webcam from the hardware map
        final FtcDashboard_Camera.CameraStreamProcessor processor = new FtcDashboard_Camera.CameraStreamProcessor();

        new VisionPortal.Builder()
                .addProcessor(processor)
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .build();

        FtcDashboard.getInstance().startCameraStream(processor, 20);

        waitForStart();
        robot.initializeStates();

        while(opModeIsActive())
        {
            robot.drive(-controller1.left_stick_y, controller1.left_stick_x, controller1.right_stick_x);
            robot.extend((controller1.left_trigger.getTriggerValue() - controller1.right_trigger.getTriggerValue()));
            //a Button
            if(controller1.x.onPress())
            {
                robot.intakeToggle();
            }
            //b button
            if(controller1.y.onPress())
            {
//                robot.resetStates();
                robot.openClaw();
            }
            //x Button
            if(controller1.b.onPress())
            {
//                robot.toggleClaw();
                robot.ringClaw();
            }
            //x Button && right bumper
            if( controller1.a.onPress())
            {
//                robot.ringClaw();
                robot.closeClaw();
            }


            if(controller1.left_bumper.getValue())
            {
                robot.adjustArmOffset(1);
//                robot.toggleHockeyStick();
            }
            if(controller1.right_bumper.getValue()){
                robot.adjustArmOffset(-1);
            }

            if(controller1.dpad_up.onPress()){
                robot.setHockeyStickUp();
            }
            if(controller1.dpad_down.onPress()){
                robot.setHockeyStickDown();
            }
            if(controller1.dpad_right.onPress()){
                robot.setHockeyStickReset();
            }




            // CONTROLLER 2

            if(controller2.left_bumper.getValue())
            {
                robot.adjustHockeyOffset(1);
            }
            if(controller2.right_bumper.getValue())
            {
                robot.adjustHockeyOffset(-1);
            }


            if(controller2.dpad_up.getValue())
            {
                robot.adjustPivotOffset(5);
            }
            if(controller2.dpad_down.getValue())
            {
                robot.adjustPivotOffset(-5);
            }

            if(controller2.dpad_left.getValue()){
                robot.adjustClawOffset(1);
            }
            if(controller2.dpad_right.getValue()){
                robot.adjustClawOffset(-1);
            }



//            robot.adjustArmOffset(-controller2.left_stick_y);



//            robot.adjustClawOffset(-controller2.right_stick_y);

            controller1.update();
            controller2.update();

            robot.updatePos();
            telemetry.addLine("CONTROLLER 1:");
            telemetry.addLine("Press [A] to Close Claw");
            telemetry.addLine("Press [B] to Close Claw on Ring");
            telemetry.addLine("Press [X] to toggle Parallel/Perpendicular Intake");
            telemetry.addLine("Press [Y] to Open Claw");
            telemetry.addLine("Press [Left_Bumper] to Lower Arm");
            telemetry.addLine("Press [Right_Bumper] to Raise Arm");
            telemetry.addLine("Hold [Triggers] to extend Horizontal Extendo");
            telemetry.addLine("Drive: [Left Joystick Y], Strafe: [Left Joystick X], Rotate: [Right Joystick x]");
            telemetry.addLine("\n CONTROLLER 2:");
            telemetry.addLine("Press [DPAD_LEFT] to Adjust Hockey Stick to Postive #");
            telemetry.addLine("Press [DPAD_RIGHT] to Adjust Hockey Stick to Negative #");
            telemetry.addLine("Press [DPAD_UP] to Adjust PIVOT to Postive #");
            telemetry.addLine("Press [DPAD_DOWN] to Adjust PIVOT to Negative #");

            telemetry.addLine(robot.toString());
            telemetry.update();
        }
    }
}
