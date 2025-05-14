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
    private int multipler = 5;

    public int targetPosition = 0;

    enum State{
        TOP,
        BOTTOM,
        RESET
    }
    State currentState = State.RESET;

    private DcMotorEx hockeyStick;
    public HockeyStick(HardwareMap hardwareMap, String motorName)
    {
        hockeyStick = hardwareMap.get(DcMotorEx.class, motorName);
        hockeyStick.setDirection(DcMotorSimple.Direction.REVERSE);

        hockeyStick.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hockeyStick.setTargetPosition(hockeyStick.getCurrentPosition());
        hockeyStick.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hockeyStick.setPower(1);

        currentState = State.RESET;
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
        targetPosition += val * multipler;
        if (currentState == State.TOP){
            TOP = targetPosition;
        }
        else if( currentState == State.BOTTOM){
            BOTTOM = targetPosition;
        }


        setPosition(targetPosition);
    }
    public void setDown()
    {
        currentState = State.BOTTOM;
        setPosition(BOTTOM);
    }

    public void setUP()
    {
        currentState = State.TOP;
        setPosition(TOP);
    }

    public void reset()
    {
        currentState = State.RESET;
        setPosition(RESET);
    }

    public void setPosition(int pos)
    {
        targetPosition = pos;
        hockeyStick.setTargetPosition(targetPosition);
//        hockeyStick.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        hockeyStick.setPower(0.7);
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
        return "STICK POS: " + getPosition()
                + "\n TARGET POS: " + targetPosition
                + "\nTOP VALUE: " + TOP
                + "\nBOTTOM VALUE: " + BOTTOM;
    }
}
