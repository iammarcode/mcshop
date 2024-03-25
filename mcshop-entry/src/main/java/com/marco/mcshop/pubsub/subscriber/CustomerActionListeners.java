package com.marco.mcshop.pubsub.subscriber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marco.mcshop.service.AuthenticationService;
import com.marco.mcshop.model.dto.customer.CustomerRegisterDto;
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
    private final AuthenticationService authenticationService;
    private final ObjectMapper objectMapper;

    @Autowired
    public CustomerActionListeners(AuthenticationService authenticationService, ObjectMapper objectMapper) {
        this.authenticationService = authenticationService;
        this.objectMapper = objectMapper;
    }

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
