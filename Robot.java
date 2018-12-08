package org.usfirst.frc.team2637.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class Robot extends IterativeRobot {

	public static XboxController xbox;
	public static DriveTrain driveTrain;
	
	final int DRIVE_XBOX_PORT = 0;

	@Override
	public void robotInit() {
		xbox = new XboxController(DRIVE_XBOX_PORT);
		driveTrain = new DriveTrain();
	}
	
	@Override
	public void autonomousInit() {
		
	}
	
	@Override
	public void autonomousPeriodic() {
		
	}

	@Override
	public void teleopPeriodic() {
		//Put this in arcadeDrive parameter if you want trigger method 
		//xbox.getTriggerAxis(Hand.kRight) - xbox.getTriggerAxis(Hand.kLeft), xbox.getX(Hand.kLeft)
		driveTrain.arcadeDrive(xbox.getY(Hand.kLeft), xbox.getX(Hand.kRight));
		/*
		if(xbox.getAButton()) {
			driveTrain.setHighGear();
		}
		if(xbox.getBButton()) {
			driveTrain.setLowGear();
		}*/
	}
	
	@Override
	public void testPeriodic() {
		
	}
	
	@Override
	public void robotPeriodic() {
		
	}

	@Override
	public void disabledInit() {
		
	}

	@Override
	public void disabledPeriodic() {
		
	}
}
