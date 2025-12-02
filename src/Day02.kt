private class Day02(val ranges: List<LongRange>) {
    companion object {
        fun fromInput(input: List<String>): Day02 {
            val numberRanges = input.first().split(",")
                .map { rangeString ->
                    val numberStrings = rangeString.split("-").map { it.toLong() }
                    numberStrings[0]..numberStrings[1]
                }
            return Day02(numberRanges)
        }
    }

    fun solvePart1(): Long {
        val invalidIds = ranges.flatMap{range->
            range.filter {
                val s = it.toString()
                val length = s.length
                length % 2 == 0 && s.take(length / 2) == s.takeLast(length / 2)
            }
        }
        return invalidIds.sum()
    }

    fun solvePart2(): Long {
        return 0L
    }
}

fun main() {
    // Or read a large test input from the `src/Day02_test.txt` file:
    val testInput = readInput("Day02_test")
    profiledCheck(1227775554L, "Part 1 test") {
        val day = Day02.fromInput(testInput)
        day.solvePart1()
    }
    profiledCheck(0L, "Part 2 test") {
        val day = Day02.fromInput(testInput)
        day.solvePart2()
    }

    // Read the input from the `src/Day02.txt` file.
    val input = readInput("Day02")
    profiledExecute("Part 1") {
        val day = Day02.fromInput(input)
        day.solvePart1()
    }.println()
    profiledExecute("Part 2") {
        val day = Day02.fromInput(input)
        day.solvePart2()
    }.println()
}
