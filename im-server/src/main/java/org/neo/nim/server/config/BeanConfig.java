package org.neo.nim.server.config;

import okhttp3.OkHttpClient;
import org.I0Itec.zkclient.ZkClient;
import org.neo.nim.common.constant.Constants;
import org.neo.nim.common.protocol.NIMReqMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author : Kyle
 * @version : 1.0
 * @email : edelweissvx@gmail.com
 * @description :
 */
@Configuration
public class BeanConfig {

    @Resource
    private AppConfiguration appConfiguration;

    @Bean
    public ZkClient buildZKClient() {
        return new ZkClient(appConfiguration.getZkAddr(), appConfiguration.getZkConnectTimeout());
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
     * Construct  heartBeat singleton
     *
     * @return
     */
    @Bean(value = "heartBeat")
    public NIMReqMsg heartBeat() {
        NIMReqMsg heart = new NIMReqMsg(0L, "pong", Constants.CommandType.PING);
        return heart;
    }
}
