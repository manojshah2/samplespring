package com.manoj.app.sampleproject.web.controller;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.manoj.app.sampleproject.business.SchTaskService;
import com.manoj.app.sampleproject.model.SchTask;
import com.manoj.app.sampleproject.web.exceptions.RequestValidationException;
import com.manoj.app.sampleproject.web.request.SchTaskRequest;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(value = "SchTask API")
@RestController
@RequestMapping("/v1/schtask")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SchTaskController implements BaseController<SchTaskRequest, SchTask> {

	private final SchTaskService service;

	@Override
	@GetMapping
	public Page<SchTask> getAll(@RequestParam final MultiValueMap<String, String> params,
			@PageableDefault(size = 20) Pageable pageable) {
		return service.getAll(params, pageable);
	}

	@Override
	@GetMapping("/{id}")
	public SchTask getById(@PathVariable final String id) {
		return service.getById(id);
	}

	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public SchTask create(@RequestBody @Valid final SchTaskRequest request, BindingResult result) {
		if (result.hasFieldErrors())
			throw new RequestValidationException("SchTask #", result.getFieldErrors());
		var task= service.create(request);
		log.info("Task Created : " +task.getSeconds());
		service.addScheduler(task.getId());
		return task;
	}

	@Override
	@PutMapping("/{id}")
	public void update(@PathVariable final String id, @RequestBody @Valid final SchTaskRequest request,
			BindingResult result) {
		if (result.hasFieldErrors())
			throw new RequestValidationException("SchTask #", result.getFieldErrors());
		service.update(id, request);
	}

	@Override
	@DeleteMapping("/{id}")
	public void delete(@PathVariable final String id) {
		service.delete(id);
	}

	@Override
	@GetMapping("/count")
	public Long count(@RequestParam final MultiValueMap<String, String> params) {
		return service.count(params);
	}

}