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

import edu.wpi.first.wpilibj.command.ConditionalCommand;
import org.usfirst.frc2619.Resistance2019.Robot;

/**
 *
 */
public class GetHatchConfig extends ConditionalCommand {


    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR
    public GetHatchConfig() {
      super(new ElevateGetHatch(), new ElevateGetHatchSafety());

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTOR

    }

    @Override
    protected boolean condition(){
        return Robot.elevator.getTicks() < Robot.elevator.LANCE_HEIGHT_TICKS;
    }
}
