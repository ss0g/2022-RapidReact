package com.spartronics4915.frc2022;

import com.spartronics4915.lib.hardware.motors.SensorModel;
import com.spartronics4915.lib.hardware.motors.SpartronicsMax;
import com.spartronics4915.lib.hardware.motors.SpartronicsMotor;
import com.spartronics4915.lib.util.TriFunction;
import com.spartronics4915.lib.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static).
 * Do not put anything functional in this class.
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants
{
    public static final class Drive { // TODO: some is a stand in value
        // hardware IDs
        public static final int kPigeonID = 12;
        
        public static final int kRightMotorID = 1;
        public static final int kRightFollowerMotorID = 2;

        public static final int kLeftMotorID = 3;
        public static final int kLeftFollowerMotorID = 4;

        public static final double kWheelDiameter = 0.1524; // in meters
        public static final double kNativeUnitsPerRevolution = 1;

        //Test this -- to correct for angular drift autonomous
        public static final double kLeftFactor = .93;

        // constructors
        public static final TriFunction<Integer, SensorModel, Integer, SpartronicsMotor> kMotorConstructor = SpartronicsMax::makeMotor;

        // output inversion
        public static final boolean kLeftOutputInverted = false;
        public static final boolean kRightOutputInverted = true;
        public static final boolean kLeftFollowerOutputInverted = false;
        public static final boolean kRightFollowerOutputInverted = true;

        // PID
        public static final double kP = 1;
        public static final double kD = 0;

        // current limits
        public static final int kMaxCurrent = 30; // poof alakazam

        // controls
        //TODO: test. 2020 was .5.
        public static final double kSlowModeMultiplier = 0.5;
        public static final double kLinearResponseCurveExponent = 5.0 / 3.0;
        public static final double kJoystickDeadzoneSize = 0.1;
    }

    public static final class Intake {
        public static final int kIntakeMotorId = 7;
        public static final int kIntakeSolenoidId = 0;

        public static final boolean kIntakeMotorInverted = true;

        public static final double kHarvestSpeed = 0.6;
        public static final double kEjectSpeed = -0.6;
        
        public static final double kRetractIntakeDelay = 0.3;

        public static final int kMaxCurrent = 30;
    }

    public static class Conveyor {
    
        public static final int kBottomMotorId = 8;
        public static final int kTopMotorId = 9;

        public static final int kTopBeamBreakerId = 0;
        public static final int kBottomBeamBreakerId = 1;

        public static final double kMotorSpeed = 0.45;
        public static final double kSlowFactor = 0.3333;
        
        public static final double kTopConveyorTime = 2.0;
        public static final double kBottomConveyorTime = 1.0;

        public static final int kStopFrequency = (int)(1 / 0.02);
        public static final int kStopLength = (int)(0.1 / 0.02);
        
        public static final double kStopConveyorsDelay = 1.0;

        public static final int kMaxCurrent = 30;
    }
    public static class Launcher {
        
        public static class Flywheel {
            // SysId gave us 0.18366 for kP. Did not work and thought it was broken. However dividing by 6, the encoder
            //  ratio which we thought it used gave us this. Very close to ATHENA's value (.03) which works because it
            //  is the same setup.
            public static final int kId = 5;
            public static final double kP = 0.03061;
            public static final double kD = 0;

            public static final boolean kInverted = false;
            
            public static final double kRPS = 29.57;
            public static final double kFarRPS = 10;

            public static final int kMaxCurrent = 40;
        }

        public static class SpinMotor {
            public static final int kId = 6;
            
            public static final boolean kInverted = false;
            public static final double kSpeed = 0.1;

            //Does not use PID currently. Maybe should.
            public static final double kP = 0;
            public static final double kD = 0;
            
            public static final double kSpeedFactor = .5;

            public static final int kMaxCurrent = 40;
        }
        // public static class Spin {
        //     public static final double kP = 0.03;
        //     public static final double kS = 0.0286; // 0.0654;
        //     public static final double kV = 7.86; // 7.18;
        //     public static final double kA = 5.16;
        // }
    }
    public static final class Climber
    {
        public static final int kClimberMotorId = 10;
        public static final double kClimberMotorSpeed = 1.0;
        public static final boolean kMotorIsInverted = false;

        public static final int kClimberSolenoidId = 1;
        public static final boolean kSolenoidIsInverted = false;

        /**
         * Distance we can rotate the motor from being down before breaking it. Found through testing and logging.
         */
        public static final double kMaxRotations = 7.388;

        /**
         * Climber will be put down before every match so that's where 0 is.
         */
        public static final double kMinRotations = 0;

        /**
         * Takes 12 rotations of climber to rotate winch.
         */
        public static final double kClimberGearRatio = 12.0;
        /**
         * For Climber encoder -- divide getIntegratedSensorPosition() by this.
         */
        public static final int kNativeUnitsPerRevolution = 2048;
        /**
         * The delay in seconds between disengaging the ratchet and starting the motor, or stopping the motor and 
         * engaging the ratchet.
         */
        public static final double kDelay = 0.1; // adjust 1st number for ms

        public static final int kMaxCurrent = 40;
    }

    public static final class OIConstants {
        /**
         * Fight stick for operator
         */
        public static final int kArcadeStickPort = 1;
        /**
         * Joystick for driver
         */
        public static final int kJoystickPort = 0;
        
        public static final int kSlowModeButton = 1;

        public static final int kIntakeToggleButton = 2;

        public static final int kConveyorReverseBothButton = 3;
        public static final int kConveyorReverseBottomButton = 4;
        public static final int kConveyorReverseTopButton = 5;

        public static final int kLauncherShootButton = 6;
        public static final int kLauncherToggleButton = 8;
        public static final int kLauncherShootFarButton = 7;
        
        public static final int kClimberExtendButton = 9;
        public static final int kClimberRetractButton = 10;

    }
    public static final class Autonomous {
        /**
         * Number from Jack to move the robot past the line after shooting
         */
        public static final double kDriveDistanceMeters = Units.feetToMeters(7.5);
        //TODO: test
        /**
         * Speed the robot moves straight backwards during autonomous
         */
        public static final double kDriveSpeedPercent = .3;

        public static final double kDriveGearRatio = 10.57; 

        /**
         * Delay in seconds between starting Auto and shooting to let the wheel reach its speed
         */
        public static final int kShootDelay = 1;

        //Not used for first competition autonomous.
        /*public static final class Trajectory {

            public static final double kMaxVelocity = 2;
            public static final double kMaxAcceleration = .05;
            
            //Feedforward constants (will get from SysId)
            public static final double kS = 0;
            public static final double kV = 0;
            public static final double kA = 0;

            //Robot size (meters)
            public static final double kTrackWidth = 1; 
        }*/
    }

    public static final int kPCMId = 11;
}
