package com.manoj.app.sampleproject.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.manoj.app.sampleproject.model.Board;
import com.manoj.app.sampleproject.model.QBoard;
import com.querydsl.core.types.dsl.StringPath;

public interface BoardRepository extends MongoRepository<Board,String>, QuerydslPredicateExecutor<Board>, QuerydslBinderCustomizer<QBoard>{
	
	@Override
	default void customize(QuerydslBindings bindings,QBoard root) {
		bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
	}

}
