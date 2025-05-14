package org.firstinspires.ftc.teamcode.subsystems;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class HorizontalExtendo {
    DcMotorEx leftMotor, rightMotor;
    private final double multipleConstant = 100;
    private final double motorSpeed = 1;

    private int targetPosition;

    public HorizontalExtendo(HardwareMap hardwareMap, String leftMotorName, String rightMotorName)
    {
        leftMotor = hardwareMap.get(DcMotorEx.class, leftMotorName);
        rightMotor = hardwareMap.get(DcMotorEx.class, rightMotorName);


        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
//        resetPos();

        leftMotor.setTargetPosition(getPos());
        rightMotor.setTargetPosition(getPos());

        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftMotor.setPower(motorSpeed);
        rightMotor.setPower(motorSpeed);
    }


    public void movePos(double val)
    {
//        if(Math.abs(val) > 0.1)
//        {
            val *= multipleConstant;
            targetPosition = leftMotor.getCurrentPosition() - (int) val;

            leftMotor.setTargetPosition(targetPosition);
            rightMotor.setTargetPosition(targetPosition);
//        }
    }

    public void setPos(int ticks){
        targetPosition = ticks;
        leftMotor.setTargetPosition(targetPosition);
        rightMotor.setTargetPosition(targetPosition);
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

    @NonNull
    @SuppressLint("DefaultLocale")
    public String toString(){
        return String.format("Extendo Target Position: %d\n" +
                "Extendo Current Position: %d\n",
                targetPosition,
                getPos() );
    }
}
