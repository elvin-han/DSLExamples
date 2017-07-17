package kr.elvin.part1.step3

import java.io.File

object Main {
    fun binaryFile(file: File, callback: BinaryFile.() -> Unit) {
        val binaryFile = BinaryFile(file)
        binaryFile.callback()
    }

    fun main(file: File) {
//    val file = File(args[0])
        binaryFile(file) {

            //ID3 tags occupy the last 128 bytes of the file
            jump(fileLength() - 128)

            val tag = literalString("TAG")

            val title = string(30)
            val artist = string(30)
            val album = string(30)
            val year = string(4)

            val zeroByte = peek(28) {
                byte()
            }

            //if it's zero, this is (or might be) ID3 v1.1, and the comment is 28 bytes
            val comment = if (zeroByte == 0x00) {
                string(28)
            } else {
                //if it's not zero, it's definitely ID3 v1, and the comment is 30 bytes
                string(30)
            }

            //for ID3 v1.1, we get a byte for the track number after the comment
            val trackNumber = if (zeroByte == 0x00) {
                skip(1)
                byte()
            } else {
                0
            }

            val genre = byte()

            println("""
                Title: $title
                Artist: $artist
                Album: $album
                Year: $year

                Comment: $comment
                Track Number: $trackNumber
                Genre: $genre
            """)
        }
    }
}