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

import de.lukas.tmt.util.getPrivateProperty
import javafx.beans.Observable
import javafx.beans.property.Property
import javafx.collections.ObservableList
import javafx.event.EventTarget
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.paint.Color
import tornadofx.*
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

class BetterListView(list: Observable, private val view: KClass<out Fragment>, private val scrollable: Boolean = true) :
    View() {
    override var root: Parent = assemble(listOf<Any>())

    private lateinit var theParent: Parent

    init {
        when (list) {
            is ObservableList<*> -> {
                list.onChange {
                    root = assemble(list)
                    clearParent()
                    if (list.isNotEmpty()) {
                        theParent += this
                    }
                }
                root = assemble(list)
            }
            is Property<*> -> {
                list.onChange {
                    root = assemble(list.value as List<*>)
                    clearParent()
                    if ((list.value as List<*>).isNotEmpty()) {
                        theParent += this
                    }
                }
                root = assemble(list.value as List<*>)
            }
            else -> throw IllegalArgumentException("type of $list cannot be displayed in a BetterListView!")
        }
    }

    private fun assemble(list: List<*>): Parent {
        val data = vbox {
            fitToParentWidth()
            addClass("edge-to-edge")
            for (item in list) {
                if (item is Property<*>) {
                    this += view.primaryConstructor!!.call(item.value)
                } else {
                    this += view.primaryConstructor!!.call(item)
                }
            }
        }
        return scrollpane(true) {
            parentProperty().onChange {
                parent?.let {
                    theParent = parent
                    if (list.isEmpty()) {
                        clearParent()
                    }
                }
            }
            style {
                baseColor = Color.TRANSPARENT
                backgroundColor += Color.TRANSPARENT
                borderWidth += box(0.px)
            }
            addClass("edge-to-edge")
            this += data
        }
    }

    private fun clearParent() = theParent.getPrivateProperty<Parent, MutableList<Node>>("children")!!.clear()
}

fun EventTarget.betterListView(list: Observable, view: KClass<out Fragment>, scrollable: Boolean = true) {
    vbox {
        this += BetterListView(list, view, scrollable)
    }
}