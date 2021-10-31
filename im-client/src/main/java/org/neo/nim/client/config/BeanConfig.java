package org.neo.nim.client.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import okhttp3.OkHttpClient;
import org.neo.nim.client.handle.MsgHandleCaller;
import org.neo.nim.client.service.impl.MsgCallBackListener;
import org.neo.nim.common.constant.Constants;
import org.neo.nim.common.data.construct.RingBufferWheel;
import org.neo.nim.common.protocol.NIMReqMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
@Configuration
public class BeanConfig {


    private final static Logger LOGGER = LoggerFactory.getLogger(BeanConfig.class);


    @Value("${im.user.id}")
    private long userId;

    @Value("${im.callback.thread.queue.size}")
    private int queueSize;

    @Value("${im.callback.thread.pool.size}")
    private int poolSize;


    /**
     * 创建心跳单例
     *
     * @return NIMReqMsg
     */
    @Bean(value = "heartBeat")
    public NIMReqMsg heartBeat() {
        NIMReqMsg heart = new NIMReqMsg(userId, "ping", Constants.CommandType.PING);
        return heart;
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

    /**
     * Create a callback thread pool
     *
     * @return ThreadPoolExecutor
     */
    @Bean("callBackThreadPool")
    public ThreadPoolExecutor buildCallerThread() {
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue(queueSize);
        ThreadFactory product = new ThreadFactoryBuilder()
                .setNameFormat("msg-callback-%d")
                .setDaemon(true)
                .build();
        ThreadPoolExecutor productExecutor = new ThreadPoolExecutor(poolSize, poolSize, 1, TimeUnit.MILLISECONDS, queue, product);
        return productExecutor;
    }


    @Bean("scheduledTask")
    public ScheduledExecutorService buildSchedule() {
        ThreadFactory sche = new ThreadFactoryBuilder()
                .setNameFormat("reConnect-job-%d")
                .setDaemon(true)
                .build();
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1, sche);
        return scheduledExecutorService;
    }

    /**
     * Callback bean
     *
     * @return
     */
    @Bean
    public MsgHandleCaller buildCaller() {
        MsgHandleCaller caller = new MsgHandleCaller(new MsgCallBackListener());

        return caller;
    }


    @Bean
    public RingBufferWheel bufferWheel() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        return new RingBufferWheel(executorService);
    }

}
