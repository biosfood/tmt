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

import javafx.collections.ObservableList
import javafx.scene.control.ScrollPane
import tornadofx.*
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

class BetterListView<T>(private val list: ObservableList<T>, private val view: KClass<out Fragment>) : View() {
    init {
        list.onChange {
            root = assemble()
        }
    }

    override var root = assemble()

    private fun assemble(): ScrollPane {
        return scrollpane(true) {
            addClass("edge-to-edge")
            vbox {
                for (item in list) {
                    this += view.primaryConstructor!!.call(item)
                }
            }
        }
    }
}