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
import edu.wpi.first.cameraserver.*;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SerialPort;

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
    CameraServer server;
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
    public static BallSensor ballSensor;
    public static Climber climber;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public static boolean DEBUG = true;
    // BEGIN Debug values
    public static boolean rewriteInitialValues = false;
    // END Debug values
    public static SerialPort visionPort;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {

        server = CameraServer.getInstance();
        server.startAutomaticCapture("driverCam", 0);

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
        ballSensor = new BallSensor();
        climber = new Climber();

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

        //visionPort = VisionUtil.createSerialPort();
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
        indicatorLights.lightOn();
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
        SmartDashboard.putBoolean("Ball Detected", ballSensor.isBallSensed());
        SmartDashboard.putBoolean("Any Sensor", sensorBar.isOneSensed());
        SmartDashboard.putNumber("Yaw", driveTrain.getYaw());

        SmartDashboard.putNumber("Elevator Encoder", elevator.getTicks());
        SmartDashboard.putNumber("Elevator Target", elevator.getTarget());
        SmartDashboard.putBoolean("Elevator Alert", (!elevator.safeToElevatePositionUp()) || (!elevator.safeToElevatePositionDown()));

        SmartDashboard.putNumber("Match Time", DriverStation.getInstance().getMatchTime());
        SmartDashboard.putBoolean("Extension Out", extension.isExtended());
        SmartDashboard.putBoolean("Hatch Up", hatchers.isUp());
        SmartDashboard.putBoolean("Hatch Grab", hatchers.isGrabberOut());

        
        SmartDashboard.putBoolean("FL Sensor", sensorBar.isSensorTriggered(0));
        SmartDashboard.putBoolean("L Sensor", sensorBar.isSensorTriggered(1));
        SmartDashboard.putBoolean("M Sensor", sensorBar.isSensorTriggered(2));
        SmartDashboard.putBoolean("R Sensor", sensorBar.isSensorTriggered(3));
        SmartDashboard.putBoolean("FR Sensor", sensorBar.isSensorTriggered(4));
        
        SmartDashboard.putBoolean("RAW FL Sensor", sensorBar.isRawSensorTriggered(0));
        SmartDashboard.putBoolean("RAW L Sensor", sensorBar.isRawSensorTriggered(1));
        SmartDashboard.putBoolean("RAW M Sensor", sensorBar.isRawSensorTriggered(2));
        SmartDashboard.putBoolean("RAW R Sensor", sensorBar.isRawSensorTriggered(3));
        SmartDashboard.putBoolean("RAW FR Sensor", sensorBar.isRawSensorTriggered(4));

        //SmartDashboard.putNumber("Current", driveTrain.getCurrentAmps());
        
        //SmartDashboard.putBoolean("Colector Running Out", intake.isRunningOut());
        //SmartDashboard.putBoolean("Collector Running In", intake.isRunningIn());
        //SmartDashboard.putBoolean("Shooter Running Out", shooter.isRunningOut());
        //SmartDashboard.putBoolean("Shooter Running In", shooter.isRunningIn());
	}

    /**
     * This function is called when Debuging or Tuning the Robot
     * Add useful Dashboard values that are only nessesary during debuging or testing
     * This code could be improved with a dictionary
     */
    public void dashboardDebugValues() {
        //SmartDashboard.putNumber("Drive Ticks", driveTrain.getEncoderTicks());
        //SmartDashboard.putNumber("Drive Target", driveTrain.MotionMagicDistanceTicks);
        //SmartDashboard.putBoolean("Safe to retract", elevator.safeToRetract());
        //SmartDashboard.putBoolean("Toplimit", hatchers.checkTopLimitSwitch());
        //SmartDashboard.putBoolean("Botlimit", hatchers.checkBottomLimitSwitch());
        /*
        double tempDouble = driveTrain.MotionMagicP;
        tempDouble = SmartDashboard.getNumber("Drive P", tempDouble);
        if (tempDouble != driveTrain.MotionMagicP){
            driveTrain.MotionMagicP = tempDouble;
            rewriteInitialValues = true;
        }
        */
    }
    
    public void writeInitialDashboardValues(){
        if (DEBUG){

        //DriveTrain commands
        SmartDashboard.putData("InvertDrive", new InvertDrive());
        SmartDashboard.putData("ShiftLow", new ShiftLow());
        SmartDashboard.putData("ShiftHigh", new ShiftHigh());
        SmartDashboard.putData("LockStraight", new LockStraight());
        SmartDashboard.putData("TankDrive", new TankDrive());
        //SmartDashboard.putData("DriveXFeetMotionMagic: Drive3Feet", new DriveXFeetMotionMagic(3, 0, 0));
        //SmartDashboard.putData("DriveXFeetMotionMagic: Drive2Feet", new DriveXFeetMotionMagic(2, 0, 0));
        //SmartDashboard.putData("DriveXFeetMotionMagic: Drive1foot", new DriveXFeetMotionMagic(1, 0, 0));
        //SmartDashboard.putData("TurnNDegreesAbsolutePID: Turn180DegreesAbsolutePID", new TurnNDegreesAbsolutePID(180));
        //SmartDashboard.putData("TurnNDegreesAbsolutePID: Turn90DegreesAbsolutePID", new TurnNDegreesAbsolutePID(90));
        //SmartDashboard.putData("TurnNDegreesAbsolutePID: Turn0DegreesAbsolutePID", new TurnNDegreesAbsolutePID(0));
        //SmartDashboard.putData("DriveToLine: default", new DriveToLine(0.2));
        SmartDashboard.putData("PIDLineFollow", new PIDLineFollow());
        //SmartDashboard.putData("DriveToCurrent: default", new DriveToCurrent(0.1, 10));
    
        //Extension commands
        SmartDashboard.putData("Extend", new Extend());
        SmartDashboard.putData("Retract", new Retract());

        //Elevator commands
        SmartDashboard.putData("ElevateToXPositionMotionMagic: BallCollect", new ElevateToXPositionMotionMagic(0));
        SmartDashboard.putData("ElevateToXPositionMotionMagic: Low-Rocket", new ElevateToXPositionMotionMagic(0.31654));
        SmartDashboard.putData("ElevateToXPositionMotionMagic: Cargo", new ElevateToXPositionMotionMagic(0.550359));
        SmartDashboard.putData("ElevateToXPositionMotionMagic: Mid-Rocket", new ElevateToXPositionMotionMagic(0.8381294));
        SmartDashboard.putData("OverrideElevator Up", new OverrideElevator(0.2));
        SmartDashboard.putData("OverrideElevator Down", new OverrideElevator(-0.2));
        SmartDashboard.putData("BrakeOn", new BrakeOn());
        SmartDashboard.putData("BrakeOff", new BrakeOff());
        //SmartDashboard.putData("StopElevator", new StopElevator());

        //Hatcher commands
        SmartDashboard.putData("HatchGrab", new HatchGrab());
        SmartDashboard.putData("HatchRelease", new HatchRelease());
        SmartDashboard.putData("RunHatchMotor", new RunHatchMotor());
        //SmartDashboard.putData("HatchUp", new HatchUp());
        //SmartDashboard.putData("HatchDown", new HatchDown());

        //Shooter commands
        SmartDashboard.putData("RunShooter Out", new RunShooter(0.5));
        SmartDashboard.putData("RunShooter In", new RunShooter(-0.4));

        //Intake commands
        SmartDashboard.putData("RunIntake In", new RunIntake(0.7));
        SmartDashboard.putData("RunIntake Out", new RunIntake(-0.5));

        //Other commands
        SmartDashboard.putData("Climb", new Climb(0.5));
        //SmartDashboard.putData("ToggleLight", new ToggleLight());
        //SmartDashboard.putData("UpdateLights", new UpdateLights());
        //SmartDashboard.putData("LightOn", new LightOn());
        }
    }
}
