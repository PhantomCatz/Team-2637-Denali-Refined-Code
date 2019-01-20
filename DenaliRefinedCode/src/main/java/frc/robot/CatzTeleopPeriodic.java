package frc.robot;


import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;

import edu.wpi.first.wpilibj.Timer;






/**********************************************************

 * Author : Andrew Lai 

 * 

 * Revision History :

 * 2/16/2018 AL Revised driver controls to fun with 2 xbox

 * 3-17-18   DD Integrated bottom limit switch into lift controls

 * 

 * Controllers Methods: runGrabbercontrols, runLiftControls, runClimberControls, runTeleopPeriodic

 * 

 * Functionality: activates driver controls in periodic class

 *********************************************************/



public class CatzTeleopPeriodic {

	public static boolean reversed = false;

	private static final double MIN_LIFT_INPUT_POWER = .75;

	private static final int MIN_DELTA_PULSES = 1;

	private static final double LIFTER_DISABLED_TIME = 1.5;

	private static Timer lifterDisabler = new Timer();

	private static double currentLiftValue;

	private static double lastLiftValue = 0;

	private static double deltaLiftValue;

    private static boolean liftDisabled = false;
    
    private static double INTAKE_CUBE = 1.0;

	private static  double SHOOT_CUBE = -1.0;

	

	public static void runTeleopPeriodic() {

		
        runDrivetrain();

		runGrabberControls();

		runLiftControls();

    }
    


    private static void runDrivetrain() {
        Robot.driveTrain.arcadeDrive(Robot.xboxDrive.getY(Hand.kLeft), Robot.xboxDrive.getX(Hand.kRight));

    }



	private static void runGrabberControls() {

		Robot.grabber.setIntakeSpeed(Robot.xboxDrive.getTriggerAxis(Hand.kRight)

				- Robot.xboxDrive.getTriggerAxis(Hand.kLeft) - Robot.xboxAux.getTriggerAxis(Hand.kLeft));

	



		// bicep controls

		if (Robot.grabber.forearmOpen == false) {

			if (Robot.xboxAux.getBumper(Hand.kRight))

			{

				Robot.grabber.deployBicep(0.0);

			} 

			else if (Robot.xboxAux.getBumper(Hand.kLeft)) 

			{

				Robot.grabber.retractBicep(0.0);

			}

		}

		

		// opens forearm

		if(Robot.xboxAux.getXButton())

		{

			Robot.grabber.toggleForearm();;

		}

		

		// set forearm and bicep to portal pickup mode

		if (Robot.xboxAux.getAButton()) {

			Robot.grabber.openForearm(0.0);

			Robot.grabber.retractBicep(0.0);

		}



		// Driver controls for forearms

		if (Robot.xboxDrive.getAButton()) {

			Robot.grabber.toggleForearm();

		}

		

		if(Robot.xboxAux.getBButton())

		{

			Robot.grabber.placeCube(-0.8);

		}

		

		if(Robot.xboxDrive.getBButton())

		{

			Robot.grabber.placeCube(SHOOT_CUBE);

		}



		// sets forearm and bicep to cube pickup mode

		if (Robot.xboxDrive.getXButton()) {

			Robot.grabber.openForearm(0.0);

			Robot.grabber.deployBicep(0.0);

		}



		// rumble if biceps are down

		if (CatzGrabber.bicepDeployed == true) {

			Robot.xboxAux.setRumble(RumbleType.kLeftRumble, .5);

			Robot.xboxAux.setRumble(RumbleType.kRightRumble, .5);

		} else {

			Robot.xboxAux.setRumble(RumbleType.kLeftRumble, 0);

			Robot.xboxAux.setRumble(RumbleType.kRightRumble, 0);

		}

		

	}



	// Lifter controls

	private static void runLiftControls() {

		// currentLiftValue = Robot.liftEncoder.get();

		double power = Robot.xboxAux.getY(Hand.kLeft);

		/**

		 * if lifter limit is activated, only have the ability to move lifter down Aux

		 * controller X button overrides the limit switch

		 */



		if (Robot.xboxAux.getYButton() == true) 

		{

            Robot.lift.RTLift.set(power);
			Robot.lift.LTLift.set(power);

		} 

		else 

		{

			if (Robot.lift.lifterLimitTop.get() == true) 

			{

				if(power <= .0)

				{

                    Robot.lift.RTLift.set(power);
                    Robot.lift.LTLift.set(power);

				} else {

					Robot.lift.stopLift();

				}

			}

			else if(Robot.lift.lifterLimitBot.get() == true)

			{

				if(power >= -.0)

				{
                    Robot.lift.RTLift.set(power);
                    Robot.lift.LTLift.set(power);

				} else {

					Robot.lift.stopLift();

				}

			}

			else 

			{

                Robot.lift.RTLift.set(power);
                Robot.lift.LTLift.set(power);

			}

		}

	}

	

	




	private void noStallLift() {

		double power = 0;

		if (Robot.xboxAux.getXButton() == true) { // override limit switch (whc)

            Robot.lift.RTLift.set(power);
            Robot.lift.LTLift.set(power);

		} else {

			if (liftDisabled) {

				if (lifterDisabler.get() > LIFTER_DISABLED_TIME) {

					liftDisabled = false;

					lifterDisabler.reset();

				}

			} else {

				if (Robot.lift.lifterLimitTop.get() == false) {

					 Robot.lift.RTLift.set(power);
                     Robot.lift.LTLift.set(power);

				} else {

					if (power <= 0.3) 

					{ 

						// limit switch is engaged; lifter can only go down. (whc)

						// Robot.lifterL.set(power);

                        Robot.lift.RTLift.set(power);
                        Robot.lift.LTLift.set(power);
					}

				}

			}

		}

		deltaLiftValue = currentLiftValue - lastLiftValue;



		if (liftDisabled == false && Math.abs(power) > MIN_LIFT_INPUT_POWER && deltaLiftValue < MIN_DELTA_PULSES) {

			liftDisabled = true;

			lifterDisabler.reset();

			lifterDisabler.start();

            Robot.lift.RTLift.set(power);
            Robot.lift.LTLift.set(power);

			

		}



		lastLiftValue = currentLiftValue;



	}

}