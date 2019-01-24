package frc.robot;


import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveTrain {
	
	//motor controllers
	
	private WPI_TalonSRX drvTrainMtrCtrlRightBack;
	private WPI_TalonSRX drvTrainMtrCtrlLeftBack;
	private WPI_TalonSRX drvTrainMtrCtrlRightFront;
	private WPI_TalonSRX drvTrainMtrCtrlLeftFront;

	private final int DRVTRAIN_MTR_ID_RIGHT_BACK = 6;
	private final int DRVTRAIN_MTR_ID_LEFT_BACK = 3;
	private final int DRVTRAIN_MTR_ID_RIGHT_FRONT = 5;
	private final int DRVTRAIN_MTR_ID_LEFT_FRONT = 2;
	
	private DifferentialDrive drvTrainDifferentialDrive;

	
	public DriveTrain() {
		//initialize motor controllers
		drvTrainMtrCtrlRightBack = new WPI_TalonSRX(DRVTRAIN_MTR_ID_RIGHT_BACK);
		drvTrainMtrCtrlLeftBack = new WPI_TalonSRX(DRVTRAIN_MTR_ID_LEFT_BACK);
		drvTrainMtrCtrlRightFront = new WPI_TalonSRX(DRVTRAIN_MTR_ID_RIGHT_FRONT);
		drvTrainMtrCtrlLeftFront = new WPI_TalonSRX(DRVTRAIN_MTR_ID_LEFT_FRONT);

		drvTrainDifferentialDrive = new DifferentialDrive(drvTrainMtrCtrlLeftBack, drvTrainMtrCtrlRightBack);
	}
		
	
	public void arcadeDrive(double xSpeed, double zRotation) {
		drvTrainDifferentialDrive.arcadeDrive(xSpeed, zRotation);
	}

}
