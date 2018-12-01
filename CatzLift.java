package mechanisms;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;
import robot.CatzConstants;
import robot.Robot;

/*
 *  Author : Derek Duenas
 *  
 *  Revision History : 
 *  2-19-18	Added debug data printouts
 *  3-16-18 Removed lift to scale height 2 method
 *  3-17-18 Using the bottom limit switch instead of encoder for the lift to go down all the way
 *  4-5-18  Changed lift method to go to different heights
 *  
 *  Methods : lift
 *  Functionality : Move the lift up and down
 */

public class CatzLift {
	
	/******************************************************************************************************************
	 * Constant Values
	 ******************************************************************************************************************/
	
	final private static double LIFT_COUNTS_PER_INCH          =  505026.0 / 80.0;    //527221.0/80.0;  // 674898.0 / 60.0 - wrong counts per inch value
	final private static double LIFT_SCALE_HEIGHT             = 68.0 * LIFT_COUNTS_PER_INCH;
	final private static double LIFT_SWITCH_HEIGHT            = 17 * LIFT_COUNTS_PER_INCH; //TODO test
	final private static double LIFTER_ERROR_THRESHOLD_PULSES = LIFT_COUNTS_PER_INCH / 2.0;
	final private static double LIFT_SPEED = 1.0;

	/******************************************************************************************************************
	 * State of the Thread
	 ******************************************************************************************************************/
	
	static boolean threadComplete;
	public static boolean liftThreadRunning;

	/******************************************************************************************************************
	 * Timer
	 ******************************************************************************************************************/
	
	private static Timer timeout = new Timer();
	
	/******************************************************************************************************************
	 * Limit Switch
	 ******************************************************************************************************************/
	
	public static DigitalInput lifterLimitTop;
	public static DigitalInput lifterLimitBot;
	
	/******************************************************************************************************************
	 * Encoder
	 ******************************************************************************************************************/
	
	public static Encoder liftEncoder;
	
	/******************************************************************************************************************
	 * Spark Assignment/Placement
	 ******************************************************************************************************************/
	public static Spark lifterRTRT;
	public static Spark lifterRTLT;
	public static Spark lifterLTRT;
	public static Spark lifterLTLT;

	/******************************************************************************************************************
	 * Controller Grouping
	 ******************************************************************************************************************/
	
	public static SpeedControllerGroup LTLift;
	public static SpeedControllerGroup RTLift;

	/******************************************************************************************************************
	 * 
	 * Method
	 * Gives out statistics of robot
	 * 
	 * 
	 ******************************************************************************************************************/
	
	public CatzLift() 
	{

		CatzLift.lifterLimitTop = new DigitalInput(CatzConstants.TOP_LIFT_LIMIT_DIO);
		CatzLift.lifterLimitBot = new DigitalInput(CatzConstants.BOT_LIFT_LIMIT_DIO);
		
		CatzLift.lifterRTRT = new Spark(CatzConstants.RIGHT_RIGHT_LIFTER_PWM);
		CatzLift.lifterRTLT = new Spark(CatzConstants.RIGHT_LEFT_LIFTER_PWM);
		CatzLift.lifterLTRT = new Spark(CatzConstants.LEFT_RIGHT_LIFTER_PWM);
		CatzLift.lifterLTLT = new Spark(CatzConstants.LEFT_LEFT_LIFTER_PWM);

		threadComplete    = false;
		liftThreadRunning = false;
		printOutDebugData("Successfully initialized CatzLift");
	
		RTLift = new SpeedControllerGroup(lifterRTRT, lifterRTLT);
		LTLift = new SpeedControllerGroup(lifterLTRT, lifterLTLT);
		LTLift.setInverted(true);
	}
	

	/**
	 * liftingTo____Height: The three methods below will move the lifter to the
	 * desired height in parallel with other code. For example, you can run one of
	 * these lift loops and drive/turn at the same time by using threading.
	 */
	
	//RENAME listToScaleHeight() to liftToHeight() AND PASS IN HEIGHT IN INCHES
	//THEN GET RID OF SWITCH HEIGHT AND USE COMMON FUNCTION SO WE DON'T HAVE TO MAINTAIN TWO 
	//VERSIONS OF THE SAME CODE


