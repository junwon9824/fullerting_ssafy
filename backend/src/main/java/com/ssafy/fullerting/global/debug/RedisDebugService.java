package com.ssafy.fullerting.global.debug;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import jakarta.annotation.PostConstruct;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RedisDebugService {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @PostConstruct
    public void printRedisConnectionInfo() {
        if (redisConnectionFactory instanceof LettuceConnectionFactory) {
            LettuceConnectionFactory factory = (LettuceConnectionFactory) redisConnectionFactory;
            String host = factory.getHostName();
            int port = factory.getPort();
            int db = factory.getDatabase();
            System.out.println(">>> [Redis] Connected to host: " + host + ", port: " + port + ", database: " + db);
        } else {
            System.out.println(">>> [Redis] Unknown connection factory: " + redisConnectionFactory.getClass());
        }
    }


    @PostConstruct
    public void printAllRedisKeys() {
        Set<String> keys = redisTemplate.keys("*");
        System.out.println(">>> [Redis] All keys: " + keys);
    }
}
