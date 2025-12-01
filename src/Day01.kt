import kotlin.math.absoluteValue

private class Day01(val rotations: List<Int>) {
    companion object {
        fun fromInput(input: List<String>): Day01 {
            val rotations = input.map { (if (it.startsWith('L')) -1 else 1) * it.drop(1).toInt() }
            return Day01(rotations)
        }
    }

    fun solvePart1(): Long {
        val endStates = rotations.runningFold(initial = 50) { acc, it -> (100 + acc + it) % 100 }
        return endStates.count { it == 0 }.toLong()
    }

    fun solvePart2(): Long {
        var count = 0L
        var position = 50
        for (rotation in rotations) {
            val numFullRotations = rotation / 100
            count += numFullRotations.absoluteValue

            val rawNewPos = position + (rotation - numFullRotations * 100)
            val newPos = (100 + rawNewPos) % 100
            if (newPos == 0 && position != 0) {
                ++count
            } else if (newPos != rawNewPos && position != 0) {
                ++count
            }
            position = newPos
        }
        return count
    }
}

fun main() {
    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    profiledCheck(3L, "Part 1 test") {
        val day = Day01.fromInput(testInput)
        day.solvePart1()
    }
    profiledCheck(6L, "Part 2 test") {
        val day = Day01.fromInput(testInput)
        day.solvePart2()
    }

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    profiledExecute("Part 1") {
        val day = Day01.fromInput(input)
        day.solvePart1()
    }.println()
    profiledExecute("Part 2") {
        val day = Day01.fromInput(input)
        day.solvePart2()
    }.println()
}
