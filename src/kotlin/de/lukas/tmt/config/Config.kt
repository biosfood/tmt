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

package de.lukas.tmt.config

import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import de.lukas.tmt.task.Task
import de.lukas.tmt.util.Util
import de.lukas.tmt.util.log.Log.log
import de.lukas.tmt.util.log.LogLevels
import tornadofx.toObservable
import java.io.File

data class Config(
    @Json(name = "startMaximized") val startMaximized: Boolean = true,
    @Json(name = "tasks") var mutableTasks: MutableList<Task> = mutableListOf(),
) {
    val tasks = mutableTasks.toObservable()
    fun save() {
        tasks.sort()
        mutableTasks = tasks
        val file = File(CONFIG_FILE_PATH)
        log(LogLevels.VERBOSE) { "saving configuration" }
        file.writeText(MOSHI.toJson(this), Charsets.UTF_8)
    }

    companion object {
        private val CONFIG_FILE_PATH = "${Util.TMT_HOME}config.json"

        @OptIn(ExperimentalStdlibApi::class)
        private val MOSHI = Moshi.Builder().add(KotlinJsonAdapterFactory()).build().adapter<Config>().indent("  ")

        fun readConfig(): Config {
            log(LogLevels.VERBOSE) { "reading configuration" }
            val file = File(CONFIG_FILE_PATH)
            val config = if (file.exists()) {
                val data = file.readLines()
                MOSHI.fromJson(data.joinToString(""))!!
            } else {
                log(LogLevels.INFO) { "config file doesn't exist, creating a new one..." }
                File(Util.TMT_HOME).mkdirs()
                Config()
            }
            config.save()
            return config
        }
    }
}