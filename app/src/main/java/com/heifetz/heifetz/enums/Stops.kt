package com.heifetz.heifetz.enums

enum class Stops(var title: String) {
    START("Конечка"),
    SHUVALOVA1("Шувалова д.1"),
    END("Лента");

    override fun toString(): String {
        return title
    }
}
