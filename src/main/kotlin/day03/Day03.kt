package day03

import common.c
import common.loadInput
import common.loadTestInput
import kotlin.math.max
import kotlin.math.min

fun main() {
    val input = loadInput(3)
    val testInput = loadTestInput(3)

    c(part1(testInput), 357)
    println("Part 1: ${part1(input)}")

    c(part2(testInput), 3121910778619)
    println("Part 2: ${part2(input)}")
}

fun part1(input: String): Int {
    val banks = input.lines().map { it.map { it.digitToInt() } }

    return banks.sumOf { bank ->
        val max = bank.max()
        val maxIndex = bank.indexOf(max)
        if (maxIndex + 1 >= bank.size) {
            val secondMax = bank.subList(0, maxIndex).max()
            secondMax * 10 + max
        } else {
            val secondMax = bank.subList(maxIndex + 1, bank.size).max()
            max * 10 + secondMax
        }
    }
}

fun part2(input: String): Long {
    val banks = input.lines().map { it.map { it.digitToInt() } }

    return banks.sumOf { bank ->
        val result = mutableListOf<Int>()
        var start = 0
        for (end in (bank.size - 12) until bank.size) {
            val subBank = bank.subList(start, end + 1)
            val max = subBank.max()
            start = bank.indexOf(max, start) + 1
            result.add(max)
        }

        result.joinToString("").toLong()
    }
}

fun <T> List<T>.indexOf(element: T, startIndex: Int = 0): Int {
    for (i in startIndex until this.size) {
        if (this[i] == element) return i
    }
    return -1
}
