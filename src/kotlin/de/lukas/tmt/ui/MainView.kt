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
import de.lukas.tmt.task.Task
import de.lukas.tmt.util.log.Log.log
import de.lukas.tmt.util.log.LogLevels
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.layout.Priority
import kfoenix.jfxbutton
import kfoenix.jfxtabpane
import tornadofx.*

class MainView : View("tmt") {
    override val root = hbox {
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
                        alignment = Pos.TOP_RIGHT
                        jfxbutton {
                            alignment = Pos.TOP_RIGHT
                            addClass(Styles.greenButton)
                            graphic = FontAwesomeIconView(FontAwesomeIcon.PLUS)
                            (graphic as FontAwesomeIconView).size = "4em"
                            graphic.addClass(Styles.greenButton)
                            onAction = EventHandler {
                                log(LogLevels.INFO) { "adding a new task" }
                                currentlyEditingTask = Task()
                                openInternalWindow<EditTaskView>()
                            }
                        }
                    }
                    listview(Tmt.config.tasks) {
                        fitToParentHeight()
                        maxHeightProperty().bind(Tmt.config.tasks.sizeProperty * 67 + 2)
                        cellFormat {
                            addClass(Styles.cellFormat)
                            val task = it
                            graphic = hbox {
                                addClass(Styles.taskCard)
                                label(task.description)
                                // separator
                                pane {
                                    hgrow = Priority.ALWAYS
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
                        style {
                            backgroundColor += Styles.background
                            fill = Styles.background
                        }
                        background = Background(BackgroundFill(Styles.background, CornerRadii(0.0), Insets(0.0)))
                    }
                }
            }
            tab("calendar") {
                label("todo")
            }
        }
    }

    companion object {
        lateinit var currentlyEditingTask: Task
    }
}