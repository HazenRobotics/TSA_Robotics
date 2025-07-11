package org.firstinspires.ftc.teamcode.subsystems;

import android.annotation.SuppressLint;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class AutomatedIntake {


    //Arm 90 degrees = 1 - 0.4 = 0.6
    //Arm Parallel - 0.242
    double ARM_PARALLEL = 0.4;
    double DEGREES_TO_ARM_POS = 0.6/90;
    Arm arm;
    //Wrist 90 degrees - 0.65 - 0.39 = 0.26
    //Parallel Wrist - 0.388
    double DEGREES_TO_WRIST_POS = 0.26/90;
    double WRIST_PARALLEL = 0.388;
    Wrist wrist;
    //Ticks Per Inch
    //985 ticks per feet

    double INCH_TO_TICKS = 985.0/12;
    HorizontalExtendo extendo;


    //Target Data:
    boolean relativeTargetting = false;
    double extendoHeight = 12; //inches
    double armLength = 11.5; //inches

    double clawLength = 4;
    double horizontal, vertical, angle;


    public AutomatedIntake(HardwareMap hw){
        //Initialize Extendo
        arm = new Arm(hw, "frontArm", "backArm");
        //Initialize wrist
        wrist = new Wrist(hw, "wrist");
        //Initialize Horizontal Extendo
        extendo = new HorizontalExtendo(hw, "leftExtendo", "rightExtendo");
        //Initialized IMU
    }

    public void init(){
        wrist.setPosition(0.65);
        arm.setPosition(1);
        angle = Math.toRadians(180);
        horizontal = 20;
        vertical = extendoHeight;
    }

    public void horizontalChange(double change){
        //Math for Horizontal Change
        changeCheck(change * 0.25,0,0);
    }
    public void verticalChange(double change){
        //Math for vertical change
        changeCheck(0, change * 0.25, 0);
    }

    public void angleChange(double change){
        changeCheck(0,0,change * 0.025);
    }

    public void setAngle(double a){
        angle = a;
    }

    public void update(){
        if (!relativeTargetting){
            return;
        }
        double extendoPos = (horizontal + clawLength * Math.cos(angle))
                - Math.sqrt(
                    Math.abs(Math.pow(armLength,2)
                        - Math.pow(
                                extendoHeight -
                                vertical +
                                clawLength * Math.sin(angle)
                                ,2
                        ))
                );
        double armAngle = Math.atan2(
                vertical + clawLength * Math.sin(-angle) - extendoHeight,
                Math.sqrt(Math.pow(armLength,2) -
                        Math.abs(Math.pow(
                                extendoHeight -
                                vertical -
                                clawLength * Math.sin(-angle)
                                ,2 )
                                ))
        );
        double wristPos = (180-Math.toDegrees(angle + armAngle)) * DEGREES_TO_WRIST_POS;

        double armPos = Math.toDegrees(armAngle) * DEGREES_TO_ARM_POS;

        extendo.setPos((int) Math.max(extendoPos * INCH_TO_TICKS, 0));
        wrist.setPosition(WRIST_PARALLEL + wristPos);
        arm.setPosition(ARM_PARALLEL - armPos);

    }

    public void changeCheck(double hori, double vert, double angl){
        //
        double val = Math.pow(armLength,2) - Math.pow(extendoHeight - (vertical+vert) +
                                                        clawLength * Math.sin(angle+angl) ,2);
        if (val >= 0){
            //Only if Val is non-negative can the change be valid.
            vertical += vert;
            angle += angl;
        }
        horizontal = Math.min(22,Math.max(horizontal + hori,0));
    }

    public void toggleTargetting(){
        relativeTargetting = !relativeTargetting;
        //If manual mode -> relative coordinate system
        //False -> true;
        if (relativeTargetting){
            angle = Math.toRadians(180 - (wrist.getPosition()-WRIST_PARALLEL) /  DEGREES_TO_WRIST_POS) -
                    Math.toRadians((ARM_PARALLEL - arm.getPosition()) / DEGREES_TO_ARM_POS);

            horizontal = extendo.getPos() / INCH_TO_TICKS +
                    armLength * Math.cos(Math.toRadians((ARM_PARALLEL - arm.getPosition()) / DEGREES_TO_ARM_POS)) -
                    clawLength * Math.cos(angle);

            vertical = extendoHeight +
                    armLength * Math.sin(Math.toRadians((ARM_PARALLEL - arm.getPosition()) / DEGREES_TO_ARM_POS)) +
                    clawLength * Math.sin(angle);


        }
    }

//    @SuppressLint("DefaultLocale")
//    public String test(){
//        return String.format("Extendo Dist: %.2f\n" +
//                "Arm Horizontal Dist: %.2f\n" +
//                "Claw Horizontal Dist: %.2f\n" +
//                        "Arm Vertical Dist: %.2f\n" +
//                        "Claw Vertical Dist: %.2f\n"
//                ,
//                extendo.getPos() / INCH_TO_TICKS,
//                armLength * Math.cos(Math.toRadians((ARM_PARALLEL - arm.getPosition()) / DEGREES_TO_ARM_POS)),
//                -clawLength * Math.cos(tempA),
//                armLength * Math.sin(Math.toRadians((ARM_PARALLEL - arm.getPosition()) / DEGREES_TO_ARM_POS)),
//                -clawLength * Math.sin(tempA));
//    }

    public void adjustExtedo(double change){
        extendo.movePos(change);
    }

    public void adjustArm(double change){
        arm.adjustPosition(change);
    }
    public void adjustWrist(double change){
        wrist.adjustPosition(change);
    }

    public void resetExtendo(){
        extendo.resetPos();
    }

    public boolean getRelativeTargetting(){
        return relativeTargetting;
    }

    @SuppressLint("DefaultLocale")
    public String toString(){
        return String.format(
                        "Arm Position: %f\n" +
                        "Wrist Position: %f\n" +
                        "Extendo Position:%d\n" +
                        "Relative Targeting On: %b\n" +
                        "Horizontal: %.2f\n" +
                        "Vertical: %.2f\n" +
                        "Angle (Deg): %.2f\n",
                arm.getPosition(),
                wrist.getPosition(),
                extendo.getPos(),
                relativeTargetting,
                horizontal,
                vertical,
                Math.toDegrees(angle));
    }


}
