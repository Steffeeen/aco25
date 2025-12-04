package day01

import common.c
import common.loadInput
import common.loadTestInput

fun main() {
    val input = loadInput(1)
    val testInput = loadTestInput(1)
    
    c(part1(testInput), 3)
    println("Part 1: ${part1(input)}")
    
    c(part2(testInput), 6)
    println("Part 2: ${part2(input)}")
}

fun part1(input: String): Int {
    var current = 50
    var count = 0

    for (line in input.lines()) {
        val change = line.substring(1).toInt() * if (line[0] == 'R') 1 else -1
        current += change
        current %= 100

        if (current == 0) {
            count++
        }
    }

    return count
}

fun part2(input: String): Int {
    var current = 50
    var count = 0

    for (line in input.lines()) {
        var change = line.substring(1).toInt()
        val dir = if (line[0] == 'R') 1 else -1

        while (change > 0) {
            current += dir
            current = current.mod(100)
            if (current == 0) {
                count++
            }
            change--
        }
    }

    return count
}
