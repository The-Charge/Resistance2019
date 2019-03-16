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
import edu.wpi.first.wpilibj.AnalogGyro;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.kauailabs.navx.frc.AHRS;

public class DriveTrain extends Subsystem {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private WPI_TalonSRX leftFrontMotor;
    private WPI_TalonSRX leftRearMotor;
    private WPI_TalonSRX rightFrontMotor;
    private WPI_TalonSRX rightRearMotor;
    private AnalogGyro doNotUse;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
	//Correct Gyro
	private static final AHRS ahrs = new AHRS(Port.kMXP);

	//PID constants used for PID speed control
    private static final double SPEED_P_CONSTANT = 0.3;
	private static final double SPEED_I_CONSTANT = 0.001;
	private static final double SPEED_D_CONSTANT = 0.0;
	private static final double SPEED_F_CONSTANT = 0.12;

	//DriveXFeet and other DriveTrain constants
	private static final int MAX_TICKS_PER_SECOND = 6500;
	private static final double TICKS_PER_FOOT = 49622;
	private static final int TIMEOUT_MS = 10;
	private static final int PID_SLOT_SPEED_MODE = 1;
	private static final int MOTION_MAGIC_SLOT_DISTANCE_MODE = 2;
    private static final int MOTION_MAGIC_PID_INDEX = 0;
	public static final int MAX_MOTION_MAGIC_DISTANCE_TICKS = 500; // distance from target in ticks 
	
	//Live tuning values for MotionMagic
    public double MotionMagicP = 2;
    public double MotionMagicI = 0.001;
    public double MotionMagicD = 0;
	private double MotionMagicF = 0.72;
    public int MotionMagicAcceleration = 2500;
	public int MotionMagicVelocity = 8000;

	//This is the current target for the MotionMagic loop
    public double MotionMagicDistanceTicks;	
	//This correction value is a multiplyer for the right motor in motion magic calculations
	public double correctionR = 1;	
    
	
	//Drive Constants
    private static boolean isReversed = false;
    private boolean driveLocked = false;
    private boolean quarterSpeed = false;
	private boolean halfSpeed = false;
	
    public DriveTrain() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        leftFrontMotor = new WPI_TalonSRX(15);
        
        
        
        leftRearMotor = new WPI_TalonSRX(14);
        
        
        
        rightFrontMotor = new WPI_TalonSRX(0);
        
        
        
        rightRearMotor = new WPI_TalonSRX(1);
        
        
        
        doNotUse = new AnalogGyro(0);
        addChild("DoNotUse",doNotUse);
        doNotUse.setSensitivity(0.007);
        

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

	//Set motors to coast mode
	setNeutralMode();

	//Set the right motors inverted
    rightFrontMotor.setInverted(true);
	rightRearMotor.setInverted(true);
	
