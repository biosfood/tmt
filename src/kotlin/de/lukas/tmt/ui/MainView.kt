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

import de.lukas.tmt.Tmt
import javafx.geometry.Insets
import kfoenix.jfxtabpane
import tornadofx.*

class MainView : View() {
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
                    }
                    // todo: nicer arrangement in a dynamic grid!
                    vbox {
                        for (task in Tmt.config.tasks) {
                            hbox {
                                label(task.description) {
                                    addClass(Styles.taskCard)
                                    fitToParentWidth()
                                    hboxConstraints {
                                        margin = Insets(10.0)
                                    }
                                }
                            }
                        }
                    }
                }
            }
            tab("calendar") {
                label("todo")
            }
        }
    }
}