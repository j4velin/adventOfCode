package aoc2025

import PointL
import readInput
import withEachOf
import kotlin.math.abs
import kotlin.math.min

object Day09 {
    fun part1(input: List<String>): Long {
        val redTiles =
            input.map { it.split(",", limit = 2) }.map { PointL(x = it.first().toLong(), y = it.last().toLong()) }
        return redTiles.withEachOf(redTiles).maxOf { (t1, t2) -> (abs(t1.x - t2.x) + 1) * (abs(t1.y - t2.y) + 1) }
    }

    fun part2(input: List<String>): Long {
        val redTiles =
            input.map { it.split(",", limit = 2) }.map { PointL(x = it.first().toLong(), y = it.last().toLong()) }
        val greenTilesBorder = (redTiles + redTiles.first()).windowed(size = 2, step = 1).flatMap { reds ->
            val t1 = reds.first()
            val t2 = reds.last()
            if (t1.y == t2.y) {
                (min(t1.x, t2.x) + 1L..<maxOf(t1.x, t2.x)).map { PointL(x = it, y = t1.y) }
            } else if (t1.x == t2.x) {
                (min(t1.y, t2.y) + 1L..<maxOf(t1.y, t2.y)).map { PointL(x = t1.x, y = it) }
            } else throw IllegalArgumentException("Red tiles do not share same x or y coordinate: $t1 and $t2")
        }
        val borderTiles =
            redTiles.withEachOf(redTiles)
                .filter { (t1, t2) -> t1 != t2 }
                .sortedByDescending { (t1, t2) -> (abs(t1.x - t2.x) + 1) * (abs(t1.y - t2.y) + 1) }
                .asSequence()
                // optimization: if there is a red tile within this rectangle, then there must be a path of green border
                // tiles leading from and to it -> skip
                .filter { (t1, t2) ->
                    redTiles.filter { it.x != t1.x && it.x != t2.x && it.y != t1.y && it.y != t2.y }
                        .none { it.isWithin(t1 to t2) }
                }
                // if there are border tiles within the rectangle, it can not consist fully of green tiles -> skip
                .filter { (t1, t2) ->
                    greenTilesBorder.filter { it.x != t1.x && it.x != t2.x && it.y != t1.y && it.y != t2.y }
                        .none { it.isWithin(t1 to t2) }
                }
                .first()
        return (abs(borderTiles.first.x - borderTiles.second.x) + 1) * (abs(borderTiles.first.y - borderTiles.second.y) + 1)
    }
}

fun main() {
    val testInput = readInput("Day09_test", 2025)
    check(Day09.part1(testInput) == 50L)
    check(Day09.part2(testInput) == 24L)

    val input = readInput("Day09", 2025)
    println(Day09.part1(input))
    println(Day09.part2(input))
}
