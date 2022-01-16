package de.lukas.tmt.ui.util

import de.lukas.tmt.ui.Styles
import javafx.event.EventTarget
import javafx.scene.Parent
import javafx.scene.paint.Color
import tornadofx.*

class BetterProgressBar(
    var progress: Double,
    var backgroundColor: Color,
    var foregroundColor: Color,
    op: BetterProgressBar.() -> Unit
) : View() {
    init {
        op()
    }

    override var root: Parent = assemble()

    private fun assemble(): Parent = stackpane {
        group {
            rectangle(0.0, 0.0, 200.0, 10.0) {
                fill = backgroundColor
            }
            rectangle(0.0, 0.0, 200 * progress, 10.0) {
                fill = foregroundColor
            }
        }
    }
}

fun EventTarget.betterProgressBar(
    progress: Double = 0.0,
    backgroundColor: Color = Styles.comments,
    foregroundColor: Color = Styles.error,
    op: BetterProgressBar.() -> Unit = {}
) {
    this += BetterProgressBar(progress, backgroundColor, foregroundColor, op)
}