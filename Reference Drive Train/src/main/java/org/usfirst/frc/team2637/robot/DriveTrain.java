package org.usfirst.frc.team2637.robot;


import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.text.DecimalFormat;

public class DriveTrain {
	
	//motor controllers

	
	private WPI_TalonSRX  drvTrainMtrCtrlRtFrnt;
	private WPI_VictorSPX drvTrainMtrCtrlRtMidl;
	private WPI_VictorSPX drvTrainMtrCtrlRtBack;

	private WPI_TalonSRX  drvTrainMtrCtrlLtFrnt;
	private WPI_VictorSPX drvTrainMtrCtrlLtMidl;
	private WPI_VictorSPX drvTrainMtrCtrlLtBack;



	private final int DRVTRAIN_MTR_CNTLR_ID_RT_FRNT = 5;
	private final int DRVTRAIN_MTR_CNTLR_ID_RT_MIDL = 2;
	private final int DRVTRAIN_MTR_CNTLR_ID_RT_BACK = 3;
	
	private final int DRVTRAIN_MTR_CNTLR_ID_LT_FRNT = 6;
	private final int DRVTRAIN_MTR_CNTLR_ID_LT_MIDL = 4;
	private final int DRVTRAIN_MTR_CNTLR_ID_LT_BACK = 1;
	
	
	private DifferentialDrive drvTrainDifferentialDrive;

	//solenoids

	
	private DoubleSolenoid drvTrainGearBoxShifter;

	private final int DRVTRAIN_RT_GEARBOX_SHIFTER_SOL_PORT = 0;	//on the PCM
	private final int DRVTRAIN_LT_GEARBOX_SHIFTER_SOL_PORT = 1;

	


/*	//encoders

	private Encoder drvTrainRtEncoder;
	private Encoder drvTrainLtEncoder;
	
	private final int DRVTRAIN_RT_ENCODER_A_DIO_PORT = 0;
	private final int DRVTRAIN_RT_ENCODER_B_DIO_PORT = 0;
	private final int DRVTRAIN_LT_ENCODER_A_DIO_PORT = 0;
	private final int DRVTRAIN_LT_ENCODER_B_DIO_PORT = 0;
	
	private final double DRVTRAIN_ENCODER_PULSES_PER_REV = 256.0;
	private final double DRVTRAIN_WHEEL_DIAMETER = 6.0;
	private final double DRVTRAIN_WHEEL_CIRCUMFRENCE = DRVTRAIN_WHEEL_DIAMETER * Math.PI;

	private final double DRVTRAIN_ENCODER_INCHES_PER_PULSE = DRVTRAIN_WHEEL_CIRCUMFRENCE / DRVTRAIN_ENCODER_PULSES_PER_REV;
*/
	public DriveTrain() {
		//initialize motor controllers
		drvTrainMtrCtrlRtFrnt = new WPI_TalonSRX (DRVTRAIN_MTR_CNTLR_ID_RT_FRNT);
		drvTrainMtrCtrlRtMidl = new WPI_VictorSPX(DRVTRAIN_MTR_CNTLR_ID_RT_MIDL);
		drvTrainMtrCtrlRtBack = new WPI_VictorSPX(DRVTRAIN_MTR_CNTLR_ID_RT_BACK);

		drvTrainMtrCtrlLtFrnt = new WPI_TalonSRX (DRVTRAIN_MTR_CNTLR_ID_LT_FRNT);		
		drvTrainMtrCtrlLtMidl = new WPI_VictorSPX(DRVTRAIN_MTR_CNTLR_ID_LT_MIDL);
		drvTrainMtrCtrlLtBack = new WPI_VictorSPX(DRVTRAIN_MTR_CNTLR_ID_LT_BACK);

		drvTrainMtrCtrlRtMidl.follow(drvTrainMtrCtrlRtFrnt);
		drvTrainMtrCtrlRtBack.follow(drvTrainMtrCtrlRtFrnt);

		drvTrainMtrCtrlLtMidl.follow(drvTrainMtrCtrlLtFrnt);
		drvTrainMtrCtrlLtBack.follow(drvTrainMtrCtrlLtFrnt);
		
		drvTrainDifferentialDrive = new DifferentialDrive(drvTrainMtrCtrlRtFrnt, drvTrainMtrCtrlLtFrnt);
		
		//initialize solenoids
		
		drvTrainGearBoxShifter = new DoubleSolenoid(DRVTRAIN_RT_GEARBOX_SHIFTER_SOL_PORT,
													DRVTRAIN_LT_GEARBOX_SHIFTER_SOL_PORT);
		 
												
		
		//initialize encoders

	/*	drvTrainWheelEncoderR = new Encoder(DRVTRAIN_WHEEL_ENCODER_R_DIOA, DRVTRAIN_WHEEL_ENCODER_R_DIOB,
				false, Encoder.EncodingType.k4X);

		drvTrainWheelEncoderL = new Encoder(DRVTRAIN_WHEEL_ENCODER_L_DIOA, DRVTRAIN_WHEEL_ENCODER_L_DIOB,
				false, Encoder.EncodingType.k4X);
		
		drvTrainWheelEncoderR.setDistancePerPulse(DRVTRAIN_ENCODER_INCHES_PER_PULSE);
		drvTrainWheelEncoderR.setReverseDirection(true);

		drvTrainWheelEncoderL.setDistancePerPulse(DRVTRAIN_ENCODER_INCHES_PER_PULSE);
		*/
	}

	public void setHighGear() {

		
		drvTrainGearBoxShifter.set(Value.kForward);
	}

	public void setLowGear() {
	
	
		drvTrainGearBoxShifter.set(Value.kReverse);
	}
	
/*	public Encoder getDrvTrainWheelEncoderR() {
		return this.drvTrainWheelEncoderR;
	}
	
	public Encoder getDrvTrainWheelEncoderL() {
		return this.drvTrainWheelEncoderL;
	}
	*/
	public void arcadeDrive(double xSpeed, double zRotation) {
		
		drvTrainDifferentialDrive.arcadeDrive(xSpeed, zRotation);
		//drvTrainDifferentialDrive.setSafetyEnabled(false);
	}
	

}

