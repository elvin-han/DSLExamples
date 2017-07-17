package kr.elvin.part3.step3

import java.io.File

/**
 * Created by kyoung-hoonhan on 2017. 7. 14..
 */
object Main {
    inline fun <reified T: BinaryFile> binaryFile(file: File) {
        val binaryFile = T::class.constructors.first().call(file)
        binaryFile.parse()
    }

    fun main(file: File) {
        binaryFile<ZipFile>(file)
    }
}