package aoc2025

import readInput

object Day11 {

    private data class Device(val name: String, val connections: List<String>)

    private fun getPathsRec(
        current: String,
        devices: Map<String, Device>,
        end: String = "out",
        cache: MutableMap<String, Long> = mutableMapOf(),
    ): Long {
        return if (current == end) {
            1L
        } else {
            cache.getOrPut(current) {
                devices[current]?.connections?.sumOf { next -> getPathsRec(next, devices, end, cache) } ?: 0L
            }
        }
    }

    fun part1(input: List<String>): Int {
        val devicesByName = input.associate {
            val split = it.split(": ")
            val name = split.first()
            val connections = split.last().split(" ")
            name to Device(name, connections)
        }
        return getPathsRec("you", devicesByName).toInt()
    }

    fun part2(input: List<String>): Long {
        val devicesByName = input.associate {
            val split = it.split(": ")
            val name = split.first()
            val connections = split.last().split(" ")
            name to Device(name, connections)
        }
        val svrToDac = getPathsRec(
            "svr",
            devicesByName,
            "dac"
        )

        val svrToFft = getPathsRec(
            "svr",
            devicesByName,
            "fft"
        )

        val dacToFft = if (svrToDac > 0) getPathsRec(
            "dac",
            devicesByName,
            "fft"
        ) else 0

        val fftToDac = if (svrToFft > 0) getPathsRec(
            "fft",
            devicesByName,
            "dac"
        ) else 0

        val dacToOut = if (fftToDac > 0) getPathsRec(
            "dac",
            devicesByName
        ) else 0

        val fftToOut = if (dacToFft > 0) getPathsRec(
            "fft",
            devicesByName
        ) else 0

        return svrToDac * dacToFft * fftToOut + svrToFft * fftToDac * dacToOut
    }
}

fun main() {
    val testInput = readInput("Day11_test", 2025)
    val testInput2 = readInput("Day11_test_2", 2025)
    check(Day11.part1(testInput) == 5)
    check(Day11.part2(testInput2) == 2L)

    val input = readInput("Day11", 2025)
    println(Day11.part1(input))
    println(Day11.part2(input))
}
