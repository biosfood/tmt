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

package de.lukas.tmt

import de.lukas.tmt.config.Config
import de.lukas.tmt.ui.UI
import de.lukas.tmt.util.log.Log.log
import de.lukas.tmt.util.log.LogLevels

fun main() {
    val config = Config.readConfig()
    log(LogLevels.INFO) { "test value: ${config.test}" }
    UI.launchUI()
}