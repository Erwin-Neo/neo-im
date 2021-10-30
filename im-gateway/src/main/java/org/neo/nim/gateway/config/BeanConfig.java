package org.neo.nim.gateway.config;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import okhttp3.OkHttpClient;
import org.I0Itec.zkclient.ZkClient;
import org.neo.nim.common.route.algorithm.RouteHandle;
import org.neo.nim.common.route.algorithm.consistenthash.AbstractConsistentHash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
@Configuration
public class BeanConfig {

    private static Logger logger = LoggerFactory.getLogger(BeanConfig.class);

    @Resource
    private AppConfiguration appConfiguration;

    @Bean
    public ZkClient buildZKClient() {
        return new ZkClient(appConfiguration.getZkAddr(), appConfiguration.getZkConnectTimeout());
    }

    @Bean
    public LoadingCache<String, String> buildCache() {
        return CacheBuilder.newBuilder()
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String s) throws Exception {
                        return null;
                    }
                });
    }


    /**
     * Redis bean
     *
     * @param factory RedisConnectionFactory
     * @return RedisTemplate
     */
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate redisTemplate = new StringRedisTemplate(factory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }


    /**
     * http client
     *
     * @return okHttp
     */
    @Bean
    public OkHttpClient okHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);
        return builder.build();
    }

    @Bean
    public RouteHandle buildRouteHandle() throws Exception {
        String routeWay = appConfiguration.getRouteWay();
        RouteHandle routeHandle = (RouteHandle) Class.forName(routeWay).newInstance();
        logger.info("Current route algorithm is [{}]", routeHandle.getClass().getSimpleName());
        
        if (routeWay.contains("ConsistentHash")) {
            Method method = Class.forName(routeWay).getMethod("setHash", AbstractConsistentHash.class);
            AbstractConsistentHash consistentHash = (AbstractConsistentHash)
                    Class.forName(appConfiguration.getConsistentHashWay()).newInstance();
            method.invoke(routeHandle, consistentHash);
        }
        return routeHandle;

    }
}
