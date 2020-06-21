package com.marivan

import com.marivan.entity.Categories
import com.marivan.model.Command
import com.marivan.model.Update
import com.marivan.services.CategoryServiceImpl
import com.marivan.services.ExpensesServiceImpl
import com.mashape.unirest.http.exceptions.UnirestException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.logging.Level
import java.util.logging.Logger
import java.util.stream.Collectors

@RestController
class WebhookService {
    private val logger: Logger = Logger.getLogger("[EchoBot]")

    private val API_ENDPOINT = "https://api.telegram.org/bot"

    constructor(){
        setCommands()
    }

    companion object {
        private val START_COMMAND = "/start"
        private val ECHO_COMMAND = "/echo"
        private val GET_ALL_CATEGORIES = "/categories"
        private val ADD_CATEGORIES = "/addcategory"
        private val GET_EXPENSES = "/expenses"
    }

    @Value("\${token}")
    lateinit var token: String

    @Autowired
    lateinit var categoryServiceImpl: CategoryServiceImpl
    @Autowired
    lateinit var expensesServiceImpl: ExpensesServiceImpl
    @Autowired
    lateinit var telegramBotClient: TelegramBotClient;


    @GetMapping
    fun hello() = "PaymentManager working!";

    @PostMapping("/\${token}")
    fun onUpdate(@RequestBody update: Update) {
        logger.log(Level.INFO, "Got update: " + update)
        if (update.message != null) {
            val chatId = update.message.chat.id
            val text = update.message.text
            when {
                text?.startsWith(CommandsEnum.START_COMMAND.commands) == true -> onStartCommand(chatId)
                text?.startsWith(CommandsEnum.ECHO_COMMAND.commands) == true -> onEchoCommand(chatId, text)
                text?.startsWith(CommandsEnum.GET_ALL_CATEGORIES.commands) == true -> getAllCategories(chatId)
                text?.startsWith(CommandsEnum.ADD_CATEGORIES.commands) == true -> addCategory(chatId, text)
                text?.startsWith(CommandsEnum.GET_EXPENSES.commands) == true -> getExpensesForMonth(chatId)
                else -> addExpenses(chatId, text!!);
            }

        }
    }

    private fun onStartCommand(chatId: Long) = try {
        telegramBotClient.sendMessage(chatId, "Hello! I'm PaymentManagerBot.")
    } catch (e: UnirestException) {
        logger.log(Level.SEVERE, "Can not send START response!", e)
    }

    private fun onEchoCommand(chatId: Long, text: String) = try {
        val response = text.subSequence(ECHO_COMMAND.length, text.length).trim().toString()
        telegramBotClient.sendMessage(chatId, response)
    } catch (e: UnirestException) {
        logger.log(Level.SEVERE, "Can not send ECHO response!", e)
    }

    private fun getAllCategories(chatId: Long) = try {
        val categories:List<Categories> = categoryServiceImpl.getAllCategories();
        telegramBotClient.sendMessage(chatId,"Available categories:\n" + categories.joinToString(separator = "\n", transform = { c -> c.name}));
    } catch (e: UnirestException) {
        logger.log(Level.SEVERE, "Can not send CATEGORIES response!", e)
    }

    private fun addCategory(chatId: Long, text: String) = try {
        categoryServiceImpl.addCategory(text);
        telegramBotClient.sendMessage(chatId, "Category successfully added!")
    } catch (e: UnirestException) {
        logger.log(Level.SEVERE, "Can not send ADD_CATEGORY response!", e)
    }

    private fun getExpensesForMonth(chatId: Long) = try {
        telegramBotClient.sendMessage(chatId,  expensesServiceImpl.getAllExpensesForMonth().joinToString(separator = "\n", transform = { e -> e.category_id.name + " - " + e.amount}))
    } catch (e: UnirestException) {
        logger.log(Level.SEVERE, "Can not send EXPENSES response!", e)
    }

    private fun addExpenses(chatId: Long, text: String) = try {
        expensesServiceImpl.addExpenses(text);
        telegramBotClient.sendMessage(chatId, "Expenses successfully added!")
    } catch (e: UnirestException) {
        logger.log(Level.SEVERE, "Can not send ADD_EXPENSES response!", e)
    }

    private fun setCommands() = try {
        val commands:List<Command> =  CommandsEnum.values().asList().stream().map{ c ->Command(c.commands, c.name)}.collect(Collectors.toList())
        telegramBotClient.setCommands(commands)
    } catch (e: UnirestException) {
        logger.log(Level.SEVERE, "Can not send SET_COMMANDS response!", e)
    }
}
