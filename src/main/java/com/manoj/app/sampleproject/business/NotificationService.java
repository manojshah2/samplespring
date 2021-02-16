package com.manoj.app.sampleproject.business;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import com.manoj.app.sampleproject.model.Notification;
import com.manoj.app.sampleproject.repository.NotificationRepository;
import com.manoj.app.sampleproject.sdk.OneSignal;
import com.manoj.app.sampleproject.web.request.NotificationRequest;
import com.querydsl.core.types.Predicate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NotificationService implements BaseService<Notification,NotificationRequest>{

	private final NotificationRepository repository;
	private final PredicateService predicateService;	
	private final OneSignal oneSignalApp;

	@Override
	@Transactional(readOnly=true)
	public Page<Notification> getAll(MultiValueMap<String, String> params, Pageable pageable) {
		// TODO Auto-generated method stub
		Predicate predicate=predicateService.getPredicateFromParameters(params, Notification.class);		
		return predicate !=null ? repository.findAll(predicate, pageable) : repository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public Notification getById(String id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Notification create(NotificationRequest request) {
		// TODO Auto-generated method stub
		Notification notification=Notification.builder()
				.deviceId(request.getDeviceId())
				.message(request.getMessage())
				.title(request.getTitle())
				.build();
		
		//send the actual notification
		oneSignalApp.SendPushNotification(notification.getTitle(), notification.getMessage(), notification.getDeviceId());
		
		return repository.save(notification);
	}

	@Override
	@Transactional
	public void update(String id, NotificationRequest request) {
		// TODO Auto-generated method stub
		Notification notification=getById(id);
		if(notification==null)
			throw new NoSuchElementException("Notification #"+ id);
		
		
	}

	@Override	
	public void delete(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Transactional(readOnly=true)
	public Long count(MultiValueMap<String, String> params) {
		// TODO Auto-generated method stub
		Predicate predicate=predicateService.getPredicateFromParameters(params, Notification.class);
		return predicate!=null?repository.count(predicate):repository.count();
	}
}
