package aoc2025

import Point3
import multiplyOf
import readInput
import withEachOf
import java.util.*

object Day08 {

    private data class JunctionBox(val position: Point3, var circuit: Int) {
        override fun equals(other: Any?) = Objects.equals(position, (other as? JunctionBox)?.position)
        override fun hashCode() = position.hashCode()
    }

    private data class Result(val junctionBoxes: List<JunctionBox>, val lastConnection: Pair<JunctionBox, JunctionBox>)

    private fun connectJunctionBoxes(input: List<String>, max: Int? = null): Result {
        val junctionBoxes =
            input.withIndex().map { JunctionBox(position = Point3.fromCsvString(it.value), circuit = it.index) }
        val sortedByDistance = junctionBoxes.withEachOf(junctionBoxes).filter { it.first != it.second }
            .sortedBy { (first, second) -> first.position.singleLineDistanceTo(second.position) }
        val alreadyConnected = mutableSetOf<Pair<JunctionBox, JunctionBox>>()
        var currentIndex = 0
        var lastConnected: Pair<JunctionBox, JunctionBox>? = null
        val loop = {
            var pair: Pair<JunctionBox, JunctionBox>
            do {
                pair = sortedByDistance[currentIndex]
                currentIndex++
            } while (alreadyConnected.contains(pair))
            alreadyConnected.add(pair)
            alreadyConnected.add(pair.second to pair.first)
            if (pair.first.circuit != pair.second.circuit) {
                junctionBoxes.filter { it.circuit == pair.second.circuit }.forEach { it.circuit = pair.first.circuit }
            }
            lastConnected = pair
        }
        if (max != null) {
            repeat(max) { loop() }
        } else {
            while (junctionBoxes.groupBy { it.circuit }.size > 1) {
                loop()
            }
        }
        return Result(junctionBoxes, lastConnected!!)
    }

    fun part1(input: List<String>, pairs: Int): Int {
        val result = connectJunctionBoxes(input, pairs)
        return result.junctionBoxes.groupBy { it.circuit }.values.sortedByDescending { it.size }.take(3)
            .multiplyOf { it.size }
    }

    fun part2(input: List<String>): Int {
        val result = connectJunctionBoxes(input)
        return (result.lastConnection.first.position.x * result.lastConnection.second.position.x).toInt()
    }
}

fun main() {
    val testInput = readInput("Day08_test", 2025)
    check(Day08.part1(testInput, 10) == 40)
    check(Day08.part2(testInput) == 25272)

    val input = readInput("Day08", 2025)
    println(Day08.part1(input, 1000))
    println(Day08.part2(input))
}
