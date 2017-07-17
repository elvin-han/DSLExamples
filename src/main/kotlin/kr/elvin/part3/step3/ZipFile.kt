package kr.elvin.part3.step3

import java.io.File
import java.nio.ByteOrder
import java.time.LocalDateTime

/**
 * Created by kyoung-hoonhan on 2017. 7. 17..
 */
class ZipFile(file: File): BinaryFile(file) {
    override fun parse() {
        setOrder(ByteOrder.LITTLE_ENDIAN)

        int() .. "localFileHeaderSignature"
        short() .. "versionNeededToExtract"
        short() .. "generalPurposeBitFlag"
        short() .. "compressionMethod"

        msDosDateTime() .. "lastModified"

        println(lastModified)
//        println(+"lastModified")
    }

    val lastModified: String by valueMap

    fun msDosDateTime(): String {
        val timeElement = buffer.short
        val dateElement = buffer.short

        val seconds =(timeElement.toInt() and 0b0000000000011111) * 2
        val minutes = timeElement.toInt() and 0b0000011111100000 shr 5
        val hours   = timeElement.toInt() and 0b1111100000000000 shr 11

        val day   = dateElement.toInt() and 0b0000000000011111
        val month = dateElement.toInt() and 0b0000000111100000 shr 5
        val year  =(dateElement.toInt() and 0b1111111000000000 shr 9) + 1980

        return LocalDateTime.of(year, month, day, hours, minutes, seconds).toString()
    }
}