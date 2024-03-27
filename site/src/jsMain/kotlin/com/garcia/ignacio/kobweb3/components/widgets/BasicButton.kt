package com.garcia.ignacio.kobweb3.components.widgets

import androidx.compose.runtime.Composable
import androidx.compose.web.events.SyntheticMouseEvent
import com.varabyte.kobweb.compose.foundation.layout.RowScope
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.theme.colors.ColorScheme
import com.varabyte.kobweb.silk.theme.colors.ColorSchemes
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

@Composable
fun BasicButton(
    onClick: (evt: SyntheticMouseEvent) -> Unit,
    colorScheme: ColorScheme = ColorSchemes.Blue,
    modifier: Modifier = Modifier.padding(leftRight = 50.px).margin(leftRight = 2.percent, topBottom = 2.percent),
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        colorScheme = colorScheme,
        modifier = modifier,
        content = content,
    )
}