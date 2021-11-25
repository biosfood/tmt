package de.lukas.tmt.ui.util

import javafx.beans.InvalidationListener
import javafx.beans.property.Property
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.collections.ObservableList
import tornadofx.onChange

class ListWrapper<U, T>(property: ObservableList<U>, callback: (ObservableList<U>) -> T) : Property<T> {
    private val changeListeners = mutableListOf<ChangeListener<in T>>()
    private var value = callback.invoke(property)

    init {
        property.onChange {
            val oldValue = value
            value = callback.invoke(property)
            for (listener in changeListeners) {
                listener.changed(this, oldValue, value)
            }
        }
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
