package com.manoj.app.sampleproject.util;

import java.util.List;

import com.manoj.app.sampleproject.web.request.SocketRequest;

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
public class Action {

	private String id;
	
	private String name;
	
	private String pin;
	
	private String value;
	
	private List<SocketRequest> socketValue; 
}
