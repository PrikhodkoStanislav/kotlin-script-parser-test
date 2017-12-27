package hello

import java.io.BufferedReader
import java.io.File

object Id {
    fun id(): Int {
        value++
        return value
    }
    var value = 0
}

object FileToWrite {
    fun file(): File = File(dest)
    val dest = "features.txt"
}

object SnipToFile {
    fun file(destFolder: String, name: String, id: String): File = File(destFolder + "\\" + name + "_" + id + ".kt")
}

fun main(args: Array<String>) {
    startExtract()
}

fun startExtract() {

//    val dest = "features.txt"
//
//    val file = File(dest)

    FileToWrite.file().writeText("")

    val folder = "files"
    val snipFolder = "snippets"
    createSnippets(folder, snipFolder)

    walkInFolder(snipFolder)
}

fun createSnippets(folder: String, snipFolder: String) {
    File(folder).listFiles().forEach {
        val path = it.path
        path.replace(' ', '_')
        if (it.isFile && path.endsWith(".kt"))
            extractSnippets(path, snipFolder)
        if (it.isDirectory)
            createSnippets(path, snipFolder)
    }
}

fun extractSnippets(path: String, snipFolder: String) {
    val bufferedReader: BufferedReader = File(path).bufferedReader()

    val lineList = mutableListOf<String>()

    bufferedReader.useLines { lines -> lines.forEach { lineList.add(it) } }

    val size = lineList.size

    var snipId = 1

    var numBrackets = 0

    var i = 0
    while (i < size) {
        val line = lineList[i]
        if (line.contains("fun")) {
            var fileName = ""
            val startName = path.lastIndexOf('\\') + 1
            val finishName = path.lastIndexOf('.')
            if (startName != 0 && finishName != -1)
                fileName = path.substring(startName, finishName)
            else
                fileName = path
            SnipToFile.file(snipFolder, fileName, snipId.toString()).writeText(line)
            SnipToFile.file(snipFolder, fileName, snipId.toString()).appendText("\n")

            line.forEach {
                when (it) {
                    '{' -> numBrackets++
                    '}' -> numBrackets--
                }
            }
            while (numBrackets != 0 && i < size - 1) {
                i++
                val theLine = lineList[i]
                theLine.forEach {
                    when (it) {
                        '{' -> numBrackets++
                        '}' -> numBrackets--
                    }
                }
                SnipToFile.file(snipFolder, fileName, snipId.toString()).appendText(theLine)
                SnipToFile.file(snipFolder, fileName, snipId.toString()).appendText("\n")
            }
            snipId++
        }
        i++
    }
}

fun walkInFolder(folder: String) {
    File(folder).listFiles().forEach {
        val path = it.path
        if (it.isFile && path.endsWith(".kt"))
            extractFeatures(path)
        if (it.isDirectory)
            walkInFolder(path)
    }
}

fun extractFeatures(path: String) {

//    val path = "test.kt"

    val bufferedReader: BufferedReader = File(path).bufferedReader()

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

    // May be -1
    val numNewLines = lineList.size - 1

    // length with new-line symbol, may be -1
    val length = lengthWithoutNewLine + numNewLines

//    println(path)
//    println(length)
//    println(lengthWithoutNewLine)
//    println(numNewLines)

    val whiteSpaceRatio = numSpaces + numTabs + numNewLines

//    println(numSpaces)
//    println(numTabs)
//    println(numNewLines)
//    println(numEmptyLines)
//    println(numTabsLeadLines)
//    println(whiteSpaceRatio)
//    println(length + numNewLines - whiteSpaceRatio)

    val featureNumTabs = if (numTabs == 0 || length <= 0) 0.0 else Math.log(numTabs.toDouble() / length)
    val featureNumSpaces = if (numSpaces == 0 || length <= 0) 0.0 else Math.log(numSpaces.toDouble() / length)
    val featureNumEmptyLines = if (numEmptyLines == 0 || length <= 0) 0.0 else Math.log(numEmptyLines.toDouble() / length)
    val featureWhiteSpaceRatio = if (length <= whiteSpaceRatio) 0.0 else (whiteSpaceRatio.toDouble() / (length - whiteSpaceRatio))
    val featureNumTabsLeadLines = if (numTabsLeadLines * 2 >= numNewLines) 1.0 else 0.0
    val featureAvgLineLength = if (numNewLines <= 0) 0.0 else (length.toDouble() / numNewLines)

    val listFeatures = mutableListOf(featureNumTabs, featureNumSpaces, featureNumEmptyLines, featureWhiteSpaceRatio,
            featureNumTabsLeadLines, featureAvgLineLength)

    FileToWrite.file().appendText(Id.id().toString())

    // Add features from PSI
    listFeatures.addAll(FeatureExtractorPSI().featuresFromPSI(path))

    listFeatures.forEach {
        FileToWrite.file().appendText("\t")
        FileToWrite.file().appendText(it.toString())
    }

    FileToWrite.file().appendText("\n")

//    println("=====")
//
//    println(path)
//    println(Id.id())
//    println()
//
//    println(featureNumTabs)
//    println(featureNumSpaces)
//    println(featureNumEmptyLines)
//    println(featureWhiteSpaceRatio)
//    println(featureNumTabsLeadLines)
//
//    println("=====")
}