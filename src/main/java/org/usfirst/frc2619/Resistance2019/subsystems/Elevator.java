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
// TODO: Whitespace fix
    import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

    import com.ctre.phoenix.motorcontrol.ControlMode;
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
	
	private final static int MOTION_MAGIC_VELOCITY_CONSTANT = 500;
	private final static int MOTION_MAGIC_ACCELERATION_CONSTANT = 300;
	private final static double MOTION_MAGIC_P_CONSTANT = 1;
	private final static double MOTION_MAGIC_I_CONSTANT = 0.001;
	private final static double MOTION_MAGIC_D_CONSTANT = 0.0;
	private final static double MOTION_MAGIC_F_CONSTANT = 10;
	
    private static final int TICKS_TO_TOP = 6000;

    final int TIMEOUT_MS = 10;

	//TODO: Make these private unless they're used outside the class (they shouldn't be)
    public double SpeedP = SPEED_P_CONSTANT;
    public double SpeedI = SPEED_I_CONSTANT;
    public double SpeedD = SPEED_D_CONSTANT;
    public double SpeedF = SPEED_F_CONSTANT;
	
	//TODO: Make these private unless they're used outside the class (they shouldn't be)
    public double MotionMagicP = MOTION_MAGIC_P_CONSTANT;
    public double MotionMagicI = MOTION_MAGIC_I_CONSTANT;
    public double MotionMagicD = MOTION_MAGIC_D_CONSTANT;
    public double MotionMagicF = MOTION_MAGIC_F_CONSTANT;
    public int MotionMagicVelocity = MOTION_MAGIC_VELOCITY_CONSTANT;
	public int MotionMagicAcceleration = MOTION_MAGIC_ACCELERATION_CONSTANT;
	public int MAX_MOTION_MAGIC_DISTANCE = 500;
    public double MotionMagicDistance;
	public final static int PID_SLOT_SPEED_MODE = 1;
	public final static int MOTION_MAGIC_SLOT_DISTANCE_MODE = 0;

	// TODO: Is this being set anywhere? I see it being referenced, but not set.
    public boolean movable = true;
    public boolean isUp = false;

    public Elevator() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        motor = new WPI_TalonSRX(2);
        
        
        
        brakes = new Solenoid(0, 1);
        addChild("Brakes",brakes);
        
        

	// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
	motor.setInverted(true);
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
    public void initSpeedPercentageMode() {
		motor.set(ControlMode.Velocity, 0);
		motor.selectProfileSlot(PID_SLOT_SPEED_MODE, 0);
		motor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, TIMEOUT_MS);
		// TODO: Move 0.5 and 1 to constants up top. Make sure to give them good names
		if (isUp)
			motor.configClosedloopRamp(0.5, TIMEOUT_MS);
		else
			motor.configClosedloopRamp(1, TIMEOUT_MS);

		motor.configNominalOutputForward(0, TIMEOUT_MS);
		motor.configNominalOutputReverse(0, TIMEOUT_MS);
	}
    
	public void stopClosedloopRamp() {
		motor.configClosedloopRamp(0, TIMEOUT_MS);
	}
	
	public void set(double percentSpeed) {
		if (movable)
			motor.set(ControlMode.Velocity, MAX_TICKS_PER_SEC * percentSpeed);
	}

	public void stop() {
		motor.set(ControlMode.Velocity, 0);
		motor.disable();
		brakeOn();
	}
	 
    public void writeDashboardDebugValues() {
    	SmartDashboard.putBoolean("UpperLimitSwitch", motor.getSensorCollection().isFwdLimitSwitchClosed());
        SmartDashboard.putBoolean("LowerLimitSwitch", motor.getSensorCollection().isRevLimitSwitchClosed());
    	SmartDashboard.putNumber("ElevatorMMDistance", MotionMagicDistance);
    	SmartDashboard.putBoolean("MMFinished", isAtPIDDestination());
    }
    
    public void readDashboardControlValues() {
		SpeedP = SmartDashboard.getNumber("ShooterSpeedP", SPEED_P_CONSTANT);
		SpeedI = SmartDashboard.getNumber("ShooterSpeedI", SPEED_I_CONSTANT);
		SpeedD = SmartDashboard.getNumber("ShooterSpeedD", SPEED_D_CONSTANT);
		SpeedF = SmartDashboard.getNumber("ShooterSpeedF", SPEED_F_CONSTANT);

		motor.config_kP(PID_SLOT_SPEED_MODE, SpeedP, TIMEOUT_MS);
		motor.config_kI(PID_SLOT_SPEED_MODE, SpeedI, TIMEOUT_MS);
		motor.config_kD(PID_SLOT_SPEED_MODE, SpeedD, TIMEOUT_MS);
		motor.config_kF(PID_SLOT_SPEED_MODE, SpeedF, TIMEOUT_MS);
	}
    
    public void writeDefaultDashboardValues() {
		SmartDashboard.putNumber("ShooterSpeedP", SPEED_P_CONSTANT);
		SmartDashboard.putNumber("ShooterSpeedI", SPEED_I_CONSTANT);
		SmartDashboard.putNumber("ShooterSpeedD", SPEED_D_CONSTANT);
		SmartDashboard.putNumber("ShooterSpeedF", SPEED_F_CONSTANT);
	}

    

    public boolean checkLimitSwitches() {
    	if (motor.getSensorCollection().isFwdLimitSwitchClosed()) {
    		resetPosBottom();
    		return true;
    	}
    	else if (motor.getSensorCollection().isRevLimitSwitchClosed()) {
    		resetPosTop();
    		return true;
    	}
    	return false;
    }
    
    public boolean checkBottomLimitSwitch() {
    	if (motor.getSensorCollection().isFwdLimitSwitchClosed()) {
    		resetPosBottom();
    		return true;
    	}
    	return false;
    }
    
    public boolean checkTopLimitSwitch() {
    	if (motor.getSensorCollection().isRevLimitSwitchClosed()) {
    		resetPosTop();
    		return true;
    	}
    	return false;
    }
    
    public void resetPosTop() {
    	
    }
    
    public void resetPosBottom() {
    	//motor.setSelectedSensorPosition(0, 0, TIMEOUT_MS);
    }
    
    public void brakeOn() {
    	brakes.set(false);
    }
    
    public void brakeOff() {
    	brakes.set(true);
    }
    
    public void MotionMagicInit(double percentDistance) {
    	if (movable) {
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
	    	MotionMagicDistance *= TICKS_TO_TOP;
	    	motor.set(ControlMode.MotionMagic, MotionMagicDistance);
    	}
    }
    
    public boolean isAtPIDDestination() {
		return (Math.abs(this.motor.getSelectedSensorPosition(0) - MotionMagicDistance) < MAX_MOTION_MAGIC_DISTANCE);// || this.leftFrontMotor.getSelectedSensorPosition(MotionMagicPIDIndex) < -MotionMagicDistance + 6000)
	}
}

