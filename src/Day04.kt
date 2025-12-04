private class Day04Map(data: List<List<Boolean>>) : DataMap<Boolean>(data) {
    fun getAccessiblePaperRollCoordinates() =
        coordinates.filter { coordinate -> this[coordinate] && coordinate.getEightNeighbours { this[it] }.size < 4 }

    companion object {
        fun fromInput(input: List<String>): Day04Map {
            return Day04Map(input.map { row -> row.map { it == '@' } })
        }
    }
}

private class Day04(val dataMap: Day04Map) {
    companion object {
        fun fromInput(input: List<String>) = Day04(Day04Map.fromInput(input))
    }

    fun solvePart1(): Long {
        return dataMap.getAccessiblePaperRollCoordinates().size.toLong()
    }

    fun solvePart2(): Long {
        var numRemoves = 0L

        var lastMap = dataMap
        while (true) {
            val removableCoordinates = lastMap.getAccessiblePaperRollCoordinates().toSet()
            if (removableCoordinates.isEmpty()) break

            numRemoves += removableCoordinates.size
            lastMap = Day04Map(
                lastMap.data.withIndex().map { (indX, row) ->
                    row.withIndex().map { (indY, oldValue) ->
                        oldValue && IntCoordinate(indX, indY) !in removableCoordinates
                    }
                }
            )
        }
        return numRemoves
    }
}

fun main() {
    // Or read a large test input from the `src/Day04_test.txt` file:
    val testInput = readInput("Day04_test")
    profiledCheck(13L, "Part 1 test") {
        val day = Day04.fromInput(testInput)
        day.solvePart1()
    }
    profiledCheck(43L, "Part 2 test") {
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
