package com.marivan

enum class CommandsEnum(val commands: String, val description: String) {
    START_COMMAND("/start", "Start"),
    ECHO_COMMAND("/echo", "Echo"),
    GET_ALL_CATEGORIES("/categories","Get all categories"),
    ADD_CATEGORIES("/addcategory", "Add category"),
    GET_EXPENSES("/expenses", "Get all expenses")
}
