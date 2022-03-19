package com.spartronics4915.frc2022.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.spartronics4915.frc2022.Constants;
import com.spartronics4915.lib.subsystems.SpartronicsSubsystem;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.simulation.SimHooks;

import static com.spartronics4915.frc2022.Constants.Intake.*;


/**
 * Detailed description of Intake.
 */
public class Intake extends SpartronicsSubsystem
{
    // The subsystem's hardware is defined here...
    private CANSparkMax mIntakeMotor;
    private Solenoid mIntakeArm;

    private boolean mToggleState;
    //private DigitalInput switchState;

    /** Creates a new Intake.
     * @param SpartronicsMax */
    public Intake()
    {
        boolean success = true;
        try
        {
            // ...and constructed here.
           mIntakeMotor = new CANSparkMax(kIntakeMotorId,MotorType.kBrushless);
           mIntakeArm = new Solenoid(Constants.kPCMId, PneumaticsModuleType.CTREPCM, kIntakeSolenoidId);
           mToggleState = false;
           //switchState = new DigitalInput(0);

        }
        catch (Exception exception)
        {
            logException("Could not construct hardware: ", exception);
            success = false;
        }
        logInitialized(success);
        mIntakeMotor.setInverted(kIntakeMotorInverted);
        
        mIntakeMotor.setSmartCurrentLimit(kMaxCurrent);
    }

    //Subsystem methods - actions the robot can take - should be placed here.

    public void startIntake(boolean reversed){
        mIntakeArm.set(true);
        mIntakeMotor.set(reversed ? kEjectSpeed : kHarvestSpeed);
        mToggleState = true;
        //logInfo("intake running"); - not sure if we need this could be too much for driver to pay attention to
    }

    public void stopIntake() {
        mIntakeArm.set(false);
        mIntakeMotor.set(0);
        mToggleState = false;
        //logInfo("intake stopped"); - not sure if we need this, same as above
    }

    public void stopIntakeMotor(){
        mIntakeMotor.set(0);
    }

    /*public void showArmState(){
        Boolean arm = mIntakeArm.get();
        logInfo("current arm state:" + arm.toString());
    }*/

    public boolean toggleIntake() {
        mToggleState = !mToggleState;
        return mToggleState;
    }

    public boolean getToggleState() {
        return mToggleState;
    }
    
    /** This method will be called once per scheduler run. */
    @Override
    public void periodic() {
       /* if(switchState.get()){
            mIntakeMotor.set(.5);
        } else {
            mIntakeMotor.set(0);
        }*/
    }

    /** This method will be called once per scheduler run during simulation. */
    @Override
    public void simulationPeriodic() {}

    public void outputTelemetry() {}
}
