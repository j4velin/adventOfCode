package aoc2025

import PointL
import maxX
import maxY
import readInput
import to2dCharArray

object Day04 {

    private data class RemovalResult(val removedCount: Int, val newGrid: Array<CharArray>)

    private fun removePapers(grid: Array<CharArray>): RemovalResult {
        var removedPaperRolls = 0
        val validGrid: Pair<PointL, PointL> = PointL(0, 0) to PointL(grid.maxX, grid.maxY)
        val newGrid = Array(grid.size) { CharArray(grid.size) { '.' } }
        for (x in grid.indices) {
            for (y in grid[x].indices) {
                newGrid[x][y] = grid[x][y]
                if (grid[x][y] == '@') {
                    val point = PointL(x, y)
                    val paperNeighbourCount =
                        point.getNeighbours(withDiagonal = true, validGrid = validGrid)
                            .filter { grid[it.x.toInt()][it.y.toInt()] == '@' }.size
                    if (paperNeighbourCount < 4) {
                        removedPaperRolls++
                        newGrid[x][y] = '.'
                    }
                }
            }
        }
        return RemovalResult(removedCount = removedPaperRolls, newGrid = newGrid)
    }

    fun part1(input: List<String>): Int {
        val grid = input.to2dCharArray()
        return removePapers(grid).removedCount
    }

    fun part2(input: List<String>): Int {
        var grid = input.to2dCharArray()
        var totalRemoved = 0
        do {
            val result = removePapers(grid)
            totalRemoved += result.removedCount
            grid = result.newGrid
        } while (result.removedCount > 0)
        return totalRemoved
    }
}

fun main() {
    val testInput = readInput("Day04_test", 2025)
    check(Day04.part1(testInput) == 13)
    check(Day04.part2(testInput) == 43)

    val input = readInput("Day04", 2025)
    println(Day04.part1(input))
    println(Day04.part2(input))
}
