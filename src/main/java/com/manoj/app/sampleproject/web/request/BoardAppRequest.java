package com.manoj.app.sampleproject.web.request;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.manoj.app.sampleproject.util.Action;

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
@ApiModel(description = "Request to create BoardAppRequest")
public class BoardAppRequest {
	
	@NotNull
	private String boardId;
	
	@NotNull
	private String name;
	
	private List<Action> actions;
}
