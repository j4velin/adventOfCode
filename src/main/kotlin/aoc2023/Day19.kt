package aoc2023

import readInput

object Day19 {
	fun part1(input: List<String>): Int {
		return 0
	}

	fun part2(input: List<String>): Int {
		return 0
	}
}

fun main() {
	val testInput = readInput("Day19_test", 2023)
	check(Day19.part1(testInput) == 0)
	check(Day19.part2(testInput) == 0)

	val input = readInput("Day19", 2023)
	println(Day19.part1(input))
	println(Day19.part2(input))
}