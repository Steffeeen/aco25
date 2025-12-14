package day10

import common.c
import common.loadInput
import common.loadTestInput
import io.ksmt.KContext
import io.ksmt.expr.KExpr
import io.ksmt.expr.KInt32NumExpr
import io.ksmt.solver.KSolverStatus
import io.ksmt.solver.z3.KZ3Solver
import io.ksmt.sort.KBoolSort
import io.ksmt.utils.getValue
import io.ksmt.utils.mkConst
import java.util.*

fun main() {
    val input = loadInput(10)
    val testInput = loadTestInput(10)

    c(part1(testInput), 7)
    println("Part 1: ${part1(input)}")

    c(part2(testInput), 33)
    println("Part 2: ${part2(input)}")
}

fun part1(input: String): Int {
    val machines = parseInput(input)
    return machines.sumOf { machine ->
        val targetLights = machine.lights.toLongArray().first()
        val buttonMasks = machine.buttons.map { button ->
            var mask = 0L
            for (lightIndex in button) {
                mask = mask or (1L shl lightIndex)
            }
            mask
        }

        val combinations = calculateCombinations(buttonMasks.indices.toList())

        combinations.mapNotNull { evaluateCombination(it, buttonMasks, targetLights) }.min()
    }
}

fun evaluateCombination(combination: List<Int>, buttonMasks: List<Long>, targetLights: Long): Int? {
    var currentLights = 0L
    for (index in combination) {
        currentLights = currentLights xor buttonMasks[index]
    }
    return if (currentLights == targetLights) {
        combination.size
    } else {
        null
    }
}

fun <T> calculateCombinations(list: List<T>): List<List<T>> {
    if (list.isEmpty()) {
        return listOf(listOf())
    }
    val result = mutableListOf<List<T>>()
    val first = list.first()
    val rest = list.drop(1)
    for (combination in calculateCombinations(rest)) {
        result.add(combination)
        result.add(listOf(first) + combination)
    }
    return result
}

fun part2(input: String): Int {
    val machines = parseInput(input)
    return machines.mapIndexed { index, machine ->
        val result = determineMinimalPresses(machine)
        result
    }.sum()
}

fun determineMinimalPresses(machine: Machine): Int {
    val context = KContext()
    return with(context) {
        val buttonVariables = machine.buttons.mapIndexed { index, _ -> intSort.mkConst("button_${index}_count") }

        val numberOfPresses by intSort

        val constraints = mutableListOf<KExpr<KBoolSort>>()
        for ((index, requirement) in machine.joltageRequirements.withIndex()) {
            val relevantButtons = machine.buttons.withIndex().filter { index in it.value }.map { it.index }
            val buttonSum = mkArithAdd(relevantButtons.map { buttonVariables[it] })

            constraints += requirement.expr eq buttonSum
        }
        constraints += buttonVariables.map { mkArithGe(it, mkIntNum(0)) }
        constraints += numberOfPresses eq mkArithAdd(buttonVariables)

        KZ3Solver(this).use { solver ->
            solver.assert(constraints)

            var best = Int.MAX_VALUE

            while (solver.check() == KSolverStatus.SAT) {
                val model = solver.model()
                val x = model.eval(numberOfPresses) as KInt32NumExpr
                best = x.value
                solver.assert(mkArithLt(numberOfPresses, mkIntNum(best)))
            }

            best
        }
    }
}

data class Machine(val lights: BitSet, val buttons: List<List<Int>>, val joltageRequirements: List<Int>)

fun parseInput(input: String): List<Machine> {
    val lines = input.lines()
    return lines.map { line ->
        val lightsPart = line.split(" ")[0].trim().replace("[", "").replace("]", "")
        val lights = BitSet().apply {
            lightsPart.forEachIndexed { index, c ->
                if (c == '#') {
                    set(index)
                }
            }
        }

        val buttons = line.substringAfter("] ").substringBefore(" {").split(" ").map { buttonString ->
            buttonString.replace("(", "").replace(")", "").split(",").map { it.toInt() }
        }

        val joltageRequirements = line.substringAfter("{").replace("}", "").split(",").map { it.toInt() }
        Machine(lights, buttons, joltageRequirements)
    }
}