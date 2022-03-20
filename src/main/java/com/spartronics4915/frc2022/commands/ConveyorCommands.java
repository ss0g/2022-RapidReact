package com.spartronics4915.frc2022.commands;

import static com.spartronics4915.frc2022.Constants.Conveyor.*;

import com.spartronics4915.frc2022.subsystems.Conveyor;
import com.spartronics4915.frc2022.subsystems.Conveyor.State;
import com.spartronics4915.frc2022.subsystems.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class ConveyorCommands {
    private final Conveyor mConveyor;
    private final Intake mIntake;
    
    public ConveyorCommands(Conveyor conveyor, Intake intake) {
        mConveyor = conveyor;
        mIntake = intake;
    }

    public class SetState extends CommandBase {
        private State mState;

        public SetState(State state) {
            mState = state;
            addRequirements(mConveyor);
        }

        @Override
        public void initialize() {
            mConveyor.setState(mState);
        }
        
        @Override
        public boolean isFinished() {
            return true;
        }
    }

    public class ToggleConveyor extends ConditionalCommand{
        public ToggleConveyor() {
            super(
                new SequentialCommandGroup(
                    new WaitCommand(kStopConveyorsDelay),
                    new SetState(State.OFF)
                ),
                new SetState(State.FILL),
                mConveyor::isActive
            );
        }
    }

    public class ReverseBoth extends CommandBase {
        public ReverseBoth() {
            addRequirements(mConveyor, mIntake);
        }

        @Override
        public void initialize(){
            mConveyor.setState(State.REVERSE_BOTH);
            mIntake.startIntake(true);
        }

        @Override
        public void end(boolean interrupted) {
            mConveyor.setState(State.FILL);
            mIntake.startIntake(false);
        }
    }

    public class ReverseBottom extends CommandBase {
        public ReverseBottom(){
            addRequirements(mConveyor, mIntake);
        }

        @Override
        public void initialize(){
            mConveyor.setState(State.REVERSE_BOTTOM);
            mIntake.startIntake(true);
        }

        @Override
        public void end(boolean interrupted){
            mConveyor.setState(State.FILL);
            mIntake.startIntake(false);
        }
    }

    public class ShootFromTop extends SequentialCommandGroup {
        public ShootFromTop() {
            addCommands(
                new InstantCommand(() -> mConveyor.setState(State.SHOOT_FROM_TOP)),
                new WaitCommand(kTopConveyorTime),
                new InstantCommand(() -> mConveyor.setState(State.OFF))
            );

            addRequirements(mConveyor);
        }
    }

    public class ShootFromBottom extends SequentialCommandGroup {
        public ShootFromBottom() {
            addCommands(
                new InstantCommand(() -> mConveyor.setState(State.SHOOT_FROM_BOTTOM)),
                new WaitCommand(kTopConveyorTime + kBottomConveyorTime),
                new InstantCommand(() -> mConveyor.setState(State.OFF))
            );

            addRequirements(mConveyor);
        }
    }

    public class Shoot1 extends ConditionalCommand {
        public Shoot1(){
            super(
                new ShootFromTop(),
                new ShootFromBottom(),
                mConveyor::hasTopBall
            );
        }
    }
}
