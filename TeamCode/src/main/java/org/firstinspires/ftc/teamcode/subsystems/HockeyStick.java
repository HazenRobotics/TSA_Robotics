package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class HockeyStick
{
    //FIGURE OUT LIMIT
    private int TOP = 290;
    private int BOTTOM = 390;
    private int RESET = 0;
    private int multipler = 5;

    public int targetPosition = 0;
    private double integralSum = 0;
    private double lastError = 0;
    private ElapsedTime timer;

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
        timer = new ElapsedTime();
        hockeyStick.setPower(0.5);

        currentState = State.RESET;
    }
    public void moveToPosition(int targetPosition, double tolerance) {
        final double kP = 0.01;
        final double kD = 0.001;
        final double kI = 0.0001;

        integralSum = 0;
        lastError = 0;
        timer.reset();

        hockeyStick.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        while (Math.abs(hockeyStick.getCurrentPosition() - targetPosition) > tolerance) {
            int currentPosition = hockeyStick.getCurrentPosition();
            int error = targetPosition - currentPosition;

            // Time difference
            double currentTime = timer.seconds();
            double deltaTime = currentTime;
            timer.reset();

            // Integral and derivative
            integralSum += error * deltaTime;
            double derivative = (error - lastError) / deltaTime;

            // PID output
            double power = (kP * error) + (kI * integralSum) + (kD * derivative);

            // Clamp power between -1 and 1
            power = Math.max(-1.0, Math.min(1.0, power));
            hockeyStick.setPower(power);
            lastError = error;
        }

        hockeyStick.setPower(0);
    }

    public void reverseDirection(){
        hockeyStick.setDirection(DcMotorSimple.Direction.FORWARD);
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
        moveToPosition(RESET, 5);
    }

    public void setPosition(int pos)
    {
        targetPosition = pos;
        hockeyStick.setTargetPosition(targetPosition);
        hockeyStick.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hockeyStick.setPower(1);
    }

    //Change this method to be in a certain range
    public void toggle()
    {
        int TOGGLE = BOTTOM;
        if(Math.abs(getPosition() - BOTTOM) <= 50 )
        {
            TOGGLE = TOP;
        }
//        hockeyStick.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        moveToPosition(TOGGLE, 5);
    }

    public String toString()
    {
        return "STICK POS: " + getPosition()
                + "\n TARGET POS: " + targetPosition
                + "\nTOP VALUE: " + TOP
                + "\nBOTTOM VALUE: " + BOTTOM;
    }
}
