package com.manoj.app.sampleproject.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.manoj.app.sampleproject.model.BoardApp;
import com.manoj.app.sampleproject.model.QBoardApp;
import com.querydsl.core.types.dsl.StringPath;

public interface BoardAppRepository
		extends MongoRepository<BoardApp, String>, QuerydslPredicateExecutor<BoardApp>, QuerydslBinderCustomizer<QBoardApp> {

	@Override
	default void customize(QuerydslBindings bindings, QBoardApp root) {
		bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
	}

}
