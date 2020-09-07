package com.inma.invest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.inma.invest.entity.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {


	List<Task> findByEnabledAndExecutedAndError(boolean enabled, boolean posted, boolean error);

	List<Task> findByEnabled(boolean enabled);

	List<Task> findByExecuted(boolean enabled);

	List<Task> findByScheduled(boolean enabled);

	List<Task> findByError(boolean error);

	Page<Task> findByCreatedBy(String userId, Pageable paging);

	Optional<Task> findByIdAndCreatedBy(long taskId, String userId);

}
