package com.marco.shop.controller;

import io.awspring.cloud.sns.core.SnsTemplate;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/sns")
@Slf4j
public class SQSController {
    @Autowired
    private SnsTemplate snsTemplate;

    @Value("${mcshop.aws.sns.email.topic.name}")
    private String emailTopic;

    @GetMapping("/publish")
    public ResponseEntity publishNotification() {
        snsTemplate.sendNotification(emailTopic, "test message", "subject");

        return new ResponseEntity(HttpStatus.OK);
    }

    @SqsListener("${mcshop.aws.sqs.email.consumer.name}")
    public void listen(String message) {
        log.info("Message received - {}", message);
    }
}
