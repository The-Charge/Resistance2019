// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc2619.Resistance2019.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;

import org.usfirst.frc2619.Resistance2019.Robot;

/**
 *
 */
public class GoToVisionTarget extends Command {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
 
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public GoToVisionTarget() {

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    double[] visionData;
    double success, time, distance, angle1, angle2, timeToRun;
    double previousTime;
    NetworkTableEntry visionArray;
    double turnAngle, firstDist, secondDist;
    int stage;

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        NetworkTable table = inst.getTable("Root/Vision");

        success = 0; time = 0; distance = 0; angle1 = 0; angle2 = 0; timeToRun = 0;
        previousTime = 0;
        stage = 0;

        visionArray = table.getEntry("Result");
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        //Collecting data from NetworkTables:
        visionData = visionArray.getDoubleArray(new double[] {});

        // success = visionData[0]; //whether the vision successfully got vision data
        // time = visionData[1]; //a current time stamp
        // distance = visionData[2]; //distance from robot to vision target
        // angle1 = visionData[3]; //the angle of the vision target from due "north" of the robot
        // angle2 = visionData[4]; // the angle of the robot from due "north" of the vision target
        // timeToRun = visionData[5]; //how long the vision recognition took to run

        //TESTING DATA
        // success = 1;
        // time = 0;
        // distance = SmartDashboard.getNumber("VisionDistance", 0);
        // angle1 = SmartDashboard.getNumber("VisionAngle1", 0);
        // angle2 = SmartDashboard.getNumber("VisionAngle2", 0);
        // timeToRun = 0;

        //Method #1: Drive, Turn, Drive
        double turnFirst = 0;
        double angle3;

        if(getAngleSign(angle1) == getAngleSign(angle2));
            angle1 = getAngleSign(angle1) * (90 - Math.abs(angle1));

        angle3 = getAngleSign(angle1) * (180 - Math.abs(angle1) - Math.abs(angle2));
            
        firstDist = distance * (Math.sin(angle2) / Math.sin(angle3));


        if(firstDist > 0) {
            turnFirst = 0;
            turnAngle = getAngleSign(angle1) * (90 - Math.abs(angle3));
            secondDist = distance * (Math.sin(angle1) / Math.sin(angle3));
        }
        else if(firstDist == 0) {
            turnFirst = angle1;
            firstDist = distance;
            secondDist = 0;
            turnAngle = 0;
        }
        else {
            turnFirst = getAngleSign(angle1) * 90;
            turnAngle = -getAngleSign(angle1) * (90 - Math.abs(angle1) + Math.abs(angle2));
            firstDist = (-getAngleSign(angle2) * distance * Math.sin(Math.abs(angle2))) / (getAngleSign(turnAngle)) * (Math.sin(180 - Math.abs(turnAngle)));
            secondDist = (distance * Math.sin(90 - Math.abs(angle1))) / (getAngleSign(turnAngle) * Math.sin(180 - Math.abs(turnAngle)));
        }

        if(success == 0 || time <= previousTime) {
            //GIVE WARNING: RUNNING ON OLD DATA
        }
        else {
            previousTime = time;
        }

        //TEST DATA
        firstDist = SmartDashboard.getNumber("VisionDistance1", 0);
        secondDist = SmartDashboard.getNumber("VisionDistance2", 0);
        turnFirst = SmartDashboard.getNumber("VisionAngle1", 0);
        turnAngle = SmartDashboard.getNumber("VisionAngle2", 0);

        //If vision is lost, use old data until vision is regained
        new FollowPath(firstDist, secondDist, turnFirst, turnAngle);
        



        //Checks for when we got there
        if(distance == 0)
            isFinished();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }

    //Returns -1 or 1 depending on the sign of the angle
    private double getAngleSign(double a) {
        return Math.abs(a) / a;
    }
}
