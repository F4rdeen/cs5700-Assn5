package detector

import detector.impl.PasswordDetector
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PasswordDetectorTest {
    private val detector = PasswordDetector()

    @Test
    fun `valid passwords`() {
        assertTrue(detector.process("aaaaH!aa"))        // meets all requirements
        assertTrue(detector.process("1234567*9J"))      // meets all requirements
        assertTrue(detector.process("Passw0rd!a"))      // special char not last
        assertTrue(detector.process("LongPasswordWithSpecial@Char1")) // valid
        assertTrue(detector.process("ABCdef123!xyz"))   // special in middle
        assertTrue(detector.process("1A2b3c4d5e6f!g"))  // special not last
        assertTrue(detector.process("Abcdef1!a"))       // exactly 8 chars, valid
        assertTrue(detector.process("abcde123!X"))      // capital last
        assertTrue(detector.process("Password1!a"))     // special not last
    }

    @Test
    fun `invalid passwords`() {
        // Too short
        assertFalse(detector.process("a"))
        assertFalse(detector.process("A!1"))

        // Missing capital
        assertFalse(detector.process("aaaaaaa!"))
        assertFalse(detector.process("12345678!"))

        // Missing special char
        assertFalse(detector.process("aaaHaaaaa"))
        assertFalse(detector.process("Password123"))

        // Ends with special char
        assertFalse(detector.process("Abbbbbbb!"))
        assertFalse(detector.process("Passw0rd!"))
        assertFalse(detector.process("12345678A!"))
        assertFalse(detector.process("Abcdef!@"))       // exactly 8 chars, ends with special

        // Too short and missing requirements
        assertFalse(detector.process("A!"))             // too short
        assertFalse(detector.process("1234Abc"))        // no special char
        assertFalse(detector.process("12345678"))       // no capital or special
        assertFalse(detector.process("abcdefgh"))       // no capital or special
        assertFalse(detector.process("ABCDEFGH"))       // no special
        assertFalse(detector.process("!@#$%^&*"))      // no capital
        assertFalse(detector.process("1234ABC! "))      // space char
    }
    // Edge cases covered above
}