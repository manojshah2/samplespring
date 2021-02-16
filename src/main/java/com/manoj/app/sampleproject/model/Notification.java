package com.manoj.app.sampleproject.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.querydsl.core.annotations.QueryEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "push_notification")
@QueryEntity
public class Notification extends AbstractDocument{

	private static final long serialVersionUID = 1L;
	
	private String title;
	
	private String message;
	
	private List<String> deviceId;
	
}
