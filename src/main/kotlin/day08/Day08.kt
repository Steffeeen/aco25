package day08

import common.c
import common.loadInput
import common.loadTestInput

fun main() {
    val input = loadInput(8)
    val testInput = loadTestInput(8)
    
    c(part1(testInput, true), 40)
    println("Part 1: ${part1(input, false)}")
    
    c(part2(testInput), 25272)
    println("Part 2: ${part2(input)}")
}

data class Circuit(val points: MutableSet<Point>)

fun part1(input: String, isTestInput: Boolean): Int {
    val numberOfBoxesToConnect = if (isTestInput) 10 else 1000
    val points = parseInput(input)

    val squaredDistances = calculateDistances(points)
    val pairsToLookAt = squaredDistances.toList().sortedBy { it.second }.take(numberOfBoxesToConnect)

    val circuits = mutableMapOf<Point, Circuit>()

    for ((points, _) in pairsToLookAt) {
        val (p1, p2) = points
        connect(p1, p2, circuits)
    }

    return circuits.values.distinct().sortedByDescending { it.points.size }.take(3).fold(1) { acc, c -> acc * c.points.size }
}

fun part2(input: String): Long {
    val points = parseInput(input)

    val squaredDistances = calculateDistances(points).toList().sortedBy { it.second }.toMutableList()
    val circuits = mutableMapOf<Point, Circuit>()

    while (true) {
        val (p1, p2) = squaredDistances.removeFirst().first
        connect(p1, p2, circuits)

        if (circuits.values.distinct().size == 1 && circuits.size == points.size) {
            return p1.x * p2.x;
        }
    }
}

fun connect(p1: Point, p2: Point, circuits: MutableMap<Point, Circuit>) {
    val circuit1 = circuits[p1]
    val circuit2 = circuits[p2]

    when {
        circuit1 == null && circuit2 == null -> {
            val newCircuit = Circuit(mutableSetOf(p1, p2))
            circuits[p1] = newCircuit
            circuits[p2] = newCircuit
        }
        circuit1 != null && circuit2 == null -> {
            circuit1.points.add(p2)
            circuits[p2] = circuit1
        }
        circuit1 == null && circuit2 != null -> {
            circuit2.points.add(p1)
            circuits[p1] = circuit2
        }
        circuit1 != null && circuit2 != null && circuit1 != circuit2 -> {
            // Merge circuits
            circuit1.points.addAll(circuit2.points)
            for (point in circuit2.points) {
                circuits[point] = circuit1
            }
        }
    }
}

fun calculateDistances(points: Set<Point>): Map<Pair<Point, Point>, Long> {
    val distances = mutableMapOf<Pair<Point, Point>, Long>()

    for (p1 in points) {
        for (p2 in points) {
            if (p1 != p2 && !distances.containsKey(Pair(p2, p1))) {
                distances[Pair(p1, p2)] = p1.squaredDistanceTo(p2)
            }
        }
    }

    return distances
}

data class Point(val x: Long, val y: Long, val z: Long)

fun Point.squaredDistanceTo(point: Point) = (x - point.x) * (x - point.x) + (y - point.y) * (y - point.y) + (z - point.z) * (z - point.z)

fun parseInput(input: String): Set<Point> {
    return input.lines().map {
        val (x, y, z) = it.split(",").map { it.toLong() }
        Point(x, y, z)
    }.toSet()
}