	/******************************************************************************************************************
	 * 
	 * Method
	 * Measures the amount of counts the robot's lift takes to reach a certain heights.
	 * 
	 * 
	 ******************************************************************************************************************/
	public void liftToHeight(double heightInches) {
		Thread t = new Thread(() -> {
			double error = 0;
			int count = 0;
			double counts = heightInches * LIFT_COUNTS_PER_INCH;
			double targetCount = liftEncoder.get() + counts;
			
			
			boolean done             = false;
			boolean limitSwitchState = false;
			
			while (!Thread.interrupted()) {

			printOutDebugData("Lifter scale thread beginning");
			timeout.reset();
			timeout.start();

			while(liftThreadRunning == true) {
				
					Timer.delay(0.005);
					
				}

				liftThreadRunning = true;
				threadComplete    = false;
				double startingCount = liftEncoder.get();
				targetCount = startingCount + counts;
				
				this.liftUp();
				
				/******************************************************************************************************************
				 * 
				 * Loop
				 * This scans for data and completes the loop and moves onto the remaining code in two conditions - 
				 * when the threshold of counts is more than the current incompleted amount of counts and when the limit switch is reached to the limit.
				 * 
				 * 
				 ******************************************************************************************************************/
				
				while (done == false && timeout.get() < 4.2) //Get rid of 4.2, magic number
				{
					count = liftEncoder.get();
					error = Math.abs(targetCount - count);

					if(error < LIFTER_ERROR_THRESHOLD_PULSES) 
					{ 
						done = true;
					}
					
					limitSwitchState = lifterLimitTop.get();
					if (limitSwitchState == true)
					{
						done = true;
						System.out.println("limitTripped");
					}	
				}
				this.stopLift();
				liftThreadRunning = false;
				threadComplete    = true;

				printOutDebugData("Lift to scale height thread complete");
				System.out.println(timeout.get() + ": " + count + ", " + error + ", " + startingCount);

				timeout.stop();
				timeout.reset();

				Thread.currentThread().interrupt();
			}
		});
		t.start();  //what does t mean??? be more descriptive!
	}

	/******************************************************************************************************************
	 * 
	 * Method
	 * Initiates a thread.-
	 * 
	 * 
	 ******************************************************************************************************************/
	
	public void dropToGroundHeight() {

		Thread t = new Thread(() -> {
			int count = 0;

			boolean done =             false;
			boolean limitSwitchState = false;


			while (!Thread.interrupted()) {
				int limitSwitchStateCounter = 0;
				printOutDebugData("dropToGroundHeight thread beginning");
				timeout.reset();
				timeout.start();
				limitSwitchState = false;
				
				while(liftThreadRunning == true) {
					//wait for previous thread to finish
					Timer.delay(0.005);
				}

				liftThreadRunning = true;	
				threadComplete    = false;
				
				this.liftDown();
				System.out.println("Limit Switch: " + limitSwitchState);
				while (!done && timeout.get() < 4.0) {	//wait for bottom limit switch to read true
					// previous code: "done == false" avoided double negative. -Kazuma
					count            = liftEncoder.get();
					limitSwitchState = lifterLimitBot.get();
					
					if(limitSwitchState)
					{
						limitSwitchStateCounter++;
					}
					else
					{
						limitSwitchStateCounter = 0;
					}
					
					if(limitSwitchStateCounter > 5)
					{
						done = true;
					}
					System.out.println("Limit Switch: " + limitSwitchState);
					Timer.delay(0.010);
				}
				this.stopLift();

				liftThreadRunning = false;
				threadComplete    = true;

				printOutDebugData("Drop to ground height thread complete");
				System.out.println(timeout.get() + ": " + count + ", " + limitSwitchState);

				timeout.stop();
				timeout.reset();

				Thread.currentThread().interrupt();
			}
		});
		t.start(); //be a little more specific on naming this thread
	}
	
	
	

	/******************************************************************************************************************
	 * Initiates code for lift to move upwards
	 ******************************************************************************************************************/
	public void liftUp() {
		RTLift.set(LIFT_SPEED);
		LTLift.set(-LIFT_SPEED);
	}
	/******************************************************************************************************************
	 * Initiates code for lift to move downwards
	 ******************************************************************************************************************/
	public void liftDown() {
		RTLift.set(-1.0);
		LTLift.set(-1.0);
	}
	/******************************************************************************************************************
	 * Stops lift movement
	 ******************************************************************************************************************/
	public void stopLift() {
		RTLift.set(0);
		LTLift.set(0);
	}
	/******************************************************************************************************************
	 * After all the requirements are met the robot is ready to lift.
	 ******************************************************************************************************************/
	public void setReadyToLift(boolean ready) {
		threadComplete = ready;
	}
	/******************************************************************************************************************
	 * Debugging
	 ******************************************************************************************************************/
	private static void printOutDebugData(String info) {
		if (Robot.debugMode == true) {
			double currentTime = Robot.globalTimer.get();
			System.out.println(currentTime + "  -" + info);
		}
	}
	
	/******************************************************************************************************************
	 * Raise / Lower lift based on input
	 ******************************************************************************************************************/
	

	public void setLiftMotors(double percentOutput) {
		
		if(lifterLimitTop.get() == true || lifterLimitBot.get() == true)
		{
			percentOutput = 0;
		}
			
		
		RTLift.set(percentOutput);
		LTLift.set(percentOutput);
	}
}


