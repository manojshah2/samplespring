package com.manoj.app.sampleproject.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.manoj.app.sampleproject.model.BoardAutomation;
import com.manoj.app.sampleproject.model.QBoardAutomation;
import com.querydsl.core.types.dsl.StringPath;

public interface BoardAutomationRepository
		extends MongoRepository<BoardAutomation, String>, QuerydslPredicateExecutor<BoardAutomation>, QuerydslBinderCustomizer<QBoardAutomation> {

	@Override
	default void customize(QuerydslBindings bindings, QBoardAutomation root) {
		bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
	}

}
