
package com.manoj.app.sampleproject.message;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;



@Configuration
public class MqConfig {

	@Autowired
	MqProperties mqProperties;

	private static final boolean NON_DURABLE = false;
	public final static String TOPIC_EXCHANGE_NAME = "amq.topic";
	public final static String TOPIC_WORK_KEY_NAME = "inTopic";
	public final static String TOPIC_MAIL_KEY_NAME = "*.mail";

	public final static String Q_WORK = "mqtt-subscription-esp8266 clientqos0";
	

	@Bean
	public Declarables topicBindings() {
		Queue topicQueue1 = new Queue(Q_WORK, NON_DURABLE);
		
		TopicExchange topicExchange = new TopicExchange(TOPIC_EXCHANGE_NAME);

		return new Declarables(topicQueue1, topicExchange,
				BindingBuilder.bind(topicQueue1).to(topicExchange).with(TOPIC_WORK_KEY_NAME));
				

	}

	@Bean
	public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
		return new MappingJackson2MessageConverter();
	}

	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(messageConverter());
		return rabbitTemplate;
	}

	@Bean
	public Jackson2JsonMessageConverter messageConverter() {
		Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
		DefaultJackson2JavaTypeMapper classMapper = new DefaultJackson2JavaTypeMapper();

		Map<String, Class<?>> idClassMapping = new HashMap<>();
		//idClassMapping.put("com.manoj.message.request.MailRequest",MailRequest.class);

		classMapper.setIdClassMapping(idClassMapping);
		converter.setClassMapper(classMapper);

		return converter;
	}

}
