package com.manoj.app.sampleproject.web.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface BaseController<Request, Response> {
	
	Page<Response> getAll(@RequestParam final MultiValueMap<String, String> params, Pageable pageable);
	
	Response getById(@PathVariable final String id);
	
	Response create(@RequestBody @Valid final Request request, BindingResult result);
	
	void update(@PathVariable final String id, @RequestBody @Valid final Request request, BindingResult result);
	
	void delete(@PathVariable final String id);
	
	Long count(@RequestParam final MultiValueMap<String, String> params);

}
