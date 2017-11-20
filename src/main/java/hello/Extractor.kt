package hello

import java.io.BufferedReader
import java.io.File

fun main(args: Array<String>) {
    startExtract()
}

object Id {
    fun id(): Int {
        value++
        return value
    }
    var value = 0
}

fun startExtract() {

    val folder = "files"

    walkInFolder(folder)
}

fun walkInFolder(folder: String) {

    for ( file in File(folder).listFiles()) {
        val path = file.path
        if (file.isFile && path.endsWith(".kt")) {
            extractFeatures(path)
        }
        if (file.isDirectory)
            walkInFolder(path)
    }
}

fun extractFeatures(file: String) {

//    val file = "test.kt"

    val bufferedReader: BufferedReader = File(file).bufferedReader()

    val lineList = mutableListOf<String>()

    bufferedReader.useLines { lines -> lines.forEach { lineList.add(it) } }

//    val inputString = bufferedReader.use { it.readText() }
//    println(inputString)

//    val length = inputString.length

    var numSpaces = 0
    var numTabs = 0
    var numEmptyLines = 0
    var numTabsLeadLines = 0
    var lengthWithoutNewLine = 0

//    for (i in inputString) {
//        when(i) {
//            ' ' -> numSpaces++
//            '\t' -> numTabs++
//            '\n' -> numNewLines++
//        }
//    }

    for (line in lineList) {
        line.forEach {
            when (it) {
                ' ' -> numSpaces++
                '\t' -> numTabs++
            }
        }

        // itLingth without new-line symbol
        val itLength = line.length
        lengthWithoutNewLine += itLength

        if (itLength > 0) {
            when (line[0]) {
                ' ' -> numTabsLeadLines++
                '\t' -> numTabsLeadLines++
            }
        }
        if (itLength == 0)
            numEmptyLines++
    }

    val numNewLines = lineList.size - 1

    // length with new-line symbol
    val length = lengthWithoutNewLine + numNewLines

    val whiteSpaceRatio = numSpaces + numTabs + numNewLines

//    println(numSpaces)
//    println(numTabs)
//    println(numNewLines)
//    println(numEmptyLines)
//    println(numTabsLeadLines)
//    println(whiteSpaceRatio)
//    println(length + numNewLines - whiteSpaceRatio)

    val featureNumTabs = if (numTabs == 0) 0.0 else Math.log(numTabs.toDouble() / length)
    val featureNumSpaces = if (numSpaces == 0) 0.0 else Math.log(numSpaces.toDouble() / length)
    val featureNumEmptyLines = if (numEmptyLines == 0) 0.0 else Math.log(numEmptyLines.toDouble() / length)
    val featureWhiteSpaceRatio = whiteSpaceRatio.toDouble() / (length - whiteSpaceRatio)
    val featureNumTabsLeadLines = if (numTabsLeadLines * 2 >= numNewLines) 1 else 0

    println("=====")

    println(file)
    println(Id.id())
    println()

    println(featureNumTabs)
    println(featureNumSpaces)
    println(featureNumEmptyLines)
    println(featureWhiteSpaceRatio)
    println(featureNumTabsLeadLines)

    println("=====")
}