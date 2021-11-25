package de.lukas.tmt

import de.lukas.tmt.util.log.Log
import de.lukas.tmt.util.log.LogLevels
import kotlin.system.exitProcess

fun main() {
    Log.log(LogLevels.INFO) { "test" }
    // done
    exitProcess(0)
}