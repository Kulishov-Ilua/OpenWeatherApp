package ru.kulishov.openweatherapp.presentation.ui.components.app

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.LocalTextStyle
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
fun TextFieldCustom(inpText: String,
                    isError: Boolean=false,
                    enable: Boolean=true,
                    readOnly: Boolean=false,
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
                    textStyle: TextStyle= LocalTextStyle.current,
                    primaryColor: Color=  Color.White,
                    onUpdate: (String) -> Unit)
{
    TextField(
        value = inpText,
        onValueChange = {it -> onUpdate(it)},
        colors = TextFieldColors(
            focusedTextColor = primaryColor,
            unfocusedTextColor = primaryColor,
            disabledTextColor = primaryColor,
            errorTextColor = Color.Red,
            focusedSupportingTextColor = Color.Yellow,
            focusedLabelColor = primaryColor,
            focusedPrefixColor = primaryColor,
            focusedSuffixColor = primaryColor,
            focusedContainerColor = Color.Transparent, // background
            focusedIndicatorColor = Color.Transparent, //подчеркивание
            focusedPlaceholderColor = Color.Cyan,
            focusedTrailingIconColor = Color.Cyan,
            focusedLeadingIconColor = Color.Cyan,
            unfocusedSupportingTextColor = Color.Cyan,
            unfocusedLabelColor = primaryColor,
            unfocusedPrefixColor = primaryColor,
            unfocusedSuffixColor = primaryColor,
            unfocusedContainerColor = Color.Transparent,
            unfocusedIndicatorColor = primaryColor, //подчеркивание
            unfocusedPlaceholderColor = primaryColor,
            unfocusedLeadingIconColor = primaryColor,
            unfocusedTrailingIconColor = primaryColor,
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
            disabledLabelColor = primaryColor,
            disabledPrefixColor = primaryColor,
            disabledSuffixColor = primaryColor,
            disabledIndicatorColor = Color.Transparent,
            disabledPlaceholderColor = primaryColor,
            disabledLeadingIconColor = primaryColor,
            disabledTrailingIconColor = primaryColor,
            cursorColor = primaryColor,
            textSelectionColors = TextSelectionColors(
                handleColor = primaryColor,
                backgroundColor = Color.Transparent
            )
        ),
        label = label,
        isError = isError,
        enabled = enable,
        readOnly = readOnly,
        textStyle = textStyle,
        modifier=modifier,
        placeholder=placeholder,
        leadingIcon=leadingIcon,
        trailingIcon=trailingIcon,
        prefix=prefix,
        suffix=suffix,
        supportingText=supportingText,
        visualTransformation=visualTransformation,
        keyboardOptions=keyboardOptions,
        keyboardActions=keyboardActions,
        singleLine=singleLine,
        minLines=minLines,
        interactionSource=interactionSource,
        shape = shape

    )
}
