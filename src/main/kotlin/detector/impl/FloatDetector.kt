package detector.impl

import detector.base.StateMachine
import detector.base.State
import detector.base.ErrorState

class FloatDetector : StateMachine() {
    override val initialState: State = StartState()

    private inner class StartState : State {
        override fun handle(char: Char, machine: StateMachine) = when {
            char in '1'..'9' -> IntegerPartState()
            char == '0' -> ZeroState()
            char == '.' -> AfterPeriodState()
            else -> ErrorState
        }
    }

    private inner class ZeroState : State {
        override fun handle(char: Char, machine: StateMachine) =
            if (char == '.') AfterPeriodState() else ErrorState
    }

    private inner class IntegerPartState : State {
        override fun handle(char: Char, machine: StateMachine) = when {
            char in '0'..'9' -> this
            char == '.' -> AfterPeriodState()
            else -> ErrorState
        }
    }

    private inner class AfterPeriodState : State {
        override fun handle(char: Char, machine: StateMachine) =
            if (char in '0'..'9') FractionPartState() else ErrorState
    }

    private inner class FractionPartState : State {
        override fun handle(char: Char, machine: StateMachine) =
            if (char in '0'..'9') this else ErrorState
        override fun isAccepting() = true
    }
}