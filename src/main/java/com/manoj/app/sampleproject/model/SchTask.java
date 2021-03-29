package com.manoj.app.sampleproject.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.manoj.app.sampleproject.util.Action;
import com.querydsl.core.annotations.QueryEntity;

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
@Document(collection = "SchTask")
@QueryEntity
public class SchTask extends AbstractDocument {

	private static final long serialVersionUID = 1L;

	private int seconds;
	
	private List<Action> actions;
	
	private boolean active;
	
}
