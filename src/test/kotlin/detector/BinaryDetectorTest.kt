package detector

import detector.impl.BinaryDetector
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BinaryDetectorTest {
    private val detector = BinaryDetector()

    @Test
    fun `valid binary numbers`() {
        assertTrue(detector.process("1"))
        assertTrue(detector.process("11"))
        assertTrue(detector.process("101"))
        assertTrue(detector.process("111111"))
        assertTrue(detector.process("10001"))
        assertTrue(detector.process("10011010001"))
        assertTrue(detector.process("101010101"))
    }

    @Test
    fun `invalid binary numbers`() {
        assertFalse(detector.process("01"))
        assertFalse(detector.process("10"))
        assertFalse(detector.process("1000010"))
        assertFalse(detector.process("100a01"))
        assertFalse(detector.process(""))           // empty
        assertFalse(detector.process("0"))          // single 0
        assertFalse(detector.process("1000"))       // ends with 0
        assertFalse(detector.process("2"))          // invalid digit
        assertFalse(detector.process("101b"))       // invalid char
        assertFalse(detector.process(" 101"))       // leading space
        assertFalse(detector.process("101 "))       // trailing space
    }
}