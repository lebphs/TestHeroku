package com.marivan.model

data class Update(
        val message: Message?
)
data class Message(
        val chat: Chat,
        val text: String?
)
data class Chat(
        val id: Long
)

data class Command(
        val command: String,
        val description: String
)
