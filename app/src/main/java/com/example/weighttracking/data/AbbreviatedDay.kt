package com.example.weighttracking.data

enum class AbbreviatedDay(val day: String) {
    MONDAY("M"),
    TUESDAY("T"),
    WEDNESDAY("W"),
    THURSDAY("Th"),
    FRIDAY("F"),
    SATURDAY("Sa"),
    SUNDAY("Su");

    companion object{
        fun fromDayOfWeek(dayOfWeek: Int): AbbreviatedDay {
            return when (dayOfWeek) {
                1 -> MONDAY
                2 -> TUESDAY
                3 -> WEDNESDAY
                4 -> THURSDAY
                5 -> FRIDAY
                6 -> SATURDAY
                else -> SUNDAY
            }
        }
    }

}