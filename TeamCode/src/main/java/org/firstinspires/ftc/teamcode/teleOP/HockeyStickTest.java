package org.firstinspires.ftc.teamcode.teleOP;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.subsystems.HockeyStick;
import org.firstinspires.ftc.teamcode.utils.GamepadEvents;
@TeleOp(name = "A HockeyStick test")
public class HockeyStickTest extends LinearOpMode {
    GamepadEvents controller1, controller2;
    HockeyStick hockeyStick, leftHockeyStick;
    DriveTrain driveTrain;
    @Override
    public void runOpMode() throws InterruptedException {
        controller1 = new GamepadEvents(gamepad1);
        controller2 = new GamepadEvents(gamepad2);
        hockeyStick = new HockeyStick(hardwareMap, "rightHockeyStick");
        leftHockeyStick = new HockeyStick(hardwareMap, "LeftHockeyStick");
        driveTrain = new DriveTrain(hardwareMap,"frontLeft", "backLeft", "frontRight",
                "backRight");

        waitForStart();



        while (opModeIsActive()) {
            driveTrain.cubedDrive(-controller1.left_stick_y, controller1.left_stick_x, controller1.right_stick_x);
            //UP Pos:750
            //Down Pos: 1000
            if (controller1.left_bumper.onPress())
            {
                hockeyStick.toggle();
            }

            if(controller1.right_bumper.onPress())
            {
                hockeyStick.reset();
            }

            if(controller2.dpad_up.getValue())
            {
                hockeyStick.adjustPos(1);
                leftHockeyStick.adjustPos(-1);
            }
            if(controller2.dpad_down.getValue())
            {
                hockeyStick.adjustPos(-1);
                leftHockeyStick.adjustPos(1);
            }



            controller1.update();
            controller2.update();


//            telemetry.addLine("Press[Left_Bumper] to set UP Pos");
//            telemetry.addLine("Press[Right_Bumper] to set DOWN Pos");
            telemetry.addLine("Press[Left Bumper] to set Toggle Pos");
            telemetry.addLine("press [DPAD UP & DPAD DOWN] to adjust set Posiitons");
            telemetry.addLine(hockeyStick.toString());
            telemetry.addLine(leftHockeyStick.toString());
            telemetry.update();
        }
    }
}
