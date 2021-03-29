package com.manoj.app.sampleproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class ThreadPoolTaskSchedulerConfig {

	@Bean
	public TaskScheduler scheduler() {
		return new CustomTaskScheduler();
	}
	
	/*
	 @Bean
	    public ThreadPoolTaskScheduler threadPoolTaskScheduler(){
	        ThreadPoolTaskScheduler threadPoolTaskScheduler
	          = new ThreadPoolTaskScheduler();
	        threadPoolTaskScheduler.setPoolSize(1);
	        threadPoolTaskScheduler.setThreadNamePrefix(
	          "ThreadPoolTaskScheduler");
	        return threadPoolTaskScheduler;
	    }
	    */
	
	private class CustomTaskScheduler extends ThreadPoolTaskScheduler {
		  public CustomTaskScheduler() {
			  this.setPoolSize(1);
			  this.setThreadNamePrefix("ThreadPoolTaskScheduler");
		  }
	}
}
