package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class HockeyStick
{
    //FIGURE OUT LIMIT
    private int TOP = 900;
    private int BOTTOM = 1050;
    private int RESET = 0;
    private int multipler = 25;

    private double parallel;
    private double hover;
    private DcMotorEx hockeyStick;
    public HockeyStick(HardwareMap hardwareMap, String motorName)
    {
        hockeyStick = hardwareMap.get(DcMotorEx.class, motorName);
        hockeyStick.setDirection(DcMotorSimple.Direction.REVERSE);

        hockeyStick.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hockeyStick.setTargetPosition(hockeyStick.getCurrentPosition());
        hockeyStick.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hockeyStick.setPower(0.7);


    }


//    public void resetEncoders(){
//        hockeyStick.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        hockeyStick.setTargetPosition(hockeyStick.getCurrentPosition());
//        hockeyStick.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//    }


    public int getPosition()
    {
        return hockeyStick.getCurrentPosition();
    }

    public void adjustPos(int val)
    {
        val *= multipler;
        TOP += val;
        BOTTOM += val;
        setPosition(getPosition() + val);
    }
    public void setDown()
    {
        setPosition(BOTTOM);
    }

    public void setUP()
    {
        setPosition(TOP);
    }

    public void reset()
    {
        setPosition(RESET);
    }

    public void setPosition(int pos)
    {
        hockeyStick.setTargetPosition(pos);
        hockeyStick.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hockeyStick.setPower(0.7);
    }

    //Change this method to be in a certain range
    public void toggle()
    {
        int TOGGLE = TOP;
        if(Math.abs(getPosition() - TOP) <= 50 )
        {
            TOGGLE = BOTTOM;
        }
//        hockeyStick.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setPosition(TOGGLE);
    }

    public String toString()
    {
        return "STICK POS: " + getPosition() + "\nTOP VALUE: " + TOP
                + "\nBOTTOM VALUE: " + BOTTOM;
    }
}
