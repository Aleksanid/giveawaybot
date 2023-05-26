package ua.aleksanid.giveawaybot.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ua.aleksanid.giveawaybot.models.Giveaway;

import java.util.HashMap;
import java.util.List;

@Component
public class GamePowerClient {

    private final WebClient webClient;

    public GamePowerClient() {
        ObjectMapper objectMapper = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        Jackson2JsonEncoder encoder = new Jackson2JsonEncoder(objectMapper);
        Jackson2JsonDecoder decoder = new Jackson2JsonDecoder(objectMapper);
        ExchangeStrategies strategies = ExchangeStrategies
                .builder()
                .codecs(configure -> {
                    configure.defaultCodecs().jackson2JsonEncoder(encoder);
                    configure.defaultCodecs().jackson2JsonDecoder(decoder);
                }).build();

        this.webClient = WebClient.builder().baseUrl("https://www.gamerpower.com/api").exchangeStrategies(strategies).build();
    }


    public List<Giveaway> getGiveaways(String platform, String sortBy, String type) {
        HashMap<String, String> uriVariables = new HashMap<>();

        if (platform != null) uriVariables.put("platform", platform);
        if (sortBy != null) uriVariables.put("sort-by", sortBy);
        if (type != null) uriVariables.put("type", type);


        Mono<List<Giveaway>> response = this.webClient.get()
                .uri("/giveaways", uriVariables).exchangeToMono(httpResponse -> {
                    if (httpResponse.statusCode().equals(HttpStatus.OK)) {
                        return httpResponse.bodyToMono(new ParameterizedTypeReference<>() {
                        });
                    } else {
                        return httpResponse.createError();
                    }
                });
        return response.block();
    }
}
