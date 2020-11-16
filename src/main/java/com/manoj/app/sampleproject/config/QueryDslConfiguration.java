package com.manoj.app.sampleproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.data.querydsl.binding.QuerydslBindingsFactory;
import org.springframework.data.querydsl.binding.QuerydslPredicateBuilder;


@Configuration
public class QueryDslConfiguration {

    @Autowired
    ApplicationContext applicationContext;

//    @PersistenceContext
//    private EntityManager entityManager;
//
//    @Bean
//    public JPAQueryFactory queryFactory() {
//        return new JPAQueryFactory(entityManager);
//    }

    @Lazy
    @Bean
    public QuerydslBindingsFactory querydslBindingsFactory() {
        QuerydslBindingsFactory querydslBindingsFactory = new QuerydslBindingsFactory(SimpleEntityPathResolver.INSTANCE);

        querydslBindingsFactory.setApplicationContext(applicationContext);
        return querydslBindingsFactory;
    }

    @Bean
    public QuerydslPredicateBuilder querydslPredicateBuilder() {
        return new QuerydslPredicateBuilder(new DefaultConversionService(), querydslBindingsFactory().getEntityPathResolver());
    }

}
