package day04

import common.c
import common.loadInput
import common.loadTestInput
import common.parseGrid

fun main() {
    val input = loadInput(4)
    val testInput = loadTestInput(4)
    
    c(part1(testInput), 13)
    println("Part 1: ${part1(input)}")
    
    c(part2(testInput), 43)
    println("Part 2: ${part2(input)}")
}

fun part1(input: String): Int {
    val grid = parseGrid(input)
    return getRemovableRolls(grid).size
}

fun part2(input: String): Int {
    var grid = parseGrid(input)
    var removableRolls = getRemovableRolls(grid)
    var count = removableRolls.size

    while (removableRolls.isNotEmpty()) {
        grid = grid.map { row -> row.map {
            if (it in removableRolls) {
                Triple('.', it.second, it.third)
            } else it
        } }
        removableRolls = getRemovableRolls(grid)
        count += removableRolls.size
    }

    return count
}

fun getRemovableRolls(grid: List<List<Triple<Char, Int, Int>>>): List<Triple<Char, Int, Int>> {
    val positionsToCheck = (-1..1).flatMap { x -> (-1..1).map { x to it } } - (0 to 0)

    return grid.flatten().filter { (c, x, y) ->
        val adjacent = positionsToCheck.mapNotNull { (dx, dy) ->
            grid.getOrNull(y + dy)?.getOrNull(x + dx)?.first
        }
        c == '@' && adjacent.count { it == '@' } < 4
    }
}
