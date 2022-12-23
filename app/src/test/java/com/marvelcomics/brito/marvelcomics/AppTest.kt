package com.marvelcomics.brito.marvelcomics

import junit.framework.Assert.assertEquals
import org.junit.Assert
import org.junit.Test
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class AppTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun zeroTest() {
        listOf(
            431536,
            28696,
            6760,
            482944,
            13745,
            216968,
            13739,
            64618,
            25796,
            258799,
            103859,
            13742,
            61868,
            29134,
            118661,
            206993,
            4080,
            64993,
            14120,
            438840,
            59867,
            113284,
            64280,
            16239,
            27684,
            255945,
            30759,
            28153,
            1999,
            218472,
            130786,
            32215,
            113187,
            213524,
            31815,
            7732,
            29990,
            460319,
            30534,
            13245,
            118460,
            103335,
            16039,
            935822,
            26396,
            6999,
            14939,
            13648,
            32203,
            14698
        )
            .map { amount ->
                val bigDecimalResult =
                    BigDecimal(amount)
                        .divide(BigDecimal(100), 2, RoundingMode.HALF_DOWN)
                        .multiply(BigDecimal(100))
                        .toInt()

                Assert.assertTrue(
                    "Big decimal fail $amount diff $bigDecimalResult",
                    amount == bigDecimalResult
                )
            }
    }

    @Test
    fun jefTest() {
        val doubleValue = 322.46
        val doubleValueLong = 32246L
        val bigDecimalResult = doubleValue.multiplyBigDecimal().toLong()

        Assert.assertTrue(
            "Big decimal fail ${doubleValue.toLong()} diff $bigDecimalResult",
            doubleValueLong == bigDecimalResult
        )
    }

    fun Double.multiplyBigDecimal(multVal: Int = 100, scale: Int = 1): BigDecimal {
        return BigDecimal(this).multiply(BigDecimal(multVal)).setScale(scale, RoundingMode.HALF_UP)
    }

    @Test
    fun testItem() {
        val objCreated = tryOrNull {
            EmptyItemTest(loading = true)
        }
        val objThrown = tryOrNull {
            throw Exception("Anything")
        }
        assertEquals(EmptyItemTest::class.java, objCreated)
        assertEquals(null, objThrown)
    }

    data class EmptyItemTest(val loading: Boolean)

    private fun tryOrNull(blockCode: () -> Unit): Any? {
        return try {
            blockCode
        } catch (e: Exception) {
            null
        }
    }
}
