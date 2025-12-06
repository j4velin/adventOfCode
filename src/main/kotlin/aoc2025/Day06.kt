package aoc2025

import multiplyOfLong
import readInput
import to2dCharArray

object Day06 {
    fun part1(input: List<String>): Long {
        val split = input.map { it.split("\\s+".toRegex()).filter { entry -> entry.isNotEmpty() } }
        val numbers = split.dropLast(1)
        val instructions = split.last()
        return instructions.withIndex().sumOf { (idx, instruction) ->

            if (instruction == "+") numbers.map { it[idx] }.sumOf { it.toLong() }
            else numbers.map { it[idx] }.multiplyOfLong { it.toLong() }
        }
    }

    fun part2(input: List<String>): Long {
        val grid = input.to2dCharArray()
        var result = 0L
        val currentNumbers = mutableListOf<Long>()
        // read from right to left
        for (x in grid.size - 1 downTo 0) {
            val numberStr = mutableListOf<Char>()
            // read number from top to bottom
            for (y in 0 until input.size - 1) {
                if (grid[x][y].isDigit()) {
                    numberStr.add(grid[x][y])
                }
            }
            if (numberStr.isNotEmpty()) {
                currentNumbers.add(numberStr.joinToString(separator = "").toLong())
                val instruction = grid[x][input.size - 1]
                if (instruction == '+') {
                    result += currentNumbers.sum()
                    currentNumbers.clear()
                } else if (instruction == '*') {
                    result += currentNumbers.multiplyOfLong { it }
                    currentNumbers.clear()
                }
            }
        }

        return result
    }
}

fun main() {
    val testInput = readInput("Day06_test", 2025)
    check(Day06.part1(testInput) == 4277556L)
    check(Day06.part2(testInput) == 3263827L)

    val input = readInput("Day06", 2025)
    println(Day06.part1(input))
    println(Day06.part2(input))
}
