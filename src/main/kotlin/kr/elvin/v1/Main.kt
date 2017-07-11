package kr.elvin.v1

import java.io.File
import java.io.FileInputStream
import java.nio.ByteBuffer
import kotlin.system.exitProcess

/**
 * Created by kyoung-hoonhan on 2017. 7. 11..
 */

fun analyze(file: File) {
//    val file = File(args[0])

    val channel = FileInputStream(file).channel

    val buffer = ByteBuffer.allocate(file.length().toInt())
    channel.read(buffer)

    //flip() puts us back at the start of the buffer, ready to read
    buffer.flip()

    //ID3 tags occupy the last 128 bytes of the file
    buffer.position(file.length().toInt() - 128)

    val tagArray = ByteArray(3)
    buffer.get(tagArray)
    val tag = String(tagArray).trim { char -> char.isWhitespace() or (char == 0x00.toChar()) }
    if(tag != "TAG") exitProcess(1)

    val titleArray = ByteArray(30)
    buffer.get(titleArray)
    val title = String(titleArray).trim { char -> char.isWhitespace() or (char == 0x00.toChar()) }

    val artistArray = ByteArray(30)
    buffer.get(artistArray)
    val artist = String(artistArray).trim { char -> char.isWhitespace() or (char == 0x00.toChar()) }

    val albumArray = ByteArray(30)
    buffer.get(albumArray)
    val album = String(albumArray).trim { char -> char.isWhitespace() or (char == 0x00.toChar()) }

    val yearArray = ByteArray(4)
    buffer.get(yearArray)
    val year = String(yearArray).trim { char -> char.isWhitespace() or (char == 0x00.toChar()) }

    //mark() sets a point we can reset() to later
    buffer.mark()

    //skip ahead and get the 29th byte
    buffer.position(buffer.position() + 28)
    val zeroByte = buffer.get()

    //go back to where we were
    buffer.reset()

    //if it's zero, this is (or might be) ID3 v1.1, and the comment is 28 bytes
    val comment = if(zeroByte.toInt() == 0x00) {
        val commentArray = ByteArray(28)
        buffer.get(commentArray)
        String(commentArray).trim { char -> char.isWhitespace() or (char == 0x00.toChar()) }
    } else {
        //if it's not zero, it's definitely ID3 v1, and the comment is 30 bytes
        val commentArray = ByteArray(30)
        buffer.get(commentArray)
        String(commentArray).trim { char -> char.isWhitespace() or (char == 0x00.toChar()) }
    }

    //for ID3 v1.1, we get a byte for the track number after the comment
    val trackNumber = if(zeroByte.toInt() == 0x00) {
        buffer.position(buffer.position() + 1)
        buffer.get().toInt()
    } else {
        0
    }

    val genre = buffer.get().toInt()

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