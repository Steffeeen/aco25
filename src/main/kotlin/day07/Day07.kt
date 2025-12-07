package day07

import common.c
import common.loadInput
import common.loadTestInput
import common.parseGrid

fun main() {
    val input = loadInput(7)
    val testInput = loadTestInput(7)
    
    c(part1(testInput), 21)
    println("Part 1: ${part1(input)}")
    
    c(part2(testInput), 40)
    println("Part 2: ${part2(input)}")
}

fun part1(input: String): Int {
    val grid = parseGrid(input)

    val start = grid.first().filter { (c, _, _) -> c == 'S' }.map { (_, x, y) -> x to y }.first()

    var beams = setOf(start)
    var splits = 0

    repeat(grid.size - 1) {
        val newBeams = mutableSetOf<Pair<Int, Int>>()
        for ((x, y) in beams) {
            if (grid[y + 1][x].first == '^') {
                splits++
                newBeams += (x - 1 to y + 1)
                newBeams += (x + 1 to y + 1)
            } else {
                newBeams += (x to y + 1)
            }
        }

        beams = newBeams.toSet()
    }

    return splits
}

fun part2(input: String): Long {
    val grid = parseGrid(input)

    val (x, y) = grid.first().filter { (c, _, _) -> c == 'S' }.map { (_, x, y) -> x to y }.first()

    val cache = mutableMapOf<Pair<Int, Int>, Long>()


    return runFrom(grid, x, y, cache) + 1
}

typealias Grid = List<List<Triple<Char, Int, Int>>>

fun runFrom(grid: Grid, x: Int, y: Int, cache: MutableMap<Pair<Int, Int>, Long>): Long {
    if (y == grid.size - 1) {
        return 0
    }

    val key = x to y
    if (key in cache) {
        return cache[key]!!
    }

    val next = grid[y + 1][x]
    val splits = if (next.first == '^') {
        1 + runFrom(grid, x - 1, y + 1, cache) + runFrom(grid, x + 1, y + 1, cache)
    } else {
        runFrom(grid, x, y + 1, cache)
    }
    cache[key] = splits
    return splits
}
