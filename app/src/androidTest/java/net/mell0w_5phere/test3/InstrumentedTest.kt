package net.mell0w_5phere.test3

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTextReplacement
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.mell0w_5phere.test3.ui.theme.Test3Theme
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class InstrumentedTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun test_cup_calc() {
        composeTestRule.setContent {
            Test3Theme {
                App()
            }
        }
        val node_u = composeTestRule.onNodeWithText("Under")
        node_u.performTextInput("70")
        node_u.assert(hasText("70"))
        val node_t = composeTestRule.onNodeWithText("Top")
        node_t.performTextInput("122")
        node_t.assert(hasText("122"))
        composeTestRule.onNodeWithText("Cup: Q", useUnmergedTree = true)
            .assertExists("node not found")

    }

    @Test
    fun test_single_letter_input() {
        composeTestRule.setContent {
            Test3Theme {
                App()
            }
        }
        composeTestRule.onNode(hasTestTag("button_top")).performClick()
        val node_c = composeTestRule.onNodeWithText("Cup")
        node_c.performTextInput("Question")
        node_c.assert(hasText("Q"))
        node_c.performTextReplacement("voltage")
        node_c.assert(hasText("V"))
    }
}