package org.firstinspires.ftc.teamcode.teleOP;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.AutomatedIntake;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.subsystems.HockeyStick;
import org.firstinspires.ftc.teamcode.utils.GamepadEvents;
import org.firstinspires.ftc.teamcode.utils.IndicatorLight;

@TeleOp(name="Version 2 TeleOp")
public class Version2TeleOp extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DriveTrain robot = new DriveTrain(hardwareMap,"frontLeft", "backLeft", "frontRight",
                "backRight");
        AutomatedIntake intake = new AutomatedIntake(hardwareMap);
        Claw claw = new Claw(hardwareMap, "claw");
        HockeyStick rightHockeyStick= new HockeyStick(hardwareMap, "rightHockeyStick");
        HockeyStick leftHockeyStick = new HockeyStick(hardwareMap, "leftHockeyStick");
        IndicatorLight light = new IndicatorLight(hardwareMap, "light");
        rightHockeyStick.reverseDirection();

        long lightClock = System.currentTimeMillis();

        GamepadEvents controller1 = new GamepadEvents(gamepad1);
        GamepadEvents controller2 = new GamepadEvents(gamepad2);

        waitForStart();

        intake.init();


        while(opModeIsActive()){

            robot.drive(-gamepad1.left_stick_y - gamepad2.left_stick_y,gamepad1.left_stick_x + gamepad2.left_stick_x, gamepad1.right_stick_x + gamepad2.right_stick_x);


            if(controller1.a.onPress()) {
                claw.toggle();
            }
            //Open Manual
            if(gamepad1.y){
                claw.adjustPosition(-1);
            }
            //Close Manual
            else if(gamepad1.b){
                claw.adjustPosition(1);
            }

            if(controller1.x.onPress()){
                intake.toggleTargetting();
                if (intake.getRelativeTargetting()) {
                    light.setColor(IndicatorLight.GREEN_WEIGHT);
                }else{
                    light.setColor(IndicatorLight.BLUE_WEIGHT);
                }
                lightClock = System.currentTimeMillis() + 1000;
            }
            if (intake.getRelativeTargetting()){
                intake.horizontalChange(controller1.right_trigger.getTriggerValue() - controller1.left_trigger.getTriggerValue());
                if (gamepad1.dpad_up){
                    intake.verticalChange(1);
                }
                else if(gamepad1.dpad_down){
                    intake.verticalChange(-1);
                }
                if (gamepad1.dpad_left){
                    intake.angleChange(1);
                }
                else if(gamepad1.dpad_right){
                    intake.angleChange(-1);
                }

            }
            else{
                intake.adjustExtedo(gamepad1.left_trigger - gamepad1.right_trigger);
                if (gamepad1.dpad_up){
                    intake.adjustArm(-1);
                }
                else if(gamepad1.dpad_down){
                    intake.adjustArm(1);
                }
                if (gamepad1.dpad_left){
                    intake.adjustWrist(-1);
                }
                else if(gamepad1.dpad_right){
                    intake.adjustWrist(1);
                }
            }

            if(controller1.left_bumper.onPress() || controller2.left_bumper.onPress())
            {
                rightHockeyStick.reset();
                leftHockeyStick.reset();
            }
            if(controller1.right_bumper.onPress() || controller2.left_bumper.onPress())
            {
                rightHockeyStick.toggle();
                leftHockeyStick.toggle();
            }


            if (controller1.left_stick_button.onPress()){
                intake.setAngle(Math.toRadians(180));
            }
            if (controller1.right_stick_button.onPress()){
                intake.setAngle(Math.toRadians(270));
            }

            intake.update();

            if (lightClock < System.currentTimeMillis()){
                light.setColor(0);
            }

            if (controller2.dpad_down.getValue()){
                leftHockeyStick.adjustPos(1);
                rightHockeyStick.adjustPos(1);
            }
            if (controller2.dpad_up.getValue()){
                leftHockeyStick.adjustPos(-1);
                rightHockeyStick.adjustPos(-1);
            }

            if(gamepad2.left_trigger > 0.9 && gamepad2.right_trigger > 0.9){
                intake.resetExtendo();
                light.setColor(IndicatorLight.YELLOW_WEIGHT);
                lightClock = System.currentTimeMillis() + 1000;
            }


            telemetry.addLine(intake.toString());
            telemetry.addLine(claw.toString());
            telemetry.addLine(rightHockeyStick.toString());
            telemetry.addLine(leftHockeyStick.toString());
            controller1.update();
            controller2.update();
            telemetry.update();
        }
    }
}
