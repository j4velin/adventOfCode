package aoc2023

import readInput

object Day20 {
	fun part1(input: List<String>): Int {
		return 0
	}

	fun part2(input: List<String>): Int {
		return 0
	}
}

fun main() {
	val testInput = readInput("Day20_test", 2023)
	check(Day20.part1(testInput) == 0)
	check(Day20.part2(testInput) == 0)

	val input = readInput("Day20", 2023)
	println(Day20.part1(input))
	println(Day20.part2(input))
}