package com.manoj.app.sampleproject.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.manoj.app.sampleproject.business.ApplicationUserService;
import com.manoj.app.sampleproject.web.request.UserRequest;
import com.manoj.web.exception.RequestValidationException;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@Api(value="UserAPI")
@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ApplicationUserController {
	
	private final ApplicationUserService service;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void signUp(@RequestBody @Valid final UserRequest request,BindingResult result) {
		if(result.hasErrors())
			throw new RequestValidationException("User #", result.getFieldErrors());
		service.create(request);
	}

}
