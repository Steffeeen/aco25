package day05

import common.c
import common.loadInput
import common.loadTestInput

fun main() {
    val input = loadInput(5)
    val testInput = loadTestInput(5)
    
    c(part1(testInput), 3)
    println("Part 1: ${part1(input)}")
    
    c(part2(testInput), 14)
    println("Part 2: ${part2(input)}")
}

fun part1(input: String): Int {
    val (ranges, ids) = parseInput(input)

    return ids.count { ranges.any { range -> it in range } }
}

fun part2(input: String): Long {
    val (ranges, ids) = parseInput(input)

    val mergedRanges = mutableListOf<LongRange>()
    for (range in ranges.sortedBy { it.start }) {
        if (mergedRanges.isEmpty() || !mergedRanges.last().overlaps(range)) {
            mergedRanges.add(range)
        } else {
            val lastRange = mergedRanges.removeLast()
            mergedRanges.add(lastRange.start..maxOf(lastRange.endInclusive, range.endInclusive))
        }
    }

    return mergedRanges.sumOf { it.endInclusive - it.start + 1 }
}

fun LongRange.overlaps(other: LongRange): Boolean {
    return this.start <= other.endInclusive && other.start <= this.endInclusive
}

fun parseInput(input: String): Pair<List<LongRange>, List<Long>> {
    val sections = input.split("\n\n")

    val ranges = sections.first().lines().map {
        val (start, end) = it.split("-")
        start.toLong()..end.toLong()
    }

    val ids = sections.last().lines().map { it.toLong() }
    return ranges to ids
}
