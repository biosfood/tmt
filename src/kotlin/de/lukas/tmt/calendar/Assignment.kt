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
import java.sql.Date
import java.time.LocalTime

data class Assignment(
    var title: String = "",
    var description: String = "",
    var start: Int = LocalTime.of(0, 0).toSecondOfDay(),
    var end: Int = LocalTime.of(23, 0).toSecondOfDay(),
    var date: Long = UI.today,
    var type: AssignmentType = AssignmentType.REGULAR_ASSIGNMENT,
    var isFullDay: Boolean = false,
    val task: Task? = null
) : Comparable<Assignment> {
    fun isOnDay(day: Long): Boolean {
        if (date == day || type == AssignmentType.DAILY_ASSIGNMENT && day > date) {
            return true
        }
        if (day < date || type == AssignmentType.REGULAR_ASSIGNMENT || type == AssignmentType.TASK_DEADLINE) {
            return false
        }
        return when (type) {
            AssignmentType.DAILY_ASSIGNMENT -> true
            AssignmentType.WEEKLY_ASSIGNMENT ->
                Date(day * UI.MILLISECONDS_PER_DAY).toLocalDate().dayOfWeek ==
                        Date(date * UI.MILLISECONDS_PER_DAY).toLocalDate().dayOfWeek
            AssignmentType.MONTHLY_ASSIGNMENT ->
                Date(day * UI.MILLISECONDS_PER_DAY).toLocalDate().dayOfMonth ==
                        Date(date * UI.MILLISECONDS_PER_DAY).toLocalDate().dayOfMonth
            AssignmentType.YEARLY_ASSIGNMENT ->
                Date(day * UI.MILLISECONDS_PER_DAY).toLocalDate().dayOfYear ==
                        Date(date * UI.MILLISECONDS_PER_DAY).toLocalDate().dayOfYear
            else -> {
                throw RuntimeException()
            }
        }
    }

    override fun compareTo(other: Assignment): Int {
        if (isFullDay) {
            if (other.isFullDay) {
                return 0
            }
            return 1
        }
        if (other.isFullDay) {
            return -1
        }
        return start - other.start
    }
}
