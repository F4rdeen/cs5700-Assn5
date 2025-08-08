package detector

import detector.impl.IntegerDetector
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class IntegerDetectorTest {
    private val detector = IntegerDetector()

    @Test
    fun `valid integers`() {
        assertTrue(detector.process("1"))
        assertTrue(detector.process("123"))
        assertTrue(detector.process("987654321"))
        assertTrue(detector.process("9"))
    }

    @Test
    fun `invalid integers`() {
        assertFalse(detector.process(""))
        assertFalse(detector.process("01"))
        assertFalse(detector.process("123a"))
        assertFalse(detector.process("0"))
        assertFalse(detector.process("12.3"))
        assertFalse(detector.process("123 "))   // trailing space
        assertFalse(detector.process(" 123"))   // leading space
        assertFalse(detector.process("1a"))     // invalid char
        assertFalse(detector.process("123!"))   // special char
    }
}