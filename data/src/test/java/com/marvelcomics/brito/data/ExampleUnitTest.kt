package com.marvelcomics.brito.data

import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val hashmapOne = HashMap<String, String>()
        val mapTwo = HashMap<String, String>() as Map<String, String>

        hashmapOne["item_teste"] = "teste"
        val mapOne = hashmapOne as Map<String, String>

        println(mapOne["item_teste"])
        println(mapTwo["item_teste"])
    }
}
