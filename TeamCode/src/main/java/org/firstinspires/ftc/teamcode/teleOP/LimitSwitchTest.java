package org.firstinspires.ftc.teamcode.teleOP;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DigitalChannel;

@TeleOp(name = "Limit Switch Test")
public class LimitSwitchTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        DigitalChannel sensor = hardwareMap.digitalChannel.get("touchSensor");

        waitForStart();

        while(opModeIsActive()){
            telemetry.addData("Pressed: ", sensor.getState());
            telemetry.update();
        }
    }
}
