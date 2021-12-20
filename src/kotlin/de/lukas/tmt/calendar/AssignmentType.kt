package de.lukas.tmt.calendar

enum class AssignmentType(val typeName: String) {
    TASK_DEADLINE("deadline"),
    REGULAR_ASSIGNMENT("regular"),
    DAILY_ASSIGNMENT("daily"),
    WEEKLY_ASSIGNMENT("weekly"),
    MONTHLY_ASSIGNMENT("monthly"),
    YEARLY_ASSIGNMENT("yearly"),
    ;

    override fun toString(): String = typeName

    companion object {
        val VALUES = values()

        val CHOOSABLE_VALUES = VALUES.filterNot { it == TASK_DEADLINE }
    }
}
