package org.firstinspires.ftc.teamcode.teleOP;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.utils.GamepadEvents;

@TeleOp(name = "Claw Tester")
public class ClawTester extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Claw claw = new Claw(hardwareMap,"Claw");
        GamepadEvents controller = new GamepadEvents(gamepad1);

        waitForStart();
//        claw.setPosition(0.7);
        while(opModeIsActive())
        {
            //set zero as open

//            claw.setPosition(0);
//            if(controller.b.onPress())
//            {
//                claw.toggle();
//            }
//
////
//            if(controller.b.onPress() && controller.left_bumper.onPress())
//            {
//                claw.ringClose();
//            }
            claw.adjustPosition(controller.left_stick_y);

            telemetry.addData("Expected Claw Pos", claw.getPos());
            telemetry.addLine("Resetting to Zero");
            telemetry.addLine("PRESS[B] to open claw");
            telemetry.update();
            controller.update();
        }
    }
}