	//Set rear motors to follow the front
    leftRearMotor.follow(leftFrontMotor);
	rightRearMotor.follow(rightFrontMotor);

    }

    @Override
    public void initDefaultCommand() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DEFAULT_COMMAND

        setDefaultCommand(new TankDrive());

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
	
    public void driveTrainInit() {
		setNeutralMode();

		//Set rear motors to follow the front
		leftRearMotor.follow(leftFrontMotor);
		rightRearMotor.follow(rightFrontMotor);
	
		//Set the right motors inverted
		rightFrontMotor.setInverted(true);
		rightRearMotor.setInverted(true);
    }

	//the basic run method for driving the robot. (Drive booleans are implimented here)
    public void run(double l, double r) {
    	double leftSpeed = l;
    	double rightSpeed = r;
    	
    	if (quarterSpeed) {
	    		leftSpeed = l *.25;
	    		rightSpeed = r *.25;
    	}
    	else if (halfSpeed) {
    		leftSpeed = l *.5;
    		rightSpeed = r *.5;
    	}
    		
    	if (driveLocked) {
			double avSpeed = (leftSpeed + rightSpeed) / 2.0;
			leftSpeed = avSpeed;
			rightSpeed = avSpeed;
			if (!isReversed) {
				leftFrontMotor.set(leftSpeed);
				rightFrontMotor.set(rightSpeed);
			} 
			else {
				leftFrontMotor.set(-1 * rightSpeed);
				rightFrontMotor.set(-1 * leftSpeed);
			}
		} 
    	else if (!isReversed) {
			leftFrontMotor.set(leftSpeed);
			rightFrontMotor.set(rightSpeed);
		} 
    	else {
			leftFrontMotor.set(-1 * rightSpeed);
			rightFrontMotor.set(-1 * leftSpeed);
		}
    }


	public void drive(double l, double r){
		leftFrontMotor.set(l);
		rightFrontMotor.set(r);
	}

	//stops the motors
    public void stop() {
    	leftFrontMotor.set(0);
    	rightFrontMotor.set(0);
    }
	
	//puts the motors in PercentOutput mode
    public void setPercentVBus() {
    	leftFrontMotor.set(ControlMode.PercentOutput, 0);
    	rightFrontMotor.set(ControlMode.PercentOutput, 0);
    }
    
    
    public void MotionMagicInit(double distance) {
    	this.MotionMagicInit(distance, MotionMagicVelocity, MotionMagicAcceleration);
    }
	
	//Initial positional motion magic command
    public void MotionMagicInit(double distance, int backVelocity, int backAcceleration) {
		leftFrontMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, MOTION_MAGIC_PID_INDEX, TIMEOUT_MS);
    	rightFrontMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, MOTION_MAGIC_PID_INDEX, TIMEOUT_MS);
    	
    	leftFrontMotor.selectProfileSlot(MOTION_MAGIC_SLOT_DISTANCE_MODE, MOTION_MAGIC_PID_INDEX);
    	rightFrontMotor.selectProfileSlot(MOTION_MAGIC_SLOT_DISTANCE_MODE, MOTION_MAGIC_PID_INDEX);
    	
    	leftFrontMotor.config_kP(MOTION_MAGIC_SLOT_DISTANCE_MODE, MotionMagicP, TIMEOUT_MS);
    	leftFrontMotor.config_kI(MOTION_MAGIC_SLOT_DISTANCE_MODE, MotionMagicI, TIMEOUT_MS);
    	leftFrontMotor.config_kD(MOTION_MAGIC_SLOT_DISTANCE_MODE, MotionMagicD, TIMEOUT_MS);
    	leftFrontMotor.config_kF(MOTION_MAGIC_SLOT_DISTANCE_MODE, MotionMagicF, TIMEOUT_MS);
    	
    	rightFrontMotor.config_kP(MOTION_MAGIC_SLOT_DISTANCE_MODE, MotionMagicP, TIMEOUT_MS);
    	rightFrontMotor.config_kI(MOTION_MAGIC_SLOT_DISTANCE_MODE, MotionMagicI, TIMEOUT_MS);
    	rightFrontMotor.config_kD(MOTION_MAGIC_SLOT_DISTANCE_MODE, MotionMagicD, TIMEOUT_MS);
    	rightFrontMotor.config_kF(MOTION_MAGIC_SLOT_DISTANCE_MODE, MotionMagicF, TIMEOUT_MS);
    	
    	leftFrontMotor.configMotionAcceleration(backAcceleration, TIMEOUT_MS);
    	leftFrontMotor.configMotionCruiseVelocity(backVelocity, TIMEOUT_MS);
    	
    	rightFrontMotor.configMotionAcceleration((int)(correctionR*backAcceleration), TIMEOUT_MS);
    	rightFrontMotor.configMotionCruiseVelocity((int)(correctionR*backVelocity), TIMEOUT_MS);
    	
    	leftFrontMotor.setSelectedSensorPosition(0, MOTION_MAGIC_PID_INDEX, TIMEOUT_MS);
    	rightFrontMotor.setSelectedSensorPosition(0, MOTION_MAGIC_PID_INDEX, TIMEOUT_MS);
    	
    	MotionMagicDistanceTicks = distance * TICKS_PER_FOOT;
    	leftFrontMotor.set(ControlMode.MotionMagic, MotionMagicDistanceTicks);
    	rightFrontMotor.set(ControlMode.MotionMagic, correctionR*MotionMagicDistanceTicks);
    }
	
	//checks if the motors are at the MotionMagic PID target
    public boolean isAtPIDDestination() {
		return (Math.abs(this.leftFrontMotor.getSelectedSensorPosition(MOTION_MAGIC_PID_INDEX) - MotionMagicDistanceTicks) < MAX_MOTION_MAGIC_DISTANCE_TICKS) 
		|| (Math.abs(this.rightFrontMotor.getSelectedSensorPosition(MOTION_MAGIC_PID_INDEX) + MotionMagicDistanceTicks) < MAX_MOTION_MAGIC_DISTANCE_TICKS);// || this.leftFrontMotor.getSelectedSensorPosition(MOTION_MAGIC_PID_INDEX) < -MotionMagicDistance + 6000)
	}
	
	//Initial PID speed control method
    public void initSpeedMode() {    	
    	leftFrontMotor.set(ControlMode.Velocity, 0);
    	rightFrontMotor.set(ControlMode.Velocity, 0);
    	
    	leftFrontMotor.config_kP(PID_SLOT_SPEED_MODE, SPEED_P_CONSTANT, TIMEOUT_MS);
    	leftFrontMotor.config_kI(PID_SLOT_SPEED_MODE, SPEED_I_CONSTANT, TIMEOUT_MS);
    	leftFrontMotor.config_kD(PID_SLOT_SPEED_MODE, SPEED_D_CONSTANT, TIMEOUT_MS);
    	leftFrontMotor.config_kF(PID_SLOT_SPEED_MODE, SPEED_F_CONSTANT, TIMEOUT_MS);

    	rightFrontMotor.config_kP(PID_SLOT_SPEED_MODE, SPEED_P_CONSTANT, TIMEOUT_MS);
    	rightFrontMotor.config_kI(PID_SLOT_SPEED_MODE, SPEED_I_CONSTANT, TIMEOUT_MS);
    	rightFrontMotor.config_kD(PID_SLOT_SPEED_MODE, SPEED_D_CONSTANT, TIMEOUT_MS);
    	rightFrontMotor.config_kF(PID_SLOT_SPEED_MODE, SPEED_F_CONSTANT, TIMEOUT_MS);
    	
    	leftFrontMotor.selectProfileSlot(PID_SLOT_SPEED_MODE, 0);
    	rightFrontMotor.selectProfileSlot(PID_SLOT_SPEED_MODE, 0);
    }
	
	//sets the target speed for the speed PID loop
	/**
	 * @param setSpeed The target speed of the PID loop in percent.
	 */
    public void setPercentSpeedPID(double setSpeed) {
		setSpeed = MathUtil.clamp(setSpeed, -1, 1);
		leftFrontMotor.set(ControlMode.Velocity, MAX_TICKS_PER_SECOND * setSpeed);
		rightFrontMotor.set(ControlMode.Velocity, MAX_TICKS_PER_SECOND * setSpeed);
	}

	public void setPercentSpeedPID(double setSpeedL, double setSpeedR) {
		setSpeedR = MathUtil.clamp(setSpeedR, -1, 1);
		setSpeedL = MathUtil.clamp(setSpeedL, -1, 1);
		leftFrontMotor.set(ControlMode.Velocity, MAX_TICKS_PER_SECOND * setSpeedL);
		rightFrontMotor.set(ControlMode.Velocity, MAX_TICKS_PER_SECOND * setSpeedR);
	}
	
	//gyro gets:
    public double getYaw() {
    	return ahrs.getYaw();
	}
	public static AHRS getGyro() {
		return ahrs;
	}
	public static double getGyroPID(){
		return ahrs.pidGet();
	}
	
	
	public void writePIDs(double output){
		writeIndivPIDs(output, -output);
	}

	//writes the output for a PID loop
	public void writeIndivPIDs(double outputl, double outputr){
		leftFrontMotor.pidWrite(outputl);
		rightFrontMotor.pidWrite(outputr);
	}

	//sets the target speed for speed control
	public void setSpeedPID(double setSpeed)
	{
		leftFrontMotor.set(ControlMode.Velocity, MAX_TICKS_PER_SECOND * setSpeed);
		rightFrontMotor.set(ControlMode.Velocity, MAX_TICKS_PER_SECOND * setSpeed);
	}

	//gets and sets for drive booleans:
	public boolean getLocked(){
		return driveLocked;
	}
	public void setLocked(boolean lock){
		driveLocked = lock;
	}
	public boolean getReversed(){
		return isReversed;
	}
	public void setReversed(boolean reverse){
		isReversed = reverse;
	}
	public boolean getHalf(){
		return halfSpeed;
	}
	public void setHalf(boolean half){
		halfSpeed = half;
	}
	public boolean getQuarter(){
		return quarterSpeed;
	}
	public void setQuarter(boolean quarter){
		quarterSpeed = quarter;
	}
	

	public void setNeutralMode(){
		//Sets all the motors to coast mode when not in use
		leftFrontMotor.setNeutralMode(NeutralMode.Coast);
    	leftRearMotor.setNeutralMode(NeutralMode.Coast);
    	rightFrontMotor.setNeutralMode(NeutralMode.Coast);
    	rightRearMotor.setNeutralMode(NeutralMode.Coast);
	}

	public void setBrakeMode(){
		//Sets all the motors to brake mode when not in use
		leftFrontMotor.setNeutralMode(NeutralMode.Brake);
    	leftRearMotor.setNeutralMode(NeutralMode.Brake);
    	rightFrontMotor.setNeutralMode(NeutralMode.Brake);
    	rightRearMotor.setNeutralMode(NeutralMode.Brake);
	}

	//gets the average current use of the two front motors
    public double getCurrentAmps() {
		return (leftFrontMotor.getOutputCurrent()+rightFrontMotor.getOutputCurrent())/2;
	}

	public int getEncoderTicks(){
		//Debug value
		return leftFrontMotor.getSelectedSensorPosition(MOTION_MAGIC_PID_INDEX);
	}

	public int getEncoderVelocity(){
		//Debug value
		return leftFrontMotor.getSelectedSensorVelocity();
	}
	
}

