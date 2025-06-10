package org.firstinspires.ftc.teamcode.subsystems;

import android.annotation.SuppressLint;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Wrist {

    Servo wrist;
    private double position;
    private double SPEED_MULI = 0.005;
    private double MAX = 1;
    private double MIN = 0;

    public static double PARALLEL = 0.2;

    public Wrist(HardwareMap hw, String wristName){
        wrist = hw.get(Servo.class,wristName);
    }

    public void adjustPosition(double increment){
        position += SPEED_MULI * increment;
        position = Math.max(Math.min(position, MAX), MIN);
        wrist.setPosition(position);
    }

    public double getPosition() {
        return position;
    }

    //Not the actual parallel position by the way!
    public void setParallel(){
        position = 0.2;
        wrist.setPosition(position);
    }
    public void setPerpendicular(){
        position = 0.5;
        wrist.setPosition(position);
    }

    public void setPosition(double pos){
        position = Math.max(Math.min(pos, MAX), MIN);
        wrist.setPosition(position);
    }
    public void initPos(){
        position = 0.7;
        wrist.setPosition(0.7);
    }
    public void initPos(){
        wrist.setPosition(0.5);
    }
    public void initPos(){
        wrist.setPosition(0.5);
    }

    @SuppressLint("DefaultLocale")
    public String toString(){
        return String.format("Wrist Position: %.2f",position);
    }
}
