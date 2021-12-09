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
import com.jfoenix.controls.JFXTextArea
import com.jfoenix.controls.JFXTextField
import de.lukas.tmt.Tmt
import de.lukas.tmt.ui.Styles
import javafx.event.EventHandler
import kfoenix.jfxbutton
import kfoenix.jfxdatepicker
import kfoenix.jfxtextarea
import kfoenix.jfxtextfield
import tornadofx.*
import java.time.ZoneId
import java.util.*

class EditTaskView(private val task: Task) : Fragment("Edit task") {
    override val root = vbox {
        addClass(Styles.root)

        lateinit var titleField: JFXTextField
        lateinit var descriptionField: JFXTextArea
        lateinit var date: JFXDatePicker
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
                    date.value = Date(task.deadline * Task.MILLISECONDS_PER_DAY).toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDate()
                    date.defaultColor = Styles.functions
                }
            }
        }
        jfxbutton("save") {
            addClass(Styles.saveButton)
            onAction = EventHandler {
                task.title = titleField.text
                task.description = descriptionField.text
                task.deadline = date.value.toEpochDay()
                Tmt.config.tasks.remove(task)
                Tmt.config.tasks.add(0, task)
                Tmt.config.save()
                close()
            }
        }
    }
}