package frc.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class Robot extends IterativeRobot {

	public static XboxController xboxDrive;
	public static XboxController xboxAux;
	public static DriveTrain driveTrain;
	public static CatzLift lift;
	public static CatzGrabber grabber;
	
	final int DRIVE_XBOX_PORT = 0;
	final int AUX_XBOX_PORT = 1;

	@Override
	public void robotInit() {
		xboxDrive = new XboxController(DRIVE_XBOX_PORT);
		xboxAux = new XboxController(AUX_XBOX_PORT);
		driveTrain = new DriveTrain();
		lift = new CatzLift();
		grabber = new CatzGrabber();

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
