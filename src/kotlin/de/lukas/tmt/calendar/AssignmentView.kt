package de.lukas.tmt.calendar

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import de.lukas.tmt.Tmt
import de.lukas.tmt.task.TaskEditor
import de.lukas.tmt.ui.Styles
import de.lukas.tmt.ui.openDialogue
import de.lukas.tmt.util.log.Log
import de.lukas.tmt.util.log.LogLevels
import javafx.scene.paint.Color
import kfoenix.jfxbutton
import tornadofx.*
import java.time.LocalTime

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
        if (assignment.isFullDay) {
            vbox {
                spacer()
                label("full day") {
                    style {
                        textFill = Styles.functions
                    }
                }
                spacer()
            }
        } else {
            vbox {
                spacer()
                label(LocalTime.ofSecondOfDay(assignment.start.toLong()).toString()) {
                    style {
                        textFill = Styles.green
                    }
                }
                spacer()
                label(LocalTime.ofSecondOfDay(assignment.end.toLong()).toString()) {
                    style {
                        textFill = Styles.red
                    }
                }
                spacer()
            }
        }
        pane {
            minWidth = 10.0
        }
        jfxbutton {
            graphic = FontAwesomeIconView(FontAwesomeIcon.GEAR)
            (graphic as FontAwesomeIconView).size = "2em"
            setOnAction {
                Log.log(LogLevels.INFO) { "editing an assignment" }
                if (assignment.type == AssignmentType.TASK_DEADLINE) {
                    openDialogue(TaskEditor(assignment.task!!), parent)
                } else {
                    openDialogue(AssignmentEditor(assignment), parent)
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