package aoc2025

import PointL
import readInput
import to2dCharArray
import find
import maxY
import java.lang.IllegalArgumentException

object Day07 {
    fun part1(input: List<String>): Int {
        val grid = input.to2dCharArray()
        val start = grid.find('S') ?: throw IllegalArgumentException("No start found")
        val beams = mutableSetOf(start)
        var splits = 0
        while (beams.isNotEmpty()) {
            val beam = beams.first().also { beams.remove(it) }
            val nextPosition = beam.move(0, 1)
            if (nextPosition.y < grid.maxY) {
                if (grid[nextPosition.x.toInt()][nextPosition.y.toInt()] == '^') {
                    beams.add(nextPosition.move(1, 0))
                    beams.add(nextPosition.move(-1, 0))
                    splits++
                } else {
                    beams.add(nextPosition)
                }
            }
        }
        return splits
    }

    fun part2(input: List<String>): Long {
        val grid = input.to2dCharArray()
        val start = grid.find('S') ?: throw IllegalArgumentException("No start found")
        return getNumberOfTimelinesRec(grid, start)
    }

    private val cache = mutableMapOf<PointL, Long>()

    private fun getNumberOfTimelinesRec(grid: Array<CharArray>, beam: PointL): Long {
        var currentPosition = beam
        while (currentPosition.y < grid.maxY && grid[currentPosition.x.toInt()][currentPosition.y.toInt()] != '^') {
            currentPosition = currentPosition.move(0, 1)
        }
        if (grid[currentPosition.x.toInt()][currentPosition.y.toInt()] == '^') {
            val left = currentPosition.move(-1, 0)
            val right = currentPosition.move(1, 0)
            val timelinesLeft = cache[left] ?: run {
                val count = getNumberOfTimelinesRec(grid, left)
                cache[left] = count
                count
            }
            val timelinesRight = cache[right] ?: run {
                val count = getNumberOfTimelinesRec(grid, right)
                cache[right] = count
                count
            }
            cache[beam] = timelinesLeft + timelinesRight
            return timelinesLeft + timelinesRight
        } else {
            return 1L
        }
    }
}

fun main() {
    val testInput = readInput("Day07_test", 2025)
    check(Day07.part1(testInput) == 21)
    check(Day07.part2(testInput) == 40L)

    val input = readInput("Day07", 2025)
    println(Day07.part1(input))
    println(Day07.part2(input))
}
