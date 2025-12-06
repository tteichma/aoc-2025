private class Day06(val input: List<String>) {
    companion object {
        fun fromInput(input: List<String>): Day06 {
            return Day06(input)
        }
    }

    fun solvePart1(): Long {
        val numbers = input.dropLast(1).map { getLongListFromString(it) }
        val operators = input.last().filterNot { it.isWhitespace() }.toList()

        return operators.withIndex().sumOf { indexedOp ->
            when (indexedOp.value) {
                '+' -> numbers.sumOf { it[indexedOp.index] }
                '*' -> numbers.map { it[indexedOp.index] }.reduce { acc, it -> acc * it }
                else -> TODO("Not implemented")
            }
        }
    }

    fun solvePart2(): Long {
        val indsBetweenColumns = listOf(-1) + input.first().indices
            .filter { colInd -> input.map { it[colInd] }.all { it.isWhitespace() } } + listOf(input.first().length)
        var result = 0L
        for ((indStart, indEnd) in indsBetweenColumns.windowed(2)) {
            val columnInput = input.map { it.substring(indStart+1, indEnd) }
            val op = columnInput.last().trim().first()
            val columnNumbersInput = columnInput.dropLast(1)
            val numbers =
                columnNumbersInput.first().indices.map { ind ->
                    columnNumbersInput.map { it[ind] }.joinToString("").trim().toLong()
                }
            result += when (op) {
                '+' -> numbers.sum()
                '*' -> numbers.reduce { acc, it -> acc * it }
                else -> TODO("Not implemented")
            }
        }
        return result
    }
}

fun main() {
    // Or read a large test input from the `src/Day06_test.txt` file:
    val testInput = readInput("Day06_test")
    profiledCheck(4277556L, "Part 1 test") {
        val day = Day06.fromInput(testInput)
        day.solvePart1()
    }
    profiledCheck(3263827L, "Part 2 test") {
        val day = Day06.fromInput(testInput)
        day.solvePart2()
    }

    // Read the input from the `src/Day06.txt` file.
    val input = readInput("Day06")
    profiledExecute("Part 1") {
        val day = Day06.fromInput(input)
        day.solvePart1()
    }.println()
    profiledExecute("Part 2") {
        val day = Day06.fromInput(input)
        day.solvePart2()
    }.println()
}
