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

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc2619.Resistance2019.MathUtil;
import org.usfirst.frc2619.Resistance2019.Robot;
import org.usfirst.frc2619.Resistance2019.VisionData;
import org.usfirst.frc2619.Resistance2019.VisionUtil;

/**
 *
 */
public class LockToTargetTankDrive extends PIDCommand {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
 
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
    VisionData camInfo;

    Timer timeWithoutTarget;
    public static final double TIMEOUT = 0.5;
    private boolean timerIsRunning = false;

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public LockToTargetTankDrive() {

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        super("LockToTargetTankDrive", 0.05, 0.0, 0.0001, 0.02);
        getPIDController().setAbsoluteTolerance(0.2);
        getPIDController().setContinuous(false);
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

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
        
        //note: this method seems to throw an error when the robot initializes
        //      it won't crash the robot though

        camInfo = VisionUtil.getSerialInfo(Robot.visionPort);

        SmartDashboard.putString("VisionData", camInfo.toString());
        SmartDashboard.putNumber("Angle to closest target", camInfo.findYawOfClosestTarget());

        if (camInfo.hasTargets()) {
            double targetYaw = camInfo.findYawOfClosestTarget();
            return targetYaw;
        } else {
            return 0.0;
        }

    }

    @Override
    protected void usePIDOutput(double output) {
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);

        double xInput = -Robot.oi.leftJoystick.getY();
        double yInput = -Robot.oi.rightJoystick.getY();
        xInput = MathUtil.adjSpeed(xInput);
        yInput = MathUtil.adjSpeed(yInput);

        double xAdjust = xInput + output;
        double yAdjust = yInput - output;

        double maxValue = Math.max(Math.abs(xAdjust), Math.abs(yAdjust));
        if (maxValue > 1.0) {
            xAdjust /= maxValue;
            yAdjust /= maxValue;
        }

        SmartDashboard.putNumber("xPID", xAdjust);
        SmartDashboard.putNumber("yPID", yAdjust);
        SmartDashboard.putNumber("X input", xInput);
        SmartDashboard.putNumber("Y input", yInput);

        try {
            if (camInfo.hasTargets()) {
                Robot.driveTrain.run(xAdjust, yAdjust);

                timerIsRunning = false;
                timeWithoutTarget.reset();
                timeWithoutTarget.stop();
            } else {
                if (!timerIsRunning) {
                    timeWithoutTarget.start();
                    timerIsRunning = true;
                }
                if (timeWithoutTarget.get() > TIMEOUT)
                    Robot.driveTrain.run(xInput, yInput);
            }
        } catch(Exception e) {
            Robot.driveTrain.run(xInput, yInput);
        }
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.driveTrain.setPercentVBus();
        getPIDController().setSetpoint(0.0);
        timeWithoutTarget = new Timer();
        timeWithoutTarget.stop();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        camInfo = VisionUtil.getSerialInfo(Robot.visionPort);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return Robot.visionPort == null;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.driveTrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
