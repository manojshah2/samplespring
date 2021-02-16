package com.manoj.app.sampleproject.business;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import com.manoj.app.sampleproject.config.AppAuditor;
import com.manoj.app.sampleproject.model.Board;
import com.manoj.app.sampleproject.repository.BoardRepository;
import com.manoj.app.sampleproject.web.request.BoardRequest;
import com.querydsl.core.types.Predicate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BoardService implements BaseService<Board,BoardRequest> {
	
	private final BoardRepository repository;
	private final PredicateService predicateService;	
	private final AppAuditor auditor;

	@Override
	@Transactional(readOnly=true)
	public Page<Board> getAll(MultiValueMap<String, String> params, Pageable pageable) {
		// TODO Auto-generated method stub
		Predicate predicate=predicateService.getPredicateFromParameters(params, Board.class);		
		return predicate !=null ? repository.findAll(predicate, pageable) : repository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public Board getById(String id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Board create(BoardRequest request) {
		// TODO Auto-generated method stub
		Board board=Board.builder()
				.name(request.getName())
				.users(auditor.getCurrentAuditor().get())
				.build();
		return repository.save(board);
	}

	@Override
	@Transactional
	public void update(String id, BoardRequest request) {
		// TODO Auto-generated method stub
		Board board=getById(id);
		if(board==null)
			throw new NoSuchElementException("Board #"+ id);
		
		board.setName(request.getName());		
		repository.save(board);
	}

	@Override
	@Transactional
	public void delete(String id) {
		// TODO Auto-generated method stub
		Board board=getById(id);
		if(board==null)
			throw new NoSuchElementException("Board #"+ id);
		
		repository.delete(board);
	}

	@Override
	@Transactional(readOnly=true)
	public Long count(MultiValueMap<String, String> params) {
		// TODO Auto-generated method stub
		Predicate predicate=predicateService.getPredicateFromParameters(params, Board.class);
		return predicate!=null?repository.count(predicate):repository.count();
	}
}
