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

package de.lukas.tmt.calendar

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon
import de.lukas.tmt.Tmt
import de.lukas.tmt.task.TaskView
import de.lukas.tmt.ui.Styles
import de.lukas.tmt.ui.UI
import de.lukas.tmt.ui.openDialogue
import de.lukas.tmt.ui.util.IconView
import de.lukas.tmt.ui.util.ListWrapper
import de.lukas.tmt.ui.util.betterListView
import de.lukas.tmt.util.log.Log
import de.lukas.tmt.util.log.LogLevels
import kfoenix.jfxbutton
import tornadofx.*
import java.sql.Date

class DayView(private val day: Long) : Fragment() {
    override val root = hbox {
        addClass(Styles.root)
        addClass(Styles.card)
        if (day == UI.today) {
            style {
                backgroundColor += Styles.secondBackground
            }
        }
        vbox {
            fitToParentWidth()
            label(UI.FULL_DATE_FORMAT.format(Date(day * UI.MILLISECONDS_PER_DAY)))
            pane {
                prefHeight = 5.0
            }
            val assignments = ListWrapper(Tmt.config.assignments) {
                it.filter { assignment ->
                    assignment.isOnDay(day)
                }.sorted()
            }
            val tasks = ListWrapper(Tmt.config.tasks) {
                it.filter { task ->
                    task.deadline == day
                }
            }
            betterListView(tasks, TaskView::class)
            betterListView(assignments, AssignmentView::class)
        }
        pane {
            minWidth = 10.0
        }
        jfxbutton {
            graphic = IconView(FontAwesomeIcon.PLUS, Styles.strings, 2)
            setOnAction {
                Log.log(LogLevels.INFO) { "adding a new assignment" }
                val assignment = Assignment(date = day)
                Tmt.config.assignments += assignment
                openDialogue(AssignmentEditor(assignment), parent)
            }
        }
    }
}