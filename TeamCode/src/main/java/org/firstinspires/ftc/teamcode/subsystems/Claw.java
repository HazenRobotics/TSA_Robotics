package org.firstinspires.ftc.teamcode.subsystems;

import android.annotation.SuppressLint;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw {
    private Servo claw;
    private double open = 0.1;
    private double close = 0.35;
    private double ring = 0.418;
    private double position;
    /**
        @param: (String) name of the claw
     */
    public Claw(HardwareMap hardwareMap,String clawName)
    {
        claw = hardwareMap.get(Servo.class, clawName);
    }

    public void open()
    {
        position=open;
        claw.setPosition(position);
    }

    public void close()
    {
        position=close;
        claw.setPosition(position);
    }

    public void setPosition(double pos)
    {
        claw.setPosition(pos);
    }

    public void toggle()
    {
         position = (position == open) ? close: open;
         claw.setPosition(position);
    }

    public void toggle()
    {
         position = (position == open) ? close: open;
    }

    public void toggle()
    {
         position = (position == open) ? close: open;
    }

    public double getPos()
    {
        return claw.getPosition();
    }

    public void ringClose()
    {
        position=ring;
        claw.setPosition(ring);
    }
    public void adjustPosition(double increment)
    {
        position += increment*0.005;
        position = Math.max(Math.min(position, close+0.05), open-0.05);
        claw.setPosition(position);
    }

    @SuppressLint("DefaultLocale")
    public String toString(){
        return String.format("Position: %.2f",
                position);
    }
}
