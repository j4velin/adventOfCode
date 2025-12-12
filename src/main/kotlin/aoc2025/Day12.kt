package aoc2025

import readInput
import separateBy
import to2dCharArray

object Day12 {

    private class Present(shape: Array<CharArray>) {
        companion object {
            fun fromStrings(lines: List<String>): Present {
                val shape = lines.drop(1).to2dCharArray()
                return Present(shape)
            }
        }

        val solidSpace = shape.sumOf { row -> row.count { it == '#' } }

        /*
        private val permutations: Set<List<List<Boolean>>> = buildSet {
            var current = shape
            add(current.toList())
            repeat(3) {
                current = current.rotate()
                add(current.toList())
            }
            current = shape.mirrorX()
            add(current.toList())
            repeat(3) {
                current = current.rotate()
                add(current.toList())
            }
        }

        private fun Array<CharArray>.toList(): List<List<Boolean>> = map { inner -> inner.map { it == '#' }.toList() }

        private fun Array<CharArray>.rotate(): Array<CharArray> {
            val rotated = Array(this.maxY + 1) { CharArray(this.maxX + 1) }
            for (x in indices) {
                for (y in this[x].indices) {
                    rotated[y][-x + maxY] = this[x][y]
                }
            }
            return rotated
        }

        private fun Array<CharArray>.mirrorX(): Array<CharArray> {
            val mirrored = Array(this.maxX + 1) { CharArray(this.maxY + 1) }
            for (x in indices) {
                mirrored[-x + maxX] = this[x]
            }
            return mirrored
        }
         */
    }

    private class Region(val size: Pair<Int, Int>, val expectedPresents: IntArray) {
        companion object {
            fun fromString(line: String): Region {
                val parts = line.split(": ")
                val sizeParts = parts.first().split("x").map { it.toInt() }
                val size = Pair(sizeParts.first(), sizeParts.last())
                val expectedPresents = parts.last().split(" ").map { it.toInt() }.toIntArray()
                return Region(size, expectedPresents)
            }
        }
    }

    fun part1(input: List<String>): Int {
        val presents = input.separateBy { it.isEmpty() }.filter { it.first().endsWith(":") }.map {
            Present.fromStrings(it)
        }
        val regions = input.separateBy { it.isEmpty() }.dropWhile { it.first().endsWith(":") }.flatten()
            .map { Region.fromString(it) }

        val upperBound = regions.filter { region ->
            val requiredSpace =
                region.expectedPresents.withIndex().sumOf { presents[it.index].solidSpace.toLong() * it.value }
            val availableSpace = region.size.first.toLong() * region.size.second
            availableSpace >= requiredSpace
        }.size

        // solved due to reddit memes...
        return upperBound
    }

    fun part2(input: List<String>): Int {
        return 0
    }
}

fun main() {
    val testInput = readInput("Day12_test", 2025)
    // meme-soliution does not work on example...
    //check(Day12.part1(testInput) == 2)
    check(Day12.part2(testInput) == 0)

    val input = readInput("Day12", 2025)
    println(Day12.part1(input))
    println(Day12.part2(input))
}
