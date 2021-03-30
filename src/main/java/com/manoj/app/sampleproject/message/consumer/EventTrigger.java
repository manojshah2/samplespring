package com.manoj.app.sampleproject.message.consumer;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manoj.app.sampleproject.business.BoardAutomationService;
import com.manoj.app.sampleproject.message.MqProducer;
import com.manoj.app.sampleproject.message.request.BoardRequest;
import com.manoj.app.sampleproject.message.request.MailRequest;
import com.manoj.app.sampleproject.util.Action;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EventTrigger {
	
	private BoardAutomationService boardAutomationService;
	private MqProducer mqProducer;
	
	@RabbitListener(queues = "Q_MAIL")
	public void processMails(MailRequest request) throws Exception {
		System.out.println(request.getFromName());
	}
	
	//@RabbitListener(bindings = @QueueBinding(value=@Queue(value="order",durable="True"),exchange=@Exchange(value="amq.topic"),key="outTopic"))
	@RabbitListener(queues="triggerQueue")
	public void processData(Action msg) {
		log.info(String.format("Received Event From Id:%s, Pin:%s, Value:%s", msg.getId(),msg.getPin(),msg.getValue()));
		boardAutomationService.getAll().forEach(automation->{
			List<Action> trigger=automation.getTriggers();
			boolean allmatch=false;			
			trigger.forEach(_trigger->{
				if(_trigger.getId().equals(msg.getId()) && _trigger.getPin().equals(msg.getPin()) && _trigger.getValue().equals(msg.getValue())) {
					List<Action> actions=automation.getActions();
					actions.forEach(_action->{
						//send each action to queue for execute
						log.info("Performing action");
						BoardRequest request=BoardRequest.builder()
								.boardId(_action.getId())
								.pin(_action.getPin())
								.pinValue(_action.getValue())
								.build();
						mqProducer.sendCommand(request, _action.getId());
					});
				}
			});
		});
	}

}
