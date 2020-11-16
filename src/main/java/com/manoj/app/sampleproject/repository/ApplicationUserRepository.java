package com.manoj.app.sampleproject.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.manoj.app.sampleproject.model.ApplicationUser;
import com.manoj.app.sampleproject.model.QApplicationUser;
import com.querydsl.core.types.dsl.StringPath;

public interface ApplicationUserRepository extends MongoRepository<ApplicationUser,String>,QuerydslPredicateExecutor<ApplicationUser>,QuerydslBinderCustomizer<QApplicationUser> {

	List<ApplicationUser> findByUsername(String username);
	
	@Override
	default void customize(QuerydslBindings bindings,QApplicationUser root) {
		bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
	}
}
