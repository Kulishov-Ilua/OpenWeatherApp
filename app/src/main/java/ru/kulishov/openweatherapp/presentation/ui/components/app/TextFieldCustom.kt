package ru.kulishov.openweatherapp.presentation.ui.components.app

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation


@Composable
fun TextFieldCustom(
    inpText: String,
    isError: Boolean = false,
    enable: Boolean = true,
    readOnly: Boolean = false,
    modifier: Modifier = Modifier,
    placeholder: @Composable (() -> Unit)? = null,
    label: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    minLines: Int = 1,
    interactionSource: MutableInteractionSource? = null,
    shape: Shape = TextFieldDefaults.shape,
    textStyle: TextStyle = LocalTextStyle.current,
    onUpdate: (String) -> Unit
) {
    TextField(
        value = inpText,
        onValueChange = { it -> onUpdate(it) },
        colors = TextFieldColors(
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            disabledTextColor = MaterialTheme.colorScheme.onSurface,
            errorTextColor = Color.Red,
            focusedSupportingTextColor = Color.Yellow,
            focusedLabelColor = MaterialTheme.colorScheme.onSurface,
            focusedPrefixColor = MaterialTheme.colorScheme.onSurface,
            focusedSuffixColor = MaterialTheme.colorScheme.onSurface,
            focusedContainerColor = Color.Transparent, // background
            focusedIndicatorColor = Color.Transparent, //подчеркивание
            focusedPlaceholderColor = Color.Cyan,
            focusedTrailingIconColor = Color.Cyan,
            focusedLeadingIconColor = Color.Cyan,
            unfocusedSupportingTextColor = Color.Cyan,
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurface,
            unfocusedPrefixColor = MaterialTheme.colorScheme.onSurface,
            unfocusedSuffixColor = MaterialTheme.colorScheme.onSurface,
            unfocusedContainerColor = Color.Transparent,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface, //подчеркивание
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurface,
            unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurface,
            errorSupportingTextColor = Color.Red,
            errorLabelColor = Color.Red,
            errorCursorColor = Color.Red,
            errorPrefixColor = Color.Red,
            errorSuffixColor = Color.Red,
            errorContainerColor = Color.Red,
            errorIndicatorColor = Color.Red,
            errorPlaceholderColor = Color.Red,
            errorLeadingIconColor = Color.Red,
            errorTrailingIconColor = Color.Red,
            disabledContainerColor = Color.Transparent,
            disabledSupportingTextColor = Color.Yellow,
            disabledLabelColor = MaterialTheme.colorScheme.onSurface,
            disabledPrefixColor = MaterialTheme.colorScheme.onSurface,
            disabledSuffixColor = MaterialTheme.colorScheme.onSurface,
            disabledIndicatorColor = Color.Transparent,
            disabledPlaceholderColor = MaterialTheme.colorScheme.onSurface,
            disabledLeadingIconColor = MaterialTheme.colorScheme.onSurface,
            disabledTrailingIconColor = MaterialTheme.colorScheme.onSurface,
            cursorColor = MaterialTheme.colorScheme.onSurface,
            textSelectionColors = TextSelectionColors(
                handleColor = MaterialTheme.colorScheme.onSurface,
                backgroundColor = Color.Transparent
            )
        ),
        label = label,
        isError = isError,
        enabled = enable,
        readOnly = readOnly,
        textStyle = textStyle,
        modifier = modifier,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        prefix = prefix,
        suffix = suffix,
        supportingText = supportingText,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        minLines = minLines,
        interactionSource = interactionSource,
        shape = shape

    )
}
