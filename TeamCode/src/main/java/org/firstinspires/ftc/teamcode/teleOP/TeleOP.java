package org.firstinspires.ftc.teamcode.teleOP;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot.Robot;
import org.firstinspires.ftc.teamcode.utils.GamepadEvents;

@TeleOp(name = "A GOATED TeleOP")
public class TeleOP extends LinearOpMode {
    Robot robot;
    GamepadEvents controller1, controller2;
    public static GamepadEvents.GamepadButton controlBinding1[];
    public static GamepadEvents.GamepadButton controlBinding2[];
    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot(hardwareMap);
        controller1 = new GamepadEvents(gamepad1);
        controller2 = new GamepadEvents(gamepad2);

        waitForStart();
        robot.initializeStates();

        while(opModeIsActive())
        {
            robot.drive(-controller1.left_stick_y, controller1.left_stick_x, controller1.right_stick_x);
            robot.extend((controller1.left_trigger.getTriggerValue() - controller1.right_trigger.getTriggerValue())* 100);
            //a Button
            if(controller1.a.onPress())
            {
                robot.intakeToggle();
            }
            //b button
            if(controller1.b.onPress())
            {
                robot.resetStates();
            }
            //x Button
            if(controller1.x.onPress())
            {
                robot.toggleClaw();
            }
            //x Button && right bumper
            if( controller1.y.onPress())
            {
                robot.ringClaw();
            }

            if(controller1.left_bumper.onPress())
            {
                robot.toggleHockeyStick();
            }

            // CONTROLLER 2

            if(controller2.dpad_left.onPress())
            {
                robot.adjustHockeyOffset(1);
            }
            if(controller2.dpad_right.onPress())
            {
                robot.adjustHockeyOffset(-1);
            }


            if(controller2.dpad_up.onPress())
            {
                robot.adjustPivotOffset(5);
            }
            if(controller2.dpad_down.onPress())
            {
                robot.adjustPivotOffset(-5);
            }



            robot.adjustArmOffset(-controller2.left_stick_y);



//            robot.adjustClawOffset(-controller2.right_stick_y);

            controller1.update();
            controller2.update();

            robot.updatePos();
            telemetry.addLine("CONTROLLER 1:");
            telemetry.addLine("Press [A] to toggle Intake");
            telemetry.addLine("Press [B] to reset states");
            telemetry.addLine("Press [X] to toggle Claw");
            telemetry.addLine("Press [Y] to press RING Claw");
            telemetry.addLine("Press [Left_Bumper] to toggle Hockey Stick");
            telemetry.addLine("Hold [Triggers] to extend Horizontal Rxtendo");
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
