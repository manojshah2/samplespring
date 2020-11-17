package com.manoj.app.sampleproject.business;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;

public interface BaseService<Entity,Request> {

	Page<Entity> getAll(final MultiValueMap<String, String> params, Pageable pageable);
	
	Entity getById(final String id);
	
	Entity create(Request request);
	
	void update(final String id, Request request);
	
	void delete(final String id);
	
	Long count(final MultiValueMap<String, String> params);
}
