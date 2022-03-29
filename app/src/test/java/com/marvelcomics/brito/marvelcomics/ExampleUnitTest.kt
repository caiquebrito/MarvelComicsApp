package com.marvelcomics.brito.marvelcomics

import br.com.concrete.canarinho.formatador.FormatadorValor
import junit.framework.Assert.assertEquals
import org.junit.Assert
import org.junit.Test
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.roundToInt

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
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
    fun zeroTestCanarinho() {
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
                val amountDividedByHundred = amount / 100.0
                val formattedWithSymbol =
                    FormatadorValor.VALOR_COM_SIMBOLO.formata(amountDividedByHundred.toString())
                val removedSymbol =
                    FormatadorValor.VALOR_COM_SIMBOLO.desformata(formattedWithSymbol).toDouble()
                val removedSymbolMultHundred = removedSymbol * 100.0
                val removedSymbolMultAsInt = removedSymbolMultHundred.roundToInt()

                Assert.assertTrue(
                    "Big decimal fail $amount diff $removedSymbolMultAsInt",
                    amount == removedSymbolMultAsInt
                )
            }
    }

    @Test
    fun hiker_functions() {
        val sequence = arrayOf(-2, 3, 12, -5, 0, -2)
        assertEquals(-5, minimun(sequence))
        assertEquals(12, maximun(sequence))
        assertEquals(1, med(sequence))
        assertEquals(6, sizeOfArray(sequence))
    }

    fun minimun(sequence: Array<Int>): Int {
        var currentMin = 0
        for (i in sequence.indices) {
            if (currentMin > sequence[i]) {
                currentMin = sequence[i]
            }
        }
        return currentMin
    }

    fun maximun(sequence: Array<Int>): Int {
        var currentMax = 0
        sequence.forEach { value ->
            if (currentMax < value) {
                currentMax = value
            }
        }
        return currentMax
    }

    fun sizeOfArray(sequence: Array<Int>): Int {
        var count = 0
        for (i in 0..sequence.size) {
            count = i
        }
        return count
    }

    fun med(sequence: Array<Int>): Int {
        var sumAll = 0
        for (value in sequence) {
            sumAll += value
        }
        return sumAll / sequence.size
    }
}
