private class Day07(val startIndex: Int, val splitterIndices: Map<Int, Set<Int>>) {
    companion object {
        fun fromInput(input: List<String>): Day07 {
            val startInd = input.first().indexOf('S')
            val splitterIndices = input.withIndex().drop(1)
                .map { row ->
                    row.index to row.value.mapIndexedNotNull { ind, elem -> ind.takeIf { elem == '^' } }
                }
                .associate { it.first to it.second.toSet() }
            return Day07(startInd, splitterIndices)
        }
    }

    fun solvePart1(): Long {
        val indLastSplitter = splitterIndices.keys.max()
        val indsBeam = mutableSetOf(startIndex)
        var count = 0L
        for (row in 1..indLastSplitter) {
            val indsBeamsHittingSplitter = indsBeam.intersect(splitterIndices[row] ?: setOf())
            indsBeamsHittingSplitter.forEach {
                indsBeam.remove(it)
                indsBeam.addAll(listOf(it - 1, it + 1))
                ++count
            }
        }
        return count
    }

    fun solvePart2(): Long {
        val indLastSplitter = splitterIndices.keys.max()
        val beamIndexToNumTimeLines = mutableMapOf<Int, Long>()
        for (row in indLastSplitter downTo 1){
            for (ind in splitterIndices[row] ?: setOf()){
                beamIndexToNumTimeLines[ind] = (beamIndexToNumTimeLines[ind-1] ?: 1L) + (beamIndexToNumTimeLines[ind+1] ?: 1L)
            }
        }
        return beamIndexToNumTimeLines[startIndex] ?: 1L
    }
}

fun main() {
    // Or read a large test input from the `src/Day07_test.txt` file:
    val testInput = readInput("Day07_test")
    profiledCheck(21L, "Part 1 test") {
        val day = Day07.fromInput(testInput)
        day.solvePart1()
    }
    profiledCheck(40L, "Part 2 test") {
        val day = Day07.fromInput(testInput)
        day.solvePart2()
    }

    // Read the input from the `src/Day07.txt` file.
    val input = readInput("Day07")
    profiledExecute("Part 1") {
        val day = Day07.fromInput(input)
        day.solvePart1()
    }.println()
    profiledExecute("Part 2") {
        val day = Day07.fromInput(input)
        day.solvePart2()
    }.println()
}
