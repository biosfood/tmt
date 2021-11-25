package de.lukas.tmt.util.log

import java.awt.Color

enum class LogLevels(
    val color: Color
) {
    FATAL(Color.RED),
    WARN(Color.YELLOW),
    INFO(Color.CYAN),
    VERBOSE(Color.GREEN),
    ;
}