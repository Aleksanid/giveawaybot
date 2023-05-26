package ua.aleksanid.giveawaybot.listeners;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ua.aleksanid.giveawaybot.services.RedisService;

import java.util.List;

@Component
public class TelegramBotListener implements UpdatesListener {
    private final static Logger logger = LoggerFactory.getLogger(TelegramBotListener.class);
    private final RedisService redisService;

    public TelegramBotListener(RedisService redisService) {
        this.redisService = redisService;
    }

    @Override
    public int process(List<Update> updates) {
        for (Update update :
                updates) {
            if (update.message() != null) {
                String text = update.message().text();
                if (text != null && text.equals("Sub")) {
                    this.addSubscription(update.message().chat().id());
                    logger.atInfo().log("Added sub");
                }
            }
        }

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void addSubscription(Long id) {
        this.redisService.putIntoStringList("subscribers", String.valueOf(id));
    }
}
