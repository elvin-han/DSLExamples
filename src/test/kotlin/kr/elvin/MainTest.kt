package kr.elvin

import org.junit.Before
import org.junit.Test
import java.io.File

/**
 * Created by kyoung-hoonhan on 2017. 7. 14..
 */
class MainTest {
    lateinit var file: File

    @Before
    fun beforeTest() {
        val url = Thread.currentThread().contextClassLoader.getResource("ShakingThrough.mp3")
        file = File(url.path)
    }

    @Test
    fun part1_step1() {
        kr.elvin.part1.step1.Main.main(file)
    }

    @Test
    fun part1_step2() {
        kr.elvin.part1.step2.Main.main(file)
    }

    @Test
    fun part1_step3() {
        kr.elvin.part1.step3.Main.main(file)
    }

    @Test
    fun part2() {
        kr.elvin.part2.Main.main(file)
    }

    @Test
    fun part3_step1() {
        kr.elvin.part3.step1.Main.main(file)
    }

    @Test
    fun part3_step2() {
        kr.elvin.part3.step2.Main.main(file)
    }

    @Test
    fun part3_step3() {
        val zipUrl = Thread.currentThread().contextClassLoader.getResource("archive.zip")
        val zipFile = File(zipUrl.path)
        kr.elvin.part3.step3.Main.main(zipFile)
    }
}