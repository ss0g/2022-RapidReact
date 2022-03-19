package com.spartronics4915.frc2022.commands;

import static com.spartronics4915.frc2022.Constants.Drive.*;

import static com.spartronics4915.frc2022.Constants.OIConstants;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.spartronics4915.frc2022.subsystems.Drive;
import com.spartronics4915.lib.util.Logger;

import org.apache.commons.math3.analysis.function.Constant;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class DriveCommands
{
    private final Drive mDrive;
    private final Joystick mJoystick;
    private boolean mInvertJoystickY;
    private boolean mSlowMode;
    private final Joystick mArcadeController;

    public DriveCommands(Drive drive, Joystick joystick, Joystick arcadeController)
    {
        mDrive = drive;
        mJoystick = joystick;
        mInvertJoystickY = true; // convention is to invert joystick y
        mSlowMode = false;
        mArcadeController = arcadeController;
        
        mDrive.setDefaultCommand(new TeleOpCommand());
    }

    public class TeleOpCommand extends CommandBase
    {
        public TeleOpCommand()
        {
            addRequirements(mDrive);
        }

        @Override
        public void initialize()
        {

        }

        @Override
        public void execute()
        {
            // get -1 to 1 values for X and Y of the joystick
            double x = mJoystick.getX();
            double y = mJoystick.getY();
            Logger.info(x + ", " + y);

            if (mInvertJoystickY) y = -y;

            y = Math.signum(y) * Math.pow(Math.abs(y), kLinearResponseCurveExponent); // apply response curve
            
            if (mJoystick.getRawButton(OIConstants.kSlowModeButton)) {
                x *= kSlowModeMultiplier;
                y *= kSlowModeMultiplier;
            }

            mDrive.arcadeDrive(applyDeadzone(y), applyDeadzone(x));
        }

        private double applyDeadzone(double axis)
        {
            return Math.abs(axis) < kJoystickDeadzoneSize ? 0 : axis;
        }
    }

    public class ToggleInverted extends CommandBase
    {
        public ToggleInverted() {
            addRequirements(mDrive);
        }

        @Override
        public void initialize()
        {
            mInvertJoystickY = !mInvertJoystickY;
        }

        @Override
        public boolean isFinished()
        {
            return true;
        }
    }
}
