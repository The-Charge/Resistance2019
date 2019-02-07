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
import org.usfirst.frc2619.Resistance2019.Robot;

import org.usfirst.frc2619.Resistance2019.subsystems.SensorBar;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 *
 */
public class PIDLineFollow extends PIDCommand {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
 
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

    public DigitalInput[] sensArray; //One array to hold the sensors in this command
    private double doemem; //Degree of Error Memory - Remember the previous doe for the next cycle

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public PIDLineFollow() {

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=PID
        super("PIDLineFollow", 1.0, 0.0, 0.0, 0.02);
        getPIDController().setContinuous(false);
        getPIDController().setAbsoluteTolerance(0.2);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=PID

        sensArray = new DigitalInput[5];
        for (int senspace = 0; senspace < 5; senspace++){ // Loading in all sensors into array seperate from sensorBar
            sensArray[senspace] = Robot.sensorBar.getSensor(senspace);
        }
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
        requires(Robot.driveTrain);

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    @Override
    protected double returnPIDInput() { //read sensors.. find doe..
        boolean[] sensbool = new boolean[5]; //Var to hold boolean values and be easily changed
        int ctr = 0; //self explanatory - used to count current sensors active
        int total = 0; //self explanatory - total of sensor assigned integer positions
        double doe = 9999; // Degree Of Error - calculated with total and ctr
        
        for (int x = 0; x < 5; x++){ //Get boolean values from digital input sensors
            if (x == 1 || x == 2 || x == 3) 
            sensbool[x] = sensArray[x].get();
            else
            {
                //sensbool[x] = false;
                sensbool[x] = !sensArray[x].get();
            }
            //sensbool[x] = false; //FOR TESTING ONLY!!!
        }

        for (int y = 0; y < 5; y++){ //Gathering data for doe
            if(sensbool[y]){
                total += y;
                ctr++;
            }
        }
        
        if (ctr==0){
            total = -1; //Null sensor situation - way far left or right checker
            doe = 0; //Ensure doe reads an impossible value
        } 
        else doe = (total / ctr) - 2; //Calculate Degree of Error

        SmartDashboard.putNumber("Current Degree", doe);
        //doe = SmartDashboard.getNumber("Degree", 0); //FOR TESTING PURPOSES ONLY
        //If structure to change speed

        return doe;
    }

    @Override
    protected void usePIDOutput(double output) {
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);
        output = output / 4;

        if(output != 0){
            Robot.driveTrain.writeIndivPIDs(output, -output);
        }
        else Robot.driveTrain.writeIndivPIDs(0.25, 0.25);
      
    }
    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        this.setTimeout(3); //a timeout could be a good idea
        getPIDController().setSetpoint(0);
        
        doemem = 9999;
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if (Robot.driveTrain.getCurrentAmps() > 10) end();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {

        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.driveTrain.setPercentVBus();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to runs
    @Override
    protected void interrupted() {
        end();
    }
}
