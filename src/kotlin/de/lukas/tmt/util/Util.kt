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

package de.lukas.tmt.util

import de.lukas.tmt.util.log.Log.log
import de.lukas.tmt.util.log.LogLevels

object Util {
    private val osName = System.getProperty("os.name")
    val TMT_HOME = System.getProperty("user.home") + when {
        osName.startsWith("Windows") -> "/Appdata/Roaming/tmt/"
        osName.startsWith("Linux") -> "/.local/share/tmt/"
        else -> let {
            log(LogLevels.FATAL) { "unknown os $osName" }
            ""
        }
    }
}