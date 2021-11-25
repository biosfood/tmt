package de.lukas.tmt.calendar

import de.lukas.tmt.ui.Styles
import javafx.scene.paint.Color
import tornadofx.*

class AssignmentView(private val assignment: Assignment) : Fragment() {
    override val root = hbox {
        addClass(Styles.root)
        style {
            backgroundColor += Color.TRANSPARENT
        }
        label(assignment.name)
    }
}