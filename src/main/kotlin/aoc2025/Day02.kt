package aoc2025

import readInput

object Day02 {
	fun part1(input: List<String>): Int {
		return 0
	}

	fun part2(input: List<String>): Int {
		return 0
	}
}

fun main() {
	val testInput = readInput("Day02_test", 2025)
	check(Day02.part1(testInput) == 0)
	check(Day02.part2(testInput) == 0)

	val input = readInput("Day02", 2025)
	println(Day02.part1(input))
	println(Day02.part2(input))
}
