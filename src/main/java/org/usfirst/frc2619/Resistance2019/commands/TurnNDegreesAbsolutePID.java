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

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.SPI.Port;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.Timer;
import org.usfirst.frc2619.Resistance2019.Robot;
import org.usfirst.frc2619.Resistance2019.subsystems.DriveTrain;

/**
 *
 */
public class TurnNDegreesAbsolutePID extends PIDCommand {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
    private double m_targetAngle;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
    private boolean atTarget = false;
    private double minSpeedz;
    //private Timer delay;
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public TurnNDegreesAbsolutePID(double targetAngle) {

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        super("TurnNDegreesAbsolutePID", 0.007, 0.0000725, 0.0, 0.02);
        getPIDController().setAbsoluteTolerance(2.0);
        getPIDController().setInputRange(-180.0, 180.0);
        getPIDController().setOutputRange(-0.8, 0.8);
        getPIDController().setContinuous(true);

        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        m_targetAngle = targetAngle;

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.driveTrain);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    @Override
    protected double returnPIDInput() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        // yourPot.getAverageVoltage() / kYourMaxVoltage;

        return DriveTrain.getGyroPID();
    }

    @Override
    protected void usePIDOutput(double output) {
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);

        int sign = (int) Math.signum(output);
		//double minSpeed = minSpeedz;
		double finalOutput = sign * Math.max(minSpeedz, Math.abs(output));

        Robot.driveTrain.drive(finalOutput, -finalOutput);

        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=OUTPUT
    
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=OUTPUT
    SmartDashboard.putNumber("PID Output", output);
    SmartDashboard.putNumber("Final Output", finalOutput);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        atTarget = false;
        minSpeedz = Robot.TurnMinSpeed;
        //delay.start();
        //delay.reset();
        this.setTimeout(5);
        getPIDController().setSetpoint(m_targetAngle);
        getPIDController().setP(Robot.TurnP);
        getPIDController().setI(Robot.TurnI);
        getPIDController().setD(Robot.TurnD);
        Robot.driveTrain.setPercentVBus();
        Robot.driveTrain.setBrakeMode();
        //Robot.driveTrain.initSpeedMode();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if (getPIDController().onTarget() && !atTarget){
            atTarget = true;
            //delay.start();
            //delay.reset();
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return isTimedOut() || getPIDController().onTarget()/*(atTarget & delay.get() > 0.5)*/;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.driveTrain.stop();
        getPIDController().disable();
    	Robot.driveTrain.setPercentVBus();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
