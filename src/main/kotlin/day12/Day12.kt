package day12

import common.loadInput
import common.loadTestInput

fun main() {
    val input = loadInput(12)
    val testInput = loadTestInput(12)
    
//    c(part1(testInput), 2)
    println("Part 1: ${part1(input)}")
    
    //c(part2(testInput), 0)
    println("Part 2: ${part2(input)}")
}

fun part1(input: String): Int {
    val (shapes, regions) = parseInput(input)

    return regions.count { canFitAll(it, shapes) }
}

fun part2(input: String): Int {
    return 0
}

fun canFitAll(region: Region, shapes: List<Shape>): Boolean {
    val regionSize = region.width * region.length

    if (regionSize / 9 >= region.shapeCounts.sum()) {
        return true
    }

    val totalSpaceNeeded = region.shapeCounts.mapIndexed { index, count ->
        count * shapes[index].points.size
    }.sum()
    return totalSpaceNeeded <= regionSize
}

data class Shape(val points: Set<Pair<Int, Int>>, val index: Int)
data class Region(val width: Int, val length: Int, val shapeCounts: List<Int>)

fun parseInput(input: String): Pair<List<Shape>, List<Region>> {
    val parts = input.split("\n\n")
    val shapes = parts.dropLast(1).map { parseShape(it) }
    val regionPart = parts.last()

    val regions = regionPart.lines().map { regionLine ->
        val dimensions = regionLine.substringBefore(":")
        val (width, length) = dimensions.split("x").map { it.toInt() }

        val shapeCounts = regionLine.substringAfter(":").split(" ").map { it.trim() }.filter { it.isNotEmpty() }.map { it.toInt() }
        Region(width, length, shapeCounts)
    }

    return shapes to regions
}

fun parseShape(string: String): Shape {
    val index = string.lines().first().substringBefore(":").toInt()
    val points = string.lines().drop(1).mapIndexed { y, line ->
        line.mapIndexedNotNull { x, char ->
            if (char == '#') Pair(x, y) else null
        }
    }.flatten().toSet()
    return Shape(points, index)
}