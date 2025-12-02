package aoc2025

import readInput

object Day02 {
    fun part1(input: List<String>): Long {
        val ranges = input.first().split(",").map { range ->
            range.split("-").let { LongRange(it.first().toLong(), it.last().toLong()) }
        }
        return ranges.flatMap { range ->
            range.map { it.toString() }
                .filter { it.length % 2 == 0 }
                .filter {
                    val firstPart = it.substring(0, it.length / 2)
                    val secondPart = it.substring(it.length / 2)
                    firstPart == secondPart
                }.map { it.toLong() }
        }.sum()
    }

    fun part2(input: List<String>): Long {
        val ranges = input.first().split(",").map { range ->
            range.split("-").let { LongRange(it.first().toLong(), it.last().toLong()) }
        }
        return ranges.flatMap { range ->
            range.map { it.toString() }
                .filter { number ->
                    for (index in 0..number.length / 2) {
                        val subString = number.take(index)
                        val regex = "($subString){2,}".toRegex()
                        if (regex.matches(number)) {
                            return@filter true
                        }
                    }
                    false
                }
                .map { it.toLong() }
        }.sum()
    }
}

fun main() {
    val testInput = readInput("Day02_test", 2025)
    check(Day02.part1(testInput) == 1227775554L)
    check(Day02.part2(testInput) == 4174379265L)

    val input = readInput("Day02", 2025)
    println(Day02.part1(input))
    println(Day02.part2(input))
}
