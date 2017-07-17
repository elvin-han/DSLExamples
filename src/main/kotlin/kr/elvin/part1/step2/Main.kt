package kr.elvin.part1.step2

import java.io.File
import java.io.FileInputStream
import java.nio.ByteBuffer
import kotlin.system.exitProcess

/**
 * Created by kyoung-hoonhan on 2017. 7. 11..
 */
object Main {
    fun binaryFile(file: File, callback: ByteBuffer.() -> Unit) {
        val channel = FileInputStream(file).channel

        val buffer = ByteBuffer.allocate(file.length().toInt())
        channel.read(buffer)

        //flip() puts us back at the start of the buffer, ready to read
        buffer.flip()
        buffer.callback()
    }

    fun main(file: File) {
//    val file = File(args[0])

        binaryFile(file) {

            //ID3 tags occupy the last 128 bytes of the file
            position(file.length().toInt() - 128)

            val tag = string(3)
            if (tag != "TAG") exitProcess(1)

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
                position(position() + 1)
                get().toInt()
            } else {
                0
            }

            val genre = get().toInt()

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