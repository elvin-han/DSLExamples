package kr.elvin.part3.step2

import java.io.File

/**
 * Created by kyoung-hoonhan on 2017. 7. 14..
 */
object Main {
    // reified : By marking our generic type with the reified keyword, we can ask the compiler to keep hold of that type information.
    // inline : Nearly there, but that will also give you a compiler error. The way the compiler keeps the type information is essentially by “cutting and pasting” the code from the method everywhere that it is called, and replacing T with the type you specify in the call. So the final piece of this puzzle is to add the inline annotation to the method to allow the compiler to inline it.
    inline fun <reified T: BinaryFile> binaryFile(file: File, callback: T.() -> Unit) {

        // for reflection : compile "org.jetbrains.kotlin:kotlin-reflect:$kotlin_version"
        val binaryFile = T::class.constructors.first().call(file)
        binaryFile.callback()
    }

    fun main(file: File) {
        Main.binaryFile(file, MP3File::asId3)
    }
}