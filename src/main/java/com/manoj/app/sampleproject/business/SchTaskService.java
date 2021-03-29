package com.manoj.app.sampleproject.business;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import com.manoj.app.sampleproject.config.AppAuditor;
import com.manoj.app.sampleproject.constants.AppConstants;
import com.manoj.app.sampleproject.message.MqProducer;
import com.manoj.app.sampleproject.message.request.BoardRequest;
import com.manoj.app.sampleproject.model.SchTask;
import com.manoj.app.sampleproject.repository.SchTaskRepository;
import com.manoj.app.sampleproject.scheduler.RunnableTask;
import com.manoj.app.sampleproject.util.Action;
import com.manoj.app.sampleproject.web.request.SchTaskRequest;
import com.querydsl.core.types.Predicate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SchTaskService implements BaseService<SchTask, SchTaskRequest> {

	private final SchTaskRepository repository;
	private final PredicateService predicateService;
	private final TaskScheduler taskScheduler;
	private final MqProducer mqProducer;
	private final AppAuditor auditor;

	@Override
	@Transactional(readOnly = true)
	public Page<SchTask> getAll(MultiValueMap<String, String> params, Pageable pageable) {
		// TODO Auto-generated method stub
		Predicate predicate = predicateService.getPredicateFromParameters(params, SchTask.class);
		return predicate != null ? repository.findAll(predicate, pageable) : repository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public SchTask getById(String id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public SchTask create(SchTaskRequest request) {
		// TODO Auto-generated method stub
		SchTask schTask = SchTask.builder()
				.seconds(request.getSeconds())
				.actions(request.getActions())
				.build();
		return repository.save(schTask);
	}
	
	@Transactional
	public boolean toggleTask(String id) {
		SchTask task=getById(id);
		if(task==null)
			throw new NoSuchElementException("Task #" + id);
		boolean startScheduler=!task.isActive();
		task.setActive(startScheduler);
		repository.save(task);
		return startScheduler;
	}
	
	public void addScheduler(String id) {
		SchTask task=getById(id);
		
		if(task==null)
			throw new NoSuchElementException("Task #" +id);
		
		
		ScheduledFuture<?> scheduledFuture=taskScheduler.schedule(new RunnableTask(task.getId(),this), new Date(System.currentTimeMillis() + task.getSeconds()*1000));
		AppConstants.scheduledTask.put(task.getId(), scheduledFuture);
		log.info("Task Scheduled"+ task.getSeconds());
	}
	
	public void runScheduler(String id) {
		SchTask task=getById(id);
		if(task==null)
			throw new NoSuchElementException("Task #" +id);
		
		List<Action> actions=task.getActions();
		actions.forEach(action->{			
			BoardRequest request=BoardRequest.builder()
					.boardId(action.getId())
					.pin(action.getPin())
					.pinValue(action.getValue())
					.build();
			mqProducer.sendCommand(request, id);
		});
	}
	
	public void stopScheduler(SchTask task) {
		
		AppConstants.scheduledTask.forEach((k,v)->{				
			if(k.toString().equals(task.getId())) {
				v.cancel(false);
				log.info("Task Cancelled due to Run Once From controller");
			}
		});
		
		AppConstants.scheduledTask.keySet().removeIf(key ->key.equals(task.getId()));
		task.setActive(false);
		repository.save(task);
	}

	@Override
	@Transactional
	public void update(String id, SchTaskRequest request) {
		// TODO Auto-generated method stub
		SchTask schTask = getById(id);
		if (schTask == null)
			throw new NoSuchElementException("SchTask #" + id);

		schTask.setSeconds(request.getSeconds());
		repository.save(schTask);
	}

	@Override
	@Transactional
	public void delete(String id) {
		// TODO Auto-generated method stub
		SchTask schTask = getById(id);
		if (schTask == null)
			throw new NoSuchElementException("SchTask #" + id);

		repository.delete(schTask);
	}

	@Override
	@Transactional(readOnly = true)
	public Long count(MultiValueMap<String, String> params) {
		// TODO Auto-generated method stub
		Predicate predicate = predicateService.getPredicateFromParameters(params, SchTask.class);
		return predicate != null ? repository.count(predicate) : repository.count();
	}
}
