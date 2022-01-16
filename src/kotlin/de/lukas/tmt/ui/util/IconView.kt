package de.lukas.tmt.ui.util

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.Text
import tornadofx.style

class IconView(icon: FontAwesomeIcon, color: Color, val size: Int) : Text() {
    init {
        font = Font("FontAwesome", 12.0 * size)
        styleClass.addAll("root", "glyph-icon")
        text = String(charArrayOf(icon.char))
        style {
            fill = color
        }
    }
}