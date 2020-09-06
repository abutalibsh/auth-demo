package com.inma.invest.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inma.invest.config.secuirty.CurrentUser;
import com.inma.invest.config.secuirty.UserPrincipal;
import com.inma.invest.dto.PageTaskDto;
import com.inma.invest.dto.TaskDto;
import com.inma.invest.service.TaskService;

@RestController
@RequestMapping("/task")
public class TaskController {

	@Autowired
	private TaskService taskService;

	@GetMapping("/list")
	public ResponseEntity<PageTaskDto> getAllUserTasks(@CurrentUser UserPrincipal currentUser,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "5") Integer pageSize,
			@RequestParam(defaultValue = "createdAt") String sortBy) {

		return ResponseEntity.ok(taskService.getAllTasksForUser(currentUser.getId(), pageNo, pageSize, sortBy));
	}

	@GetMapping("/{id}")
	public ResponseEntity<TaskDto> getUserTask(@CurrentUser UserPrincipal currentUser, @PathVariable long id) {

		return ResponseEntity.ok(taskService.getTaskForUser(currentUser.getId(), id));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@CurrentUser UserPrincipal currentUser, @PathVariable long id) {

		taskService.delete(id);

		return ResponseEntity.ok(true);
	}

	@PostMapping
	public ResponseEntity<TaskDto> save(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody TaskDto taskDto) {

		return ResponseEntity.ok(taskService.save(currentUser.getId(), taskDto));
	}

}
