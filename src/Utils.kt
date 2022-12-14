import java.io.File
import java.math.BigInteger
import java.security.MessageDigest
import java.util.*
import kotlin.math.abs
import kotlin.math.absoluteValue

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String, year: Int = 2021) = File("src/aoc$year", "$name.txt")
    .readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')


infix fun Int.modulo(mod: Int) = if (this > mod) this % mod else this
infix fun Long.modulo(mod: Long) = if (this > mod) this % mod else this

/**
 * Generates a "cartesian product" of two sequences
 *
 * @param other the sequence to 'mix' with this
 * @return a sequence of the cartesian product of [this] and [other]
 */
fun <T, U> Sequence<T>.withEachOf(other: Sequence<U>): Sequence<Pair<T, U>> = flatMap { t -> other.map { u -> t to u } }

/**
 * Data class representing a point on a 2D area
 */
data class Point(val x: Int, val y: Int) {

    /**
     * @param withDiagonal true to also include diagonal neighbours
     * @param validGrid optional area (pair of bottom left and upper right corner) in which the neighbours must be within
     * @return the neighbouring points of this point
     */
    fun getNeighbours(withDiagonal: Boolean = false, validGrid: Pair<Point, Point>? = null) = buildSet {
        for (i in -1..1) {
            for (j in -1..1) {
                if ((i.absoluteValue == j.absoluteValue) && (i == 0 || !withDiagonal)) {
                    continue
                }
                val p = Point(x + i, y + j)
                if (validGrid == null || p.isWithin(validGrid)) {
                    add(p)
                }
            }
        }
    }

    infix operator fun plus(other: Point) = move(other.x, other.y)

    /**
     * @param dx the delta in x direction
     * @param dy the delta in y direction
     * @return the new resulting point, which is created by moving this point along the given vector
     */
    fun move(dx: Int, dy: Int) = Point(x + dx, y + dy)

    private fun isWithin(grid: Pair<Point, Point>) =
        x >= grid.first.x && x <= grid.second.x && y >= grid.first.y && y <= grid.second.y

    fun distanceTo(other: Point) = abs(x - other.x) + abs(y - other.y)
}

/**
 * Splits a list into a list of lists by separating the original elements whenever an element matches the given predicate.
 * The matched element is *NOT* added to any sublist!
 *
 * @param predicate lambda to apply on an element to test if it should be a separator element
 * @return the list split into a list of lists by separating them at specific separator elements
 */
fun <T> List<T>.separateBy(predicate: (T) -> Boolean): List<List<T>> {
    val list = mutableListOf<List<T>>()
    var sublist = mutableListOf<T>()
    forEach {
        if (predicate(it)) {
            list.add(sublist)
            sublist = mutableListOf()
        } else {
            sublist.add(it)
        }
    }
    list.add(sublist)
    return list
}

/**
 * Finds a random common item in the given collections or throws a [NoSuchElementException] if there is none
 */
fun <T> findCommon(vararg collections: Collection<T>): T {
    var intersect = collections.first().toSet()
    collections.drop(1).map { it.toSet() }.forEach { intersect = intersect.intersect(it) }
    return intersect.first()
}

fun <T> Stack<T>.popTo(other: Stack<T>, count: Int = 1) {
    repeat(count) {
        val element = this.pop()
        other.add(element)
    }
}