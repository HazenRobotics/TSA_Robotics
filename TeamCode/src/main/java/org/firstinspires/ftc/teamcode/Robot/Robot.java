package org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.subsystems.AutomaticPivot;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.DriveTrain;
import org.firstinspires.ftc.teamcode.subsystems.HockeyStick;
import org.firstinspires.ftc.teamcode.subsystems.HorizontalExtendo;

public class Robot {
    
    DriveTrain driveTrain;
    Claw claw;
    HockeyStick hockeyStick;
    HorizontalExtendo horizontalExtendo;
    AutomaticPivot automaticPivot;

    enum ScoringType{
        Parallel,
        Perpendicular
    }
    ScoringType currentState;
    public Robot(HardwareMap hw)
    {
        driveTrain = new DriveTrain(hw,"frontLeft", "backLeft", "frontRight",
                "backRight");
        claw = new Claw(hw,"Claw");
        hockeyStick =  new HockeyStick(hw, "hockeyStick");
        horizontalExtendo = new HorizontalExtendo(hw, "leftExtendo", "rightExtendo");
        automaticPivot =  new AutomaticPivot(hw);
        currentState = ScoringType.Parallel;
    }

    public void drive(double forward, double strafe, double rotate)
    {
        driveTrain.drive(forward, strafe, rotate);
    }
    public void initializeStates() throws InterruptedException {
        claw.close();
        automaticPivot.init();
//        Thread.sleep(1000);
//        claw.open();
    }

    public void resetStates()
    {
        automaticPivot.setModeParallel();
        horizontalExtendo.resetPos();

    }

    public void intakeToggle()
    {
        automaticPivot.toggleMode();
    }

    public void toggleClaw()
    {
        claw.toggle();
    }

    public void openClaw(){
        claw.open();
    }

    public void closeClaw(){
        claw.close();
    }

    public void ringClaw()
    {
        claw.ringClose();
    }

    public void updatePos()
    {
        automaticPivot.updatePos();
    }

    public void extend(double val)
    {
        horizontalExtendo.movePos(val );
    }

    public void toggleHockeyStick()
    {
        hockeyStick.toggle();
    }
    public void setHockeyStickReset(){
        hockeyStick.reset();
    }
    public void setHockeyStickUp(){
        hockeyStick.setUP();
    }
    public void setHockeyStickDown(){
        hockeyStick.setDown();
    }

    public void adjustPivotOffset(int pivotAdjustment)
    {
       automaticPivot.adjustOffset(pivotAdjustment);
    }

    public void adjustHockeyOffset(int hockeyOffset)
    {
        hockeyStick.adjustPos(hockeyOffset);
    }

    public void adjustClawOffset(double clawOffset)
    {
        claw.adjustPosition(clawOffset);
    }

    public void adjustArmOffset(double armOffset)
    {
        automaticPivot.adjustArm(armOffset);
    }

    public String toString()
    {
        return "Claw Pos: " + claw.getPos()
                + "\n" + automaticPivot.toString()
                + "\nHorizontal Extendo Data: " + horizontalExtendo.toString()
                + "\nHockey Stick Pos: " + hockeyStick.getPosition();
    }


}
