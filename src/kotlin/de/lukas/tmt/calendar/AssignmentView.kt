package de.lukas.tmt.calendar

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import de.lukas.tmt.Tmt
import de.lukas.tmt.task.TaskEditor
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
        vbox {
            label(assignment.title)
            label(assignment.type.typeName) { style { textFill = Styles.functions } }
        }
        spacer()
        jfxbutton {
            graphic = FontAwesomeIconView(FontAwesomeIcon.GEAR)
            (graphic as FontAwesomeIconView).size = "2em"
            setOnAction {
                Log.log(LogLevels.INFO) { "editing an assignment" }
                if (assignment.type == AssignmentType.TASK_DEADLINE) {
                    openInternalWindow(TaskEditor(assignment.task!!), owner = parent.scene.root)
                } else {
                    openInternalWindow(AssignmentEditor(assignment), owner = parent.scene.root)
                }
            }
        }
        jfxbutton {
            graphic = FontAwesomeIconView(FontAwesomeIcon.TRASH)
            (graphic as FontAwesomeIconView).size = "2em"
            graphic.addClass(Styles.redButton)
            setOnAction {
                Log.log(LogLevels.INFO) { "removing an assignment" }
                if (assignment.type == AssignmentType.TASK_DEADLINE) {
                    Tmt.config.tasks -= assignment.task
                } else {
                    Tmt.config.assignments -= assignment
                }
                Tmt.config.save()
            }
        }
    }
}