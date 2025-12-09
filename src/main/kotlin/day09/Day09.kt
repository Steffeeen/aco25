package day09

import common.c
import common.firstTwoToPair
import common.loadInput
import common.loadTestInput
import kotlin.math.abs

fun main() {
    val input = loadInput(9)
    val testInput = loadTestInput(9)

    c(part1(testInput), 50)
    println("Part 1: ${part1(input)}")

    c(part2(testInput), 24)
    println("Part 2: ${part2(input)}")
}

typealias Point = Pair<Long, Long>

fun part1(input: String): Long {
    val points = input.lines().map { it.split(",").map { it.toLong() }.firstTwoToPair() }

    val combinations = createCombinations(points)

    return combinations.maxOf { (p1, p2) -> (abs(p1.first - p2.first) + 1) * (abs(p1.second - p2.second) + 1) }
}

fun part2(input: String): Long {
    val points = input.lines().map { it.split(",").map { it.toLong() }.firstTwoToPair() }

    val combinations = createCombinations(points)

    val filteredCombinations = combinations.filter { (p1, p2) -> isValidCombination(p1, p2, calculateLines(points)) }

    return filteredCombinations.maxOf { (p1, p2) -> (abs(p1.first - p2.first) + 1) * (abs(p1.second - p2.second) + 1) }
}

fun isValidCombination(p1: Point, p2: Point, lines: Set<Line>): Boolean {
    val xMin = minOf(p1.first, p2.first)
    val xMax = maxOf(p1.first, p2.first)
    val yMin = minOf(p1.second, p2.second)
    val yMax = maxOf(p1.second, p2.second)

    return lines.all {
        maxOf(it.start.first, it.end.first) <= xMin || xMax <= minOf(it.start.first, it.end.first) ||
                maxOf(it.start.second, it.end.second) <= yMin || yMax <= minOf(it.start.second, it.end.second)
    }
}

data class Line(val start: Point, val end: Point)

fun calculateLines(points: List<Point>): Set<Line> {
    val lines = mutableSetOf<Line>()

    lines.add(Line(points.last(), points.first()))
    points.windowed(2).forEach { (p1, p2) -> lines.add(Line(p1, p2)) }

    return lines
}

fun createCombinations(points: List<Point>): Set<Pair<Point, Point>> {
    val results = mutableSetOf<Pair<Pair<Long, Long>, Pair<Long, Long>>>()

    for (p1 in points) {
        for (p2 in points) {
            if (p1 != p2 && Pair(p2, p1) !in results) {
                results.add(Pair(p1, p2))
            }
        }
    }

    return results
}
