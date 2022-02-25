package luk.practiceapps.calculator

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.*
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.window.*
import luk.practiceapps.calculator.resources.lightThemeColors
import luk.practiceapps.calculator.services.calculate
import luk.practiceapps.calculator.view.DisplayPanel
import luk.practiceapps.calculator.view.Keyboard
import luk.practiceapps.calculator.view.pressKey

@OptIn(ExperimentalComposeUiApi::class)
fun main(){

    application {
        val mainOutput = remember { mutableStateOf(TextFieldValue("0")) }
        Window(
                onCloseRequest = ::exitApplication,
                title = "Luk's Kotlin Calculator",
                state = rememberWindowState(
                    position = WindowPosition(alignment = Alignment.Center),
                ),
                onKeyEvent = {
                    if (it.type == KeyEventType.KeyDown) {
                        when (it.key) {
                            Key.NumPad0, Key.Zero -> pressKey("0", mainOutput)
                            Key.NumPad1, Key.One -> pressKey("1", mainOutput)
                            Key.NumPad2, Key.Two -> pressKey("2", mainOutput)
                            Key.NumPad3, Key.Three -> pressKey("3", mainOutput)
                            Key.NumPad4, Key.Four -> pressKey("4", mainOutput)
                            Key.NumPad5, Key.Five -> pressKey("5", mainOutput)
                            Key.NumPad6, Key.Six -> pressKey("6", mainOutput)
                            Key.NumPad7, Key.Seven -> pressKey("7", mainOutput)
                            Key.NumPad8, Key.Eight -> pressKey("8", mainOutput)
                            Key.NumPad9, Key.Nine -> pressKey("9", mainOutput)
                            Key.NumPadDot, Key.Period -> pressKey(".", mainOutput)
                            Key.NumPadAdd, Key.Plus -> pressKey("+", mainOutput)
                            Key.NumPadSubtract, Key.Minus -> pressKey("-", mainOutput)
                            Key.NumPadMultiply, Key.Multiply -> pressKey("x", mainOutput)
                            Key.NumPadDivide, Key.Slash -> pressKey("÷", mainOutput)
                            Key.NumPadEnter, Key.Enter -> {val input = mainOutput.value.text
                                calculate(input)?.let { result ->
                                    mainOutput.value = TextFieldValue(text = result)
                                }}
                            Key.Backspace -> {
                                val textValue = mainOutput.value.text
                                if (textValue.isNotEmpty()) {
                                    mainOutput.value = TextFieldValue(
                                        text = if(textValue.length == 1) "0" else textValue.substring(0, textValue.length - 1)
                                    )
                                }
                            }
                            else -> false
                        }
                        true
                    } else {
                        // let other handlers receive this event
                        false
                    }}
            ) {

                MaterialTheme(colors = lightThemeColors) {
                    Column(Modifier.fillMaxHeight()) {
                        DisplayPanel(
                            Modifier.weight(1f),
                            mainOutput
                        )
                        Keyboard(
                            Modifier.weight(4f),
                            mainOutput
                        )
                    }
                }
        }
    }
}