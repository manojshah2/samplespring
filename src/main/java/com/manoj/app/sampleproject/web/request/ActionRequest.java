package com.manoj.app.sampleproject.web.request;

import java.util.List;

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
@ApiModel(description = "Request to create ActionRequest")
public class ActionRequest {

	@NotNull
	private String id;
	
	@NotNull
	private String name;
	
	
	private String pin;
	
	
	private String value;
	
	private List<SocketRequest> socketValue; 
}
