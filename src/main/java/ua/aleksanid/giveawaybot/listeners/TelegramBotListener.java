package ua.aleksanid.giveawaybot.listeners;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.aleksanid.giveawaybot.services.RedisService;

import java.util.List;


public class TelegramBotListener implements UpdatesListener {
    private final static Logger logger = LoggerFactory.getLogger(TelegramBotListener.class);
    private final RedisService redisService;
    private final TelegramBot telegramBot;

    public TelegramBotListener(RedisService redisService, TelegramBot telegramBot) {
        this.redisService = redisService;
        this.telegramBot = telegramBot;
    }

    @Override
    public int process(List<Update> updates) {
        for (Update update :
                updates) {
            if (update.message() != null) {
                String text = update.message().text();
                if (text != null && text.equals("/subscribe")) {
                    Long chatId = update.message().chat().id();
                    this.addSubscription(chatId);
                    this.reply(chatId, "You are now subscribed.");
                    logger.atInfo().log("Added sub");
                }
                if (text != null && text.equals("/unsubscribe")) {
                    Long chatId = update.message().chat().id();
                    this.removeSubscription(chatId);
                    this.reply(chatId, "You are now unsubscribed.");
                    logger.atInfo().log("Removed sub");
                }
            }
        }

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void addSubscription(Long chatId) {
        this.redisService.putIntoStringSet("subscribers", String.valueOf(chatId));
    }

    private void removeSubscription(Long chatId) {
        this.redisService.deleteFromStringSet("subscribers", String.valueOf(chatId));
    }

    private void reply(Long chatId, String message) {
        telegramBot.execute(new SendMessage(chatId, message));
    }
}
