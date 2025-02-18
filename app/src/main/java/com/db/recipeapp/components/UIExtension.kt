package com.db.recipeapp.components

import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp


fun Modifier.borderTopAndBottom(strokeWidth: Dp, color: Color) = composed {

    val density = LocalDensity.current
    val strokeWidthPx = with(density) { strokeWidth.toPx() }

    this.then(
        Modifier.drawBehind {
            val width = size.width
            val height = size.height

            drawLine(
                color = color,
                start = Offset(x = 0f, y = strokeWidthPx / 2),
                end = Offset(x = width, y = strokeWidthPx / 2),
                strokeWidth = strokeWidthPx
            )

            drawLine(
                color = color,
                start = Offset(x = 0f, y = height - strokeWidthPx / 2),
                end = Offset(x = width, y = height - strokeWidthPx / 2),
                strokeWidth = strokeWidthPx
            )
        }
    )
}