package kr.elvin.v1

import org.junit.Test
import java.io.File

/**
 * Created by kyoung-hoonhan on 2017. 7. 11..
 */
class MainTest {

    @Test
    fun anlayze() {
        val url = Thread.currentThread().contextClassLoader.getResource("ShakingThrough.mp3")
        analyze(File(url.path))
    }

}