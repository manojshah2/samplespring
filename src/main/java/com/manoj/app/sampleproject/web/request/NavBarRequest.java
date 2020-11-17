package com.manoj.app.sampleproject.web.request;

import java.util.Set;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(description = "Request to create NavBar")
public class NavBarRequest {

	@NotNull
	private String name;
	
	private String url;
	
	@NotNull
	private String icon;
	
	private String parentId;
	
	private Set<String> users;
}
