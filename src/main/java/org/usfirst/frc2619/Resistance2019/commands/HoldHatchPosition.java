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
import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class HoldHatchPosition extends Command {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
 
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
    public Timer burnoutTimer;
    private static final double FAILSAFE_TIMEOUT = 5;

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public HoldHatchPosition() {

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.hatchers);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        burnoutTimer = new Timer();
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        burnoutTimer.start();
        burnoutTimer.reset();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if (Robot.hatchers.isUp() && !Robot.hatchers.checkTopLimitSwitch()){
            if (burnoutTimer.get()<FAILSAFE_TIMEOUT){
                Robot.hatchers.holdHatch(Robot.hatchers.isUp());
            }else{
                Robot.hatchers.stop();
            }
        }else if (!Robot.hatchers.isUp() && !Robot.hatchers.checkBottomLimitSwitch()){
            if (burnoutTimer.get()<FAILSAFE_TIMEOUT){
                Robot.hatchers.holdHatch(Robot.hatchers.isUp());
            }else{
                Robot.hatchers.stop();
            }
        }else{
            Robot.hatchers.stop();
            if (burnoutTimer.get()>0.3){
                burnoutTimer.reset();
            }
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.hatchers.stop();
        burnoutTimer.reset();
        burnoutTimer.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
