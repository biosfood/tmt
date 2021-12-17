package de.lukas.tmt.util

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