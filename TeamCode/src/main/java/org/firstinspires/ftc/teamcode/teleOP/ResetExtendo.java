package org.firstinspires.ftc.teamcode.teleOP;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.HockeyStick;
import org.firstinspires.ftc.teamcode.subsystems.HorizontalExtendo;


@TeleOp(name = "Reset Extendo")
public class ResetExtendo extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        HorizontalExtendo extendo = new HorizontalExtendo(hardwareMap,"leftExtendo", "rightExtendo");
        telemetry.addLine("Hit Start to reset extendo.");
        telemetry.addLine(extendo.toString());
        HockeyStick rightHockeyStick= new HockeyStick(hardwareMap, "rightHockeyStick");
        HockeyStick leftHockeyStick = new HockeyStick(hardwareMap, "leftHockeyStick");
        telemetry.addLine("Resets Hockey Sticks as well to sync");
        telemetry.update();
        waitForStart();

        boolean move = true;
        while(opModeIsActive() && move){

            extendo.movePos(gamepad1.left_trigger - gamepad1.right_trigger);
            rightHockeyStick.reset();
            leftHockeyStick.reset();
            if(gamepad1.a){
                move = false;
            }
            telemetry.addLine("Use [Triggers] to adjust position");
            telemetry.addLine("Press [a] to end movement and finalize reset");
            telemetry.addLine(extendo.toString());
            telemetry.update();
        }

        extendo.resetPos();
        for (int i=5; i>0; i--){
            telemetry.addLine("Reset Complete");
            telemetry.addData("Ending Telemetry in: ", i);
            telemetry.addLine(extendo.toString());
            telemetry.update();
            sleep(1000);
        }


    }
}
