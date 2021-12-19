/*
 * Copyright 2021 Lukas Eisenhauer
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.lukas.tmt.task

import com.jfoenix.controls.JFXDatePicker
import com.jfoenix.controls.JFXSlider
import com.jfoenix.controls.JFXTextArea
import com.jfoenix.controls.JFXTextField
import de.lukas.tmt.Tmt
import de.lukas.tmt.ui.Styles
import de.lukas.tmt.ui.UI
import de.lukas.tmt.ui.util.DateConverter
import de.lukas.tmt.util.update
import javafx.scene.layout.HBox
import kfoenix.*
import tornadofx.*
import java.time.ZoneId
import java.util.*

class TaskEditor(private val task: Task) : Fragment("Edit task") {
    override val root = vbox {
        parentProperty().onChange {
            parent.addClass(Styles.internalWindow)
            parent.parent.childrenUnmodifiable.filterIsInstance<HBox>().addClass(Styles.internalWindow)
        }
        addClass(Styles.root)

        lateinit var titleField: JFXTextField
        lateinit var descriptionField: JFXTextArea
        lateinit var date: JFXDatePicker
        lateinit var progress: JFXSlider
        form {
            fieldset("Edit Task") {
                field("Title") {
                    titleField = jfxtextfield(task.title)
                    titleField.focusColor = Styles.functions
                }
                field("description") {
                    descriptionField = jfxtextarea(task.description)
                    descriptionField.focusColor = Styles.functions
                }
                field("deadline") {
                    date = jfxdatepicker()
                    date.value = Date(task.deadline * UI.MILLISECONDS_PER_DAY).toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDate()
                    date.defaultColor = Styles.functions
                    date.converter = DateConverter(UI.SHORT_DATE_FORMAT)
                }
                pane {
                    prefHeight = 50.0
                }
                field("progress") {
                    progress = jfxslider {
                        value = task.progress * 100
                    }
                }
            }
        }
        hbox {
            spacer()
            jfxbutton("save") {
                addClass(Styles.saveButton)
                style = "-fx-text-fill: black"
                setOnAction {
                    task.title = titleField.text
                    task.description = descriptionField.text
                    task.deadline = date.value.toEpochDay()
                    task.progress = progress.value / 100
                    Tmt.config.tasks.update()
                    Tmt.config.save()
                    close()
                }
            }
        }
    }
}