package de.lukas.tmt.calendar

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import de.lukas.tmt.Tmt
import de.lukas.tmt.ui.Styles
import de.lukas.tmt.util.log.Log
import de.lukas.tmt.util.log.LogLevels
import javafx.scene.paint.Color
import kfoenix.jfxbutton
import tornadofx.*

class AssignmentView(private val assignment: Assignment) : Fragment() {
    override val root = hbox {
        addClass(Styles.root)
        addClass(Styles.card)
        style {
            backgroundColor += Color.TRANSPARENT
        }
        label(assignment.title)
        spacer()
        jfxbutton {
            graphic = FontAwesomeIconView(FontAwesomeIcon.GEAR)
            (graphic as FontAwesomeIconView).size = "2em"
            setOnAction {
                Log.log(LogLevels.INFO) { "editing an assignment" }
                openInternalWindow(AssignmentEditor(assignment), owner = parent.scene.root)
            }
        }
        jfxbutton {
            graphic = FontAwesomeIconView(FontAwesomeIcon.TRASH)
            (graphic as FontAwesomeIconView).size = "2em"
            graphic.addClass(Styles.redButton)
            setOnAction {
                Log.log(LogLevels.INFO) { "removing an assignment" }
                Tmt.config.assignments -= assignment
            }
        }
    }
}