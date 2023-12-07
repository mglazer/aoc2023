import java.math.BigInteger

object Day6 {


    val TEST_INPUT = """
       Time:      7  15   30
       Distance:  9  40  200
    """.trimIndent()

    val PUZZLE_INPUT = """
        Time:        38     94     79     70
        Distance:   241   1549   1074   1091
    """.trimIndent()

    val TEST_PUZZLE_TWO = """
        Time:      71530
        Distance:  940200
    """.trimIndent()

    val PUZZLE_INPUT_TWO = """
        Time:        38947970
        Distance:   241154910741091
    """.trimIndent()

    data class TimeDistance(val time: Int, val distance: BigInteger)

    fun solve(input: String): Int {
        val timeDistanceList =  input.split("\n")
        val times = timeDistanceList[0].split(Regex("\\s+")).drop(1).map { it.toInt() }
        val distance = timeDistanceList[1].split(Regex("\\s+")).drop(1).map { it.toBigInteger() }
        val timeDistances = times.zip(distance).map { (time, distance) -> TimeDistance(time, distance) }

        val possibleDistances = timeDistances.map {td ->
            val distances = (0..td.time).map { it.toBigInteger() }.map { (it * (td.time.toBigInteger() - it)) }

            distances.filter { it > td.distance }.size
        }

        return possibleDistances.reduce { acc, i -> acc * i }
    }

}

fun main() {
    println("Part 1: " + Day6.solve(Day6.PUZZLE_INPUT))
    println("Part 2: " + Day6.solve(Day6.PUZZLE_INPUT_TWO))
}