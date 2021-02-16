package com.manoj.app.sampleproject.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import com.manoj.app.sampleproject.model.Notification;
import com.manoj.app.sampleproject.model.QNotification;
import com.querydsl.core.types.dsl.StringPath;

public interface NotificationRepository extends MongoRepository<Notification,String>, QuerydslPredicateExecutor<Notification>, QuerydslBinderCustomizer<QNotification>{
	
	@Override
	default void customize(QuerydslBindings bindings,QNotification root) {
		bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
	}

}
