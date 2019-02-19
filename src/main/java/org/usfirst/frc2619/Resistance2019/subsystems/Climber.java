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


import org.usfirst.frc2619.Resistance2019.commands.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


/**
 *
 */
public class Climber extends Subsystem {

    private CANSparkMax climberMotor;
    private final double ROTATIONS_TO_CLIMB = 0.53;
    

    public Climber() {
     
        climberMotor = new CANSparkMax(20, MotorType.kBrushless);
        climberMotor.setSmartCurrentLimit(40);
       
    }

    @Override
    public void initDefaultCommand() {
       
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop

    }

    public void run(double speed)
    {
        climberMotor.set(speed);
        //SmartDashboard.putNumber("Velocity", climberMotor.getEncoder().getVelocity());
    }
    public void stop()
    {
        climberMotor.set(0);
    }
    public boolean reached()
    {
        if (climberMotor.getEncoder().getPosition() > ROTATIONS_TO_CLIMB) return true;
        else return false;
    }

  
}

