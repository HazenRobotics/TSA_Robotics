package org.firstinspires.ftc.teamcode.subsystems;

import android.annotation.SuppressLint;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class AutomaticPivot {
    private Arm arm;
    private Wrist wrist;
    private IMU imu;

    //Assuming position change of 0.25 = 90 degrees
    //0.74
    public static double PARALLEL_OFFSET = 0;//0.05;
    public static double PERPENDICULAR_OFFSET = -0.25;//PARALLEL_OFFSET - 0.63;

    public boolean isParallel = true;

    public AutomaticPivot(HardwareMap hw){
        //Initialize arm
        arm = new Arm(hw, "frontArm", "backArm");
        //Initialize wrist
        wrist = new Wrist(hw, "wrist");

        //Initialized IMU
        imu = hw.get(IMU.class, "imu");
        imu.initialize(new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                        RevHubOrientationOnRobot.UsbFacingDirection.UP
                )
        ));


    }

    /**
     * Sets the Servos to an initial parallel position
     * Subject to change in the future.
     */
    public void init(){
        arm.setPosition(0.86);
        wrist.setPosition(0.5);

    }

    /**
     * Sets mode to Perpendicular
     */
    public void setModePerpendicular(){
        isParallel = false;
    }

    /**
     * Sets mode to Parallel
     */
    public void setModeParallel(){
        isParallel = true;
    }

    /**
     * Toggles the mode from:
     * Parallel <--> Perpendicular
     */
    public void toggleMode(){
        isParallel = !isParallel;
    }

    /**
     * Updates the Wrist position relative to the
     * arm and robot's pitch (forward/backward tilt)
     */
    public void updatePos(){
        //Getting the initial states of the robot and arm
        double pitch = imu.getRobotYawPitchRollAngles().getPitch(AngleUnit.DEGREES)/90.0;
        double armPos = arm.getPosition();

        //Maintain parallel Claw
        if(isParallel){
            wrist.setPosition(Wrist.PARALLEL + (-pitch * 0.25) + (armPos-0.5)*0.8 + PARALLEL_OFFSET);

        }
        //Maintain perpendicular Claw
        else{
            wrist.setPosition(Wrist.PARALLEL + (-pitch * 0.25) + (armPos-0.5)*0.8 + PERPENDICULAR_OFFSET);
        }
    }

    /**
     * Wrapper function to move the arm pivot
     * @param increment Used for [Trigger] Inputs to move the arm pivot
     */
    public void adjustArm(double increment){
        arm.adjustPosition(increment*5);
    }

    /**
     * Moves the wrist through tuning offset values
     * @param increment Used for [Button] Inputs to tune the offset values
     */
    public void adjustOffset(double increment){
        if(isParallel){
            PARALLEL_OFFSET = PARALLEL_OFFSET + 0.001 *increment;
        }else{
            PERPENDICULAR_OFFSET = PERPENDICULAR_OFFSET + 0.001 *increment;
        }
    }
    public void setPos(int pos)
    {
        wrist.setPosition(pos);
    }
    @SuppressLint("DefaultLocale")
    public String toString(){
        return String.format("IMU pitch reading: %.2f\n" +
                "Arm Position: %f\n" +
                "Wrist Position: %f\n" +
                "Parallel Offset:%f\n" +
                "Perpendicular Offset:%f\n" +
                "Current Mode: %s",
                imu.getRobotYawPitchRollAngles().getPitch(AngleUnit.DEGREES),
                arm.getPosition(),
                wrist.getPosition(),
                PARALLEL_OFFSET,
                PERPENDICULAR_OFFSET,
                isParallel ? "Parallel" : "Perpendicular");
    }


}
