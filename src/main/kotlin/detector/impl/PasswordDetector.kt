package detector.impl

import detector.base.StateMachine
import detector.base.State

class PasswordDetector : StateMachine() {
    override val initialState: State = ProcessingState()
    private var length = 0
    private var hasCapital = false
    private var hasSpecial = false
    private var lastChar: Char? = null
    private var hasSpace = false

    override fun reset() {
        super.reset()
        length = 0
        hasCapital = false
        hasSpecial = false
        lastChar = null
        hasSpace = false
    }

    private inner class ProcessingState : State {
        override fun handle(char: Char, machine: StateMachine): State {
            length++
            lastChar = char
            if (char.isUpperCase()) hasCapital = true
            if (char in setOf('!', '@', '#', '$', '%', '&', '*')) hasSpecial = true
            if (char.isWhitespace()) hasSpace = true
            return this
        }

        override fun isAccepting(): Boolean {
            val endsWithSpecial = lastChar in setOf('!', '@', '#', '$', '%', '&', '*')
            return length >= 8 &&
                hasCapital &&
                hasSpecial &&
                !endsWithSpecial &&
                !hasSpace
        }
    }
}