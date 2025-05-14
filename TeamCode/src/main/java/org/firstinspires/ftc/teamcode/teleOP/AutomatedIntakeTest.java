package org.firstinspires.ftc.teamcode.teleOP;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.AutomatedIntake;
import org.firstinspires.ftc.teamcode.utils.GamepadEvents;

@TeleOp(name="AutomatedIntakeTest")
public class AutomatedIntakeTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        AutomatedIntake intake = new AutomatedIntake(hardwareMap);
        GamepadEvents controller1 = new GamepadEvents(gamepad1);

        waitForStart();

        intake.init();

        while(opModeIsActive()){



            if(controller1.a.onPress()){
                intake.toggleTargetting();
            }
            if (intake.getRelativeTargetting()){
                intake.horizontalChange(controller1.left_trigger.getTriggerValue() - controller1.right_trigger.getTriggerValue());
                intake.verticalChange(-controller1.left_stick_y);
                intake.angleChange(-controller1.right_stick_y);
            }else{
                intake.adjustExtedo(gamepad1.left_trigger - gamepad1.right_trigger);
                intake.adjustArm(gamepad1.left_stick_y);
                intake.adjustWrist(gamepad1.right_stick_y);
            }

            intake.update();
            telemetry.addLine(intake.toString());
            telemetry.update();
            controller1.update();
        }


    }
}
