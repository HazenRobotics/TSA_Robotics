package org.firstinspires.ftc.teamcode.teleOP;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.subsystems.HockeyStick;
import org.firstinspires.ftc.teamcode.subsystems.HorizontalExtendo;


@TeleOp(name = "Reset Extendo")
public class ResetExtendo extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        HorizontalExtendo extendo = new HorizontalExtendo(hardwareMap,"leftExtendo", "rightExtendo");
        telemetry.addLine("Hit Start to reset extendo.");
        telemetry.addLine(extendo.toString());
        DcMotor rightStick= hardwareMap.get(DcMotor.class, "rightHockeyStick");
        DcMotor leftStick =  hardwareMap.get(DcMotor.class, "leftHockeyStick");
        telemetry.addLine("Resets Hockey Sticks as well to sync");
        telemetry.update();
        waitForStart();

        boolean move = true;
        while(opModeIsActive() && move){

            extendo.movePos(gamepad1.left_trigger - gamepad1.right_trigger);
            rightStick.setPower(gamepad1.left_stick_y);
            leftStick.setPower(-gamepad1.right_stick_y);
            if(gamepad1.a){
                move = false;
            }
            telemetry.addLine("Use [Triggers] to adjust position");
            telemetry.addLine("Press [a] to end movement and finalize reset");
            telemetry.addLine(extendo.toString());
            telemetry.update();
        }

        extendo.resetPos();
        rightStick.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftStick.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        for (int i=5; i>0; i--){
            telemetry.addLine("Reset Complete");
            telemetry.addData("Ending Telemetry in: ", i);
            telemetry.addLine(extendo.toString());
            telemetry.update();
            sleep(1000);
        }


    }
}
