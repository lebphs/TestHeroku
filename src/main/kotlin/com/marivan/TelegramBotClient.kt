package com.marivan

import com.mashape.unirest.http.Unirest
import com.mashape.unirest.http.exceptions.UnirestException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class TelegramBotClient {

    private val API_ENDPOINT = "https://api.telegram.org/bot"

    @Value("\${token}")
    lateinit var token: String


    @Throws(UnirestException::class)
    public fun sendMessage(chatId: Long, text: String) {
        Unirest.post(API_ENDPOINT + token + "/sendMessage")
                .field("chat_id", chatId)
                .field("text", text)
                .asJson()
    }
}
