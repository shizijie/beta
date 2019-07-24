package com.shizijie.beta.bean.port;

import com.shizijie.beta.bean.id.IdWorker;
import com.shizijie.beta.redis.RedisService;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.Assert;

/**
 * @author shizijie
 * @version 2018-12-09 下午1:12
 */
@Configuration
public class ServicePort implements ApplicationListener<WebServerInitializedEvent> {
    @Autowired
    RedisService redisService;
    @Value("${spring.application.name}")
    String PROJECT_NAME;
    @Value("${snowFlake.redis.open}")
    boolean OPEN;
    @Value("${snowFlake.redis.key}")
    String KEY;

    private static WebServerInitializedEvent event;

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        ServicePort.event = event;
    }
    public static int getPort() {
        Assert.notNull(ServicePort.event,"实例为空，端口号获取失败");
        int port = ServicePort.event.getWebServer().getPort();
        Assert.state(port != -1, "端口号获取失败");
        return port;
    }

    @Lazy
    @Bean(destroyMethod ="destroy" )
    public IdWorker idWorker(){
        return new IdWorker(redisService,PROJECT_NAME,OPEN,KEY);
    }
}
