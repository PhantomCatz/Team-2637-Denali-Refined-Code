 
/************************************************
 * Timothy Vu
 * 
 * Last Revised: 2/20/18 AL
 * 11/7/18 EL
 * 11/17/2018 ZT,CL,SA
 * 
 * added printout debug data code
 * flipped polarity of forearm and bicep solenoids
 * 
 * Methods: openFlapToggle(), intakeCube(), launchCube(), 
 * deployIntake(), retractIntake()
 * 
 * Functionality:
 ***********************************************/

package mechanisms;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import robot.Robot;

public class CatzGrabber {
	public static WPI_TalonSRX intakeRT;
	public static WPI_TalonSRX intakeLT;

	public static Solenoid intakeForearm;
	public static Solenoid intakeBicep;
	public static boolean bicepDeployed = false; 
	public static boolean forearmOpen = false;

	public static SolenoidState bicepState = SolenoidState.Closed;
	public static SolenoidState forearmState = SolenoidState.Closed;

	final static public int RT_INTAKE_PWM_PORT_NUMBER = 4;
	final static public int LT_INTAKE_PWM_PORT_NUMBER = 1;
	final static public int INTAKE_FOREARM_PCM_PORT_NUMBER = 1;
	final static public int INTAKE_BICEP_PCM_PORT_NUMBER = 0;

	final static public double FUNCTION_EXECUTION_DELAY = 0.1; //Time in between the execution of functions

	final static public double INTAKE_SPEED = 1.0;

	final static public double CUBE_OUTTAKE_WAIT_TIME = 0.5;
	
	public enum SolenoidState {
		Open(true), Closed(false);
		
		private boolean state;
		
		SolenoidState(boolean state){
			this.state = state;
		}
		
		public boolean getState() {
			return state;
		}
	}
	
	/***************************
	 * Constructor
	 ***************************/
	public CatzGrabber() {
		intakeRT = new WPI_TalonSRX(RT_INTAKE_PWM_PORT_NUMBER);
		intakeLT = new WPI_TalonSRX(LT_INTAKE_PWM_PORT_NUMBER);
		intakeForearm = new Solenoid(INTAKE_FOREARM_PCM_PORT_NUMBER);
		intakeBicep = new Solenoid(INTAKE_BICEP_PCM_PORT_NUMBER);

		printOutDebugData("Successfully initialized CatzGrabber");
	}
	
	/****************************************
	 * Method to set intake speed of grabber
	 ****************************************/
	public void setIntakeSpeed(double speed) {
		intakeLT.set(speed);
		intakeRT.set(-speed);
	}

	/*****************************************
	 *Method to close forearm on custom delay
	 *****************************************/
	public void closeForearm(double delay) {
		forearmState = SolenoidState.Closed;
		intakeForearm.set(forearmState.getState());
		printOutDebugData("Grabber forearm set to Closed");
		Timer.delay(delay);
	}
	
	/*******************************************
	 *Method to close forearm on default delay
	 *******************************************/
	public void closeForearm() {
		closeForearm(FUNCTION_EXECUTION_DELAY);
	}
	
	/*****************************************
	 *Method to open forearm on custom delay
	 *****************************************/
	public void openForearm(double delaySec) {
		forearmState = SolenoidState.Open;
		intakeForearm.set(forearmState.getState());
		printOutDebugData("Grabber forearm set to Open");
		Timer.delay(delaySec);
	}

	/*******************************************
	 *Method to close forearm on default delay
	 *******************************************/
	public void openForearm() {
		openForearm(FUNCTION_EXECUTION_DELAY);
	}

	/************************************************
	 *Method to toggle forearm opening and closing
	 ************************************************/
	public void toggleForearm() {
		if (forearmState == SolenoidState.Open) {
			this.closeForearm(FUNCTION_EXECUTION_DELAY);
		} else {
			this.openForearm(FUNCTION_EXECUTION_DELAY);
		}
	}

	/*****************************************
	 *Method to lower grabber on custom delay
	 *****************************************/
	public void deployBicep(double delay) {
		bicepState = SolenoidState.Open;
		intakeBicep.set(true); 
		printOutDebugData("Grabber Bicep set to Deploy");
		Timer.delay(delay);
	}

	/*****************************************
	 *Method to lower grabber on default delay
	 *****************************************/
	public void deployBicep() {
		deployBicep(FUNCTION_EXECUTION_DELAY);
	}

	/*****************************************
	 *Method to raise grabber on custom delay
	 *****************************************/
	public void retractBicep(double delay) {
		bicepState = SolenoidState.Closed;
		intakeBicep.set(false);
		printOutDebugData("Grabber forearm set to Retract");
		Timer.delay(delay);
	}

	/*****************************************
	 *Method to raise grabber on default delay
	 *****************************************/
	public void retractBicep() {
		retractBicep(FUNCTION_EXECUTION_DELAY);
	}
	
	/*************************************************************
	 *Method to close forearm and raise grabber simultaneously
	 *************************************************************/
	public void retractGrabber() {
		this.closeForearm(FUNCTION_EXECUTION_DELAY);
		this.retractBicep(FUNCTION_EXECUTION_DELAY);
	}

	/*************************************************************
	 *Method to open forearm and lower grabber simultaneously
	 *************************************************************/
	public void deployGrabber() {
		this.deployBicep(FUNCTION_EXECUTION_DELAY);
		this.openForearm(FUNCTION_EXECUTION_DELAY);
	}

	/****************************************
	 *Method to launch cube out of forearm
	 ****************************************/
	public void shootCube() {
		this.setIntakeSpeed(-INTAKE_SPEED);
		Timer.delay(CUBE_OUTTAKE_WAIT_TIME);
		this.setIntakeSpeed(0.0);
	}

	/**********************************************
	 *Method to place cube down with custom speed
	 **********************************************/
	public void placeCube(double speed) {
		this.deployBicep(FUNCTION_EXECUTION_DELAY);
		this.setIntakeSpeed(speed);
		Timer.delay(CUBE_OUTTAKE_WAIT_TIME);
		this.retractBicep(FUNCTION_EXECUTION_DELAY);
		this.setIntakeSpeed(0.0);
	}

	/*****************************************************************
	 *Method to intake cube by lowering grabber and activating intake
	 *****************************************************************/
	public void intakeCube() {
		this.deployBicep(FUNCTION_EXECUTION_DELAY);
		this.setIntakeSpeed(INTAKE_SPEED);
		Timer.delay(CUBE_OUTTAKE_WAIT_TIME);
		this.setIntakeSpeed(0.0);
	}

	/********************************
	 *Method printing out debug data
	 ********************************/
	private static void printOutDebugData(String info) {
		if (Robot.debugMode == true) {
			double currentTime = Robot.globalTimer.get();
			System.out.println(currentTime + "  -" + info);
		}
	}
}

