package ua.aleksanid.giveawaybot.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfiguration {
    @Value("${REDIS_HOST}")
    private String host;
    @Value("${REDIS_PASSWORD}")
    private String password;
    @Value("${REDIS_PORT}")
    private int port;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
