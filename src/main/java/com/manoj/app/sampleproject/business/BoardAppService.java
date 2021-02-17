package com.manoj.app.sampleproject.business;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import com.manoj.app.sampleproject.config.AppAuditor;
import com.manoj.app.sampleproject.model.BoardApp;
import com.manoj.app.sampleproject.repository.BoardAppRepository;
import com.manoj.app.sampleproject.util.Action;
import com.manoj.app.sampleproject.web.request.ActionRequest;
import com.manoj.app.sampleproject.web.request.BoardAppRequest;
import com.querydsl.core.types.Predicate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BoardAppService implements BaseService<BoardApp, BoardAppRequest> {

	private final BoardAppRepository repository;
	private final PredicateService predicateService;
	private final AppAuditor auditor;

	@Override
	@Transactional(readOnly = true)
	public Page<BoardApp> getAll(MultiValueMap<String, String> params, Pageable pageable) {
		// TODO Auto-generated method stub
		Predicate predicate = predicateService.getPredicateFromParameters(params, BoardApp.class);
		return predicate != null ? repository.findAll(predicate, pageable) : repository.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public BoardApp getById(String id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public BoardApp create(BoardAppRequest request) {
		// TODO Auto-generated method stub
		BoardApp boardApp = BoardApp.builder()
				.name(request.getName())
				.boardId(request.getBoardId())				
				.build();
		return repository.save(boardApp);
	}
	
	@Transactional
	public BoardApp addAction(String id, List<ActionRequest> actions) {
		BoardApp boardApp=getById(id);
		if(boardApp==null)
			throw new NoSuchElementException("BoardApp #"+id);
		
		
		List<Action> action=new LinkedList<Action>();
		actions.stream().forEach(_action->{			
			Action _a=Action.builder()
					.name(_action.getName())
					.pin(_action.getPin())
					.id(_action.getId())
					.value(_action.getValue())
					.build();
			action.add(_a);
		});
		
		final List<Action> existingAction=boardApp.getActions();
		if(existingAction!=null) {
			action.addAll(existingAction);
		}
		boardApp.setActions(action);	
		
		repository.save(boardApp);
		return boardApp;
	}

	@Override
	@Transactional
	public void update(String id, BoardAppRequest request) {
		// TODO Auto-generated method stub
		BoardApp boardApp = getById(id);
		if (boardApp == null)
			throw new NoSuchElementException("BoardApp #" + id);

		boardApp.setName(request.getName());
		repository.save(boardApp);
	}

	@Override
	@Transactional
	public void delete(String id) {
		// TODO Auto-generated method stub
		BoardApp boardApp = getById(id);
		if (boardApp == null)
			throw new NoSuchElementException("BoardApp #" + id);

		repository.delete(boardApp);
	}

	@Override
	@Transactional(readOnly = true)
	public Long count(MultiValueMap<String, String> params) {
		// TODO Auto-generated method stub
		Predicate predicate = predicateService.getPredicateFromParameters(params, BoardApp.class);
		return predicate != null ? repository.count(predicate) : repository.count();
	}
}
