package com.marco.mcshop.pubsub.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marco.mcshop.config.properties.AWSProperties;
import com.marco.mcshop.model.dto.customer.CustomerRegisterDto;
import io.awspring.cloud.sns.core.SnsTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class CustomerPublisher {
    private final SnsTemplate snsTemplate;
    private final AWSProperties awsProperties;
    private final ObjectMapper objectMapper;

    @Autowired
    public CustomerPublisher(SnsTemplate snsTemplate, AWSProperties awsProperties, ObjectMapper objectMapper) {
        this.snsTemplate = snsTemplate;
        this.awsProperties = awsProperties;
        this.objectMapper = objectMapper;
    }

    public void publishOtp(String email) {
        Message<String> message = MessageBuilder.withPayload(email).build();
        snsTemplate.send(awsProperties.getCustomerOtpTopic(), message);
    }

    public void publishRegister(CustomerRegisterDto customerRegisterDto) throws JsonProcessingException {
        String customerJson = objectMapper.writeValueAsString(customerRegisterDto);
        Message<String> message = MessageBuilder.withPayload(customerJson).build();

        snsTemplate.send(awsProperties.getCustomerRegisterTopic(), message);
    }
}
