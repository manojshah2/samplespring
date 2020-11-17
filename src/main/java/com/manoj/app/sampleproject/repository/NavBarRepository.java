package com.manoj.app.sampleproject.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.manoj.app.sampleproject.model.NavBar;
import com.manoj.app.sampleproject.model.QNavBar;
import com.querydsl.core.types.dsl.StringPath;

public interface NavBarRepository extends MongoRepository<NavBar,String>, QuerydslPredicateExecutor<NavBar>, QuerydslBinderCustomizer<QNavBar>{
	
	List<NavBar> findByParentId(String parentid);
	List<NavBar> findByUsers(String users);
	List<NavBar> findByParentIdAndUsers(String parentid,String users);
	
	@Override
	default void customize(QuerydslBindings bindings,QNavBar root) {
		bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
	}

}
