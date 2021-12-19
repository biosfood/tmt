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

import de.lukas.tmt.task.Task
import de.lukas.tmt.ui.UI
import java.time.LocalTime

data class Assignment(
    var title: String = "",
    var description: String = "",
    var start: Int = LocalTime.MIN.toSecondOfDay(),
    var end: Int = LocalTime.MAX.toSecondOfDay(),
    var date: Long = UI.today,
    var type: AssignmentType = AssignmentType.REGULAR_ASSIGNMENT,
    var isFullDay: Boolean = false,
    val task: Task? = null
) {
    fun isOnDay(day: Long): Boolean {
        return date == day
    }
}
