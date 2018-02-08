package hello

import java.io.File

fun main(args: Array<String>) {

    val folder = "..\\Snippets_Clone"

    val listFoldersWithSnippets = File(folder).listFiles()

    for (snipFolder in listFoldersWithSnippets) {
        val snipPath = snipFolder.path
        try {
            File(snipPath).listFiles().forEach {
                val path = it.path
                try {

                    if (!path.contains("PSI")) {
                        val result = ASTBuilder().buildAST(path)

                        val startName = path.lastIndexOf('s') + 1

                        //val fileName = snipFolder.path + "\\" + "PSI" + "\\" + "PSI" + path.substring(startName)
                        //File(fileName).appendText(result)

                        val newDirectory = File(snipPath + "\\" + "PSI" + "\\")
                        newDirectory.mkdirs()

                        val fileName = "PSI" + path.substring(startName)

                        File(newDirectory, fileName).appendText(result)
                    }
                }
                catch (e: Exception) {
                    println(path)
                    println(e.message)
                }
                catch(e: Error) {
                    println(path)
                    println(e.message)
                }
            }
        }
        catch (e: Exception) {
            println(snipPath)
            println(e.message)
        }
        catch (e: Error) {
            println(snipPath)
            println(e.message)
        }
    }
}