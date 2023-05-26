package ua.aleksanid.giveawaybot.services;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.stereotype.Service;
import ua.aleksanid.giveawaybot.configurations.TelegramConfiguration;
import ua.aleksanid.giveawaybot.listeners.TelegramBotListener;

@Service
public class TelegramBotService {
    private final TelegramConfiguration telegramConfiguration;
    private final TelegramBot telegramBot;

    TelegramBotService(TelegramBotListener telegramBotListener, TelegramConfiguration telegramConfiguration) {
        this.telegramConfiguration = telegramConfiguration;
        telegramBot = new TelegramBot(telegramConfiguration.getBotToken());
        telegramBot.setUpdatesListener(telegramBotListener);
    }

    public void sendMessage(String message, String chatId) {
        SendResponse response = telegramBot.execute(new SendMessage(chatId, message));
    }
}
