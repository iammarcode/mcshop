package com.marco.shop.subscriber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marco.shop.model.customer.CustomerRegisterDto;
import com.marco.shop.service.AuthenticationService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class CustomerActionListeners {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ObjectMapper objectMapper;

    @SqsListener("${mcshop.aws.customer-otp-queue}")
    public void listeningOtp(Message message) throws JsonProcessingException {
        Map<String, String> payload = new ObjectMapper().readValue(message.getPayload().toString(), HashMap.class);

        log.info("Receive otp message: " + payload.get("Message"));

        authenticationService.requestOtp(payload.get("Message"));
    }

    @SqsListener("${mcshop.aws.customer-register-queue}")
    public void listeningRegister(Message message) throws JsonProcessingException {
        Map<String, String> payload = new ObjectMapper().readValue(message.getPayload().toString(), HashMap.class);
        CustomerRegisterDto customerRegisterDto = objectMapper.readValue(payload.get("Message"), CustomerRegisterDto.class);

        log.info("Receive register message: " + customerRegisterDto.getEmail());

        authenticationService.register(customerRegisterDto);
    }
}
