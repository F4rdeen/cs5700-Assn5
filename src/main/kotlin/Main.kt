import detector.impl.BinaryDetector
import detector.impl.EmailDetector
import detector.impl.FloatDetector
import detector.impl.IntegerDetector
import detector.impl.PasswordDetector

fun main() {
    val intDetector = IntegerDetector()
    println("a is integer: ${intDetector.process("a")}")  // false
    println("123 is integer: ${intDetector.process("123")}")  // true

    val floatDetector = FloatDetector()
    println("123 is float: ${floatDetector.process("123")}")  // false
    println("1.23 is float: ${floatDetector.process("1.23")}")  // true

    val binaryDetector = BinaryDetector()
    println("1.01 is binary: ${binaryDetector.process("1.01")}")  // false
    println("101 is binary: ${binaryDetector.process("101")}")  // true

    val emailDetector = EmailDetector()
    println("te st@example.com is email: ${emailDetector.process("te st@example.com")}")  // false
    println("test@example.com is email: ${emailDetector.process("test@example.com")}")  // true

    val passwordDetector = PasswordDetector()
    println("Password1! is valid: ${passwordDetector.process("Password1!")}")  // false (ends with special char)
    println("Password1 is valid: ${passwordDetector.process("Password1")}")   // false (no special char)
    println("Passw0rd! is valid: ${passwordDetector.process("Passw0rd!")}")  // false (ends with special char)
    println("Passw0rd!a is valid: ${passwordDetector.process("Passw0rd!a")}") // true
}