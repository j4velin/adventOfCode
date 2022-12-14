package aoc2022

import readInput
import separateBy

fun main() {

    data class Elf(val calories: Int)

    fun getElves(input: List<String>) = input.separateBy { it.isBlank() }.map { list -> Elf(list.sumOf { it.toInt() }) }

    fun part1(input: List<String>) = getElves(input).maxBy { it.calories }.calories

    fun part2(input: List<String>) = getElves(input).sortedByDescending { it.calories }.take(3).sumOf { it.calories }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test", 2022)
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("Day01", 2022)
    println(part1(input))
    println(part2(input))
}
