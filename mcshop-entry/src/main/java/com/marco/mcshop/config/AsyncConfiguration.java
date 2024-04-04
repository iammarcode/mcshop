package com.marco.mcshop.config;

import com.marco.mcshop.exception.CustomAsyncExceptionHandler;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfiguration implements AsyncConfigurer {
    private final CustomAsyncExceptionHandler customAsyncExceptionHandler;

    public AsyncConfiguration(CustomAsyncExceptionHandler customAsyncExceptionHandler) {
        this.customAsyncExceptionHandler = customAsyncExceptionHandler;
    }

    // Spring uses a default SimpleAsyncTaskExecutor to actually run these methods asynchronously.
    @Bean(name = "threadPoolTaskExecutor")
    public Executor threadPoolTaskExecutor() {
        return new ThreadPoolTaskExecutor();
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return this.customAsyncExceptionHandler;
    }
}