package com.marco.shop.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Component
public class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... params) {
        log.error("Async Uncaught Exception: message - " + throwable.getMessage());
        log.error("Async Uncaught Exception: method name - " + method.getName());
        for (Object param : params) {
            log.error("Async Uncaught Exception: parameter - " + param);
        }
    }
}
