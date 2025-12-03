package aoc2025

import readInput

object Day03 {

    private data class ValueWithIndex(val index: Int, val value: Int)

    private fun String.findLargesBatteryWithIndex(maxIndex: Int): ValueWithIndex {
        var foundDigit = 0
        var foundIndex = 0
        for (index in 0..<maxIndex) {
            val digit = this[index].digitToInt()
            if (digit > foundDigit) {
                foundDigit = digit
                foundIndex = index
            }
        }
        return ValueWithIndex(index = foundIndex, value = foundDigit)
    }

    private fun String.findLargesJoltage(batteries: Int): Long {
        val digits = mutableListOf<Int>()
        var remainingBanks = this
        for (battery in 0..<batteries) {
            val (index, digit) = remainingBanks.findLargesBatteryWithIndex(remainingBanks.length - (batteries - battery - 1))
            digits.add(digit)
            remainingBanks = remainingBanks.drop(index + 1)
        }
        return digits.joinToString("").toLong()
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { it.findLargesJoltage(2) }.toInt()
    }

    fun part2(input: List<String>): Long {
        return input.sumOf { it.findLargesJoltage(12) }
    }
}

fun main() {
    val testInput = readInput("Day03_test", 2025)
    check(Day03.part1(testInput) == 357)
    check(Day03.part2(testInput) == 3121910778619L)

    val input = readInput("Day03", 2025)
    println(Day03.part1(input))
    println(Day03.part2(input))
}
