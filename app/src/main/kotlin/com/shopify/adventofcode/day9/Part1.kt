package com.shopify.adventofcode.day9

/**
 * Created by cavedon on 2017-06-01.
 */
fun main(args: Array<String>) {

    println(expandString(decompress(input), input).length)

}

fun expandString(markers: List<Pair<Marker, CharSequence>>, compressed: String): String {

    var temp = compressed
    for ((marker, sequence) in markers) {
        temp = temp.replaceFirst(marker.value.toString(), sequence.repeat(marker.repeat)).replaceFirst(sequence.toString(), "")
    }

    return temp
}

fun decompress(input: String): List<Pair<Marker, CharSequence>> {
    var i = 0
    val originalSize = input.length

    val decompressedStrings = ArrayList<Pair<Marker, CharSequence>>()

    while (true) {
        val currSubseq = input.subSequence(i, originalSize)
        val matchResult = "\\d+x\\d+".toRegex().find(currSubseq)
        if (matchResult != null && matchResult.groups.isNotEmpty()) {
            val matchedMarker = "(${matchResult.groups[0]!!.value})"
            val marker = Marker(currSubseq.indexOf(matchedMarker), matchedMarker)

            val target = currSubseq.subSequence(marker.range)

            decompressedStrings.add(Pair(marker, target))

            i += marker.seekEnd() + (currSubseq.subSequence(0, currSubseq.indexOf(matchedMarker)).length)

        } else break

    }
    return decompressedStrings
}

data class Marker(val idx: Int, var value: CharSequence) {

    var markerSplit = ArrayList<String>()

    init {
        markerSplit.addAll(value.split("(", "x", ")").filter { it != "" })
    }

    val index: Int
        get() {
            return idx + value.length
        }

    val range: IntRange
        get() {
            return IntRange(index, index + charCount - 1)
        }

    val charCount: Int
        get() {
            return markerSplit[0].toInt()
        }

    val repeat: Int
        get() {
            return markerSplit[1].toInt()
        }

    fun seekEnd(): Int {
        return charCount + value.length
    }

}