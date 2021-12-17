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

import com.sun.javafx.collections.NonIterableChange
import de.lukas.tmt.Tmt
import de.lukas.tmt.util.log.Log.log
import de.lukas.tmt.util.log.LogLevels
import javafx.collections.ObservableList
import javafx.collections.ObservableListBase
import javafx.stage.Stage
import tornadofx.App
import java.text.SimpleDateFormat
import java.util.*
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.jvm.isAccessible
import kotlin.system.exitProcess

class UI : App(MainView::class, Styles::class) {
    override fun start(stage: Stage) {
        super.start(stage)
        if (Tmt.config.startMaximized) {
            stage.isMaximized = true
        }
    }

    override fun stop() {
        super.stop()
        log(LogLevels.INFO) { "shutting down on closed window" }
        exitProcess(0)
    }

    companion object {
        const val MILLISECONDS_PER_DAY = 24 * 60 * 60 * 1000
        val today = Date().time / MILLISECONDS_PER_DAY

        val SHORT_DATE_FORMAT = SimpleDateFormat("dd.MM.yy")
        val FULL_DATE_FORMAT = SimpleDateFormat("EEEE, dd.MM.yy")
    }
}

inline fun <reified T> T.callPrivateFunc(name: String, vararg args: Any?): Any? =
    T::class
        .declaredMemberFunctions
        .firstOrNull { it.name == name }
        ?.apply { isAccessible = true }
        ?.call(this, *args) ?: throw NoSuchMethodError("unknown method $name")

fun ObservableList<*>.update() {
    // needs to be implemented into the original version at some point
    (this as ObservableListBase).callPrivateFunc(
        "fireChange",
        NonIterableChange.SimpleUpdateChange(0, 0, this)
    )
}
