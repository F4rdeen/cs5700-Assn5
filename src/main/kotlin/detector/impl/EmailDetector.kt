package detector.impl

import detector.base.StateMachine
import detector.base.State
import detector.base.ErrorState

class EmailDetector : StateMachine() {
    override val initialState: State = StartState()

    private var seenAt = false
    private var seenDotAfterAt = false
    private var lastChar: Char? = null
    private var part1Length = 0
    private var part2Length = 0
    private var part3Length = 0
    private var lastWasDot = false
    private var consecutiveDot = false

    override fun reset() {
        super.reset()
        seenAt = false
        seenDotAfterAt = false
        lastChar = null
        part1Length = 0
        part2Length = 0
        part3Length = 0
        lastWasDot = false
        consecutiveDot = false
    }

    private inner class StartState : State {
        override fun handle(char: Char, machine: StateMachine): State {
            if (char == '@' || char == ' ' || char == '.') return ErrorState
            if (!isValidChar(char)) return ErrorState
            part1Length++
            lastChar = char
            lastWasDot = false
            consecutiveDot = false
            return Part1State()
        }
    }

    private inner class Part1State : State {
        override fun handle(char: Char, machine: StateMachine): State {
            if (char == '@') {
                if (part1Length == 0 || seenAt || lastWasDot) return ErrorState // can't end local with dot
                seenAt = true
                lastChar = char
                lastWasDot = false
                return AfterAtState()
            }
            if (char == ' ' || !isValidChar(char)) return ErrorState
            if (char == '.') {
                if (lastWasDot || part1Length == 0) return ErrorState // no consecutive or leading dot
                lastWasDot = true
                consecutiveDot = true
                lastChar = char
                return this
            }
            part1Length++
            lastChar = char
            lastWasDot = false
            consecutiveDot = false
            return this
        }
    }

    private inner class AfterAtState : State {
        override fun handle(char: Char, machine: StateMachine): State {
            if (char == '@' || char == ' ' || !isValidChar(char)) return ErrorState
            if (char == '.') {
                // domain can't start with dot
                return ErrorState
            }
            part2Length++
            lastChar = char
            lastWasDot = false
            return Part2State()
        }
    }

    private inner class Part2State : State {
        override fun handle(char: Char, machine: StateMachine): State {
            if (char == '.') {
                if (lastWasDot || part2Length == 0) return ErrorState // no consecutive or leading dot
                seenDotAfterAt = true
                lastWasDot = true
                lastChar = char
                return AfterDotState()
            }
            if (char == '@' || char == ' ' || !isValidChar(char)) return ErrorState
            part2Length++
            lastChar = char
            lastWasDot = false
            return this
        }
    }

    private inner class AfterDotState : State {
        override fun handle(char: Char, machine: StateMachine): State {
            if (char == '@' || char == ' ' || !isValidChar(char)) return ErrorState
            if (char == '.') {
                // TLD can't start with dot
                return ErrorState
            }
            part3Length++
            lastChar = char
            lastWasDot = false
            return Part3State()
        }
    }

    private inner class Part3State : State {
        override fun handle(char: Char, machine: StateMachine): State {
            if (char == '@' || char == ' ' || !isValidChar(char)) return ErrorState
            if (char == '.') return ErrorState // no dots in part3 (TLD)
            part3Length++
            lastChar = char
            lastWasDot = false
            return this
        }
        override fun isAccepting(): Boolean {
            // must have seen @, dot after @, and all parts non-empty
            // must not end with dot or have consecutive dots or start/end with dot in any part
            return seenAt && seenDotAfterAt && part1Length > 0 && part2Length > 0 && part3Length > 0 &&
                lastChar != '.' && !lastWasDot
        }
        }
    }

    private fun isValidChar(char: Char): Boolean {
        // allow only letters, digits, and these: {}*$.&$*()
        return char.isLetterOrDigit() || char in setOf('{','}','*','$','.','&','(',')')
    }

