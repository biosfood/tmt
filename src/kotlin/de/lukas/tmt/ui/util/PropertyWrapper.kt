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

import javafx.beans.InvalidationListener
import javafx.beans.property.Property
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import tornadofx.ChangeListener

class PropertyWrapper<U, T>(property: Property<U>, callback: (U) -> T) : Property<T> {
    private val changeListeners = mutableListOf<ChangeListener<in T>>()
    private var value = callback.invoke(property.value)

    init {
        property.addListener(ChangeListener { _, _, _ ->
            val oldValue = value
            value = callback.invoke(property.value)
            for (listener in changeListeners) {
                listener.changed(this, oldValue, value)
            }
        })
    }

    override fun addListener(changeListener: ChangeListener<in T>?) {
        changeListener?.let {
            changeListeners += it
        }
    }

    override fun removeListener(changeListener: ChangeListener<in T>?) {
        changeListener?.let {
            changeListeners -= it
        }
    }

    override fun getValue(): T {
        return value
    }

    override fun setValue(p0: T) {
        throw RuntimeException("cannot set the value of PropertyWrapper")
    }

    override fun bind(p0: ObservableValue<out T>?) {
        TODO("Not yet implemented")
    }

    override fun unbind() {
        TODO("Not yet implemented")
    }

    override fun isBound(): Boolean {
        TODO("Not yet implemented")
    }

    override fun bindBidirectional(p0: Property<T>?) {
        TODO("Not yet implemented")
    }

    override fun unbindBidirectional(p0: Property<T>?) {
        TODO("Not yet implemented")
    }

    override fun addListener(p0: InvalidationListener?) {
        TODO("Not yet implemented")
    }

    override fun removeListener(p0: InvalidationListener?) {
        TODO("Not yet implemented")
    }

    override fun getBean(): Any {
        TODO("Not yet implemented")
    }

    override fun getName(): String {
        TODO("Not yet implemented")
    }
}