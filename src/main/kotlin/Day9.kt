object Day9 {
    val TEST_INPUT = """
        0 3 6 9 12 15
        1 3 6 10 15 21
        10 13 16 21 30 45
    """.trimIndent()

    fun getHistoryValues(inputLines: List<String>): Int {
        return inputLines.sumOf { getNextHistoryValue(it) }
    }

    fun getHistoryValuesBackwards(inputLines: List<String>): Int {
        return inputLines.sumOf { getPrevHistoryValue(it) }
    }

    private fun getPrevHistoryValue(inputLine: String): Int {
        val sequences = getHistoryLists(inputLine)

        sequences.last().add(0, 0)
        for (i in sequences.size - 2 downTo 0) {
            val currentSequence = sequences[i]
            val nextValue = currentSequence.first() - sequences[i+1].first()
            currentSequence.add(0, nextValue)
        }

        return sequences.first().first()
    }

    private fun getNextHistoryValue(inputLine: String): Int {
        val sequences = getHistoryLists(inputLine)

        sequences.last().add(0)
        for (i in sequences.size - 2 downTo 0) {
            val currentSequence = sequences[i]
            val nextValue = currentSequence.last() + sequences[i+1].last()
            currentSequence.add(nextValue)
        }

        return sequences.first().last()
    }

    private fun getHistoryLists(inputLine: String): MutableList<MutableList<Int>> {
        val values = inputLine.split(Regex("\\s+")).map { it.toInt() }.toMutableList()

        val sequences = mutableListOf<MutableList<Int>>()
        sequences.add(values)
        while (!sequences.last().all { it == 0 }) {
            val previousSequences = sequences.last()
            val differences = previousSequences.zipWithNext().map { (left, right) -> right - left }.toMutableList()
            sequences.add(differences)
        }

        return sequences
    }


}

fun main() {
    val lines = Utils.loadResource("day9.txt").split("\n")
    println("Part 1: ${Day9.getHistoryValues(lines)}")

    println("Part 2: ${Day9.getHistoryValuesBackwards(lines)}")
}