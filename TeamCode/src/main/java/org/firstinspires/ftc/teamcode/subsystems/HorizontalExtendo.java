package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class HorizontalExtendo {
    DcMotorEx leftMotor, rightMotor;
    private final double multipleConstant = 1000;
    private final double motorSpeed = 0.7;


    public HorizontalExtendo(HardwareMap hardwareMap, String leftMotorName, String rightMotorName)
    {
        leftMotor = hardwareMap.get(DcMotorEx.class, leftMotorName);
        rightMotor = hardwareMap.get(DcMotorEx.class, rightMotorName);

        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        leftMotor.setTargetPosition(getPos());
        rightMotor.setTargetPosition(getPos());

        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftMotor.setPower(motorSpeed);
        rightMotor.setPower(motorSpeed);
    }


    public void movePos(double val)
    {
        if(val > 0.3)
        {
            val *= multipleConstant;
            int pos = leftMotor.getCurrentPosition() + (int) val;

            leftMotor.setTargetPosition(pos);
            rightMotor.setTargetPosition(pos);
        }

    }

    public int getPos()
    {
        return leftMotor.getCurrentPosition();
    }

    public void resetPos()
    {
        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


    }
}
