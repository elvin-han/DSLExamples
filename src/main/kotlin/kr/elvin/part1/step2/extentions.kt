package kr.elvin.part1.step2

import java.nio.ByteBuffer

/**
 * Created by kyoung-hoonhan on 2017. 7. 13..
 */
fun ByteBuffer.string(length: Int): String {
    val array = ByteArray(length)
    this.get(array)
    return String(array).trim { char -> char.isWhitespace() or (char == 0x00.toChar()) }
}

fun ByteBuffer.byte(): Int = get().toInt()

fun ByteBuffer.skip(length: Int) = position(position() + length)

fun ByteBuffer.peek(skipLength: Int, callback: ByteBuffer.() -> Any): Any {
    mark()
    skip(skipLength)
    return callback().apply {
        reset()
    }
}
