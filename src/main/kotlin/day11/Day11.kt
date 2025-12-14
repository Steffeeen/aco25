package day11

import common.c
import common.loadInput
import common.loadTestInput

fun main() {
    val input = loadInput(11)
    val testInput = loadTestInput(11)
    
    c(part1(testInput), 5)
    println("Part 1: ${part1(input)}")
    
    c(part2(loadTestInput(11, "part2")), 2)
    println("Part 2: ${part2(input)}")
}

fun part1(input: String): Int {
    val devices = parseInput(input)

    val queue = ArrayDeque<String>()
    queue.add("you")

    var count = 0

    while (queue.isNotEmpty()) {
        val current = queue.removeFirst()
        if (current == "out") {
            count++
            continue
        }

        val device = devices.find { it.name == current }!!
        for (output in device.outputs) {
            queue.add(output)
        }
    }

    return count
}

fun part2(input: String): Long {
    val devices = parseInput(input)

    with (devices) {
        return dfs("svr", dacSeen = false, fftSeen = false, mutableMapOf())
    }
}

data class CacheKey(val current: String, val dacSeen: Boolean, val fftSeen: Boolean)

context(devices: List<Device>)
fun dfs(current: String, dacSeen: Boolean, fftSeen: Boolean, cache: MutableMap<CacheKey, Long>): Long {
    if (current == "out") {
        return if (dacSeen && fftSeen) 1 else 0
    }

    val key = CacheKey(current, dacSeen, fftSeen)

    if (key in cache) {
        return cache[key]!!
    }

    var total = 0L
    val device = devices.find { it.name == current }!!
    for (output in device.outputs) {
        total += dfs(
            output,
            dacSeen || output == "dac",
            fftSeen || output == "fft",
            cache
        )
    }

    cache[key] = total

    return total
}

data class Device(val name: String, val outputs: List<String>)

fun parseInput(input: String): List<Device> {
    return input.lines().map { line ->
        val name = line.substringBefore(":")
        val outputs = line.substringAfter(":").trim().split(" ").map { it.trim() }
        Device(name, outputs)
    }
}