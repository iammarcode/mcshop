package com.marco.mcshop.config.aspect;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marco.mcshop.model.log.ControllerInfoLog;
import com.marco.mcshop.util.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;

@Aspect
@Component
@Slf4j
public class ControllerLogAspect {
    private final ObjectMapper objectMapper;
    public ControllerLogAspect(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    @Pointcut("execution(public * com.marco.mcshop.controller.*.*(..))")
    public void controllerLog() {
    }

    @Around("controllerLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        HttpServletRequest request = RequestUtil.getRequest();
        ControllerInfoLog controllerInfoLog = new ControllerInfoLog();
        String method = joinPoint.getSignature().getName();

        controllerInfoLog.setUsername(RequestUtil.getRequestUsername());
        controllerInfoLog.setIp(RequestUtil.getRequestIp(request));
        controllerInfoLog.setMethod(method);
        controllerInfoLog.setParameter(Arrays.toString(joinPoint.getArgs()));
        controllerInfoLog.setUrl(request.getRequestURL().toString());
        controllerInfoLog.setStartTime(startTime);
        Map<String, Object> mapStartingLog = objectMapper.convertValue(controllerInfoLog, new TypeReference<Map<String, Object>>() {});

        log.info("Starting access: {}", mapStartingLog);

        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();

        controllerInfoLog.setSpendTime((int) (endTime - startTime));
        controllerInfoLog.setResult(result);
        Map<String, Object> mapFinishedLog = objectMapper.convertValue(controllerInfoLog, new TypeReference<Map<String, Object>>() {});

        log.info("Finished access: {}", mapFinishedLog);

        return result;
    }
}
