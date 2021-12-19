package de.lukas.tmt.util

import com.sun.javafx.collections.NonIterableChange
import javafx.collections.ObservableList
import javafx.collections.ObservableListBase
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

operator fun <E> MutableList<E>.plusAssign(item: E) {
    this.add(item)
}

fun <E, T> List<E>.forNew(operator: (E) -> T): List<T> {
    val result = mutableListOf<T>()
    forEach {
        result += operator.invoke(it)
    }
    return result
}

inline fun <reified T : Any, R> T.getPrivateProperty(name: String): R? =
    T::class
        .memberProperties
        .firstOrNull { it.name == name }
        ?.apply { isAccessible = true }
        ?.get(this) as? R

inline fun <reified T> T.callPrivateFunc(name: String, vararg args: Any?): Any =
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