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

package de.lukas.tmt.util.log

import java.awt.Color
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.LinkedBlockingQueue

data class LogMessage(
    val message: String,
    val threadName: String,
    val time: Long,
    val level: LogLevels,
) {
    override fun toString(): String =
        "[${setForeground(level.color)}$level${setForeground(Color.WHITE)}] " +
        "[${setForeground(level.color)}${Thread.currentThread().name}${setForeground(Color.WHITE)}] " +
        "[${setForeground(level.color)}${TIME_FORMAT.format(Date(time))}${setForeground(Color.WHITE)}] " +
        message

    companion object {
        private val TIME_FORMAT = SimpleDateFormat("dd/mm/yyyy HH:mm:ss.SSS")
    }
}

object Log {
    private val queue = LinkedBlockingQueue<LogMessage>()

    init {
        Thread {
            while (true) {
                val current = queue.take()
                println(current)
                // Todo: log to file?
            }
        }.start()
    }

    fun log(level: LogLevels, message: () -> String) {
        queue.add(LogMessage(
            message.invoke(),
            Thread.currentThread().name,
            System.currentTimeMillis(),
            level,
        ))
    }
}

fun setForeground(color: Color) : String = "\u001B[38;2;${color.ansiSequence}m"

val Color.ansiSequence: String
    get() = "$red;$green;$blue"