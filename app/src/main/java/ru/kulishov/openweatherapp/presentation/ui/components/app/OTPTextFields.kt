package ru.kulishov.openweatherapp.presentation.ui.components.app

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import ru.kulishov.openweatherapp.R

@Composable
fun OTPTextFields(
    length: Int,
    onFilled: (String) -> Unit
) {
    var code: List<Char> by remember { mutableStateOf(listOf()) }
    var codeStr by remember { mutableStateOf("") }
    val focusRequester: List<FocusRequester> = remember {
        val temp = mutableListOf<FocusRequester>()
        repeat(length) {
            temp.add(FocusRequester())
        }
        temp
    }

    LaunchedEffect(code) {
        if (code.size == length) {
            Log.d("OTP", code.joinToString(""))
            onFilled(code.joinToString(""))
        }
    }

    Row(
        modifier = Modifier.height(50.dp)
    ) {
        (0 until length).forEach { index ->
            var isEnabled by remember { mutableStateOf(true) }
            Box(
                Modifier
                    .width(50.dp)
                    .height(70.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.hours_params_background),
                    contentDescription = "background",
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillWidth
                )
                OutlinedTextField(
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)

                        .focusOrder(focusRequester[index]) {

                            focusRequester.getOrNull(index + 1)

                        },
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    singleLine = true,
                    value = code.getOrNull(index)?.toString() ?: "",
                    onValueChange = { input: String ->
                        val digit = input.lastOrNull { it.isDigit() }

                        val isLastField =
                            index == code.size || (code.size < focusRequester.size && index == code.size)
                        val hasFocus = focusRequester[index].freeFocus()

                        if (!hasFocus || !isLastField) {
                            return@OutlinedTextField
                        }

                        if (digit != null) {
                            val temp = code.toMutableList()
                            if (index < temp.size) {
                                temp[index] = digit
                            } else {
                                temp.add(digit)
                            }
                            code = temp

                            if (index < focusRequester.size - 1) {
                                isEnabled = false
                                focusRequester[index + 1].requestFocus()
                            }
                        } else if (input.isEmpty() && index > 0) {
                            val temp = code.toMutableList()
                            if (index < temp.size) {
                                temp.removeAt(index)
                            }
                            code = temp
                            focusRequester[index - 1].requestFocus()
                        }
                    },
                    keyboardOptions = KeyboardOptions.Default
                        .copy(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                    visualTransformation = PasswordVisualTransformation(),
                    readOnly = index < code.size && index != (code.size - 1),
                    enabled = isEnabled
                )
            }
            Spacer(Modifier.width(15.dp))
        }
    }
}