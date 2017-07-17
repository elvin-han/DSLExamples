package kr.elvin.part2

import java.io.File
import java.io.FileInputStream
import java.nio.ByteBuffer

/**
 * Created by kyoung-hoonhan on 2017. 7. 14..
 */

class BinaryFile(file: File) {
    val buffer: ByteBuffer
    private val valueMap = mutableMapOf<String, Any>()
    private val positionMap = mutableMapOf<String, Int>()
    private var lastPos = 0

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

    fun skip(length: Int) {
        buffer.position(buffer.position() + length)
        lastPos = buffer.position()
    }

    fun peek(length: Int, callback: BinaryFile.() -> Any): Any {
        buffer.mark()
        skip(length)
        return callback().apply {
            buffer.reset()
            lastPos = buffer.position()
        }
    }

    fun jump(pos: Int) {
        buffer.position(pos)
        lastPos = pos
    }

    fun fileLength() = buffer.limit()

    fun literalString(expected: String): String {
        val actual = string(expected.toByteArray().size)
        if(actual != expected) throw RuntimeException("Expected literal $expected, found $actual")
        return actual
    }

    operator fun Any.rangeTo(name: String): Any {
        valueMap.put(name, this)
        positionMap.put(name, lastPos)
        lastPos = buffer.position()
        return this
    }

    operator fun String.unaryPlus(): Any? {
        return valueMap[this]
    }

    operator fun String.not(): Int {
        return positionMap[this] ?: throw RuntimeException("Variable $this not found")
    }
}