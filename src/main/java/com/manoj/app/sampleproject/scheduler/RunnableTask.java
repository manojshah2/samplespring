package com.manoj.app.sampleproject.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.manoj.app.sampleproject.business.SchTaskService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Scope("prototype")
public class RunnableTask implements Runnable {
	
	private String schTaskId;
	private SchTaskService workService;
	
	@Autowired
	public RunnableTask(String schTaskId,SchTaskService workService) {
		this.schTaskId=schTaskId;
		this.workService=workService;
	}
	
	
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		log.info("Running the given task");
		this.workService.runScheduler(this.schTaskId);
	}
}
