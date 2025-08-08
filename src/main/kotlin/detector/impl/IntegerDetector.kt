package detector.impl

import detector.base.StateMachine
import detector.base.State
import detector.base.ErrorState

class IntegerDetector : StateMachine() {
    override val initialState: State = StartState()

    private inner class StartState : State {
        override fun handle(char: Char, machine: StateMachine) =
            if (char in '1'..'9') IntegerState() else ErrorState
    }

    private inner class IntegerState : State {
        override fun handle(char: Char, machine: StateMachine) =
            if (char in '0'..'9') this else ErrorState
        override fun isAccepting() = true
    }
}