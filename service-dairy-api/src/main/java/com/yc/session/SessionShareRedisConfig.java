package com.yc.session;

import com.yc.common.constant.Constant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import redis.clients.jedis.JedisPoolConfig;


/**
 * session 共享配置bean
 * <p>
 * session 信息存储在header Constants.REQUEST_HEADERS_AUTH_KEY
 * <p>
 * 为session共享存储指定redis,与redis缓存区分开
 *
 * @author YC
 */

@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds=28800)
public class SessionShareRedisConfig{

    /**
     * 让Spring Session不再执行config命令
     *
     * @return
     */
    @Bean
    public static ConfigureRedisAction configureRedisAction() {
        return ConfigureRedisAction.NO_OP;
    }

    /**
     * 把sessionid放入http header中
     *
     * @return
     */
    @Bean
    public HeaderHttpSessionIdResolver headerHttpSessionIdResolver() {
        return new HeaderHttpSessionIdResolver(Constant.REQUEST_HEADERS_AUTH_KEY);
    }

    /**
     * 配置redisConnectionFactory 给session存储专用
     *
     * @return
     */
    @Primary
    @Bean(name = {"connectionFactory"})
    public RedisConnectionFactory redisConnectionFactory(@Value("${spring.redis.host}") String hostName,
                                                         @Value("${spring.redis.password}") String password,
                                                         @Value("${spring.redis.port}") int port,
                                                         @Value("${spring.redis.jedis.pool.max-idle}") int maxIdle,
                                                         @Value("${spring.redis.jedis.pool.min-idle}") int minIdle,
                                                         @Value("${spring.redis.jedis.pool.max-active}") int maxActive,
                                                         @Value("${spring.redis.jedis.pool.max-wait}") int maxWait) {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMinIdle(minIdle);
        poolConfig.setMaxTotal(maxActive);
        poolConfig.setMaxWaitMillis(maxWait);

        JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();
        redisConnectionFactory.setHostName(hostName);
        redisConnectionFactory.setPort(port);

        if(StringUtils.isNotBlank(password)){
            redisConnectionFactory.setPassword(password);
        }

        redisConnectionFactory.setPoolConfig(poolConfig);
        return redisConnectionFactory;
    }

}
