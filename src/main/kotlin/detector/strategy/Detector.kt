package detector.strategy

interface Detector {
    fun process(input: String): Boolean
    fun reset()
}