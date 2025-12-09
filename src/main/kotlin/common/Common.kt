package common

import java.nio.file.Files
import java.nio.file.Paths

fun loadInput(day: Int): String {
    return Files.readString(Paths.get("src/main/resources/inputs/day${day.toString().padStart(2, '0')}.txt")).trimIndent()
}

fun loadTestInput(day: Int): String {
    return Files.readString(Paths.get("src/main/resources/test_inputs/day${day.toString().padStart(2, '0')}_example.txt")).trimIndent()
}

fun c(actual: Int, expected: Int) {
    if (actual != expected) {
        throw IllegalStateException("Check failed: expected $expected but got $actual")
    }
}

fun c(actual: Long, expected: Long) {
    if (actual != expected) {
        throw IllegalStateException("Check failed: expected $expected but got $actual")
    }
}

fun parseGrid(input: String): List<List<Triple<Char, Int, Int>>> = input.lines().mapIndexed { y, line -> line.mapIndexed { x, c -> Triple(c, x, y) }}

fun <T> List<T>.firstTwoToPair(): Pair<T, T> = Pair(this[0], this[1])