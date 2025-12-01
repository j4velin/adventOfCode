package aoc2025

import modulo
import readInput

object Day01 {

    private fun dial(input: List<String>): Pair<Int, Int> {
        var currentPosition = 50
        var countZeroPosition = 0
        var countPasses = 0
        val instructions = input.map { line -> line.first() to line.drop(1).toInt() }
        instructions.forEach { (direction, distance) ->
            val (newPosition, passes) = when (direction) {
                'R' -> {
                    val newPosition = (currentPosition + distance) modulo 100
                    val passes = ((currentPosition + distance) / 100.0).toInt()
                    newPosition to passes
                }

                'L' -> {
                    val newPosition = (currentPosition - distance) modulo 100
                    val passes = ((currentPosition - distance) / -100.0 + (if (currentPosition != 0) 1 else 0)).toInt()
                    newPosition to passes
                }

                else -> error("Unknown direction: $direction")
            }
            currentPosition = if (newPosition == 100) 0 else newPosition
            if (currentPosition == 0) countZeroPosition++
            countPasses += passes
        }
        return countZeroPosition to countPasses
    }

    fun part1(input: List<String>): Int {
        return dial(input).first
    }

    fun part2(input: List<String>): Int {
        return dial(input).second
    }
}

fun main() {
    val testInput = readInput("Day01_test", 2025)
    check(Day01.part1(testInput) == 3)
    check(Day01.part2(testInput) == 6)

    val input = readInput("Day01", 2025)
    println(Day01.part1(input))
    println(Day01.part2(input))
}
