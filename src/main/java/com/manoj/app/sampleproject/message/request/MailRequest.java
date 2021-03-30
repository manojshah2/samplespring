package com.manoj.app.sampleproject.message.request;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MailRequest {
	
	private String fromEmail;
	
	private String fromName;
	
	private String[] toEmail;
	
	private String[] ccEmail;
	
	private String[] bccEmail;
	
	private String subject;
	
	private String mailContent;
	
	private Map<String, Object> keyValueMap;
	
	
}
