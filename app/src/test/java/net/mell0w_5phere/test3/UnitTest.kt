package net.mell0w_5phere.test3

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class UnitTest {
    @Test
    fun test_cup() {
        assertEquals("Cup: Q", calcCupLetter(122f, 70f))
        assertEquals("Cup: Under A", calcCupLetter(79.9f, 70f))
        assertEquals("Cup: Over Z", calcCupLetter(137.5f, 60f))
        assertEquals(null, calcCupLetter(60f, 0f))
        assertEquals(null, calcCupLetter(60f, 70f))
    }

    @Test
    fun test_under() {
        assertEquals("Under: 72.5 ~ 75.0", calcUnder(85f, 10f))
        assertEquals(null, calcUnder(70f, 70f))
        assertEquals(null, calcUnder(70f, 0f))
    }

    @Test
    fun test_top() {
        assertEquals("Top: 80.0 ~ 82.5", calcTop(70f, 10f))
        assertEquals(null, calcTop(0f, 10f))
        assertEquals(null, calcTop(70f, 0f))
    }
}