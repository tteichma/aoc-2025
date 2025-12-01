import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.time.measureTime

val unsignedIntegerRegex = Regex("""\d+""")
val signedIntegerRegex = Regex("""-?\d+""")
fun getIntsFromString(s: String) = signedIntegerRegex.findAll(s).map { it.groupValues[0].toInt() }
fun getLongsFromString(s: String) = signedIntegerRegex.findAll(s).map { it.groupValues[0].toLong() }
fun getUnsignedIntsFromString(s: String) = unsignedIntegerRegex.findAll(s).map { it.groupValues[0].toInt() }
fun getSignedDoublesFromString(s: String) = signedIntegerRegex.findAll(s).map { it.groupValues[0].toDouble() }
fun getIntListFromString(s: String) = getIntsFromString(s).toList()
fun getLongListFromString(s: String) = getLongsFromString(s).toList()

fun Int.isEven() = this % 2 == 0
fun Int.isOdd() = this % 2 != 0
fun Long.isEven() = this % 2 == 0L
fun Long.isOdd() = this % 2 != 0L


operator fun <T> List<List<T>>.get(coordinate: IntCoordinate) = this[coordinate.first][coordinate.second]

fun <T> MutableSet<T>.pop(): T? = this.first().also { this.remove(it) }

suspend fun <A, B> Iterable<A>.pmap(f: suspend (A) -> B): List<B> = coroutineScope {
    map { async { f(it) } }.awaitAll()
}

fun <T> Sequence<T>.repeatInfinitely() = sequence { while (true) yieldAll(this@repeatInfinitely) }

/**
 * Generates all unique pairs of elements from the input, and the remainder of the list (without elements in the pair).
 */
fun <T> yieldPairAndRest(input: List<T>) = iterator<Pair<Pair<T, T>, List<T>>> {
    for (indFirst in 0..<input.lastIndex) {
        for (indSecond in indFirst + 1..input.lastIndex) {
            yield(
                Pair(
                    Pair(input[indFirst], input[indSecond]),
                    input.slice(0..<indFirst)
                            + input.slice(indFirst + 1..<indSecond)
                            + input.slice(indSecond + 1..<input.size)
                )
            )
        }
    }
}

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readLines()

/**
 * The cleaner shorthand for printing output.
 */
fun List<String>.println() = println(this.joinToString("\n"))
fun Any?.println() = println(this)

fun <T> profiledExecute(label: String? = null, f: () -> T): T {
    val out: T
    val duration = measureTime { out = f() }

    if (label != null) {
        println("Executed $label in $duration.")
    } else {
        println("Executed in $duration.")
    }
    return out
}

fun <T> profiledCheck(expected: T, label: String? = null, f: () -> T) {
    val result = profiledExecute(label, f)

    check(result == expected) {
        if (label == null) "Wrong solution for $label: $result (expected $expected)" else "Wrong solution: $result (expected $expected)"
    }
}

