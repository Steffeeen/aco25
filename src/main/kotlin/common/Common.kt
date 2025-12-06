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