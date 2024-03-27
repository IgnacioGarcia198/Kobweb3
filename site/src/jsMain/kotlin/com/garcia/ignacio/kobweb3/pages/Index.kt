package com.garcia.ignacio.kobweb3.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.staticCompositionLocalOf
import com.garcia.ignacio.kobweb3.HeadlineTextStyle
import com.garcia.ignacio.kobweb3.SubheadlineTextStyle
import com.garcia.ignacio.kobweb3.components.layouts.PageLayout
import com.garcia.ignacio.kobweb3.components.widgets.BasicButton
import com.garcia.ignacio.kobweb3.di.DI
import com.garcia.ignacio.kobweb3.toSitePalette
import com.varabyte.kobweb.compose.css.StyleVariable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.style.ComponentStyle
import com.varabyte.kobweb.silk.components.style.base
import com.varabyte.kobweb.silk.components.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.components.style.toAttrs
import com.varabyte.kobweb.silk.components.style.toModifier
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text

val counterViewModelLocal = staticCompositionLocalOf { DI.getCounterViewModel() }

// Container that has a tagline and grid on desktop, and just the tagline on mobile
val HeroContainerStyle by ComponentStyle {
    base { Modifier.fillMaxWidth().gap(2.cssRem) }
    Breakpoint.MD { Modifier.margin { top(20.vh) } }
}

// A demo grid that appears on the homepage because it looks good
val HomeGridStyle by ComponentStyle.base {
    Modifier
        .gap(0.5.cssRem)
        .width(70.cssRem)
        .height(18.cssRem)
}

private val GridCellColorVar by StyleVariable<Color>()
val HomeGridCellStyle by ComponentStyle.base {
    Modifier
        .backgroundColor(GridCellColorVar.value())
        .boxShadow(blurRadius = 0.6.cssRem, color = GridCellColorVar.value())
        .borderRadius(1.cssRem)
}

@Composable
private fun GridCell(color: Color, row: Int, column: Int, width: Int? = null, height: Int? = null) {
    Div(
        HomeGridCellStyle.toModifier()
            .setVariable(GridCellColorVar, color)
            .gridItem(row, column, width, height)
            .toAttrs()
    )
}

@Page
@Composable
fun HomePage() {
    PageLayout("Home") {
        Box {
            val sitePalette = ColorMode.current.toSitePalette()

            Column(Modifier.gap(2.cssRem)) {
                Div(HeadlineTextStyle.toAttrs()) {
                    SpanText(
                        "Counter web3 app sample", Modifier.color(
                            when (ColorMode.current) {
                                ColorMode.LIGHT -> Colors.Black
                                ColorMode.DARK -> Colors.White
                            }
                        )
                    )
                }

                val counterViewModel = counterViewModelLocal.current

                Column {
                    BasicButton(
                        onClick = { counterViewModel.selectMetamaskAccount() },
                    ) {
                        Text("Connect web3 wallet")
                    }

                    BasicButton(
                        onClick = { counterViewModel.getCode() },
                    ) {
                        Text("Get contract code")
                    }

                    BasicButton(
                        onClick = {
                            counterViewModel.incrementCounter()
                        }
                    ) {
                        Text("Increment counter 1")
                    }

                    BasicButton(
                        onClick = {
                            counterViewModel.incrementCounter2()
                        }
                    ) {
                        Text("Increment counter 2")
                    }

                    BasicButton(
                        onClick = {
                            counterViewModel.getCounter()
                        }
                    ) {
                        Text("Get counter 1")
                    }

                    BasicButton(
                        onClick = {
                            counterViewModel.getCounter2()
                        }
                    ) {
                        Text("Get counter 2")
                    }
                }

                SpanText("TestContract feedback:")
                SpanText(counterViewModelLocal.current.testContractFlow.collectAsState().value)
                SpanText("TestContract2 feedback:")
                SpanText(counterViewModelLocal.current.testContract2Flow.collectAsState().value)
            }
        }
    }
}
