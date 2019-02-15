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

/**
 * @param MotionMagicDistance The current target distance for Motion Magic.
 * @param MAX_MOTION_MAGIC_DISTANCE The distance from the target in ticks that Motion Magic will accept.
 */
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
    private final static double SPEED_P_CONSTANT = 0.3;
	private final static double SPEED_I_CONSTANT = 0.001;
	private final static double SPEED_D_CONSTANT = 0.0;
	private final static double SPEED_F_CONSTANT = 0.12;
    
    private double speedP = SPEED_P_CONSTANT;
	private double speedI = SPEED_I_CONSTANT;
	private double speedD = SPEED_D_CONSTANT;
	private double speedF = SPEED_F_CONSTANT;
	
	private final int MAX_TICKS_PER_SECOND = 8691;
    private final double TICKS_PER_FOOT = 4320;
	private final int TIMEOUT_MS = 10;
	private final static int PID_SLOT_SPEED_MODE = 1;
	private final static int MOTION_MAGIC_SLOT_DISTANCE_MODE = 0;
    
    private double MotionMagicP = 2;
    private double MotionMagicI = 0.001;
    private double MotionMagicD = 0;
    private double MotionMagicF = 0.72;
    private int MotionMagicAcceleration = 2500;
    private int MotionMagicVelocity = 8000;
    private int MotionMagicPIDIndex = 0;
    private int MotionMagicPIDSlot = 0;
    public double MotionMagicDistanceTicks;	
	//NOTE: no idea on units for this correction, may no to find later
	public double correctionR = 1.02;	
	public final static int MAX_MOTION_MAGIC_DISTANCE_TICKS = 500;
    

    private final double TIMEOUT = 0.002;
    private static final AHRS ahrs = new AHRS(Port.kMXP);
    private double turn_outer_speed;
	private final double TURN_OUTER_SPEED_DEFAULT = 0.5;
	private double turn_inner_speed;
	private final double TURN_INNER_SPEED_DEFAULT = -0.5;
    
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
    leftRearMotor.follow(leftFrontMotor);
    rightRearMotor.follow(rightFrontMotor);
    rightFrontMotor.setInverted(true);
    rightRearMotor.setInverted(true);
    
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

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public void driveTrainInit() {
		setNeutralMode();
    }

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

    public void stop() {
    	leftFrontMotor.set(0);
    	rightFrontMotor.set(0);
    }
    
    public void setPercentVBus() {
    	leftFrontMotor.set(ControlMode.PercentOutput, 0);
    	rightFrontMotor.set(ControlMode.PercentOutput, 0);
    }
    
    
    public void MotionMagicInit(double distance) {
    	this.MotionMagicInit(distance, MotionMagicVelocity, MotionMagicAcceleration);
    }
    
    public void MotionMagicInit(double distance, int backVelocity, int backAcceleration) {
		leftFrontMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, MotionMagicPIDIndex, TIMEOUT_MS);
    	rightFrontMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, MotionMagicPIDIndex, TIMEOUT_MS);
    	
    	leftFrontMotor.selectProfileSlot(MotionMagicPIDSlot, MotionMagicPIDIndex);
    	rightFrontMotor.selectProfileSlot(MotionMagicPIDSlot, MotionMagicPIDIndex);
    	
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
    	
    	leftFrontMotor.setSelectedSensorPosition(MOTION_MAGIC_SLOT_DISTANCE_MODE, MotionMagicPIDIndex, TIMEOUT_MS);
    	rightFrontMotor.setSelectedSensorPosition(MOTION_MAGIC_SLOT_DISTANCE_MODE, MotionMagicPIDIndex, TIMEOUT_MS);
    	
    	MotionMagicDistanceTicks = distance * TICKS_PER_FOOT;
    	leftFrontMotor.set(ControlMode.MotionMagic, MotionMagicDistanceTicks);
    	rightFrontMotor.set(ControlMode.MotionMagic, correctionR*MotionMagicDistanceTicks);
    }
    
    public boolean isAtPIDDestination() {
		return (Math.abs(this.leftFrontMotor.getSelectedSensorPosition(MotionMagicPIDIndex) - MotionMagicDistanceTicks) < MAX_MOTION_MAGIC_DISTANCE_TICKS) 
		|| (Math.abs(this.rightFrontMotor.getSelectedSensorPosition(MotionMagicPIDIndex) + MotionMagicDistanceTicks) < MAX_MOTION_MAGIC_DISTANCE_TICKS);// || this.leftFrontMotor.getSelectedSensorPosition(MotionMagicPIDIndex) < -MotionMagicDistance + 6000)
	}
    
    public void initSpeedMode() {    	
    	leftFrontMotor.set(ControlMode.Velocity, 0);
    	rightFrontMotor.set(ControlMode.Velocity, 0);
    	
    	leftFrontMotor.config_kP(PID_SLOT_SPEED_MODE, speedP, TIMEOUT_MS);
    	leftFrontMotor.config_kI(PID_SLOT_SPEED_MODE, speedI, TIMEOUT_MS);
    	leftFrontMotor.config_kD(PID_SLOT_SPEED_MODE, speedD, TIMEOUT_MS);
    	leftFrontMotor.config_kF(PID_SLOT_SPEED_MODE, speedF, TIMEOUT_MS);

    	rightFrontMotor.config_kP(PID_SLOT_SPEED_MODE, speedP, TIMEOUT_MS);
    	rightFrontMotor.config_kI(PID_SLOT_SPEED_MODE, speedI, TIMEOUT_MS);
    	rightFrontMotor.config_kD(PID_SLOT_SPEED_MODE, speedD, TIMEOUT_MS);
    	rightFrontMotor.config_kF(PID_SLOT_SPEED_MODE, speedF, TIMEOUT_MS);
    	
    	leftFrontMotor.selectProfileSlot(PID_SLOT_SPEED_MODE, 0);
    	rightFrontMotor.selectProfileSlot(PID_SLOT_SPEED_MODE, 0);
    }
	
	
	/**
	 * @param setSpeed The target speed of the PID loop in percent.
	 */
    public void setPercentSpeedPID(double setSpeed) {
		setSpeed = MathUtil.clamp(setSpeed, -1, 1);
		leftFrontMotor.set(ControlMode.Velocity, MAX_TICKS_PER_SECOND * setSpeed);
		rightFrontMotor.set(ControlMode.Velocity, MAX_TICKS_PER_SECOND * setSpeed);
	}
	
    public double getCurrentAmps() {
		return leftFrontMotor.getOutputCurrent();
	}
    
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

	public void writeIndivPIDs(double outputl, double outputr){
		leftFrontMotor.pidWrite(outputl);
		rightFrontMotor.pidWrite(-outputr);
	}
	public void setSpeedPID(double setSpeed)
	{
		leftFrontMotor.set(ControlMode.Velocity, MAX_TICKS_PER_SECOND * setSpeed);
		rightFrontMotor.set(ControlMode.Velocity, MAX_TICKS_PER_SECOND * setSpeed);
	}

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

	private void setNeutralMode(){
		leftFrontMotor.setNeutralMode(NeutralMode.Brake);
    	leftRearMotor.setNeutralMode(NeutralMode.Brake);
    	rightFrontMotor.setNeutralMode(NeutralMode.Brake);
    	rightRearMotor.setNeutralMode(NeutralMode.Brake);
	}
}

