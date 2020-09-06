package com.inma.invest.service;

import java.util.Objects;
import java.util.function.Function;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.inma.invest.dto.PageTaskDto;
import com.inma.invest.dto.TaskDto;
import com.inma.invest.entity.Task;
import com.inma.invest.exceptions.ResourceNotFoundException;
import com.inma.invest.repository.TaskRepository;

@Service
public class TaskService {

	@Autowired
	private TaskRepository taskRepo;

	public TaskDto getTaskForUser(String userId, long taskId) {

		Task task = taskRepo.findByIdAndCreatedBy(taskId, userId).orElseThrow(() -> new ResourceNotFoundException(""));

		TaskDto dto = new TaskDto();
		BeanUtils.copyProperties(task, dto);


		return dto;
	}

	public Task getEntityTaskForUser(String userId, long taskId) {
		Task task = taskRepo.findByIdAndCreatedBy(taskId, userId).orElseThrow(() -> new ResourceNotFoundException(""));

		return task;
	}

	public PageTaskDto getAllTasksForUser(String userId, Integer pageNo, Integer pageSize, String sortBy) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, sortBy));

		Page<Task> pagedResult = taskRepo.findByCreatedBy(userId, paging);

		System.out.println(pagedResult.getTotalElements());

		if (pagedResult.hasContent()) {

			Page<TaskDto> dtoPage = pagedResult.map(new Function<Task, TaskDto>() {
				@Override
				public TaskDto apply(Task entity) {
					TaskDto dto = new TaskDto();

					BeanUtils.copyProperties(entity, dto);

					return dto;
				}
			});

			return new PageTaskDto(dtoPage.getContent(), pagedResult.getTotalElements());
		} else {
			return new PageTaskDto();
		}
	}

	public TaskDto save(String userId, TaskDto taskDto) {

		Task task = new Task();

		if (Objects.nonNull(taskDto) && Objects.nonNull(taskDto.getId()) && taskDto.getId() != 0) {
			task = taskRepo.findById(taskDto.getId()).orElseThrow(() -> new ResourceNotFoundException("NotFound"));
		}
		BeanUtils.copyProperties(taskDto, task);

		task = taskRepo.save(task);

		taskDto.setId(task.getId());


		return taskDto;
	}

	public void delete(long id) {
		taskRepo.deleteById(id);
	}

	

}
