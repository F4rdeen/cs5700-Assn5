package detector

import detector.impl.FloatDetector
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class FloatDetectorTest {
    private val detector = FloatDetector()

    @Test
    fun `valid floats`() {
        assertTrue(detector.process("1.0"))
        assertTrue(detector.process("0.2"))
        assertTrue(detector.process("123.456"))
        assertTrue(detector.process(".123"))
        assertTrue(detector.process("0.0"))
        assertTrue(detector.process("999999999.99999999"))
        assertTrue(detector.process("0.0000001"))
    }

    @Test
    fun `invalid floats`() {
        assertFalse(detector.process("123"))
        assertFalse(detector.process("123.123."))
        assertFalse(detector.process("123.02a"))
        assertFalse(detector.process("123."))
        assertFalse(detector.process("012.4"))
        assertFalse(detector.process("."))          // no digits
        assertFalse(detector.process("0."))         // no fractional
        assertFalse(detector.process("1.2.3"))      // multiple dots
        assertFalse(detector.process("abc.123"))    // letters
        assertFalse(detector.process("1..23"))      // consecutive dots
        assertFalse(detector.process("1 .23"))      // space
        assertFalse(detector.process("1.23 "))      // trailing space
    }
}