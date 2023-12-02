
object Day1 {
    val TEST_INPUT = """
1abc2
pqr3stu8vwx
a1b2c3d4e5f
treb7uchet
    """.trim()

    val TEST_INPUT_2 = """
       two1nine
       eightwothree
       abcone2threexyz
       xtwone3four
       4nineeightseven2
       zoneight234
       7pqrstsixteen
    """.trimIndent()

    fun calibrate(input: String): Int {
        return input.split("\n")
            .map { it.toCharArray().filter { c -> c.isDigit() } }
            .map { if (it.size == 1) {
                "${it[0].digitToInt()}${it[0].digitToInt()}"
            } else {
                "${it[0].digitToInt()}${it[it.size - 1].digitToInt()}"
            }}
            .map{ it.toInt() }
            .sum()
    }

    val spellNumbers = listOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9,
    )

    fun calibratePart2(input: String): Int {
        return input.split("\n")
            .map {
                var replacedValue = it
                spellNumbers.forEach { (spelled, target) ->
                    var idx = replacedValue.indexOf(spelled)
                    while (idx >= 0) {
                        replacedValue = replacedValue.substring(0, idx+1) + target + replacedValue.substring(idx)
                        idx = replacedValue.indexOf(spelled, idx + 3)
                    }
                }
                replacedValue
            }
            .map { it.toCharArray().filter { c -> c.isDigit() } }
            .map { if (it.size == 1) {
                "${it[0].digitToInt()}${it[0].digitToInt()}"
            } else {
                "${it[0].digitToInt()}${it[it.size - 1].digitToInt()}"
            }}
            .map{ it.toInt() }
            .sum()
    }


}

fun main() {
    val realInput = Utils.loadResource("day1.txt")
    val calibrated = Day1.calibrate(realInput)

    println("Calibrated is ${calibrated}")

    val calibrated2 = Day1.calibratePart2(realInput)
    println("Calibrated 2 is ${calibrated2}")
}
