package de.lukas.tmt.util.log

import java.awt.Color
import java.util.concurrent.LinkedBlockingQueue

object Log {
    private val queue = LinkedBlockingQueue<String>()

    init {
        Thread {
            while (true) {
                val current = queue.take()
                println(current)
            }
        }.start()
    }

    fun log(level: LogLevels, message: () -> String) {
        queue.add(
                "[${setForeground(level.color)}$level${setForeground(Color.WHITE)}] " +
                "[${setForeground(level.color)}${Thread.currentThread().name}${setForeground(Color.WHITE)}] " +
                            message.invoke()
        )
    }
}

fun setForeground(color: Color) : String = "\u001B[38;2;${color.ansiSequence}m"

val Color.ansiSequence: String
    get() = "$red;$green;$blue"