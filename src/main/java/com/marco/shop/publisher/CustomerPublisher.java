package com.marco.shop.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marco.shop.config.properties.AWSProperties;
import com.marco.shop.model.customer.CustomerEntity;
import com.marco.shop.model.customer.CustomerRegisterDto;
import io.awspring.cloud.sns.core.SnsOperations;
import io.awspring.cloud.sns.core.SnsTemplate;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class CustomerPublisher {
    @Autowired
    private SnsTemplate snsTemplate;

    @Autowired
    private SnsOperations snsOperations;

    @Autowired
    private AWSProperties awsProperties;

    @Autowired
    private ObjectMapper objectMapper;

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
