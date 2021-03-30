package com.manoj.app.sampleproject.business;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import com.manoj.app.sampleproject.config.AppAuditor;
import com.manoj.app.sampleproject.model.BoardAutomation;
import com.manoj.app.sampleproject.repository.BoardAutomationRepository;
import com.manoj.app.sampleproject.web.request.BoardAutomationRequest;
import com.querydsl.core.types.Predicate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BoardAutomationService implements BaseService<BoardAutomation, BoardAutomationRequest> {

	private final BoardAutomationRepository repository;
	private final PredicateService predicateService;
	private final AppAuditor auditor;

	@Override
	@Transactional(readOnly = true)
	public Page<BoardAutomation> getAll(MultiValueMap<String, String> params, Pageable pageable) {
		// TODO Auto-generated method stub
		Predicate predicate = predicateService.getPredicateFromParameters(params, BoardAutomation.class);
		return predicate != null ? repository.findAll(predicate, pageable) : repository.findAll(pageable);
	}
	
	@Transactional(readOnly=true)
	public List<BoardAutomation> getAll(){
		return repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public BoardAutomation getById(String id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public BoardAutomation create(BoardAutomationRequest request) {
		// TODO Auto-generated method stub
		BoardAutomation boardAutomation = BoardAutomation.builder()
				.name(request.getName())
				.triggers(request.getTriggers())
				.actions(request.getActions())
				.build();
		return repository.save(boardAutomation);
	}

	@Override
	@Transactional
	public void update(String id, BoardAutomationRequest request) {
		// TODO Auto-generated method stub
		BoardAutomation boardAutomation = getById(id);
		if (boardAutomation == null)
			throw new NoSuchElementException("BoardAutomation #" + id);

		boardAutomation.setTriggers(request.getTriggers());
		boardAutomation.setActions(request.getActions());
		repository.save(boardAutomation);
	}

	@Override
	@Transactional
	public void delete(String id) {
		// TODO Auto-generated method stub
		BoardAutomation boardAutomation = getById(id);
		if (boardAutomation == null)
			throw new NoSuchElementException("BoardAutomation #" + id);

		repository.delete(boardAutomation);
	}

	@Override
	@Transactional(readOnly = true)
	public Long count(MultiValueMap<String, String> params) {
		// TODO Auto-generated method stub
		Predicate predicate = predicateService.getPredicateFromParameters(params, BoardAutomation.class);
		return predicate != null ? repository.count(predicate) : repository.count();
	}
}
