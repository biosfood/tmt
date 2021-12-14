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

import javafx.beans.property.Property
import javafx.event.EventTarget
import tornadofx.Fragment
import tornadofx.label
import tornadofx.onChange
import tornadofx.plusAssign

class BetterLabel(operator: BetterLabel.() -> Unit) : Fragment() {
    private val elements: MutableList<() -> String> = mutableListOf()
    override var root = label()

    init {
        operator.invoke(this)
        assemble()
    }

    private fun assemble() {
        var result = ""
        for (element in elements) {
            result += element.invoke()
        }
        root.text = result
    }

    operator fun plusAssign(string: String) {
        elements += { string }
    }

    operator fun plusAssign(property: Property<*>) {
        elements += { property.value.toString() }
        property.onChange { assemble() }
    }
}

fun EventTarget.betterLabel(operator: BetterLabel.() -> Unit) {
    this += BetterLabel(operator)
}
