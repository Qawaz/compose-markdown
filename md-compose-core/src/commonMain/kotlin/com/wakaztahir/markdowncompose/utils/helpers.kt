package com.wakaztahir.markdowncompose.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import kotlinx.coroutines.CoroutineDispatcher
import org.intellij.markdown.IElementType
import org.intellij.markdown.ast.ASTNode
import kotlin.random.Random

internal expect fun currentTimeMillis(): Long

internal expect val IODispatcher: CoroutineDispatcher

@Composable
internal expect fun imagePainter(url: String): Painter?

internal fun randomUUID(): String {
    return currentTimeMillis().toString() + "-" + Random.nextLong(99999999, Long.MAX_VALUE)
        .toString()
}

/**
 * Tag used to indicate an url for inline content. Required for click handling.
 */
const val TAG_URL = "MARKDOWN_URL"

/**
 * Tag used to indicate an image url for inline content. Required for rendering.
 */
const val TAG_IMAGE_URL = "MARKDOWN_IMAGE_URL"

/**
 * Find a child node recursive
 */
fun ASTNode.findChildOfTypeRecursive(type: IElementType): ASTNode? {
    for (it in children) {
        if (it.type == type) {
            return it
        } else {
            val found = it.findChildOfTypeRecursive(type)
            if (found != null) {
                return found
            }
        }
    }
    return null
}

/**
 * Helper function to drop the first and last element in the children list.
 * E.g. we don't want to render the brackets of a link
 */
internal fun List<ASTNode>.innerList(): List<ASTNode> = this.subList(1, this.size - 1)

