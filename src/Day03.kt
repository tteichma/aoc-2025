private class Day03(val batteries: List<List<Int>>) {
    companion object {
        fun fromInput(input: List<String>): Day03 {
            val batteries = input.map { line ->
                line.map { c -> c.digitToInt() }
            }
            return Day03(batteries)
        }
    }

    fun solve(numToEnable: Int): Long {
        var total = 0L
        for (bank in batteries) {
            var bestJoltage = 0L
            var currentIndex = 0
            for (numRemaining in numToEnable - 1 downTo 0) {
                bestJoltage *= 10
                for (candidate in 9 downTo 0) {
                    val indCandidate = bank.withIndex().drop(currentIndex).dropLast(numRemaining).firstOrNull {
                        it.value == candidate
                    }?.index ?: continue
                    currentIndex = indCandidate + 1
                    bestJoltage += bank[indCandidate]
                    break
                }
            }
            total += bestJoltage
        }
        return total
    }

    fun solvePart1() = solve(2)
    fun solvePart2() = solve(12)
}

fun main() {
    // Or read a large test input from the `src/Day03_test.txt` file:
    val testInput = readInput("Day03_test")
    profiledCheck(357L, "Part 1 test") {
        val day = Day03.fromInput(testInput)
        day.solvePart1()
    }
    profiledCheck(3121910778619L, "Part 2 test") {
        val day = Day03.fromInput(testInput)
        day.solvePart2()
    }

    // Read the input from the `src/Day03.txt` file.
    val input = readInput("Day03")
    profiledExecute("Part 1") {
        val day = Day03.fromInput(input)
        day.solvePart1()
    }.println()
    profiledExecute("Part 2") {
        val day = Day03.fromInput(input)
        day.solvePart2()
    }.println()
}
