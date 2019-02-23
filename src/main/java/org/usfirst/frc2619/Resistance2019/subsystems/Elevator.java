// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc2619.Resistance2019.subsystems;


import org.usfirst.frc2619.Resistance2019.MathUtil;
import org.usfirst.frc2619.Resistance2019.commands.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.motorcontrol.ControlMode;
import org.usfirst.frc2619.Resistance2019.subsystems.Extension;
import  org.usfirst.frc2619.Resistance2019.Robot;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

/**
 * @param MotionMagicDistance The current target distance for Motion Magic in ticks.
 * @param MAX_MOTION_MAGIC_DISTANCE The distance from the target in ticks that Motion Magic will accept.
 */
public class Elevator extends Subsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private WPI_TalonSRX motor;
    private Solenoid brakes;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private final static double SPEED_P_CONSTANT = 1;
	private final static double SPEED_I_CONSTANT = 0.001;
	private final static double SPEED_D_CONSTANT = 0.0;
	private final static double SPEED_F_CONSTANT = 10;
	
    private static final int MAX_TICKS_PER_SEC = 934;
	
	private final static int MOTION_MAGIC_VELOCITY_CONSTANT = 1750;
	private final static int MOTION_MAGIC_ACCELERATION_CONSTANT = 1000;
	private final static double MOTION_MAGIC_P_CONSTANT = 0.3;
	private final static double MOTION_MAGIC_I_CONSTANT = 0.001;
	private final static double MOTION_MAGIC_D_CONSTANT = 0.0;
	private final static double MOTION_MAGIC_F_CONSTANT = 1;
	
	private static int TICKS_TO_TOP = 28000;
	private static int TICKS_TO_BOTTOM = 200;

    final int TIMEOUT_MS = 10;

    private double SpeedP = SPEED_P_CONSTANT;
    private double SpeedI = SPEED_I_CONSTANT;
    private double SpeedD = SPEED_D_CONSTANT;
    private double SpeedF = SPEED_F_CONSTANT;
	
    private double MotionMagicP = MOTION_MAGIC_P_CONSTANT;
    private double MotionMagicI = MOTION_MAGIC_I_CONSTANT;
    private double MotionMagicD = MOTION_MAGIC_D_CONSTANT;
    private double MotionMagicF = MOTION_MAGIC_F_CONSTANT;
    private int MotionMagicVelocity = MOTION_MAGIC_VELOCITY_CONSTANT;
	private int MotionMagicAcceleration = MOTION_MAGIC_ACCELERATION_CONSTANT;
	private int MAX_MOTION_MAGIC_DISTANCE = 500;
    private double MotionMagicDistance;
	private final static int PID_SLOT_SPEED_MODE = 1;
	private final static int MOTION_MAGIC_SLOT_DISTANCE_MODE = 2;

	private double minSecsMinToFullThrottleIfUp = 0.5;
	private double minSecsMinToFullThrottleIfDown = 1;

	// NOTE: movable is becoming movableUp and movableDown
	public int SAFETY_LIMIT_TICKS = 8450;//upper safety position
	public int SAFETY_MID_TICKS = 4220;//mid safety position
	public int LANCE_HEIGHT_TICKS = 1540;//lower safety position
    public boolean isUp = false;

    public Elevator() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        motor = new WPI_TalonSRX(2);
        
        
        
        brakes = new Solenoid(0, 5);
        addChild("Brakes",brakes);
        
        

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
	motor.setInverted(true);
	motor.setSensorPhase(true);
    }

    @Override
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop

    }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CMDPIDGETTERS

    // Put methods for controlling this subsystem
	// here. Call these from Commands.

	//Initial method for PID speed control
    public void initSpeedPercentageMode() {
		motor.set(ControlMode.Velocity, 0);
		motor.selectProfileSlot(PID_SLOT_SPEED_MODE, 0);
		motor.config_kP(PID_SLOT_SPEED_MODE, SpeedP, TIMEOUT_MS);
		motor.config_kI(PID_SLOT_SPEED_MODE, SpeedI, TIMEOUT_MS);
		motor.config_kD(PID_SLOT_SPEED_MODE, SpeedD, TIMEOUT_MS);
		motor.config_kF(PID_SLOT_SPEED_MODE, SpeedF, TIMEOUT_MS);
		motor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TIMEOUT_MS);
		if (isUp)
			motor.configClosedloopRamp(minSecsMinToFullThrottleIfUp, TIMEOUT_MS);
		else
			motor.configClosedloopRamp(minSecsMinToFullThrottleIfDown, TIMEOUT_MS);

		motor.configNominalOutputForward(0, TIMEOUT_MS);
		motor.configNominalOutputReverse(0, TIMEOUT_MS);
	}
    
	public void stopClosedloopRamp() {
		motor.configClosedloopRamp(0, TIMEOUT_MS);
	}
	
	public void set(double percentSpeed) {
			motor.set(ControlMode.Velocity, MAX_TICKS_PER_SEC * percentSpeed);
	}

	public void stop() {
		motor.set(ControlMode.Velocity, 0);
		motor.disable();
		brakeOn();
	}

    public boolean checkLimitSwitches() {
    	if (checkTopLimitSwitch()) {
    		return (getMotorOutput()>0);
    	}
    	else if (checkBottomLimitSwitch()) {
    		return (getMotorOutput()<0);
    	}
    	return false;
	}
	
    //checks the top limit switch and resets the encoder if it is triggered
    public boolean checkTopLimitSwitch() {
    	if (motor.getSensorCollection().isFwdLimitSwitchClosed()) {
    		resetPosTop();
    		return true;
    	}
    	return false;
    }
	
	//checks the bottom limit switch and resets the encoder if it is triggered
    public boolean checkBottomLimitSwitch() {
    	if (motor.getSensorCollection().isRevLimitSwitchClosed()) {
    		resetPosBottom();
    		return true;
    	}
    	return false;
    }
	
	//resets the encoder to the top position
    public void resetPosTop() {
    	motor.setSelectedSensorPosition(TICKS_TO_TOP, 0, TIMEOUT_MS);
    }
	
	//resets the encoder to the bottom position
    public void resetPosBottom() {
    	motor.setSelectedSensorPosition(0, 0, TIMEOUT_MS);
    }
	
    public void brakeOn() {
    	brakes.set(false);
    }
    
    public void brakeOff() {
    	brakes.set(true);
    }
	
	//Initial MotionMagic method
    public void MotionMagicInit(double percentDistance) {
	    	motor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TIMEOUT_MS);
			
	    	motor.selectProfileSlot(MOTION_MAGIC_SLOT_DISTANCE_MODE,0);
	    	
	    	motor.config_kP(MOTION_MAGIC_SLOT_DISTANCE_MODE, MotionMagicP, TIMEOUT_MS);
	    	motor.config_kI(MOTION_MAGIC_SLOT_DISTANCE_MODE, MotionMagicI, TIMEOUT_MS);
	    	motor.config_kD(MOTION_MAGIC_SLOT_DISTANCE_MODE, MotionMagicD, TIMEOUT_MS);
	    	motor.config_kF(MOTION_MAGIC_SLOT_DISTANCE_MODE, MotionMagicF, TIMEOUT_MS);
	    	
	    	
	    	motor.configMotionAcceleration(MotionMagicAcceleration, TIMEOUT_MS);
	    	motor.configMotionCruiseVelocity(MotionMagicVelocity, TIMEOUT_MS);
	    	
			percentDistance = MathUtil.clamp(percentDistance, 0, 1);
	    	MotionMagicDistance = percentDistance;
			MotionMagicDistance *= TICKS_TO_TOP - TICKS_TO_BOTTOM;
			MotionMagicDistance += TICKS_TO_BOTTOM;
			motor.set(ControlMode.MotionMagic, MotionMagicDistance);
    }
	
	//Checks if the elevator is within the error range
    public boolean isAtPIDDestination() {
		double error = Math.abs(getPIDError());
		return error < MAX_MOTION_MAGIC_DISTANCE || ( getTarget() < getTicks() ? !safeToElevatePositionDown() : !safeToElevatePositionUp());
	}

	public double getPIDError(){
		return getTicks() - getTarget();
	}

	//Checks if the elevator is safe to move
	public boolean safeToElevatePositionUp()
	{
		return safeToElevatePositionUp(getTicks());
	}

	
	//Checks if the elevator is safe to move
	public boolean safeToElevatePositionDown()
	{
		return safeToElevatePositionDown(getTicks());
	}
	
	//Checks if the elevator is safe to move
	public boolean safeToRetract()
	{
		return safeToRetract(getTicks());
	}

	//chekcs if the elevator is safe to move to a position
	public boolean safeToElevatePositionUp(double position)
	{
		if (Robot.extension.isExtended()) 
		{
			return true;
		}
		else if (position < SAFETY_MID_TICKS && position > LANCE_HEIGHT_TICKS) return false;
		return true;
	}


	//chekcs if the elevator is safe to move to a position
	public boolean safeToRetract(double position)
	{
			return ((position>=SAFETY_LIMIT_TICKS)||(position<=LANCE_HEIGHT_TICKS && !Robot.ballSensor.isBallSensed()));
		
	}

	//chekcs if the elevator is safe to move to a position
	public boolean safeToElevatePositionDown(double position)
	{
		if (Robot.extension.isExtended()) 
		{
			return true;
		}
		else if (position < SAFETY_LIMIT_TICKS && position > SAFETY_MID_TICKS) return false;
		return true;
	}

	//Debug values
	public double getTicks()
	{
		return motor.getSelectedSensorPosition();
	}

	public double getMotorOutput(){
		return motor.get();
	}

	public double getTarget(){
		return MotionMagicDistance;
	}
}
