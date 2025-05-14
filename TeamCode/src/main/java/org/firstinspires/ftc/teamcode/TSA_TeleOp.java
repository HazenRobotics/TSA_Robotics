package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@TeleOp

public class TSA_TeleOp extends LinearOpMode{

    DcMotorEx frontLeft, backLeft, frontRight, backRight, arm;

    private double pivotPosition = 0;
    private double clawPosition = 0;
    private int armPos=0;




    // todo: write your code here
    @Override
    public void runOpMode() throws InterruptedException {

        frontLeft = hardwareMap.get(DcMotorEx.class, "FLM");
        backLeft = hardwareMap.get(DcMotorEx.class, "BLM");
        frontRight = hardwareMap.get(DcMotorEx.class,"FRM");
        backRight = hardwareMap.get(DcMotorEx.class, "BRM");

        arm = hardwareMap.get(DcMotorEx.class, "arm");
        armPos = arm.getCurrentPosition();
        arm.setTargetPosition(armPos);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setPower(1);


        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection((DcMotor.Direction.REVERSE));

        Servo leftPiv = hardwareMap.get(Servo.class, "leftWrist");
        Servo rightPiv = hardwareMap.get(Servo.class, "rightWrist");
        Servo claw = hardwareMap.get(Servo.class,"claw");

        leftPiv.setDirection(Servo.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive()){
            double drive = -gamepad1.left_stick_y;
            double strafe = gamepad1.left_stick_x;
            double rotate = gamepad1.right_stick_x;

            frontLeft.setPower(drive + strafe + rotate);
            backLeft.setPower(drive - strafe + rotate);
            frontRight.setPower(drive - strafe - rotate);
            backRight.setPower(drive + strafe - rotate);

            // if(gamepad1.x){
            //     frontLeft.setPower(1);
            // }
            // else if(gamepad1.y){
            //     frontRight.setPower(1);
            // }
            // else if (gamepad1.a){
            //     backLeft.setPower(1);
            // }
            // else if(gamepad1.b){
            //     backRight.setPower(1);
            // }
            // else{
            //     frontLeft.setPower(0);
            //     frontRight.setPower(0);
            //     backLeft.setPower(0);
            //     backRight.setPower(0);
            // }



            if(gamepad1.dpad_up){
                pivotPosition = Math.max( 0, pivotPosition - 0.001);
            }else if(gamepad1.dpad_down){
                pivotPosition = Math.min( 1, pivotPosition + 0.001);
            }

            if(gamepad1.left_bumper){
                clawPosition = Math.max( 0, clawPosition - 0.001);
            }else if(gamepad1.right_bumper){
                clawPosition = Math.min( 1, clawPosition + 0.001);
            }

            armPos += (gamepad1.left_trigger - gamepad1.right_trigger) * 10;


            leftPiv.setPosition(pivotPosition);
            rightPiv.setPosition(pivotPosition);
            claw.setPosition(clawPosition);
            arm.setTargetPosition(armPos);


            telemetry.addData("Claw Pos: ",claw.getPosition());
            telemetry.addData("Wrist Pos: ",leftPiv.getPosition());
            telemetry.addData("Arm Pos: ", arm.getCurrentPosition());

            telemetry.update();
        }
    }


}