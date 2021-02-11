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
public class BoardRequest {
	
	private String boardId;
	
	private String pin;
	
	private String pinValue;
}
