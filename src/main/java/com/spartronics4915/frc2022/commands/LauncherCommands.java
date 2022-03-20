package com.spartronics4915.frc2022.commands;

import static com.spartronics4915.frc2022.Constants.Launcher.*;

import com.spartronics4915.frc2022.subsystems.Conveyor;
import com.spartronics4915.frc2022.subsystems.Launcher;

import edu.wpi.first.wpilibj2.command.CommandBase;

import edu.wpi.first.wpilibj.Joystick;

public class LauncherCommands {
    private final Launcher mLauncher;
    private final Conveyor mConveyor;

    private boolean mPaused;

    public LauncherCommands(Launcher subsystem, Conveyor conveyor, Joystick mArcadecontroller) {
        mLauncher = subsystem;
        mConveyor = conveyor;
        mLauncher.setDefaultCommand(new RunLauncher());
        mPaused = false;
    }

    /**
     * DefaultCommand which makes sure the motor is set to mLauncher.getTargetRPS()
     */
    public class RunLauncher extends CommandBase {
        private double mSpeed = 0.0;
        private boolean conveyorJustFilled = false;

        public RunLauncher() {
            addRequirements(mLauncher);
        }

        @Override
        public void execute() {
            double targetRPS = mLauncher.getToggled() ? mLauncher.getTargetRPS() : 0.0;
            if (mSpeed != targetRPS)
                mLauncher.setMotorSpeed(mSpeed = targetRPS);

            if (mConveyor.isFull() && !conveyorJustFilled && !mLauncher.getToggled())
                mLauncher.toggleLauncher();

            conveyorJustFilled = mConveyor.isFull();
        }

        @Override
        public boolean isFinished() {
            return false;
        }
    }

    public class TogglePaused extends CommandBase {
        public TogglePaused() {
            addRequirements(mLauncher);
        }

        // Called when the command is initially scheduled.
        @Override
        public void initialize() {
            mPaused = !mPaused;
            mLauncher.setToggled(!mPaused);
        }

        // Called every time the scheduler runs while the command is scheduled.
        @Override
        public void execute() {

        }

        // Returns true when the command should end.
        @Override
        public boolean isFinished() {
            return true;
        }
    }

    /**
     * Turns the launcher on or off
     */
    public class ToggleLauncher extends CommandBase {
        public ToggleLauncher() {
            addRequirements(mLauncher);
        }

        // Called when the command is initially scheduled.
        @Override
        public void initialize() {
            mLauncher.toggleLauncher();
        }

        // Called every time the scheduler runs while the command is scheduled.
        @Override
        public void execute() {

        }

        // Returns true when the command should end.
        @Override
        public boolean isFinished() {
            return true;
        }

        // Called once the command ends or is interrupted.
        @Override
        public void end(boolean interrupted) {
        }
    }

    /**
     * Sets the launcher to far shooting speed
     */
    public class ShootFar extends CommandBase {
        public ShootFar() {
            addRequirements(mLauncher);
        }

        // Called when the command is initially scheduled.
        @Override
        public void initialize() {
            mLauncher.setMotorSpeed(Flywheel.kFarRPS);
            mLauncher.setToggleTrue();
        }

        // Called every time the scheduler runs while the command is scheduled.
        @Override
        public void execute() {

        }

        // No isFinished because command is a WhileHeld

        // Called once the command ends or is interrupted.
        @Override
        public void end(boolean interrupted) {
            mLauncher.setMotorSpeed(mLauncher.getTargetRPS());
        }
    }
    public class TurnOnLauncher extends CommandBase {
        public TurnOnLauncher() {
            addRequirements(mLauncher);
        }

        // Called when the command is initially scheduled.
        @Override
        public void initialize() {
            mLauncher.setMotorSpeed(Flywheel.kRPS);
            mLauncher.setToggleTrue();
        }

        // Called every time the scheduler runs while the command is scheduled.
        @Override
        public void execute() {

        }

        // Called once the command ends or is interrupted.
        @Override
        public boolean isFinished() {
            return true;
        }
    }
}
