package detector.impl

import detector.base.StateMachine
import detector.base.State
import detector.base.ErrorState

class BinaryDetector : StateMachine() {
    override val initialState: State = StartState()

    private inner class StartState : State {
        override fun handle(char: Char, machine: StateMachine) =
            if (char == '1') LastWasOneState() else ErrorState
    }

    private inner class LastWasOneState : State {
        override fun handle(char: Char, machine: StateMachine) = when (char) {
            '0' -> MiddleState()
            '1' -> this
            else -> ErrorState
        }
        override fun isAccepting() = true
    }

    private inner class MiddleState : State {
        override fun handle(char: Char, machine: StateMachine) = when (char) {
            '0' -> this
            '1' -> LastWasOneState()
            else -> ErrorState
        }
    }
}