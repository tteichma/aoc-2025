private class Day04(data: List<List<Boolean>>) : DataMap<Boolean>(data) {
    companion object {
        fun fromInput(input: List<String>): Day04 {
            return Day04(input.map { row -> row.map { it == '@' } })
        }
    }

    fun solvePart1(): Long {
        return coordinates.count { coordinate -> this[coordinate] && coordinate.getEightNeighbours { this[it] }.size < 4 }.toLong()
    }

    fun solvePart2(): Long {
        return 0L
    }
}

fun main() {
    // Or read a large test input from the `src/Day04_test.txt` file:
    val testInput = readInput("Day04_test")
    profiledCheck(13L, "Part 1 test") {
        val day = Day04.fromInput(testInput)
        day.solvePart1()
    }
    profiledCheck(0L, "Part 2 test") {
        val day = Day04.fromInput(testInput)
        day.solvePart2()
    }

    // Read the input from the `src/Day04.txt` file.
    val input = readInput("Day04")
    profiledExecute("Part 1") {
        val day = Day04.fromInput(input)
        day.solvePart1()
    }.println()
    profiledExecute("Part 2") {
        val day = Day04.fromInput(input)
        day.solvePart2()
    }.println()
}
