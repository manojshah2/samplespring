package com.manoj.app.sampleproject.model;

import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
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
@Document(collection = "navbar")
@QueryEntity
public class NavBar extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	
	private String url;
	
	private String icon;
	
	private String parentId;
	
	@JsonInclude(Include.ALWAYS)
	private Set<String> users;
}
