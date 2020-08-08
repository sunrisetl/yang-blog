package net.chen.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Chen
 * 2020/8/7 20:32
 * 使用异步
 */
@Configuration
@EnableAsync
public class AsyncConfiguration {

    public Executor asyncExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(10);
        executor.setThreadGroupName("Ay_blog");
        executor.setQueueCapacity(30);
        executor.initialize();
        return executor;
    }
}
