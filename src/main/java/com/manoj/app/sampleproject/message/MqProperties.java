package com.manoj.app.sampleproject.message;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "messaging")
public class MqProperties {

    private String queueName;
    private String exchangeName;

}
