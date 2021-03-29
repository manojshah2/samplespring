package com.manoj.app.sampleproject.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.manoj.app.sampleproject.model.SchTask;
import com.manoj.app.sampleproject.model.QSchTask;
import com.querydsl.core.types.dsl.StringPath;

public interface SchTaskRepository
		extends MongoRepository<SchTask, String>, QuerydslPredicateExecutor<SchTask>, QuerydslBinderCustomizer<QSchTask> {

	@Override
	default void customize(QuerydslBindings bindings, QSchTask root) {
		bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
	}

}
