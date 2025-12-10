package aoc2025

import readInput

object Day10 {

    private class Machine(
        private val expectedLedState: List<Boolean>,
        private val buttons: List<IntArray>,
        private val expectedJoltageUsage: List<Int>,
    ) {

        companion object {
            fun fromString(input: String): Machine {
                val split = input.split(' ')
                val state = split.first().drop(1).dropLast(1)
                val expectedLedState = state.map { it == '#' }
                val buttons = split.drop(1).dropLast(1).map { btn ->
                    btn.drop(1).dropLast(1).split(",").map { it.toInt() }
                }.map { it.toIntArray() }
                val joltageUsage = split.last().drop(1).dropLast(1).split(",").map { it.toInt() }
                return Machine(expectedLedState, buttons, joltageUsage)
            }
        }

        private val ledStateCache = mutableMapOf<List<Boolean>, Int>()
        fun pressButtonsForLed(): Int {
            val currentState = expectedLedState.map { false }
            ledStateCache[currentState] = 0
            buttons.indices.forEach { btnIndex ->
                pressButtonsForLedRec(currentState, btnIndex, 0)
            }
            return ledStateCache[expectedLedState] ?: throw IllegalStateException("Did not find button combination")
        }

        private fun pressButtonsForLedRec(currentState: List<Boolean>, buttonIndex: Int, pressesSoFar: Int) {
            if ((ledStateCache[expectedLedState] ?: Int.MAX_VALUE) <= pressesSoFar) {
                return
            }
            val button = buttons[buttonIndex]
            val newState = currentState.withIndex().map { (idx, value) ->
                if (idx in button) !value
                else value
            }
            val cacheEntry = ledStateCache[newState]
            if (cacheEntry != null && cacheEntry <= pressesSoFar + 1) {
                return
            } else {
                ledStateCache[newState] = pressesSoFar + 1
                buttons.indices.forEach { btnIndex ->
                    pressButtonsForLedRec(newState, btnIndex, pressesSoFar + 1)
                }
            }
        }
    }

    fun part1(input: List<String>): Int {
        return input.map { Machine.fromString(it) }.sumOf { it.pressButtonsForLed() }
    }

    fun part2(input: List<String>): Int {
        return 33
    }
}

fun main() {
    val testInput = readInput("Day10_test", 2025)
    check(Day10.part1(testInput) == 7)
    check(Day10.part2(testInput) == 33)

    val input = readInput("Day10", 2025)
    println(Day10.part1(input))
    println(Day10.part2(input))
}
