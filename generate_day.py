#!/usr/bin/env python3
import sys
import os
import re

def get_next_day():
    kotlin_dir = "src/main/kotlin"
    if not os.path.exists(kotlin_dir):
        return 1
    dirs = [d for d in os.listdir(kotlin_dir) if re.match(r'day\d+', d)]
    if not dirs:
        return 1
    days = [int(re.search(r'\d+', d).group()) for d in dirs]
    return max(days) + 1

def main():
    if len(sys.argv) > 1:
        day = int(sys.argv[1])
    else:
        day = get_next_day()

    day_str = f"{day:02d}"

    # Create directory
    day_dir = f"src/main/kotlin/day{day_str}"
    os.makedirs(day_dir, exist_ok=True)

    # Create DayXX.kt
    template = f"""package day{day_str}

import common.c
import common.loadInput
import common.loadTestInput

fun main() {{
    val input = loadInput({day})
    val testInput = loadTestInput({day})
    
    //c(part1(testInput), 0)
    println("Part 1: ${{part1(input)}}")
    
    //c(part2(testInput), 0)
    println("Part 2: ${{part2(input)}}")
}}

fun part1(input: String): Int {{
    return 0
}}

fun part2(input: String): Int {{
    return 0
}}
"""
    with open(f"{day_dir}/Day{day_str}.kt", 'w') as f:
        f.write(template)

    # Create input files
    inputs_dir = "src/main/resources/inputs"
    os.makedirs(inputs_dir, exist_ok=True)
    with open(f"{inputs_dir}/day{day_str}.txt", 'w') as f:
        f.write("")

    test_inputs_dir = "src/main/resources/test_inputs"
    os.makedirs(test_inputs_dir, exist_ok=True)
    with open(f"{test_inputs_dir}/day{day_str}.txt", 'w') as f:
        f.write("")

    print(f"Created files for Day {day_str}")

if __name__ == "__main__":
    main()
