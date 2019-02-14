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
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc2619.Resistance2019.Robot;

/**
 @param      m_speed        The speed in percent.
 @param      m_maxCurrent   The current threshold is amps.
 */
public class DriveToCurrent extends Command {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
    private double m_speed;
    private double m_maxCurrent;
 
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public DriveToCurrent(double speed, double maxCurrent) {

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        m_speed = speed;
        m_maxCurrent = maxCurrent;

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.driveTrain);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    	this.setTimeout(1);
		Robot.driveTrain.initSpeedMode();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
    	Robot.driveTrain.setPercentSpeedPID(m_speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        // TODO: This should probably be an or (if we time out OR we exceeded the desired current, not both)
    	return isTimedOut() && Robot.driveTrain.getCurrentAmps() > m_maxCurrent;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    	Robot.driveTrain.stop();
    	Robot.driveTrain.setPercentVBus();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    	end();
    }
}

