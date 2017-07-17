package kr.elvin.part1.step3

import java.io.File
import java.io.FileInputStream
import java.nio.ByteBuffer

/**
 * Created by kyoung-hoonhan on 2017. 7. 14..
 */

class BinaryFile(file: File) {
    val buffer: ByteBuffer

    init {
        val channel = FileInputStream(file).channel

        buffer = ByteBuffer.allocate(file.length().toInt())
        channel.read(buffer)

        buffer.flip()
    }

    fun string(length: Int): String {
        val array = ByteArray(length)
        buffer.get(array)
        return String(array).trim { char -> char.isWhitespace() or (char == 0x00.toChar()) }
    }

    fun byte(): Int = buffer.get().toInt()

    fun skip(length: Int) = buffer.position(buffer.position() + length)

    fun peek(skipLength: Int, callback: BinaryFile.() -> Any): Any {
        buffer.mark()
        skip(skipLength)
        return callback().apply {
            buffer.reset()
        }
    }

    fun jump(pos: Int) = buffer.position(pos)

    fun fileLength() = buffer.limit()

    fun literalString(expected: String): String {
        val actual = string(expected.toByteArray().size)
        if(actual != expected) throw RuntimeException("Expected literal $expected, found $actual")
        return actual
    }
}