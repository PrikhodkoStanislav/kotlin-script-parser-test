package hello

import java.io.File

fun main(args: Array<String>) {

    val folder = "..\\Snippets_Clone"

    val listFoldersWithSnippets = File(folder).listFiles()

    for (snipFolder in listFoldersWithSnippets) {
        File(snipFolder.path).listFiles().forEach {
            val path = it.path

            if (!path.contains("PSI")) {
                val result = ASTBuilder().buildAST(path)

                val startName = path.lastIndexOf('s') + 1

                //val fileName = snipFolder.path + "\\" + "PSI" + "\\" + "PSI" + path.substring(startName)
                //File(fileName).appendText(result)

                val newDirectory = File(snipFolder.path + "\\" + "PSI" + "\\")
                newDirectory.mkdirs()

                val fileName = "PSI" + path.substring(startName)

                File(newDirectory, fileName).appendText(result)
            }
        }
    }
}