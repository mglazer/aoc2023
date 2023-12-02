object Day2 {
    val TEST_DATA = """
        Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
        Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
        Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
        Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
        Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
    """.trimIndent()

    data class Balls(val red: Int, val green: Int, val blue: Int) {
        fun isValid(other: Balls): Boolean {
            return red >= other.red && green >= other.green && blue >= other.blue
        }

        companion object {
            fun fromStrings(stringVals: Map<String, Int>): Balls {
                val blue = stringVals["blue"] ?: 0
                val green = stringVals["green"] ?: 0
                val red = stringVals["red"] ?: 0

                return Balls(red = red, green = green, blue = blue)
            }
        }
    }

    data class Game(val number: Int, val sets: List<Balls>)


    fun createGames(input: String): List<Game> {
        return input.split("\n")
            .map {
                val matches = Regex("Game (?<number>\\d+): (?<inputs>.*)").matchEntire(it)
                requireNotNull(matches)

                val number = requireNotNull(matches.groups.get("number")).value.toInt()
                val sets = requireNotNull(matches.groups.get("inputs")).value.split(";").map { pull ->
                    val balls = pull.split(",")
                    val mappings = balls.map { ball ->
                        val count = ball.trim().split(" ")[0].toInt()
                        val color = ball.trim().split(" ")[1]
                        Pair(color, count)
                    }.toMap()
                    Balls.fromStrings(mappings)
                }
                Game(number = number, sets = sets)
            }
    }

    fun solvePart1(games: List<Game>, maxBalls: Balls): Int {
        return games.filter{ it.sets.all(maxBalls::isValid) }.sumOf { it.number }
    }

    fun solvePart2(games: List<Game>): Int {
        return games.map { game ->
            val minBallsRed = game.sets.map(Balls::red).filter { it != 0 }.max()
            val minBallsGreen = game.sets.map(Balls::green).filter { it != 0 }.max()
            val minBallsBlue = game.sets.map(Balls::blue).filter { it != 0 }.max()

            minBallsBlue * minBallsGreen * minBallsRed
        }.sum()
    }

}

fun main() {
    val maxBalls = Day2.Balls(red = 12, green = 13, blue = 14)
    val games = Day2.createGames(Utils.loadResource("day2.txt"))

    println("Result of part 1: ${Day2.solvePart1(games, maxBalls)}")

    println("Result of part 2: ${Day2.solvePart2(games)}")
}