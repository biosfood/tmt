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

package de.lukas.tmt.ui.util

import de.lukas.tmt.ui.UI
import javafx.util.StringConverter
import java.text.DateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

class DateConverter(private val dateFormat: DateFormat) : StringConverter<LocalDate>() {
    override fun toString(localDate: LocalDate?): String =
        dateFormat.format(Date((localDate?.toEpochDay() ?: UI.today) * UI.MILLISECONDS_PER_DAY))

    override fun fromString(string: String?): LocalDate {
        return dateFormat.parse(string).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
    }
}
