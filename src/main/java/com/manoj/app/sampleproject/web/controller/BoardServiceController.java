package com.manoj.app.sampleproject.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.manoj.app.sampleproject.message.MqProducer;
import com.manoj.app.sampleproject.message.request.BoardRequest;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@Api(value = "Board API", tags = "Board API")
@RestController
@RequestMapping("/v1/board")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BoardServiceController {
	
	private final MqProducer mqProducer;

	@GetMapping("/send")
	public void sendCommand(@RequestParam final String id,@RequestParam final String pin,@RequestParam final String pinValue) {
		BoardRequest request=BoardRequest.builder()
				.boardId(id)
				.pin(pin)
				.pinValue(pinValue)
				.build();
		mqProducer.sendCommand(request, id);
	}
}
