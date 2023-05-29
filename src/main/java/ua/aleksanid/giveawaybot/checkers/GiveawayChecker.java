package ua.aleksanid.giveawaybot.checkers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ua.aleksanid.giveawaybot.clients.GamePowerClient;
import ua.aleksanid.giveawaybot.models.Giveaway;
import ua.aleksanid.giveawaybot.services.RedisService;
import ua.aleksanid.giveawaybot.services.TelegramBotService;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Component
@EnableScheduling
public class GiveawayChecker {
    private static final Logger logger = LoggerFactory.getLogger(GiveawayChecker.class);
    private static final String TELEGRAM_MESSAGE_FORMAT = "New giveaway: {0} for {1}.\n{2}";
    private final TelegramBotService telegramBotService;
    private final RedisService redisService;
    private final GamePowerClient gamePowerClient;


    public GiveawayChecker(TelegramBotService telegramBotService, RedisService redisService, GamePowerClient gamePowerClient) {
        this.telegramBotService = telegramBotService;
        this.redisService = redisService;
        this.gamePowerClient = gamePowerClient;
    }


    @Scheduled(fixedDelay = 3600000, initialDelay = 1000)
    private void checkGiveaways() {
        logger.info("Checking for new giveaways");
        List<Giveaway> giveaways = gamePowerClient.getGiveaways("pc", "date", null);

        Date currentDate = new Date();

        String latestGiveawayCheck = redisService.getString("latestCheck");
        Date latestCheckDate = parseGiveawayDateOr(latestGiveawayCheck, new Date(0));


        List<Giveaway> newGiveaways = extractNewGiveaways(giveaways, latestCheckDate);

        if (newGiveaways.isEmpty()) {
            return;
        }
        logger.info("{} new giveaways detected", newGiveaways.size());

        notifySubscribers(newGiveaways);

        redisService.putString("latestCheck", new SimpleDateFormat(Giveaway.DATE_FORMAT).format(currentDate));
    }

    private void notifySubscribers(List<Giveaway> newGiveaways) {
        List<String> messages = newGiveaways.stream()
                .map(giveaway -> MessageFormat.format(TELEGRAM_MESSAGE_FORMAT, giveaway.getTitle(), giveaway.getPlatforms(), giveaway.getOpenGiveawayUrl()))
                .toList();

        Set<String> subscribers = redisService.getStringList("subscribers");

        subscribers.forEach(subscriber -> messages.forEach(message -> telegramBotService.sendMessage(message, subscriber)));
    }

    private List<Giveaway> extractNewGiveaways(List<Giveaway> giveaways, Date latestDate) {
        return giveaways.stream()
                .filter(giveaway -> {
                    Date publishedDate = parseGiveawayDateOr(giveaway.getPublishedDate(), null);
                    return publishedDate != null && publishedDate.after(latestDate);
                }).toList();
    }


    private Date parseGiveawayDateOr(String toParse, Date defaultValue) {

        if (toParse == null) {
            return defaultValue;
        }

        Date result;
        try {
            result = new SimpleDateFormat(Giveaway.DATE_FORMAT).parse(toParse);
        } catch (ParseException e) {
            result = defaultValue;
        }
        return result;
    }
}
