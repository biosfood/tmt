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

import java.util.*

data class Task(
    var title: String = "",
    var description: String = "",
    var deadline: Long = today,
    var progress: Double = 0.0,
) : Comparable<Task> {
    override operator fun compareTo(other: Task): Int = (deadline - other.deadline).toInt()

    companion object {
        val MILLISECONDS_PER_DAY = 24 * 60 * 60 * 1000
        val today = Date().time / MILLISECONDS_PER_DAY
    }
}