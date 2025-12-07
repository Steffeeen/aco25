package day06

import common.c
import common.loadInput
import common.loadTestInput
import kotlin.math.pow

fun main() {
    val input = loadInput(6)
    val testInput = loadTestInput(6)
    
    c(part1(testInput), 4277556)
    println("Part 1: ${part1(input)}")
    
    c(part2(testInput), 3263827)
    println("Part 2: ${part2(input)}")
}

fun part1(input: String): Long {
    val splitLines = input.lines().map { it.trim().split(Regex("\\s+")) }
    require(splitLines.map { it.size }.distinct().size == 1)

    val numberLines = splitLines.dropLast(1)
    val symbolLine = splitLines.last()

    val problems = mutableListOf<Pair<List<Long>, Char>>()
    for (i in 0 until splitLines[0].size) {
        val numbers = numberLines.map { it[i].toLong() }
        val symbol = symbolLine[i].first()
        problems.add(Pair(numbers, symbol))
    }

    return problems.sumOf { (numbers, symbol) ->
        if (symbol == '+') {
            numbers.sum()
        } else {
            numbers.reduce { acc, n -> acc * n }
        }
    }
}

fun part2(input: String): Long {
    val lines = input.lines()
    val numberLines = lines.dropLast(1)

    val maxLineLength = lines.maxOf { it.length }

    var sum = 0L
    var i = 0
    while (i < maxLineLength) {
        val symbol = lines.last()[i]
        require(symbol == '+' || symbol == '*')

        val numbers = mutableListOf<Long>()

        while (true) {
            if (i >= maxLineLength || lines.mapNotNull { it.getOrNull(i) }.all { it.isWhitespace() }) {
                break
            }

            var number = 0L
            var multiplier = 1
            for (j in numberLines.indices.reversed()) {
                if (i < lines[j].length && !lines[j][i].isWhitespace()) {
                    number += multiplier * (lines[j][i].digitToInt()).toLong()
                    multiplier = if (multiplier == 1) 10 else multiplier * 10
                }
            }

            numbers.add(number)
            i++
        }

        sum += if (symbol == '+') {
            numbers.sum()
        } else {
            numbers.reduce { acc, n -> acc * n }
        }

        i++
    }

    return sum
}
