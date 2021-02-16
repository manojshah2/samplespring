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

import com.manoj.app.sampleproject.business.BoardService;
import com.manoj.app.sampleproject.model.Board;
import com.manoj.app.sampleproject.web.exceptions.RequestValidationException;
import com.manoj.app.sampleproject.web.request.BoardRequest;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@Api(value="Board API")
@RestController
@RequestMapping("/v1/boards")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BoardController implements BaseController<BoardRequest,Board> {

	private final BoardService service;
	
	
	@Override
	@GetMapping
	public Page<Board> getAll(@RequestParam final MultiValueMap<String, String> params,@PageableDefault(size=20) Pageable pageable) {
		// TODO Auto-generated method stub
		return service.getAll(params, pageable);
	}

	@Override
	@GetMapping("/{id}")
	public Board getById(@PathVariable final String id) {
		// TODO Auto-generated method stub
		return service.getById(id);
	}

	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Board create(@RequestBody @Valid final BoardRequest request, BindingResult result) {
		// TODO Auto-generated method stub
		if(result.hasFieldErrors())
			throw new RequestValidationException("Board #", result.getFieldErrors());
		return service.create(request);
	}

	@Override
	@PutMapping("/{id}")
	public void update(@PathVariable final String id,@RequestBody @Valid final BoardRequest request, BindingResult result) {
		// TODO Auto-generated method stub
		if(result.hasFieldErrors())
			throw new RequestValidationException("Board #", result.getFieldErrors());
		service.update(id, request);
	}

	@Override
	@DeleteMapping("/{id}")
	public void delete(@PathVariable final String id) {
		// TODO Auto-generated method stub
		service.delete(id);
	}

	@Override
	@GetMapping("/count")
	public Long count(@RequestParam final MultiValueMap<String, String> params) {
		// TODO Auto-generated method stub
		return service.count(params);
	}

}
