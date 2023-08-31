package com.wakaztahir.markdowncompose.editor.value

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

/**
 * A value that represents the text of a RichTextEditor
 * @param textFieldValue the [TextFieldValue] of the text field
 * @param currentStyles the current styles applied to the text
 * @param parts the parts of the text that have different styles
 * @see RichTextStyle
 * @see RichTextPart
 */
@Immutable
internal data class RichTextValue internal constructor(
    internal val textFieldValue: TextFieldValue,
    val currentStyles: Set<RichTextStyle> = emptySet(),
    internal val parts: List<RichTextPart> = emptyList(),
) {

    /**
     * The [VisualTransformation] to apply to the text field
     */
    internal val visualTransformation
        get() = VisualTransformation {
            TransformedText(
                text = annotatedString,
                offsetMapping = OffsetMapping.Identity
            )
        }

    /**
     * The [AnnotatedString] representation of the text
     */
    internal val annotatedString
        get() = AnnotatedString(
            text = textFieldValue.text,
            spanStyles = parts.map { part ->
                val spanStyle = part.styles.fold(SpanStyle()) { acc, style ->
                    style.applyStyle(acc)
                }

                AnnotatedString.Range(
                    item = spanStyle,
                    start = part.fromIndex,
                    end = part.toIndex + 1,
                )
            }
        )

    constructor(
        text: String = "",
        currentStyles: Set<RichTextStyle> = emptySet(),
    ) : this(
        textFieldValue = TextFieldValue(text = text),
        currentStyles = currentStyles,
        parts = emptyList(),
    )

    /**
     * Toggle a style
     * @param style the style to toggle
     * @return a new [RichTextValue] with the style toggled
     * @see RichTextStyle
     */
//    fun toggleStyle(style: RichTextStyle): RichTextValue {
//        return if (currentStyles.contains(style)) {
//            removeStyle(style)
//        } else {
//            addStyle(style)
//        }
//    }

    /**
     * Add a style to the current styles
     * @param style the style to add
     * @return a new [RichTextValue] with the new style added
     * @see RichTextStyle
     */
//    fun addStyle(vararg style: RichTextStyle): RichTextValue {
//        return RichTextValueBuilder
//            .from(this)
//            .addStyle(*style)
//            .build()
//    }

    /**
     * Remove a style from the current styles
     * @param style the style to remove
     * @return a new [RichTextValue] with the style removed
     * @see RichTextStyle
     */
//    fun removeStyle(vararg style: RichTextStyle): RichTextValue {
//        return RichTextValueBuilder
//            .from(this)
//            .removeStyle(*style)
//            .build()
//    }

    /**
     * Update the current styles
     * @param newStyles the new styles
     * @return a new [RichTextValue] with the new styles
     * @see RichTextStyle
     */
//    fun updateStyles(newStyles: Set<RichTextStyle>): RichTextValue {
//        return RichTextValueBuilder
//            .from(this)
//            .updateStyles(newStyles)
//            .build()
//    }

    /**
     * Update the text field value and update the rich text parts accordingly to the new text field value
     * @param newTextFieldValue the new text field value
     * @return a new [RichTextValue] with the new text field value
     */
    internal fun updateTextFieldValue(newTextFieldValue: TextFieldValue): RichTextValue {
        return RichTextValueBuilder
            .from(this)
            .updateTextFieldValue(newTextFieldValue)
            .build()
    }

}