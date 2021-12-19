package de.lukas.tmt.calendar

import com.jfoenix.controls.JFXDatePicker
import com.jfoenix.controls.JFXTextArea
import com.jfoenix.controls.JFXTextField
import com.jfoenix.controls.JFXTimePicker
import de.lukas.tmt.Tmt
import de.lukas.tmt.ui.Styles
import de.lukas.tmt.ui.UI
import de.lukas.tmt.ui.update
import de.lukas.tmt.ui.util.DateConverter
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.layout.HBox
import kfoenix.*
import tornadofx.*
import java.time.LocalTime
import java.time.ZoneId
import java.util.*

class AssignmentEditor(private val assignment: Assignment) : Fragment("Edit assignment") {
    override val root = vbox {
        parentProperty().onChange {
            parent.addClass(Styles.internalWindow)
            parent.parent.childrenUnmodifiable.filterIsInstance<HBox>().addClass(Styles.internalWindow)
        }
        addClass(Styles.root)
        val assignmentDate = Date(assignment.date * UI.MILLISECONDS_PER_DAY).toInstant()
            .atZone(ZoneId.systemDefault()).toLocalDate()
        lateinit var titleField: JFXTextField
        lateinit var descriptionField: JFXTextArea
        lateinit var date: JFXDatePicker
        val fullDay = SimpleBooleanProperty(assignment.isFullDay)
        lateinit var startTime: JFXTimePicker
        lateinit var endTime: JFXTimePicker
        val typeProperty = SimpleObjectProperty(assignment.type)
        form {
            fieldset("Edit assignment") {
                field("title") {
                    titleField = jfxtextfield(assignment.title)
                    titleField.focusColor = Styles.functions
                    titleField.requestFocus()
                }
                field("description") {
                    descriptionField = jfxtextarea(assignment.description)
                    descriptionField.focusColor = Styles.functions
                }
                field("date") {
                    date = jfxdatepicker()
                    date.value = assignmentDate
                    date.defaultColor = Styles.functions
                    date.converter = DateConverter(UI.SHORT_DATE_FORMAT)
                }
                field("full day assignment") {
                    hbox {
                        spacer()
                        jfxcheckbox(fullDay) {
                            checkedColor = Styles.functions
                        }
                    }
                }
                field("start") {
                    hbox {
                        spacer()
                        startTime = JFXTimePicker(LocalTime.ofSecondOfDay(assignment.start.toLong()))
                        startTime.defaultColor = Styles.functions
                        startTime.setIs24HourView(true)
                        this += startTime
                    }
                }
                field("end") {
                    hbox {
                        spacer()
                        endTime = JFXTimePicker(LocalTime.ofSecondOfDay(assignment.end.toLong()))
                        endTime.defaultColor = Styles.functions
                        this += endTime
                    }
                }
                field("type") {
                    hbox {
                        spacer()
                        jfxcombobox(typeProperty, AssignmentType.CHOOSABLE_VALUES) {
                            focusColor = Styles.functions
                            value = AssignmentType.REGULAR_ASSIGNMENT
                        }
                    }
                }
            }
        }
        hbox {
            jfxbutton("save") {
                addClass(Styles.saveButton)
                style = "-fx-text-fill: black"
                setOnAction {
                    assignment.title = titleField.text
                    assignment.description = descriptionField.text
                    assignment.date = date.value.toEpochDay()
                    assignment.isFullDay = fullDay.value
                    assignment.start = startTime.value.toSecondOfDay()
                    assignment.end = endTime.value.toSecondOfDay()
                    assignment.type = typeProperty.value
                    Tmt.config.assignments.update()
                    Tmt.config.save()
                    close()
                }
            }
        }
    }
}