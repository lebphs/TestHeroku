package com.example.demo

import com.example.demo.model.Chat
import com.example.demo.model.Message
import com.example.demo.model.Update
import com.mashape.unirest.http.Unirest
import com.mashape.unirest.http.exceptions.UnirestException
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import java.util.logging.Level
import java.util.logging.Logger

@RestController
class TestRestService {
    private val logger: Logger = Logger.getLogger("[EchoBot]")

    companion object {
        private val API_ENDPOINT = "https://api.telegram.org/bot"
        private val START_COMMAND = "/start"
        private val ECHO_COMMAND = "/echo"
    }

    @Value("\${token}")
    lateinit var token: String

    @GetMapping
    fun hello():String{
        return "Hello word!";
    }

    @PostMapping("/\${token}")
    fun onUpdate(@RequestBody update: Update) {
        logger.log(Level.INFO, "Got update: " + update)
        if (update.message != null) {
            val chatId = update.message.chat.id
            val text = update.message.text
            when {
                text?.startsWith(START_COMMAND) == true -> onStartCommand(chatId)
                text?.startsWith(ECHO_COMMAND) == true -> onEchoCommand(chatId, text)
            }
        }
    }

    private fun onStartCommand(chatId: Long) = try {
        sendMessage(chatId, "Hello! I'm EchoBot.")
    } catch (e: UnirestException) {
        logger.log(Level.SEVERE, "Can not send START response!", e)
    }
    private fun onEchoCommand(chatId: Long, text: String) = try {
        val response = text.subSequence(ECHO_COMMAND.length, text.length).trim().toString()
        sendMessage(chatId, response)
    } catch (e: UnirestException) {
        logger.log(Level.SEVERE, "Can not send ECHO response!", e)
    }

    @Throws(UnirestException::class)
    private fun sendMessage(chatId: Long, text: String) {
        //val restTemplate = RestTemplate();
        //val message = Message(Chat(chatId), text);
        //restTemplate.exchange(API_ENDPOINT + token + "/sendMessage", HttpMethod.POST, HttpEntity(message), Update::class.java)
        Unirest.post(API_ENDPOINT + token + "/sendMessage")
                .field("chat_id", chatId)
                .field("text", text)
                .asJson()
    }
}