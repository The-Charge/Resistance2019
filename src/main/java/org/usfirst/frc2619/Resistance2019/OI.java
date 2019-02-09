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

import org.usfirst.frc2619.Resistance2019.commands.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);

    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.

    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:

    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());

    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());

    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());


    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public JoystickButton intakeInFastBtn;
    public JoystickButton intakeInSlowBtn;
    public JoystickButton intakeOutFastBtn;
    public JoystickButton intakeOutSlowBtn;
    public Joystick buttonBox;
    public JoystickButton extendBtn;
    public JoystickButton retractBtn;
    public JoystickButton putHatchBtn;
    public JoystickButton holdBtn;
    public JoystickButton shiftLowBtn;
    public JoystickButton shiftHighBtn;
    public JoystickButton moveKickerBtn;
    public JoystickButton tankDriveBtn;
    public Joystick leftJoystick;
    public JoystickButton elevateToBottomBtn;
    public JoystickButton elevatetoBallCollectBtn;
    public JoystickButton elevateToMiddleBtn;
    public JoystickButton stopElevatorBtn;
    public JoystickButton overrideElevatorUpBtn;
    public JoystickButton overrideElevatorDownBtn;
    public Joystick rightJoystick;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    public OI() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

        rightJoystick = new Joystick(1);
        
        overrideElevatorDownBtn = new JoystickButton(rightJoystick, 6);
        overrideElevatorDownBtn.whileHeld(new OverrideElevator(-0.1));
        overrideElevatorUpBtn = new JoystickButton(rightJoystick, 5);
        overrideElevatorUpBtn.whileHeld(new OverrideElevator(0.3));
        stopElevatorBtn = new JoystickButton(rightJoystick, 4);
        stopElevatorBtn.whileHeld(new StopElevator());
        elevateToMiddleBtn = new JoystickButton(rightJoystick, 3);
        elevateToMiddleBtn.whileHeld(new ElevateToXPositionMotionMagic(0.4));
        elevatetoBallCollectBtn = new JoystickButton(rightJoystick, 2);
        elevatetoBallCollectBtn.whileHeld(new ElevateToXPositionMotionMagic(0.2));
        elevateToBottomBtn = new JoystickButton(rightJoystick, 1);
        elevateToBottomBtn.whileHeld(new ElevateToXPositionMotionMagic(0.1));
        leftJoystick = new Joystick(0);
        
        tankDriveBtn = new JoystickButton(leftJoystick, 12);
        tankDriveBtn.whenPressed(new TankDrive());
        moveKickerBtn = new JoystickButton(leftJoystick, 2);
        moveKickerBtn.whileHeld(new ExtendKicker());
        shiftHighBtn = new JoystickButton(leftJoystick, 6);
        shiftHighBtn.whileHeld(new ShiftHigh());
        shiftLowBtn = new JoystickButton(leftJoystick, 5);
        shiftLowBtn.whileHeld(new ShiftLow());
        holdBtn = new JoystickButton(leftJoystick, 7);
        holdBtn.whileHeld(new HoldBall());
        putHatchBtn = new JoystickButton(leftJoystick, 8);
        putHatchBtn.whileHeld(new PutHatch());
        retractBtn = new JoystickButton(leftJoystick, 4);
        retractBtn.whileHeld(new Retract());
        extendBtn = new JoystickButton(leftJoystick, 3);
        extendBtn.whileHeld(new Extend());
        buttonBox = new Joystick(2);
        
        intakeOutSlowBtn = new JoystickButton(buttonBox, 4);
        intakeOutSlowBtn.whileHeld(new RunIntake(0));
        intakeOutFastBtn = new JoystickButton(buttonBox, 6);
        intakeOutFastBtn.whileHeld(new RunIntake(-0.8));
        intakeInSlowBtn = new JoystickButton(buttonBox, 3);
        intakeInSlowBtn.whileHeld(new RunIntake(0.2));
        intakeInFastBtn = new JoystickButton(buttonBox, 5);
        intakeInFastBtn.whileHeld(new RunIntake(0.8));


        // SmartDashboard Buttons
        SmartDashboard.putData("Autonomous Command", new AutonomousCommand());
        SmartDashboard.putData("DriveXFeetMotionMagic: Drive2Feet", new DriveXFeetMotionMagic(2, 0, 0));
        SmartDashboard.putData("DriveXFeetMotionMagic: Drive1foot", new DriveXFeetMotionMagic(1, 0, 0));
        SmartDashboard.putData("Extend", new Extend());
        SmartDashboard.putData("Retract", new Retract());
        SmartDashboard.putData("ElevateToXPositionMotionMagic: bottom", new ElevateToXPositionMotionMagic(0.1));
        SmartDashboard.putData("ElevateToXPositionMotionMagic: ballCollect", new ElevateToXPositionMotionMagic(0.2));
        SmartDashboard.putData("ElevateToXPositionMotionMagic: middle", new ElevateToXPositionMotionMagic(0.4));
        SmartDashboard.putData("StopElevator", new StopElevator());
        SmartDashboard.putData("PutHatch", new PutHatch());
        SmartDashboard.putData("HoldBall", new HoldBall());
        SmartDashboard.putData("InvertDrive", new InvertDrive());
        SmartDashboard.putData("ShiftLow", new ShiftLow());
        SmartDashboard.putData("ShiftHigh", new ShiftHigh());
        SmartDashboard.putData("ExtendKicker", new ExtendKicker());
        SmartDashboard.putData("DriveToCurrent: default", new DriveToCurrent(0.1, 10));
        SmartDashboard.putData("ToggleLockStraight", new ToggleLockStraight());
        SmartDashboard.putData("TurnNDegreesAbsolutePID: Turn180DegreesAbsolutePID", new TurnNDegreesAbsolutePID(180));
        SmartDashboard.putData("TurnNDegreesAbsolutePID: Turn90DegreesAbsolutePID", new TurnNDegreesAbsolutePID(90));
        SmartDashboard.putData("TurnNDegreesAbsolutePID: Turn0DegreesAbsolutePID", new TurnNDegreesAbsolutePID(0));
        SmartDashboard.putData("PIDLineFollow", new PIDLineFollow());
        SmartDashboard.putData("LightOn", new LightOn());
        SmartDashboard.putData("LightLineFollow", new LightLineFollow());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
    public Joystick getButtonBox() {
        return buttonBox;
    }

    public Joystick getLeftJoystick() {
        return leftJoystick;
    }

    public Joystick getRightJoystick() {
        return rightJoystick;
    }


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
}

