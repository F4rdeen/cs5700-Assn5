package detector

import detector.impl.EmailDetector
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class EmailDetectorTest {
    private val detector = EmailDetector()

    @Test
    fun `valid emails`() {
        assertTrue(detector.process("a@b.c"))
        assertTrue(detector.process("user@domain.com"))
        assertTrue(detector.process("{}*$.&$*(@abc.xyz"))
        assertTrue(detector.process("john.doe@example.com"))
        assertTrue(detector.process("u@d.c"))
    }

    @Test
    fun `invalid emails`() {
        assertFalse(detector.process("@b.c"))                // missing part1
        assertFalse(detector.process("a@b@c.com"))          // multiple @
        assertFalse(detector.process("a.b@b.b.c"))          // multiple dots after @
        assertFalse(detector.process("name withspace@domain.com")) // space
        assertFalse(detector.process("user@.com"))          // missing part2
        assertFalse(detector.process("user@domain."))        // missing part3
        assertFalse(detector.process("user@domain"))        // missing dot
        assertFalse(detector.process("user@domain..com"))   // consecutive dots
        assertFalse(detector.process("user@domain_com"))    // invalid char _
        assertFalse(detector.process("user@domain,com"))    // invalid char ,
        assertFalse(detector.process("user@domain@com"))    // multiple @
        assertFalse(detector.process("user@domain"))        // no TLD
        assertFalse(detector.process("user@"))              // missing parts
        assertFalse(detector.process("@domain.com"))         // missing local
        assertFalse(detector.process("user@domain."))        // trailing dot
        assertFalse(detector.process(".user@domain.com"))    // leading dot
    }
}