package com.heifetz.heifetz.enums

enum class Stops(var title: String) {
    START("Конечка"),
    MY("Шувалова д.1"),
    END("Лента");

    override fun toString(): String {
        return title
    }
}
