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

package de.lukas.tmt.ui

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView
import de.lukas.tmt.Tmt
import de.lukas.tmt.task.EditTaskView
import de.lukas.tmt.task.Task
import de.lukas.tmt.task.TaskView
import de.lukas.tmt.ui.util.BetterListView
import de.lukas.tmt.util.log.Log.log
import de.lukas.tmt.util.log.LogLevels
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.layout.Priority
import kfoenix.jfxbutton
import kfoenix.jfxtabpane
import tornadofx.*

class MainView : View("tmt") {
    override val root = hbox {
        addClass(Styles.root)
        minWidth = 200.0
        minHeight = 200.0
        jfxtabpane {
            fitToParentWidth()
            tab("tasks") {
                vbox {
                    label("TODO list") {
                        addClass(Styles.heading)
                        fitToParentWidth()
                        vboxConstraints {
                            margin = Insets(10.0)
                        }
                    }
                    hbox {
                        pane {
                            hgrow = Priority.ALWAYS
                        }
                        jfxbutton {
                            addClass(Styles.greenButton)
                            graphic = FontAwesomeIconView(FontAwesomeIcon.PLUS)
                            (graphic as FontAwesomeIconView).size = "4em"
                            graphic.addClass(Styles.greenButton)
                            onAction = EventHandler {
                                log(LogLevels.INFO) { "adding a new task" }
                                val task = Task()
                                Tmt.config.tasks += task
                                openInternalWindow(EditTaskView(task), owner = parent.scene.root)
                            }
                        }
                    }
                    this += BetterListView(Tmt.config.tasks, TaskView::class)
                }
            }
            tab("calendar") {
                label("todo")
            }
        }
    }
}