package org.firstinspires.ftc.teamcode.subsystems;

import android.annotation.SuppressLint;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Arm {
    private Servo backArm;
    private Servo frontArm;
    private double position;
    private double SPEED_MULI = 0.005;
    private double MAX = 1;
    private double MIN = 0;

    public static double PARALLEL = 0.5;
    public Arm(HardwareMap hardwareMap, String frontArmName, String backArmName)
    {
        backArm = hardwareMap.get(Servo.class, backArmName);
        frontArm = hardwareMap.get(Servo.class, frontArmName);
//        frontArm.setDirection(Servo.Direction.REVERSE);

    }
    public void setPosition(double pos){
//        double liftPosition = 0.5;
        position = pos;
        backArm.setPosition( position);
        frontArm.setPosition(position);
    }
    public double getFrontArm(){
        return frontArm.getPosition();
    }
    public double getBackArm(){
        return backArm.getPosition();
    }


    public void adjustPosition(double increment){
        position += increment * SPEED_MULI;
        position = Math.max(Math.min(position, MAX), MIN);
        backArm.setPosition(position);
        frontArm.setPosition(position);
    }

    public void setParallel(){
        backArm.setPosition(0.5);
        frontArm.setPosition(0.5);
        position = 0.5;
    }

    public double getPosition(){
        return position;
    }

    @SuppressLint("DefaultLocale")
    public String toString(){
        return String.format("Arm Position: %.2f",position);
    }
}
