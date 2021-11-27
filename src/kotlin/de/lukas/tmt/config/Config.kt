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
import de.lukas.tmt.util.Util
import de.lukas.tmt.util.log.Log.log
import de.lukas.tmt.util.log.LogLevels
import java.io.File

data class Config(
    @Json(name = "test") val test: Int = 3,
) {
    companion object {
        @OptIn(ExperimentalStdlibApi::class)
        private val MOSHI = Moshi.Builder().add(KotlinJsonAdapterFactory()).build().adapter<Config>()

        fun readConfig(): Config {
            log(LogLevels.VERBOSE) { "reading tmt config" }
            val file = File("${Util.TMT_HOME}config.json")
            val config = if (file.exists()) {
                val data = file.readLines()
                MOSHI.fromJson(data.joinToString(""))!!
            } else {
                log(LogLevels.INFO) { "config file doesn't exist, creating a new one..." }
                File(Util.TMT_HOME).mkdirs()
                Config()
            }
            file.writeText(MOSHI.toJson(config), Charsets.UTF_8)
            return config
        }
    }
}