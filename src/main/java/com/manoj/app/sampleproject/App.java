package com.manoj.app.sampleproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableScheduling
@EnableMongoAuditing
public class App 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(App.class, args);  
    }
}
