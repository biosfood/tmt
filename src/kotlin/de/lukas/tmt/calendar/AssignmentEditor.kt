package de.lukas.tmt.calendar

import com.jfoenix.controls.JFXTextField
import de.lukas.tmt.Tmt
import de.lukas.tmt.ui.Styles
import de.lukas.tmt.ui.update
import kfoenix.jfxbutton
import kfoenix.jfxtextfield
import tornadofx.*

class AssignmentEditor(private val assignment: Assignment) : Fragment("Edit assignment") {
    override val root = vbox {
        addClass(Styles.root)
        lateinit var titleField: JFXTextField
        form {
            fieldset("Edit assignment") {
                field("description") {
                    titleField = jfxtextfield(assignment.title)
                    titleField.focusColor = Styles.functions
                    titleField.requestFocus()
                }
            }
        }
        jfxbutton("save") {
            addClass(Styles.saveButton)
            style = "-fx-text-fill: black"
            setOnAction {
                assignment.title = titleField.text
                Tmt.config.assignments.update()
                Tmt.config.save()
                close()
            }
        }
    }
}