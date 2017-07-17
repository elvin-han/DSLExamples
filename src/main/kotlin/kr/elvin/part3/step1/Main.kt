package kr.elvin.part3.step1

import java.io.File

/**
 * Created by kyoung-hoonhan on 2017. 7. 14..
 */
object Main {
    fun binaryFile(file: File, callback: BinaryFile.() -> Unit) {
        val binaryFile = BinaryFile(file)
        binaryFile.callback()
    }

    fun main(file: File) {
        Main.binaryFile(file, BinaryFile::asId3)
    }
}

fun BinaryFile.asId3() {
    jump(fileLength() - 128)

    literalString("TAG").."tag"

    string(30).."title"
    string(30).."artist"
    string(30).."album"
    string(4).."year"

    string(28).."comment"
    byte().."zeroByte"

    if (+"zeroByte" != 0x00) {
        jump(!"comment")
        string(30).."comment"
    } else {
        byte().."trackNumber"
    }

    byte().."genre"

    println("""
                Title: ${+"title"}
                Artist: ${+"artist"}
                Album: ${+"album"}
                Year: ${+"year"}

                Comment: ${+"comment"}
                Track Number: ${+"trackNumber"}
                Genre: ${+"genre"}
             """)
}