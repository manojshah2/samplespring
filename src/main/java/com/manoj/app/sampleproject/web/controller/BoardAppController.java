package com.manoj.app.sampleproject.web.controller;

import java.util.List;

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

import com.manoj.app.sampleproject.business.BoardAppService;
import com.manoj.app.sampleproject.model.BoardApp;
import com.manoj.app.sampleproject.web.exceptions.RequestValidationException;
import com.manoj.app.sampleproject.web.request.ActionRequest;
import com.manoj.app.sampleproject.web.request.BoardAppRequest;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@Api(value = "BoardApp API")
@RestController
@RequestMapping("/v1/boardapp")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BoardAppController implements BaseController<BoardAppRequest, BoardApp> {

	private final BoardAppService service;

	@Override
	@GetMapping
	public Page<BoardApp> getAll(@RequestParam final MultiValueMap<String, String> params,
			@PageableDefault(size = 20) Pageable pageable) {
		return service.getAll(params, pageable);
	}

	@Override
	@GetMapping("/{id}")
	public BoardApp getById(@PathVariable final String id) {
		return service.getById(id);
	}

	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public BoardApp create(@RequestBody @Valid final BoardAppRequest request, BindingResult result) {
		if (result.hasFieldErrors())
			throw new RequestValidationException("BoardApp #", result.getFieldErrors());
		return service.create(request);
	}
	
	@PostMapping("/{id}/addaction")
	@ResponseStatus(HttpStatus.CREATED)
	public BoardApp addAction(@PathVariable final String id,@RequestBody @Valid final List<ActionRequest> actions,BindingResult result) {
		if(result.hasFieldErrors())
			throw new RequestValidationException("BoardApp #", result.getFieldErrors());
		return service.addAction(id, actions);		
	}

	@Override
	@PutMapping("/{id}")
	public void update(@PathVariable final String id, @RequestBody @Valid final BoardAppRequest request,
			BindingResult result) {
		if (result.hasFieldErrors())
			throw new RequestValidationException("BoardApp #", result.getFieldErrors());
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