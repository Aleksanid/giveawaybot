package ua.aleksanid.giveawaybot.services;

import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import ua.aleksanid.giveawaybot.configurations.RedisConfiguration;

import java.util.Set;

@Service
public class RedisService {
    private final RedisConfiguration redisConfiguration;
    private final JedisPool jedisPool;

    public RedisService(RedisConfiguration redisConfiguration) {
        this.redisConfiguration = redisConfiguration;

        jedisPool = new JedisPool(redisConfiguration.getHost(), redisConfiguration.getPort(), "default", redisConfiguration.getPassword());
    }

    public String getString(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.get(key);
        }
    }

    public void putIntoStringSet(String setKey, String element) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.sadd(setKey, element);
        }
    }

    public Set<String> getStringList(String listKey) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.smembers(listKey);
        }
    }

    public void putString(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.set(key, value);
        }
    }

    public void deleteFromStringSet(String setKey, String element) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.srem(setKey, element);
        }
    }
}
