package com.manoj.app.sampleproject.web.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.projection.ProjectionFactory;
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

import com.manoj.app.sampleproject.business.NavBarService;
import com.manoj.app.sampleproject.model.NavBar;
import com.manoj.app.sampleproject.web.exceptions.RequestValidationException;
import com.manoj.app.sampleproject.web.request.NavBarProjection;
import com.manoj.app.sampleproject.web.request.NavBarRequest;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@Api(value="NavBar API")
@RestController
@RequestMapping("/v1/navbar")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NavBarController implements BaseController<NavBarRequest,NavBar> {

	private final NavBarService service;
	private final ProjectionFactory projectionFactory;
	
	@Override
	@GetMapping
	public Page<NavBar> getAll(@RequestParam final MultiValueMap<String, String> params,@PageableDefault(size=20) Pageable pageable) {
		// TODO Auto-generated method stub
		return service.getAll(params, pageable);
	}

	@Override
	@GetMapping("/{id}")
	public NavBar getById(@PathVariable final String id) {
		// TODO Auto-generated method stub
		return service.getById(id);
	}

	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public NavBar create(@RequestBody final @Valid NavBarRequest request, BindingResult result) {
		// TODO Auto-generated method stub
		if(result.hasErrors())
			throw new RequestValidationException("NavBar #", result.getFieldErrors());
		return service.create(request);
	}

	@Override
	@PutMapping("/{id}")
	public void update(@PathVariable final String id,@RequestBody final @Valid NavBarRequest request, BindingResult result) {
		// TODO Auto-generated method stub
		if(result.hasErrors())
			throw new RequestValidationException("NavBar #", result.getFieldErrors());
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
	
	@GetMapping("/user")
	public List<NavBarProjection> getNavBarUser(){		
		return  service.getNavBarUser().stream().map(x->projectionFactory.createProjection(NavBarProjection.class,x)).collect(Collectors.toList());
	}
	
	@PutMapping("/{id}/addUser")
	public void AddUser(@PathVariable final String id,@RequestBody @Valid final Set<String> users,BindingResult result) {
		if(result.hasErrors())
			throw new RequestValidationException("NavBar #", result.getFieldErrors());
		service.AddUsers(id, users);
	}

}
