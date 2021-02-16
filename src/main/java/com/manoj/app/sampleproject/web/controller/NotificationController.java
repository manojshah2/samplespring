package com.manoj.app.sampleproject.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.manoj.app.sampleproject.business.NotificationService;
import com.manoj.app.sampleproject.model.Notification;
import com.manoj.app.sampleproject.web.exceptions.RequestValidationException;
import com.manoj.app.sampleproject.web.request.NotificationRequest;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@Api(value = "notification API", tags = "notification API")
@RestController
@RequestMapping("/v1/notification")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NotificationController implements BaseController<NotificationRequest,Notification>{
	
	private final NotificationService service;
	
	@Override
	public Page<Notification> getAll(MultiValueMap<String, String> params, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Notification getById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Notification create(@RequestBody @Valid final NotificationRequest request, BindingResult result) {
		// TODO Auto-generated method stub
		if(result.hasFieldErrors())
			throw new RequestValidationException("Notification #", result.getFieldErrors());
		return service.create(request);
	}

	@Override
	public void update(String id, @Valid NotificationRequest request, BindingResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long count(MultiValueMap<String, String> params) {
		// TODO Auto-generated method stub
		return null;
	}

}
