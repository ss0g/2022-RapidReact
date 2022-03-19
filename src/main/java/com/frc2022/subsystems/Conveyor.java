package com.spartronics4915.frc2022.subsystems;

import com.spartronics4915.lib.hardware.motors.SpartronicsSRX;
import com.spartronics4915.lib.subsystems.SpartronicsSubsystem;

import edu.wpi.first.wpilibj.DigitalInput;

import static com.spartronics4915.frc2022.Constants.Conveyor.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 * Detailed description of Conveyer.
 */
public class Conveyor extends SpartronicsSubsystem {
    // The subsystem's hardware is defined here...
    private TalonSRX mBottomMotor;
    private TalonSRX mTopMotor;

    private DigitalInput mTopBeamBreaker;
    private DigitalInput mBottomBeamBreaker;

    public enum State {
        OFF, FILL, REVERSE_BOTH, REVERSE_BOTTOM, SHOOT_FROM_BOTTOM, SHOOT_FROM_TOP
    };

    private State mState = State.OFF;

    /** Creates a new Conveyor. */
    public Conveyor() {
        boolean success = true;

        try {
            // ...and constructed here.
            mTopMotor = new TalonSRX(kTopMotorId);
            mBottomMotor = new TalonSRX(kBottomMotorId);

            mTopBeamBreaker = new DigitalInput(kTopBeamBreakerId);
            mBottomBeamBreaker = new DigitalInput(kBottomBeamBreakerId);
        } catch (Exception exception) {
            logException("Could not construct hardware: ", exception);
            success = false;
        }
        logInitialized(success);

        mTopMotor.setInverted(false);
        mBottomMotor.setInverted(false);

        mTopMotor.configPeakCurrentLimit(kMaxCurrent);
        mBottomMotor.configPeakCurrentLimit(kMaxCurrent);
    }

    // Subsystem methods - actions the robot can take - should be placed here.

    public boolean hasTopBall() {
        return !mTopBeamBreaker.get();
    }
    
    public boolean hasBottomBall() {
        return !mBottomBeamBreaker.get();
    }

    private void setMotors(double bottomFactor, double topFactor) {
        mTopMotor.set(ControlMode.PercentOutput, kMotorSpeed * topFactor);
        mBottomMotor.set(ControlMode.PercentOutput, kMotorSpeed * bottomFactor);
    }

    public void setState(State state) {
        mState = state;
    }

    public State getState() {
        return mState;
    }

    public boolean isFull() {
        return hasTopBall() && hasBottomBall();
    }

    public boolean isActive() {
        return mState != State.OFF;
    }

    /** This method will be called once per scheduler run. */
    @Override
    public void periodic() {
        switch (mState) {
            case OFF:
                setMotors(0, 0);
                break;
            case FILL:
                if (!hasTopBall()) setMotors(1, kSlowFactor);
                else if (!hasBottomBall()) setMotors(1, 0);
                else setState(State.OFF);
                break;
            case REVERSE_BOTTOM:
                setMotors(-1, 0);
                break;
            case REVERSE_BOTH:
                setMotors(-1, -1);
                break;
            case SHOOT_FROM_BOTTOM:
                setMotors(1, 1);
                break;
            case SHOOT_FROM_TOP:
                setMotors(0, 1);
                break;
        }
    }

    /** This method will be called once per scheduler run during simulation. */
    @Override
    public void simulationPeriodic() {
    }

    public void outputTelemetry() {
    }
}
