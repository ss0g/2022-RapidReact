package com.spartronics4915.frc2022.commands;

import static com.spartronics4915.frc2022.Constants.Autonomous.*;
import com.spartronics4915.frc2022.Constants;

import com.spartronics4915.frc2022.subsystems.Drive;
import com.spartronics4915.lib.hardware.motors.SpartronicsMotor;
import com.spartronics4915.lib.util.Units;

import org.apache.commons.math3.analysis.function.Constant;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class AutonomousCommands {
    private Drive mDrive;

    public AutonomousCommands(Drive drive) {
        mDrive = drive;
    }

    /**
     * Drives backwards until we leave the tarmac area
    */
    public class AutonomousDrive extends CommandBase {
        public AutonomousDrive() {
            addRequirements(mDrive);
        }
        
        @Override
        public void initialize() {
            mDrive.getLeftMotor().getEncoder().setPosition(0);
            mDrive.getRightMotor().getEncoder().setPosition(0);
            mDrive.tankDrive(-kDriveSpeedPercent, -kDriveSpeedPercent);
        }

        @Override
        public boolean isFinished() {
            return Math.abs(mDrive.getLeftMotor().getEncoder().getPosition() / kDriveGearRatio) >= kDriveDistanceMeters && Math.abs(mDrive.getRightMotor().getEncoder().getPosition()/kDriveGearRatio) >= kDriveDistanceMeters;
        }

        @Override
        public void end(boolean interrupted) {
            mDrive.tankDrive(0, 0);
        }
    }
}