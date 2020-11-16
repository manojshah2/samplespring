package com.manoj.app.sampleproject.model;

import java.util.Collection;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.querydsl.core.annotations.QueryEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "application_user")
@ToString(exclude = {"password"})
public class ApplicationUser extends AbstractDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Indexed(unique=true)
	private String username;
	
	private String password;
	
	private String emailId;
	
	private Collection<String> roles;
	
	private Boolean enabled;
	
}
