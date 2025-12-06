package day02

import common.c
import common.loadInput
import common.loadTestInput

fun main() {
    val input = loadInput(2)
    val testInput = loadTestInput(2)
    
    c(part1(testInput), 1227775554)
    println("Part 1: ${part1(input)}")
    
    c(part2(testInput), 4174379265)
    println("Part 2: ${part2(input)}")
}

fun part1(input: String): Long {
    val ranges = input.split(",").map { parseRange(it) }

    return ranges.flatMap { range -> range.filter { isInvalidId(it) } }.sum()
}

fun part2(input: String): Long {
    val ranges = input.split(",").map { parseRange(it) }

    return ranges.flatMap { range -> range.filter { x ->
        (2..x.toString().length).any { isInvalidId(x, chunks = it) }
    } }.sum()
}

fun isInvalidId(x: Long, chunks: Int = 2): Boolean {
    val s = x.toString()

    if (s.length % chunks != 0) {
        return false
    }

    return s.chunked(s.length / chunks).distinct().count() == 1
}

fun parseRange(range: String): LongRange {
    val (start, end) = range.split("-").map { it.toLong() }
    return start..end
}
