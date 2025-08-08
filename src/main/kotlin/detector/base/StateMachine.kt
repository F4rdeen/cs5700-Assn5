package detector.base

import detector.strategy.Detector

abstract class StateMachine : Detector {
    protected abstract val initialState: State
    protected var currentState: State = initialState

    override fun reset() {
        currentState = initialState
    }

    override fun process(input: String): Boolean {
        reset()
        input.forEach { char ->
            currentState = currentState.handle(char, this)
        }
        return currentState.isAccepting()
    }
}

interface State {
    fun handle(char: Char, machine: StateMachine): State
    fun isAccepting(): Boolean = false
}

object ErrorState : State {
    override fun handle(char: Char, machine: StateMachine) = this
}