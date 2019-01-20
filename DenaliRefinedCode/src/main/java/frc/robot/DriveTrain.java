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
	
	//solenoids
/*
	private Solenoid drvTrainGearBoxShifterR;
	private Solenoid drvTrainGearBoxShifterL;

	private final int DRVTRAIN_GEARBOX_SHIFTER_PORT_RIGHT = 0;
	private final int DRVTRAIN_GEARBOX_SHIFTER_PORT_LEFT = 0;

	private final boolean DRVTRAIN_GEAR_HIGH = true;
	private final boolean DRVTRAIN_GEAR_LOW = false;
	
	//encoders

	private Encoder drvTrainWheelEncoderR;
	private Encoder drvTrainWheelEncoderL;
	
	private final int DRVTRAIN_WHEEL_ENCODER_R_DIOA = 0;
	private final int DRVTRAIN_WHEEL_ENCODER_R_DIOB = 0;
	private final int DRVTRAIN_WHEEL_ENCODER_L_DIOA = 0;
	private final int DRVTRAIN_WHEEL_ENCODER_L_DIOB = 0;
	
	private final double DRVTRAIN_ENCODER_PULSES_PER_REV = 256.0;
	private final double DRVTRAIN_WHEEL_DIAMETER = 6.0;
	private final double DRVTRAIN_WHEEL_CIRCUMFRENCE = DRVTRAIN_WHEEL_DIAMETER * Math.PI;

	private final double DRVTRAIN_ENCODER_INCHES_PER_PULSE = DRVTRAIN_WHEEL_CIRCUMFRENCE / DRVTRAIN_ENCODER_PULSES_PER_REV;
*/
	public DriveTrain() {
		//initialize motor controllers
		drvTrainMtrCtrlRightBack = new WPI_TalonSRX(DRVTRAIN_MTR_ID_RIGHT_BACK);
		drvTrainMtrCtrlLeftBack = new WPI_TalonSRX(DRVTRAIN_MTR_ID_LEFT_BACK);
		drvTrainMtrCtrlRightFront = new WPI_TalonSRX(DRVTRAIN_MTR_ID_RIGHT_FRONT);
		drvTrainMtrCtrlLeftFront = new WPI_TalonSRX(DRVTRAIN_MTR_ID_LEFT_FRONT);

		drvTrainDifferentialDrive = new DifferentialDrive(drvTrainMtrCtrlLeftBack, drvTrainMtrCtrlRightBack);
		
		//initialize solenoids
		/*
		drvTrainGearBoxShifterR = new Solenoid(DRVTRAIN_GEARBOX_SHIFTER_PORT_RIGHT);
		drvTrainGearBoxShifterL = new Solenoid(DRVTRAIN_GEARBOX_SHIFTER_PORT_LEFT);
		
		//initialize encoders

		drvTrainWheelEncoderR = new Encoder(DRVTRAIN_WHEEL_ENCODER_R_DIOA, DRVTRAIN_WHEEL_ENCODER_R_DIOB,
				false, Encoder.EncodingType.k4X);

		drvTrainWheelEncoderL = new Encoder(DRVTRAIN_WHEEL_ENCODER_L_DIOA, DRVTRAIN_WHEEL_ENCODER_L_DIOB,
				false, Encoder.EncodingType.k4X);
		
		drvTrainWheelEncoderR.setDistancePerPulse(DRVTRAIN_ENCODER_INCHES_PER_PULSE);
		drvTrainWheelEncoderR.setReverseDirection(true);

		drvTrainWheelEncoderL.setDistancePerPulse(DRVTRAIN_ENCODER_INCHES_PER_PULSE);
		*/
	}
/*
	public void setHighGear() {
		drvTrainGearBoxShifterR.set(DRVTRAIN_GEAR_HIGH);
		drvTrainGearBoxShifterL.set(DRVTRAIN_GEAR_HIGH);
	}

	public void setLowGear() {
		drvTrainGearBoxShifterR.set(DRVTRAIN_GEAR_LOW);
		drvTrainGearBoxShifterL.set(DRVTRAIN_GEAR_LOW);
	}
	
	public Encoder getDrvTrainWheelEncoderR() {
		return this.drvTrainWheelEncoderR;
	}
	
	public Encoder getDrvTrainWheelEncoderL() {
		return this.drvTrainWheelEncoderL;
	}
	*/
	public void arcadeDrive(double xSpeed, double zRotation) {
		drvTrainDifferentialDrive.arcadeDrive(xSpeed, zRotation);
	}

}
