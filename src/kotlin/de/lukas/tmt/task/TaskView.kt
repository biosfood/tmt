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

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import de.lukas.tmt.Tmt
import de.lukas.tmt.ui.Styles
import de.lukas.tmt.util.log.Log.log
import de.lukas.tmt.util.log.LogLevels
import javafx.event.EventHandler
import javafx.scene.layout.Priority
import kfoenix.jfxbutton
import tornadofx.*

class TaskView(private val task: Task) : Fragment() {
    override val root = hbox {
        addClass(Styles.taskCard)
        vbox {
            label(task.title)
            label(task.description)
        }
        pane {
            hgrow = Priority.ALWAYS
        }
        jfxbutton {
            graphic = FontAwesomeIconView(FontAwesomeIcon.GEAR)
            (graphic as FontAwesomeIconView).size = "2em"
            onAction = EventHandler {
                Tmt.currentlyEditingTask = task
                log(LogLevels.INFO) { "editing a task" }
                openInternalWindow(EditTaskView(task), owner = parent.scene.root)
            }
        }
        jfxbutton {
            graphic = FontAwesomeIconView(FontAwesomeIcon.TRASH)
            (graphic as FontAwesomeIconView).size = "2em"
            graphic.addClass(Styles.redButton)
            onAction = EventHandler {
                log(LogLevels.INFO) { "removing a task" }
                Tmt.config.tasks.remove(task)
                Tmt.config.save()
            }
        }
    }
}