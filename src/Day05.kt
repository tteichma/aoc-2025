private data class Day05(val ranges: List<LongRange>, val items: List<Long>) {
    companion object {
        fun fromInput(input: List<String>): Day05 {
            val numberRanges = input.takeWhile { it != "" }
                .map { rangeString ->
                    val numberStrings = rangeString.split("-").map { it.toLong() }
                    numberStrings[0]..numberStrings[1]
                }
            val items = input.dropWhile { it != "" }.drop(1).map { it.toLong() }
            return Day05(numberRanges, items)
        }
    }

    fun solvePart1(): Long {
        return items.count { item -> ranges.any { range -> item in range } }.toLong()
    }

    fun solvePart2(): Long {
        var counter = 0L
        var lastSeen = -1L
        val sortedRanges = ranges.sortedBy { it.first }
        for (range in sortedRanges) {
            if (lastSeen < range.first) {
                counter += range.last - range.first + 1
                lastSeen = range.last
            } else if (lastSeen <= range.last) {
                counter += range.last - lastSeen
                lastSeen = range.last
            } else {
                // No Overlap
            }
        }
        return counter
    }
}

fun main() {
    // Or read a large test input from the `src/Day05_test.txt` file:
    val testInput = readInput("Day05_test")
    profiledCheck(3L, "Part 1 test") {
        val day = Day05.fromInput(testInput)
        day.solvePart1()
    }
    profiledCheck(14L, "Part 2 test") {
        val day = Day05.fromInput(testInput)
        day.solvePart2()
    }

    // Read the input from the `src/Day05.txt` file.
    val input = readInput("Day05")
    profiledExecute("Part 1") {
        val day = Day05.fromInput(input)
        day.solvePart1()
    }.println()
    profiledExecute("Part 2") {
        val day = Day05.fromInput(input)
        day.solvePart2()
    }.println()
}
