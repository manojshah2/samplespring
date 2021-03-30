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
@Document(collection = "BoardAutomation")
@QueryEntity
public class BoardAutomation extends AbstractDocument {

	private static final long serialVersionUID = 1L;

	//define variables here
	private String name;
	
	private List<Action> triggers;
	
	private List<Action> actions;
	
}
