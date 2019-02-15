// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc2619.Resistance2019;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc2619.Resistance2019.commands.*;
import org.usfirst.frc2619.Resistance2019.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in 
 * the project.
 */
public class Robot extends TimedRobot {

    Command autonomousCommand;
    SendableChooser<Command> chooser = new SendableChooser<>();

    public static OI oi;
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static DriveTrain driveTrain;
    public static Intake intake;
    public static Extension extension;
    public static Elevator elevator;
    public static Hatchers hatchers;
    public static Shifters shifters;
    public static SensorBar sensorBar;
    public static IndicatorLights indicatorLights;
    public static Shooter shooter;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public static boolean DEBUG = true;
    // BEGIN Debug values
    public static double elevatorBottom = 0.1;
    public static double elevatorLow = 0.2;
    public static double elevatorHigh = 0.4;
    public static boolean rewriteInitialValues = false;
    // END Debug values

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {

        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        driveTrain = new DriveTrain();
        intake = new Intake();
        extension = new Extension();
        elevator = new Elevator();
        hatchers = new Hatchers();
        shifters = new Shifters();
        sensorBar = new SensorBar();
        indicatorLights = new IndicatorLights();
        shooter = new Shooter();

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        // OI must be constructed after subsystems. If the OI creates Commands
        //(which it very likely will), subsystems are not guaranteed to be
        // constructed yet. Thus, their requires() statements may grab null
        // pointers. Bad news. Don't move it.
        oi = new OI();

        // Add commands to Autonomous Sendable Chooser
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS

        chooser.addObject("TankDrive", new TankDrive());
        chooser.addObject("LevelOneLeftCenter", new LevelOneLeftCenter());
        chooser.addObject("LevelOneRightCenter", new LevelOneRightCenter());
        chooser.addObject("LevelOneLeftSideClose", new LevelOneLeftSideClose());
        chooser.addObject("LevelOneLeftSideMiddle", new LevelOneLeftSideMiddle());
        chooser.addObject("LevelOneRightSideClose", new LevelOneRightSideClose());
        chooser.addObject("LevelOneRightSideMiddle", new LevelOneRightSideMiddle());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS
        SmartDashboard.putData("Auto mode", chooser);
        writeInitialDashboardValues();
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    @Override
    public void disabledInit(){

    }

    @Override
    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void autonomousInit() {
        autonomousCommand = chooser.getSelected();
        // schedule the autonomous command (example)
        if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        dashboardValues();
        if (DEBUG){
            dashboardDebugValues();
        }
        if (rewriteInitialValues){
            writeInitialDashboardValues();
            rewriteInitialValues = false;
        }
    }

    /**
     * This function is called when the robot is running
     * Add useful Dashboard values that are always nessesary
     */
    public void dashboardValues() {
        SmartDashboard.putNumber("Current", driveTrain.getCurrentAmps());
        SmartDashboard.putBoolean("Line Detected", sensorBar.isOneSensed());
        // Put dashboard values for debugging here
	}

    /**
     * This function is called when Debuging or Tuning the Robot
     * Add useful Dashboard values that are only nessesary during debuging or testing
     * This code could be improved with a dictionary
     */
    public void dashboardDebugValues() {
        double tempDouble = elevatorBottom;
        tempDouble = SmartDashboard.getNumber("BallCollect Height", tempDouble);
        if (tempDouble != elevatorBottom){
            elevatorBottom = tempDouble;
            rewriteInitialValues = true;
        }

        tempDouble = elevatorLow;
        tempDouble = SmartDashboard.getNumber("Low Height", tempDouble);
        if (tempDouble != elevatorLow){
            elevatorLow = tempDouble;
            rewriteInitialValues = true;
        }

        tempDouble = elevatorHigh;
        tempDouble = SmartDashboard.getNumber("High Height", tempDouble);
        if (tempDouble != elevatorHigh){
            elevatorHigh = tempDouble;
            rewriteInitialValues = true;
        }
    }
    
    public void writeInitialDashboardValues(){
        if (DEBUG){
        SmartDashboard.putNumber("BallCollect Height", elevatorBottom);
        SmartDashboard.putNumber("Low Height", elevatorLow);
        SmartDashboard.putNumber("High Height", elevatorHigh);
        SmartDashboard.putData("DriveXFeetMotionMagic: Drive2Feet", new DriveXFeetMotionMagic(2, 0, 0));
        SmartDashboard.putData("DriveXFeetMotionMagic: Drive1foot", new DriveXFeetMotionMagic(1, 0, 0));
        SmartDashboard.putData("Extend", new Extend());
        SmartDashboard.putData("Retract", new Retract());
        SmartDashboard.putData("ElevateToXPositionMotionMagic: ballCollect", new ElevateToXPositionMotionMagic(elevatorBottom));
        SmartDashboard.putData("ElevateToXPositionMotionMagic: low", new ElevateToXPositionMotionMagic(elevatorLow));
        SmartDashboard.putData("ElevateToXPositionMotionMagic: high", new ElevateToXPositionMotionMagic(elevatorHigh));
        SmartDashboard.putData("StopElevator", new StopElevator());
        SmartDashboard.putData("PutHatch", new PutHatch());
        SmartDashboard.putData("InvertDrive", new InvertDrive());
        SmartDashboard.putData("ShiftLow", new ShiftLow());
        SmartDashboard.putData("ShiftHigh", new ShiftHigh());
        SmartDashboard.putData("DriveToCurrent: default", new DriveToCurrent(0.1, 10));
        SmartDashboard.putData("ToggleLockStraight", new ToggleLockStraight());
        SmartDashboard.putData("TurnNDegreesAbsolutePID: Turn180DegreesAbsolutePID", new TurnNDegreesAbsolutePID(180));
        SmartDashboard.putData("TurnNDegreesAbsolutePID: Turn90DegreesAbsolutePID", new TurnNDegreesAbsolutePID(90));
        SmartDashboard.putData("TurnNDegreesAbsolutePID: Turn0DegreesAbsolutePID", new TurnNDegreesAbsolutePID(0));
        SmartDashboard.putData("PIDLineFollow", new PIDLineFollow());
        SmartDashboard.putData("DriveToLine: default", new DriveToLine(0.2));
        SmartDashboard.putData("LightLineFollow", new LightLineFollow());
        SmartDashboard.putData("LightOn", new LightOn());
        SmartDashboard.putData("Run Shooter", new RunShooter(0.1));
        }
    }
}
