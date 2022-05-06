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
    fun `extract from dependencies gradle to update all`() {
        val varsFull = "appcompat          : \"androidx.appcompat:appcompat:1.4.1\",\n" +
                "            coreKtx            : \"androidx.core:core-ktx:1.5.0\",\n" +
                "            constraintLayout   : \"androidx.constraintlayout:constraintlayout:2.0.4\",\n" +
                "            cardView           : \"androidx.cardview:cardview:1.0.0\",\n" +
                "            recyclerView       : \"androidx.recyclerview:recyclerview:1.2.0\",\n" +
                "            lifecycleViewModel : \"androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1\",\n" +
                "            lifecycleScope     : \"androidx.lifecycle:lifecycle-runtime-ktx:2.4.0-alpha01\",\n" +
                "            lifecycleExtensions: \"androidx.lifecycle:lifecycle-extensions:2.2.0\",\n" +
                "            lifecycleCommon    : \"androidx.lifecycle:lifecycle-common-java8:2.3.1\",\n" +
                "            viewpager2         : \"androidx.viewpager2:viewpager2:1.0.0\",\n" +
                "            exifInterface      : \"androidx.exifinterface:exifinterface:1.3.2\",\n" +
                "            roomRuntine        : \"androidx.room:room-runtime:2.3.0\",\n" +
                "            roomRxJava         : \"androidx.room:room-rxjava2:2.2.0\",\n" +
                "            browser            : \"androidx.browser:browser:1.3.0\",\n" +
                "            dynamicAnimation   : \"androidx.dynamicanimation:dynamicanimation:1.0.0\",\n" +
                "            cameraCore         : \"androidx.camera:camera-core:1.0.0-beta04\",\n" +
                "            camera2            : \"androidx.camera:camera-camera2:1.0.0-beta04\",\n" +
                "            installReferrer    : \"com.android.installreferrer:installreferrer:2.2\""
        val varsFullSpllitPrefix = varsFull.replace(",", ":").split(":")
        var organizeSpllittedVersion = StringBuilder()
        for (i in varsFullSpllitPrefix.indices) {
            if (i == 0) {
                continue
            }
            if (i % 4 == 0) {
                organizeSpllittedVersion = StringBuilder(organizeSpllittedVersion.dropLast(1))
                organizeSpllittedVersion.append(",")
            } else {
                organizeSpllittedVersion.append("${varsFullSpllitPrefix[i].trim()}:")
            }
        }
        val varsSeparateVersionAppended = organizeSpllittedVersion.dropLast(1).split(",")
        for (i in varsSeparateVersionAppended) {
            println("implementation $i")
        }
    }
}
