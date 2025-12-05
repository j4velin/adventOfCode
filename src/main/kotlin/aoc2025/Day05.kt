package aoc2025

import readInput
import separateBy
import kotlin.math.max

object Day05 {
    fun part1(input: List<String>): Int {
        val split = input.separateBy { it.isEmpty() }
        val ranges = split.first().map { range ->
            val rangeSplit = range.split("-")
            LongRange(rangeSplit.first().toLong(), rangeSplit.last().toLong())
        }
        val ingredients = split.last().map { it.toLong() }
        return ingredients.count { i -> ranges.any { i in it } }
    }

    fun part2(input: List<String>): Long {
        val ranges = input.separateBy { it.isEmpty() }.first().map { range ->
            val rangeSplit = range.split("-")
            LongRange(rangeSplit.first().toLong(), rangeSplit.last().toLong())
        }.sortedWith(compareBy({ it.first }, { it.last }))
        var lastIndex = 0L
        return ranges.sumOf { range ->
            val count = range.last - max(range.first, lastIndex) + 1
            lastIndex = range.last + 1
            max(0, count)
        }
    }
}

fun main() {
    val testInput = readInput("Day05_test", 2025)
    check(Day05.part1(testInput) == 3)
    check(Day05.part2(testInput) == 14L)

    val input = readInput("Day05", 2025)
    println(Day05.part1(input))
    println(Day05.part2(input))
}
