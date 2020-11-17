package com.manoj.app.sampleproject.business;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import com.manoj.app.sampleproject.config.AppAuditor;
import com.manoj.app.sampleproject.model.NavBar;
import com.manoj.app.sampleproject.repository.NavBarRepository;
import com.manoj.app.sampleproject.web.request.NavBarRequest;
import com.manoj.app.sampleproject.web.request.NavBarResponse;
import com.querydsl.core.types.Predicate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NavBarService implements BaseService<NavBar,NavBarRequest> {

	private final NavBarRepository repository;
	private final PredicateService predicateService;
	private final AppAuditor appAuditor;
	
	@Override
	@Transactional(readOnly=true)
	public Page<NavBar> getAll(MultiValueMap<String, String> params, Pageable pageable) {
		// TODO Auto-generated method stub
		Predicate predicate=predicateService.getPredicateFromParameters(params, NavBar.class);		
		return predicate !=null ? repository.findAll(predicate, pageable) : repository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public NavBar getById(String id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public NavBar create(NavBarRequest request) {
		// TODO Auto-generated method stub
		NavBar navBar=NavBar.builder()
				.icon(request.getIcon())
				.name(request.getName())
				.url(request.getUrl())
				.parentId(request.getParentId())
				.users(request.getUsers())
				.build();
		return repository.save(navBar);
	}

	@Transactional
	@Override	
	public void update(String id, NavBarRequest request) {
		// TODO Auto-generated method stub
		NavBar navBar=getById(id);
		if(navBar==null)
			throw new NoSuchElementException("NavBar # "+ id);
		navBar.setIcon(request.getIcon());
		navBar.setName(request.getName());
		navBar.setUrl(request.getUrl());
		navBar.setParentId(request.getParentId());
		navBar.setUsers(request.getUsers());
		
		repository.save(navBar);
	}

	@Override
	@Transactional
	public void delete(String id) {
		// TODO Auto-generated method stub
		NavBar navBar=getById(id);
		if(navBar==null)
			throw new NoSuchElementException("NavBar # "+id);
		repository.delete(navBar);
	}

	@Override
	@Transactional(readOnly=true)
	public Long count(MultiValueMap<String, String> params) {
		// 
		Predicate predicate=predicateService.getPredicateFromParameters(params, NavBar.class);
		return predicate!=null?repository.count(predicate):repository.count();
	}
	
	@Transactional(readOnly=true)
	public List<NavBarResponse> getNavBarUser(){
		String user=appAuditor.getCurrentAuditor().get();
		List<NavBar> bars=user.equals("manoj.shah")?repository.findAll():repository.findByUsers(user);		
		List<NavBar> navBars= bars.stream().filter(navbar -> navbar.getParentId()==null).collect(Collectors.toList());
		return navBars.stream().map(navBar -> CreateNavResponse(navBar,user)).collect(Collectors.toList());
	}
	
	@Transactional
	public void AddUsers(String id,Set<String> users) {
		NavBar navBar=getById(id);
		if(navBar==null)
			throw new NoSuchElementException("NavBar #" + id);
		
		Set<String> existingUsers=navBar.getUsers();
		if(existingUsers==null)
			existingUsers=users;
		else
			existingUsers.addAll(users);
		navBar.setUsers(existingUsers);
		
		repository.save(navBar);
	}
	
	private NavBarResponse CreateNavResponse(NavBar navBar,String user) {
		NavBarResponse navBarResponse=NavBarResponse.builder()
				.icon(navBar.getIcon())
				.name(navBar.getName())
				.url(navBar.getUrl())				
				.build();
		
		List<NavBar> navs=user.contains("manoj.shah")?repository.findByParentId(navBar.getId()) :repository.findByParentIdAndUsers(navBar.getId(),user);
		if(navs.size()<1) {
			return navBarResponse;
		}else {
			List<NavBarResponse> children= navs.stream().map(nav -> CreateNavResponse(nav,user)).collect(Collectors.toList());
			navBarResponse.setChildren(children);
			return navBarResponse;
		}
	}
	
	

}
