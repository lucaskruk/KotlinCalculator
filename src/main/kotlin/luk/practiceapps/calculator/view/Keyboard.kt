package luk.practiceapps.calculator.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import luk.practiceapps.calculator.resources.CALCULATOR_PADDING


@Composable
fun Keyboard(
    modifier: Modifier,
    mainOutput: MutableState<TextFieldValue>
) {
    Surface(modifier) {
        KeyboardKeys(mainOutput)
    }
}

@Composable
fun KeyboardKeys(mainOutput: MutableState<TextFieldValue>) {
    Row(modifier = Modifier.fillMaxSize()) {
        KeyboardLayout.forEach { keyColumn ->
            Column(modifier = Modifier.weight(1f)) {
                keyColumn.forEach { key ->
                    KeyboardKey(Modifier.weight(1f), key, mainOutput)
                }
            }
        }
    }
}

@Composable
fun KeyboardKey(modifier: Modifier, key: Key?, mainOutput: MutableState<TextFieldValue>) {
    if (key == null) {
        return EmptyKeyView(modifier)
    }
    KeyView(modifier = modifier.padding(1.dp), onClick = key.onClick?.let {
        { it(mainOutput) }
    } ?: { pressKey(key.value,mainOutput) }) {
        if (key.icon == null) {
            val textStyle = if (key.type == KeyType.COMMAND) {
                TextStyle(
                    color = MaterialTheme.colors.primary,
                    fontSize = 22.sp
                )
            } else {
                TextStyle(
                    fontFamily = FontFamily.Default,
                    fontSize = 29.sp
                )
            }
            Text(
                text = key.value,
                style = textStyle
            )
        } else {
            Icon(
                imageVector = key.icon,
                contentDescription = null,
                modifier = Modifier.size(Dp(100F)),
                tint = MaterialTheme.colors.primary
            )
        }
    }
}

fun pressKey (keyValue: String, mainOutput: MutableState<TextFieldValue>)
{val textValue = mainOutput.value.text.let {
    if (it == "0") keyValue else it + keyValue
    }
    mainOutput.value = TextFieldValue(text = textValue)
}

val KEY_BORDER_WIDTH = 1.dp
val KEY_BORDER_COLOR = Color.Gray
val KEY_ACTIVE_BACKGROUND = Color.White

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun KeyView(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    children: @Composable ColumnScope.() -> Unit
) {
    val active = remember { mutableStateOf(false) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxWidth()
            .padding(CALCULATOR_PADDING)
            .clickable(onClick = onClick)
            .background(color = if (active.value) KEY_ACTIVE_BACKGROUND else MaterialTheme.colors.background)
            .border(width = KEY_BORDER_WIDTH, color = KEY_BORDER_COLOR)
            .pointerMoveFilter(
                onEnter = {
                    active.value = true
                    false
                },
                onExit = {
                    active.value = false
                    false
                }
            ),
        content = children
    )
}

@Composable
fun EmptyKeyView(modifier: Modifier) = Box(
    modifier = modifier.fillMaxWidth()
        .background(MaterialTheme.colors.background)
        .border(width = KEY_BORDER_WIDTH, color = KEY_BORDER_COLOR)
)