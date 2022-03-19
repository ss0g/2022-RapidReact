package com.spartronics4915.frc2022;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot
{
    private Command mAutonomousCommand;
    private RobotContainer mRobotContainer;
    private Command mTeleopCommand;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit()
    {
        // Instantiate our RobotContainer. This will perform all our button
        // bindings, and put our autonomous chooser on the dashboard.
        mRobotContainer = new RobotContainer();
    }

    /**
     * This function is called every robot packet, no matter the mode.
     * Use this for items like diagnostics that you want ran during disabled,
     * autonomous, teleoperated and test.
     * <p>
     * This runs after the mode specific periodic functions,
     * but before LiveWindow and SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic()
    {
        // Runs the Scheduler. This is responsible for polling buttons,
        // adding newly-scheduled commands, running already-scheduled commands,
        // removing finished or interrupted commands, and running subsystem
        // periodic() methods.
        // This must be called from the robot's periodic block in order for
        // anything in the command-based framework to work.
        CommandScheduler.getInstance().run();
    }

    /** This function is called once each time the robot enters disabled mode. */
    @Override
    public void disabledInit() {}

    /** This function is called periodically while the robot is in disabled mode. */
    @Override
    public void disabledPeriodic() {}

    /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
    @Override
    public void autonomousInit()
    {
        mAutonomousCommand = mRobotContainer.getAutonomousCommand();

        if (mAutonomousCommand != null)
        {
            mAutonomousCommand.schedule();
        }
    }

    /** This function is called periodically during autonomous. */
    @Override
    public void autonomousPeriodic() {}

    @Override
    public void teleopInit()
    {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (mAutonomousCommand != null) {
            mAutonomousCommand.cancel();
        }

        // Run main teleop command
        mTeleopCommand = mRobotContainer.getTeleopCommand();

        if (mTeleopCommand != null) {
            mTeleopCommand.schedule();
        }
    }

    /** This function is called periodically during operator control. */
    @Override
    public void teleopPeriodic() {}

    /** This function is called once each time the robot enters test mode. */
    @Override
    public void testInit()
    {
        // Cancels all running commands at the start of test mode.
        CommandScheduler.getInstance().cancelAll();
    }

    /** This function is called periodically during test mode. */
    @Override
    public void testPeriodic() {}
}
