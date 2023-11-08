package net.mell0w_5phere.test3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.mell0w_5phere.test3.ui.theme.Test3Theme
import kotlin.math.floor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleLetterInputField(
    name: String,
    value: String,
    isError: Boolean,
    enabled: Boolean,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = { v: String ->
            onValueChange(
                if (v.isEmpty()) {
                    ""
                } else {
                    v.slice(0..0)
                }
            )
        },
        label = { Text(text = name) },
        singleLine = true,
        isError = isError,
        enabled = enabled,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NumericInputField(
    name: String,
    value: String,
    isError: Boolean,
    modifier: Modifier,
    enabled: Boolean,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = name) },
        singleLine = true,
        isError = isError,
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifier
    )

}

@Composable
fun RadioButtonWrapper(
    name: String,
    selected: Boolean,
    onClick: () -> Unit,
    content: @Composable() () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    )
    {
        RadioButton(
            selected = selected,
            onClick = onClick,
            modifier = Modifier.semantics { testTag = "button_$name" }
        )
        content()
    }
}


internal fun calcCupLetter(top: Float, under: Float): String? {
    if (under <= 0f || top < under) {
        return null
    }
    val d = floor((top - under - 10) / 2.5).toInt()
    return "Cup: %s".format(
        if (d < 0) {
            "Under A"
        } else if (d >= 26) {
            "Over Z"
        } else {
            ('A' + d).toString()
        }
    )
}

internal fun calcUnder(top: Float, diff: Float): String? =
    if(top <= diff || diff <= 0f){
        null
    }else {
        "Under: %.1f ~ %.1f".format(top - diff - 2.5, top - diff)
    }


internal fun calcTop(under: Float, diff: Float): String? =
    if (under <= 0f || diff <= 0f) {
        null
    } else {
        "Top: %.1f ~ %.1f".format(under + diff, under + diff + 2.5)
    }


@Preview(showBackground = true)
@Composable
fun App() {
    var under by remember { mutableStateOf("") }
    var top by remember { mutableStateOf("") }
    var cupLetter by remember { mutableStateOf("") }
    var isErrorUnder by remember { mutableStateOf(false) }
    var isErrorTop by remember { mutableStateOf(false) }
    var isErrorCup by remember { mutableStateOf(false) }
    var target by remember { mutableStateOf("cup") }
    var underNum by remember { mutableStateOf(0f) }
    var topNum by remember { mutableStateOf(0f) }
    var diff by remember { mutableStateOf(0f) }
    Test3Theme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 32.dp)
        ) {
            RadioButtonWrapper(
                name = "under",
                selected = target == "under",
                onClick = { target = "under" }
            ) {
                NumericInputField(
                    name = "Under",
                    value = under,
                    isError = isErrorUnder,
                    enabled = target != "under",
                    modifier = Modifier
                ) {
                    under = it
                    isErrorUnder = it.toFloatOrNull() == null
                    if (!isErrorUnder) {
                        underNum = it.toFloat()
                    }
                }
            }
            RadioButtonWrapper(
                name = "top",
                selected = target == "top",
                onClick = { target = "top" }
            ) {
                NumericInputField(
                    name = "Top",
                    value = top,
                    isError = isErrorTop,
                    enabled = target != "top",
                    modifier = Modifier
                ) {
                    top = it
                    isErrorTop = it.toFloatOrNull() == null
                    if (!isErrorTop) {
                        topNum = it.toFloat()
                    }
                }
            }
            RadioButtonWrapper(
                name = "cup",
                selected = target == "cup",
                onClick = { target = "cup" }
            ) {
                SingleLetterInputField(
                    value = cupLetter,
                    name = "Cup",
                    isError = isErrorCup,
                    enabled = target != "cup"
                ) {
                    isErrorCup = it.isEmpty() || it.uppercase()[0] !in 'A'..'Z'
                    if (isErrorCup) {
                        cupLetter = it
                        diff = 0f
                    } else {
                        cupLetter = it.uppercase()
                        diff = 10 + 2.5f * (cupLetter[0] - 'A')
                    }
                }
            }

            Text(text = "Result:")

            Text(
                text = when (target) {
                    "cup" -> calcCupLetter(topNum, underNum)

                    "top" -> calcTop(underNum, diff)

                    "under" -> calcUnder(topNum, diff)

                    else -> null
                } ?: stringResource(id = R.string.invalid_message)
            )
        }
    }
}