package detector.factory

import detector.strategy.Detector
import detector.impl.*

enum class DetectorType {
    INTEGER, FLOAT, BINARY, EMAIL, PASSWORD
}

object DetectorFactory {
    fun create(type: DetectorType): Detector = when(type) {
        DetectorType.INTEGER -> IntegerDetector()
        DetectorType.FLOAT -> FloatDetector()
        DetectorType.BINARY -> BinaryDetector()
        DetectorType.EMAIL -> EmailDetector()
        DetectorType.PASSWORD -> PasswordDetector()
    }
}