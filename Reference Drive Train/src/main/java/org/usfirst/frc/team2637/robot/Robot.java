package org.usfirst.frc.team2637.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;

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
	public void robotPeriodic() {

	//	DriveTrain.updateSmartDashboard();

		
	}

	
	@Override
	public void autonomousInit() {
		Timer timer = new Timer();
		timer.start();
		double time = timer.get();
		while(time < 10){
			driveTrain.arcadeDrive(1, 0);

		}
		timer.stop();
		timer.reset();
		driveTrain.arcadeDrive(0, 0);

	}
	
	@Override
	public void autonomousPeriodic() {
		
	}

	@Override
	public void teleopPeriodic() {
		//Put this in arcadeDrive parameter if you want trigger method 
		//xbox.getTriggerAxis(Hand.kRight) - xbox.getTriggerAxis(Hand.kLeft), xbox.getX(Hand.kLeft)
		
	//	compressor.setClosedLoopControl(true);
		// fix polarity on motors
		double throttle = -xbox.getY(Hand.kLeft);

		// cubed input to lower sensitivity
		double turn = xbox.getX(Hand.kRight);

		driveTrain.arcadeDrive(throttle, turn);
	

		if(xbox.getBumper(Hand.kRight)) {
			driveTrain.setHighGear();
		}
		if(xbox.getBumper(Hand.kLeft)) {
			driveTrain.setLowGear();
		}
		
/*

		// D-PAD up
		if (xbox.getPOV() == 0)
		{
			driveTrain.setHighGear();
		}
		
		// D-PAD down
		if (xbox.getPOV() == 180)
		{
			
			driveTrain.setLowGear();
		}
*/
	}
	
	
	@Override
	public void testPeriodic() {
		
	}


	@Override
	public void disabledInit() {
		
	}

	@Override
	public void disabledPeriodic() {
		
	}
}
