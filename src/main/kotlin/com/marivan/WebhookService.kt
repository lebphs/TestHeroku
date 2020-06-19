package com.marivan

import com.marivan.entity.Categories
import com.marivan.model.Update
import com.marivan.repositories.CategoryRepository
import com.mashape.unirest.http.Unirest
import com.mashape.unirest.http.exceptions.UnirestException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.logging.Level
import java.util.logging.Logger

@RestController
class WebhookService {
    private val logger: Logger = Logger.getLogger("[EchoBot]")

    companion object {
        private val API_ENDPOINT = "https://api.telegram.org/bot"
        private val START_COMMAND = "/start"
        private val ECHO_COMMAND = "/echo"
        private val GET_ALL_CATEGORIES = "/categories"
        private val ADD_CATEGORIES = "/addcategory"
    }

    @Value("\${token}")
    lateinit var token: String

    @Autowired
    lateinit var categoryRepository: CategoryRepository

    @GetMapping
    fun hello() = categoryRepository.findAll();

    @PostMapping("/\${token}")
    fun onUpdate(@RequestBody update: Update) {
        logger.log(Level.INFO, "Got update: " + update)
        if (update.message != null) {
            val chatId = update.message.chat.id
            val text = update.message.text
            when {
                text?.startsWith(START_COMMAND) == true -> onStartCommand(chatId)
                text?.startsWith(ECHO_COMMAND) == true -> onEchoCommand(chatId, text)
                text?.startsWith(GET_ALL_CATEGORIES) == true -> getAllCategories(chatId)
                text?.startsWith(ADD_CATEGORIES) == true -> addCategory(chatId, text)
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

    private fun getAllCategories(chatId: Long) = try {
        val categories:List<Categories> = categoryRepository.findAll() as List<Categories>;
        sendMessage(chatId,"Available categories:\n" + categories.joinToString(separator = "\n"))
    } catch (e: UnirestException) {
        logger.log(Level.SEVERE, "Can not send START response!", e)
    }

    private fun addCategory(chatId: Long, text: String) = try {
        categoryRepository.save(Categories(name = text.split("_")[1].trim()))
        sendMessage(chatId, "Category successfully added!")
    } catch (e: UnirestException) {
        logger.log(Level.SEVERE, "Can not send START response!", e)
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
