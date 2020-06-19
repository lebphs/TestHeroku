package com.marivan

import com.elbekD.bot.Bot
import com.elbekD.bot.types.InlineKeyboardButton
import com.elbekD.bot.types.InlineKeyboardMarkup
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import javax.annotation.PostConstruct


@Component
class LongPollingService {


    @Value("\${token}")
    lateinit var token: String

    @Value("\${bot.name}")
    lateinit var botName: String

    @PostConstruct
    fun main() {

        val bot = Bot.createPolling(botName, token) {}

        bot.onCommand("/test") { msg, _ ->
            bot.sendMessage(msg.chat.id, "Hello World!")
        }

        bot.onCommand("/buttons"){msg, _ ->
            val inlineKeyboardButton1 = InlineKeyboardButton("Button1", callback_data = "1")
            val inlineKeyboardButton2 = InlineKeyboardButton("Button2", callback_data = "2")
            val keyboardButtonsRow1: MutableList<InlineKeyboardButton> = ArrayList()
            keyboardButtonsRow1.add(inlineKeyboardButton1)
            keyboardButtonsRow1.add(inlineKeyboardButton2)
            val inlineKeyboardList: MutableList<MutableList<InlineKeyboardButton>> = ArrayList();
            inlineKeyboardList.add(keyboardButtonsRow1);
            val markup = InlineKeyboardMarkup(inlineKeyboardList)
            bot.sendMessage(msg.chat.id,"Кнопочки", markup = markup);
        }

        bot.onCallbackQuery { msg -> bot.sendMessage(msg.message!!.chat.id, msg.data!!) }

        bot.start()
    }
}
