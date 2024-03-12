package com.marco.shop.config.properties;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "mcshop.aws")
public class AWSProperties {
    // topics
    private String customerOtpTopic;
    private String customerRegisterTopic;

    // queues
    private String customerOtpQueue;
    private String customerRegisterQueue;
}
